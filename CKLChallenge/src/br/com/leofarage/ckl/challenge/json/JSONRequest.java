package br.com.leofarage.ckl.challenge.json;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import br.com.leofarage.ckl.challenge.json.api.Request;
import br.com.leofarage.ckl.challenge.json.models.ArticleJSON;

public class JSONRequest {

	private static final String END_POINT = "http://ckl.io";
	private String customIp;
	private String customPort;

	public JSONRequest(){
		
	}
	
	public void getArticles(Callback<List<ArticleJSON>> callback){
		Request request = getRequest();
		request.getArticles(callback);
	}

	private Request getRequest() {
		String servidor = END_POINT;
		if(customIp != null){
			servidor = "http://"+customIp;
			if(customPort != null)
				servidor += ":"+customPort;
		}
		RestAdapter restAdapter = new RestAdapter.Builder()
		.setEndpoint(servidor)
		.setLogLevel(LogLevel.FULL)
		.build();
		
		Request rsqt = restAdapter.create(Request.class);
		return rsqt;
	}
	
}
