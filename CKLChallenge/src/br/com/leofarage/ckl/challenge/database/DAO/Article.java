package br.com.leofarage.ckl.challenge.database.DAO;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table ARTICLE.
 */
public class Article {

    private Long id;
    private String website;
    private String title;
    private java.util.Date date;
    private String authors;

    public Article() {
    }

    public Article(Long id) {
        this.id = id;
    }

    public Article(Long id, String website, String title, java.util.Date date, String authors) {
        this.id = id;
        this.website = website;
        this.title = title;
        this.date = date;
        this.authors = authors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public java.util.Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

}