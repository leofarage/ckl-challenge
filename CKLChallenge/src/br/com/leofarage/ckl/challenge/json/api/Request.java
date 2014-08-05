package br.com.leofarage.ckl.challenge.json.api;

import retrofit.Callback;
import retrofit.http.GET;

public interface Request {

	@GET("/challenge")
	void getArticles(Callback<?> callback);
	
}
