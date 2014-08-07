package br.com.leofarage.ckl.challenge.fragments;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import br.com.leofarage.ckl.challenge.activities.ArticleDetailActivity;
import br.com.leofarage.ckl.challenge.activities.ArticleListActivity;
import br.com.leofarage.ckl.challenge.database.CKLDaoSession;
import br.com.leofarage.ckl.challenge.database.DAO.Article;
import br.com.leofarage.clk.challenge.R;

/**
 * A fragment representing a single Article detail screen. This fragment is
 * either contained in a {@link ArticleListActivity} in two-pane mode (on
 * tablets) or a {@link ArticleDetailActivity} on handsets.
 */
public class ArticleDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The dummy content this fragment is presenting.
	 */
	private Article mItem;

	private long idArticle;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ArticleDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			idArticle = getArguments().getLong(ARG_ITEM_ID);
			CKLDaoSession cklDaoSession = new CKLDaoSession(getActivity());
			mItem = cklDaoSession.getArticle(idArticle);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_article_detail,
				container, false);

		// Show the dummy content as text in a TextView.
		if (mItem != null) {
			((TextView) rootView.findViewById(R.id.article_title)).setText(mItem.getTitle());
			((TextView) rootView.findViewById(R.id.article_date)).setText(getActivity().getString(R.string.article_detail_date, getFormattedDate(mItem.getDate())));
			((TextView) rootView.findViewById(R.id.article_website_authors)).setText(getActivity().getString(R.string.article_website_authors, mItem.getAuthors(), mItem.getWebsite()));
		}

		return rootView;
	}
	
	//TODO: change to long for retrieving the article's ID
	public long getFragmentId(){
		return idArticle;
	}
	
	public String getFormattedDate(Date date){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}
}
