package br.com.leofarage.ckl.challenge.json.models;

import java.io.Serializable;


/**
 * JSON model based on the JSON from CKL Challenge
 * 
 * JSON item example:
 * {
        "website": "MacStories",
        "date": "05/26/2014",
        "authors": "Graham Spencer",
        "title": "Apple Debuts Two New 'Your Verse' iPad Adverts"
    }
 * */
public class ArticleJSON implements Serializable{

	private static final long serialVersionUID = -8878385250641023508L;
	
	public String website;
	public String date;
	public String authors;
	public String title;
	
}
