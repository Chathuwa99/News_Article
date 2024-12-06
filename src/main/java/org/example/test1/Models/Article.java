package org.example.test1.Models;


public class Article {

    // Fields to store article details.
    private int id;                  // Unique identifier for the article.
    private String articleName;      // Title or name of the article.
    private String articleContent;   // Main content of the article.
    private String category;         // Category to which the article belongs.


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
