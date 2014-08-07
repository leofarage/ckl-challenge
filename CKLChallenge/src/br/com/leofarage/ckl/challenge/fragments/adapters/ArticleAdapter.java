package br.com.leofarage.ckl.challenge.fragments.adapters;

import java.util.List;

import br.com.leofarage.ckl.challenge.database.DAO.Article;
import br.com.leofarage.clk.challenge.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ArticleAdapter extends BaseAdapter {

	private List<Article> data;
	private static final int LAYOUT = R.layout.item_article_list;
	private Context context;

	public ArticleAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		return (data != null) ? data.size() : 0;
	}

	@Override
	public Article getItem(int position) {
		return (data != null && data.size() > position) ? data.get(position) : null;
	}

	@Override
	public long getItemId(int position) {
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

}
