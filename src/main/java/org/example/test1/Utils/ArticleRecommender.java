package org.example.test1.Utils;

import org.example.test1.Models.Article;
import org.example.test1.Utils.DatabaseConnection;
import org.example.test1.Utils.SessionManager;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class ArticleRecommender {

    // TF-IDF based feature extraction
    private static Map<String, Double> calculateTFIDF(String document, Map<String, Double> idfScores) {
        // Tokenize and calculate term frequency
        String[] tokens = document.toLowerCase()
                .replaceAll("[^a-zA-Z\\s]", "")
                .split("\\s+");

        Map<String, Integer> termFrequency = new HashMap<>();
        for (String token : tokens) {
            termFrequency.put(token, termFrequency.getOrDefault(token, 0) + 1);
        }

        // Calculate TF-IDF
        Map<String, Double> tfidfScores = new HashMap<>();
        for (Map.Entry<String, Integer> entry : termFrequency.entrySet()) {
            double tf = 1 + Math.log(entry.getValue());
            double idf = idfScores.getOrDefault(entry.getKey(), 1.0);
            tfidfScores.put(entry.getKey(), tf * idf);
        }

        return tfidfScores;
    }

    // Precompute global IDF scores
    private static Map<String, Double> calculateGlobalIDF(List<Article> articles) {
        Map<String, Integer> documentFrequency = new HashMap<>();
        int totalDocuments = articles.size();

        // Count document frequency for each term
        for (Article article : articles) {
            Set<String> uniqueTerms = Arrays.stream(
                    article.getArticleContent().toLowerCase()
                            .replaceAll("[^a-zA-Z\\s]", "")
                            .split("\\s+")
            ).collect(Collectors.toSet());

            for (String term : uniqueTerms) {
                documentFrequency.put(term, documentFrequency.getOrDefault(term, 0) + 1);
            }
        }

        // Calculate IDF
        Map<String, Double> idfScores = new HashMap<>();
        for (Map.Entry<String, Integer> entry : documentFrequency.entrySet()) {
            idfScores.put(entry.getKey(),
                    Math.log((double) totalDocuments / (entry.getValue() + 1)) + 1
            );
        }

        return idfScores;
    }

    // Cosine similarity calculation
    private static double cosineSimilarity(Map<String, Double> vector1, Map<String, Double> vector2) {
        // Combine unique keys from both vectors
        Set<String> allTerms = new HashSet<>(vector1.keySet());
        allTerms.addAll(vector2.keySet());

        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        for (String term : allTerms) {
            double valA = vector1.getOrDefault(term, 0.0);
            double valB = vector2.getOrDefault(term, 0.0);

            dotProduct += valA * valB;
            normA += valA * valA;
            normB += valB * valB;
        }

        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB) + 1e-10);
    }

    // Main recommendation method
    public static List<Article> recommendArticles(int limit) {
        try {
            // Get all articles and current user's interactions
            List<Article> allArticles = DatabaseConnection.getAllArticles();
            int userId = SessionManager.getLoggedUserId();

            // Precompute global IDF scores
            Map<String, Double> globalIdfScores = calculateGlobalIDF(allArticles);

            // Get user's past interactions
            List<Integer> likedArticleIds = DatabaseConnection.getUserInteractionArticles(userId, "LIKE");
            List<Integer> readArticleIds = DatabaseConnection.getUserInteractionArticles(userId, "READ");

            // Combine interactions with more weight on liked articles
            Map<Integer, Double> articleScores = new HashMap<>();

            for (Article candidate : allArticles) {
                // Skip articles user has already interacted with
                if (likedArticleIds.contains(candidate.getId()) ||
                        readArticleIds.contains(candidate.getId())) {
                    continue;
                }

                double maxSimilarity = 0.0;

                // Compare candidate with liked articles
                for (int likedArticleId : likedArticleIds) {
                    Article likedArticle = DatabaseConnection.getArticleById(likedArticleId);

                    Map<String, Double> candidateTfidf = calculateTFIDF(
                            candidate.getArticleContent(),
                            globalIdfScores
                    );

                    Map<String, Double> likedTfidf = calculateTFIDF(
                            likedArticle.getArticleContent(),
                            globalIdfScores
                    );

                    double similarity = cosineSimilarity(candidateTfidf, likedTfidf);
                    maxSimilarity = Math.max(maxSimilarity, similarity);
                }

                articleScores.put(candidate.getId(), maxSimilarity);
            }

            // Sort and return top recommendations
            return articleScores.entrySet().stream()
                    .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                    .limit(limit)
                    .map(entry -> {
                        try {
                            return DatabaseConnection.getArticleById(entry.getKey());
                        } catch (SQLException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}