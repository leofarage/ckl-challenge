package br.com.leofarage.ckl.challenge.database;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import br.com.leofarage.ckl.challenge.database.DAO.Article;
import br.com.leofarage.ckl.challenge.database.DAO.ArticleDao;
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
			DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "hemoterapia-db", null);
			SQLiteDatabase db = helper.getWritableDatabase();
			DaoMaster daoMaster = new DaoMaster(db);
			daoSession = daoMaster.newSession();
		}
		return daoSession;
	}
	
	public void insertArticleList(List<Article> articles){
		ArticleDao articleDao = getDaoSession().getArticleDao();
		articleDao.insertOrReplaceInTx(articles);
	}
	
	public List<Article> getAllArticles(){
		ArticleDao articleDao = getDaoSession().getArticleDao();
		return articleDao.queryBuilder().list();
	}
	
	public Article getArticle(long id){
		ArticleDao articleDao = getDaoSession().getArticleDao();
		return articleDao.queryBuilder().where(ArticleDao.Properties.Id.eq(id)).unique();
	}
}