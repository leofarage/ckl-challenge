package br.com.leofarage.ckl.challenge.database.DAO;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table ARTICLE.
 */
public class Article {

    private Long id;
    /** Not-null value. */
    private String website;
    /** Not-null value. */
    private String title;
    /** Not-null value. */
    private java.util.Date date;
    /** Not-null value. */
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

    /** Not-null value. */
    public String getWebsite() {
        return website;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setWebsite(String website) {
        this.website = website;
    }

    /** Not-null value. */
    public String getTitle() {
        return title;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setTitle(String title) {
        this.title = title;
    }

    /** Not-null value. */
    public java.util.Date getDate() {
        return date;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setDate(java.util.Date date) {
        this.date = date;
    }

    /** Not-null value. */
    public String getAuthors() {
        return authors;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setAuthors(String authors) {
        this.authors = authors;
    }

}
