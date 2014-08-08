package br.com.leofarage.ckl.challenge.DAO.generator;
import java.io.IOException;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Index;
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

	public static void main(String[] args) {
		
		Schema schema = new Schema(1, "br.com.leofarage.ckl.challenge.database.DAO");
		
		article = schema.addEntity("Article");
			article.addIdProperty();
			Property webSiteProperty = article.addStringProperty("website").unique().notNull().getProperty();
			Property titleProperty = article.addStringProperty("title").unique().notNull().getProperty();
			article.addDateProperty("date").notNull();
			Property authorsProperty = article.addStringProperty("authors").unique().notNull().getProperty();
			Index index = new Index();
			index.addProperty(authorsProperty);
			index.addProperty(titleProperty);
			index.addProperty(webSiteProperty);
			article.addIndex(index);
		
		extracted(schema);
		
	}
	
	private static void extracted(Schema schema) {
		DaoGenerator daoGenerator;
		try {
			daoGenerator = new DaoGenerator();
			daoGenerator.generateAll(schema, "../CKLChallenge/src");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(NoClassDefFoundError e){
			e.printStackTrace();
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
