package br.com.leofarage.ckl.challenge.activities;

import java.util.ArrayList;
import java.util.List;

import br.com.leofarage.ckl.challenge.fragments.ArticleDetailFragment;
import br.com.leofarage.ckl.challenge.fragments.ArticleListFragment;
import br.com.leofarage.ckl.challenge.fragments.ArticleListFragment.Callbacks;
import br.com.leofarage.clk.challenge.R;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

/**

 */
public class ArticleListActivity extends Activity implements ArticleListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;
	private List<ArticleListFragment> fragmentsBuffer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_list);

		if (findViewById(R.id.article_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;
			fragmentsBuffer = new ArrayList<ArticleListFragment>();

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((ArticleListFragment) getFragmentManager().findFragmentById(R.id.article_list)).setActivateOnItemClick(true);
		}

		// TODO: If exposing deep links into your app, handle intents here.
	}

	/**
	 * Callback method from {@link ArticleListFragment.Callbacks} indicating
	 * that the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(ArticleDetailFragment.ARG_ITEM_ID, id);
			ArticleDetailFragment fragment = new ArticleDetailFragment();
			fragment.setArguments(arguments);
			getFragmentManager().beginTransaction().replace(R.id.article_detail_container, fragment).commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, ArticleDetailActivity.class);
			detailIntent.putExtra(ArticleDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}
}
