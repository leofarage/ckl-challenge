package br.com.leofarage.ckl.challenge.database;

import java.util.List;

import de.greenrobot.dao.Property;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import br.com.leofarage.ckl.challenge.database.DAO.Article;
import br.com.leofarage.ckl.challenge.database.DAO.ArticleDao;
import br.com.leofarage.ckl.challenge.database.DAO.ArticleDao.Properties;
import br.com.leofarage.ckl.challenge.database.DAO.DaoMaster;
import br.com.leofarage.ckl.challenge.database.DAO.DaoMaster.DevOpenHelper;
import br.com.leofarage.ckl.challenge.database.DAO.DaoSession;

public class CKLDaoSession{
	private DaoSession daoSession;
	private Context context;

	public CKLDaoSession(Context context) {
		this.context = context;
	}
	
	private DaoSession getDaoSession() {
		if (this.daoSession == null) {
			DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "ckl-challenge.db", null);
			SQLiteDatabase db = helper.getWritableDatabase();
			DaoMaster daoMaster = new DaoMaster(db);
			daoSession = daoMaster.newSession();
		}
		return daoSession;
	}
	
	public void insertArticleList(List<Article> articles){
		ArticleDao articleDao = getDaoSession().getArticleDao();
		long containedId;
		for (Article article : articles) {
			if((containedId = containsArticle(article)) >= 0){
				article.setId(containedId);
				articleDao.update(article);
			}else
				articleDao.insert(article);
		}
	}
	
	public List<Article> getAllArticles(){
		ArticleDao articleDao = getDaoSession().getArticleDao();
		return articleDao.queryBuilder().orderAsc(ArticleDao.Properties.Website).list();
	}
	
	public List<Article> getAllArticlesOrderedBy(Property property, boolean asc){
		ArticleDao articleDao = getDaoSession().getArticleDao();
		if(asc)
			return articleDao.queryBuilder().orderAsc(property).list();
		else
			return articleDao.queryBuilder().orderDesc(property).list();
	}
	
	public Article getArticle(long id){
		ArticleDao articleDao = getDaoSession().getArticleDao();
		return articleDao.queryBuilder().where(ArticleDao.Properties.Id.eq(id)).unique();
	}
	
	private long containsArticle(Article article){
		ArticleDao articleDao = getDaoSession().getArticleDao();
		Article unique = articleDao.queryBuilder().where(Properties.Authors.eq(article.getAuthors()), Properties.Title.eq(article.getTitle()), Properties.Website.eq(article.getWebsite())).unique();
		if(unique != null)
			return unique.getId();
		else
			return -1;
	}
}
