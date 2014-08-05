package br.com.leofarage.ckl.challenge.json;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.com.leofarage.ckl.challenge.database.DAO.Article;
import br.com.leofarage.ckl.challenge.json.models.ArticleJSON;

public class Converter {

	public static Article convertArticleFromJSONToDAO(ArticleJSON jsonArticle){
		Article daoArticle = new Article();
		daoArticle.setAuthors(jsonArticle.authors);
		daoArticle.setTitle(jsonArticle.title);
		daoArticle.setWebsite(jsonArticle.website);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try
        {
            Date date = simpleDateFormat.parse(jsonArticle.date);
            System.out.println("date : "+simpleDateFormat.format(date));
            daoArticle.setDate(date);
        }catch (ParseException ex){
        	daoArticle.setDate(new Date());
        }
        
        return daoArticle;
	}
	
}
