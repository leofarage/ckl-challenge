package br.com.leofarage.ckl.challenge.fragments.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import br.com.leofarage.ckl.challenge.database.DAO.Article;
import br.com.leofarage.clk.challenge.R;

public class ExpandableArticleAdapter extends BaseExpandableListAdapter implements Filterable{

	private static final int ITEM_LAYOUT = R.layout.item_article_list;
	private static final int GROUP_LAYOUT = R.layout.item_article_list_group;
	
	private List<Article> data;
	private List<Article> filteredData;
	private Map<String, List<Article>> groups;
	private List<String> groupsKey;
	private Context context;
	private ArticleFilter articleFilter;
	private boolean isDataFiltered = false;
	
	public ExpandableArticleAdapter(Context context) {
		this.context = context;
		groups = new HashMap<String, List<Article>>();
		groupsKey = new ArrayList<String>();
	}
	
	@Override
	public int getGroupCount() {
		return groups.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		if(groups != null && groups.size() > groupPosition){
			String key = groupsKey.get(groupPosition);
			return groups.get(key).size();
		}
		return  0;
	}

	@Override
	public List<Article> getGroup(int groupPosition) {
		if(groups != null && groups.size() > groupPosition){
			String key = groupsKey.get(groupPosition);
			return groups.get(key);
		}
		return null;
	}

	@Override
	public Article getChild(int groupPosition, int childPosition) {
		if(groups != null && groups.size() > groupPosition){
			String key = groupsKey.get(groupPosition);
			List<Article> childs = groups.get(key);
			if(childs != null && childs.size() > childPosition)
				return childs.get(childPosition);
		}
		return null;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		Article child = getChild(groupPosition, childPosition);
		if(child != null)
			return child.getId();
		return -1;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,	View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		TextView view = (TextView) inflater.inflate(GROUP_LAYOUT, null);
		view.setText(groupsKey.get(groupPosition));
		return view;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,	boolean isLastChild, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		Article item = getChild(groupPosition, childPosition);
		View view = inflater.inflate(ITEM_LAYOUT, null);
		TextView title = (TextView) view.findViewById(android.R.id.text1);
		if (item != null) {
			title.setText(item.getTitle());
		}else
			title.setText("noTitle");			

		return view;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

	@Override
	public void notifyDataSetChanged() {
		if(isDataFiltered)
			organizeDataIntoGroups(filteredData);
		else
			organizeDataIntoGroups(data);
		super.notifyDataSetChanged();
	}
	
	@Override
	public Filter getFilter() {
		if(articleFilter == null)
			articleFilter = new ArticleFilter();
		return articleFilter;
	}
	
	public void setData(List<Article> articles){
		this.data = articles;
		notifyDataSetChanged();
	}
	
	private void organizeDataIntoGroups(List<Article> articles){
		groups.clear();
		groupsKey.clear();
		for (Article article : articles) {
			String groupKey = article.getWebsite();
			if(!groups.containsKey(groupKey)){
				groups.put(groupKey, new ArrayList<Article>());
				groupsKey.add(groupKey);
			}
			groups.get(groupKey).add(article);
		}
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
