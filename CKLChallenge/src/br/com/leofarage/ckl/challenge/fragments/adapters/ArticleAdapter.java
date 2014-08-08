package br.com.leofarage.ckl.challenge.fragments.adapters;

import java.util.ArrayList;
import java.util.List;

import br.com.leofarage.ckl.challenge.database.DAO.Article;
import br.com.leofarage.clk.challenge.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class ArticleAdapter extends BaseAdapter implements Filterable{

	private List<Article> data;
	private List<Article> filteredData;
	private static final int LAYOUT = R.layout.item_article_list;
	private Context context;
	private ArticleFilter articleFilter;
	private boolean isDataFiltered = false;

	public ArticleAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		if(isDataFiltered){
			return (filteredData != null) ? filteredData.size() : 0;
		}
		
		return (data != null) ? data.size() : 0;
	}

	@Override
	public Article getItem(int position) {
		if(isDataFiltered){
			return (filteredData != null && filteredData.size() > position) ? filteredData.get(position) : null;
		}
		
		return (data != null && data.size() > position) ? data.get(position) : null;
	}

	@Override
	public long getItemId(int position) {
		if(isDataFiltered){
			return (filteredData != null && filteredData.size() > position) ? filteredData.get(position).getId() : -1;
		}
		
		return (data != null && data.size() > position) ? data.get(position).getId() : -1;
	}

	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {
		LayoutInflater inflater = LayoutInflater.from(context);
		Article item = getItem(position);
		View view = inflater.inflate(LAYOUT, null);
		TextView title = (TextView) view.findViewById(android.R.id.text1);
		if (item != null) {
			title.setText(item.getTitle());
		}else
			title.setText("noTitle");			

		return view;
	}
	
	public void setData(List<Article> data){
		clearAdapter();
		this.data = data;
		notifyDataSetChanged();
	}
	
	/**
	 * Must call notifyDataSetChanged() for it to have any effect over the presented data
	 * */
	private void clearAdapter(){
		this.data = null;
	}

	@Override
	public Filter getFilter() {
		if(articleFilter == null)
			articleFilter = new ArticleFilter();
		return articleFilter;
	}
	
	private class ArticleFilter extends Filter{

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();
		    // We implement here the filter logic
		    if (constraint == null || constraint.length() <= 2) {
		        // No filter implemented we return all the list
		        results.values = data;
		        results.count = data.size();
		    }
		    else {
		        // We perform filtering operation
		        List<Article> newData = new ArrayList<Article>();
		        for (Article article : data)
		        	if(doesArticleContainsConstraint(article, constraint.toString().toUpperCase()))
		        		newData.add(article);
		         
		        results.values = newData;
		        results.count = newData.size();
		 
		    }
		    return results;
		}

		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			if(results.count == 0){
				isDataFiltered = false;
				notifyDataSetInvalidated();
			}else{
				filteredData = (List<Article>) results.values;
				isDataFiltered = true;
				notifyDataSetChanged();
			}
		}
		
		private boolean doesArticleContainsConstraint(Article article, String constraint){
	        boolean titleContains, authorsContains, webSiteContains;
	        titleContains = article.getTitle().toUpperCase().contains(constraint);
	        authorsContains = article.getAuthors().toUpperCase().contains(constraint);
	        webSiteContains = article.getWebsite().toUpperCase().contains(constraint);
	        
	        return titleContains || authorsContains || webSiteContains;
		}
		
	}

}
