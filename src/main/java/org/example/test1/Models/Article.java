package org.example.test1.Models;

public class Article {
    private int id;
    private String articleName;
    private String articleContent;
    private String category;

    public Article(int id, String articleName, String articleContent, String category) {
        this.id = id;
        this.articleName = articleName;
        this.articleContent = articleContent;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getArticleName() {
        return articleName;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public String getCategory() {
        return category;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
