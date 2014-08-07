package br.com.leofarage.ckl.challenge.json.api;

import java.util.List;

import br.com.leofarage.ckl.challenge.json.models.ArticleJSON;

import retrofit.Callback;
import retrofit.http.GET;

public interface Request {

	@GET("/challenge")
	void getArticles(Callback<List<ArticleJSON>> callback);
	
}
