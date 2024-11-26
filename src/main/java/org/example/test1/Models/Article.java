package org.example.test1.Models;

public class Article {
    private int id;
    private String articleName;
    private String articleContent;

    public Article(int id, String articleName, String articleContent) {
        this.id = id;
        this.articleName = articleName;
        this.articleContent = articleContent;
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
}
