package br.com.leofarage.ckl.challenge.DAO.generator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

/**
 * DAO based on the JSON from CKL Challenge
 * 
 * JSON item example:
 * {
        "website": "MacStories",
        "date": "05/26/2014",
        "authors": "Graham Spencer",
        "title": "Apple Debuts Two New 'Your Verse' iPad Adverts"
    }
 * */
public class CKL_DAOGenerator {

	private static Entity article;
	private static Entity authors;

	public static void main(String[] args) {
		
		Schema schema = new Schema(1, "br.com.leofarage.ckl.challenge.database.DAO");
		
		article = schema.addEntity("Article");
			article.addIdProperty();
			article.addStringProperty("website");
			article.addStringProperty("title");
			article.addDateProperty("date");
			
		authors = schema.addEntity("Authors");
			authors.addIdProperty();
			Property articleId = authors.addLongProperty("idArticle").notNull().getProperty();
			authors.addStringProperty("name").notNull();
			
		authors.addToOne(article, articleId);
		
	}
	
}
