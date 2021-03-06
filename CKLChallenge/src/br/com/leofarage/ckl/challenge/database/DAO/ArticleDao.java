package br.com.leofarage.ckl.challenge.database.DAO;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import br.com.leofarage.ckl.challenge.database.DAO.Article;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table ARTICLE.
*/
public class ArticleDao extends AbstractDao<Article, Long> {

    public static final String TABLENAME = "ARTICLE";

    /**
     * Properties of entity Article.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Website = new Property(1, String.class, "website", false, "WEBSITE");
        public final static Property Title = new Property(2, String.class, "title", false, "TITLE");
        public final static Property Date = new Property(3, java.util.Date.class, "date", false, "DATE");
        public final static Property Authors = new Property(4, String.class, "authors", false, "AUTHORS");
    };


    public ArticleDao(DaoConfig config) {
        super(config);
    }
    
    public ArticleDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'ARTICLE' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'WEBSITE' TEXT NOT NULL UNIQUE ," + // 1: website
                "'TITLE' TEXT NOT NULL UNIQUE ," + // 2: title
                "'DATE' INTEGER NOT NULL ," + // 3: date
                "'AUTHORS' TEXT NOT NULL UNIQUE );"); // 4: authors
        // Add Indexes
        db.execSQL("CREATE INDEX " + constraint + "IDX_ARTICLE_AUTHORS_TITLE_WEBSITE ON ARTICLE" +
                " (AUTHORS,TITLE,WEBSITE);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'ARTICLE'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Article entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getWebsite());
        stmt.bindString(3, entity.getTitle());
        stmt.bindLong(4, entity.getDate().getTime());
        stmt.bindString(5, entity.getAuthors());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Article readEntity(Cursor cursor, int offset) {
        Article entity = new Article( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // website
            cursor.getString(offset + 2), // title
            new java.util.Date(cursor.getLong(offset + 3)), // date
            cursor.getString(offset + 4) // authors
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Article entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setWebsite(cursor.getString(offset + 1));
        entity.setTitle(cursor.getString(offset + 2));
        entity.setDate(new java.util.Date(cursor.getLong(offset + 3)));
        entity.setAuthors(cursor.getString(offset + 4));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Article entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Article entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
