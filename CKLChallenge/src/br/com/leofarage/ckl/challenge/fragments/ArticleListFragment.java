package br.com.leofarage.ckl.challenge.fragments;

import java.util.List;

import de.greenrobot.dao.Property;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import br.com.leofarage.ckl.challenge.database.CKLDaoSession;
import br.com.leofarage.ckl.challenge.database.DAO.Article;
import br.com.leofarage.ckl.challenge.fragments.adapters.ArticleAdapter;
import br.com.leofarage.ckl.challenge.fragments.adapters.ExpandableArticleAdapter;
import br.com.leofarage.ckl.challenge.fragments.dialogs.ReorderingDialog;
import br.com.leofarage.ckl.challenge.fragments.dialogs.ReorderingDialog.ReorderListListener;
import br.com.leofarage.ckl.challenge.json.Converter;
import br.com.leofarage.ckl.challenge.json.JSONRequest;
import br.com.leofarage.ckl.challenge.json.models.ArticleJSON;
import br.com.leofarage.clk.challenge.R;

/**

 */
public class ArticleListFragment extends Fragment implements OnChildClickListener, ReorderListListener{

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * activated item position. Only used on tablets.
	 */
	private static final String STATE_ACTIVATED_CHILD = "activated_position";
	private static final String STATE_ACTIVATED_GROUP = "activated_group";

	/**
	 * The fragment's current callback object, which is notified of list item
	 * clicks.
	 */
	private Callbacks mCallbacks;

	/**
	 * The current activated item position. Only used on tablets.
	 */
	private int mActivatedPosition = ListView.INVALID_POSITION;

	/**
	 * A callback interface that all activities containing this fragment must
	 * implement. This mechanism allows activities to be notified of item
	 * selections.
	 */
	public interface Callbacks {
		/**
		 * Callback for when an item has been selected.
		 */
		public void onItemSelected(long id);
	}

	/**
	 * A dummy implementation of the {@link Callbacks} interface that does
	 * nothing. Used only when this fragment is not attached to an activity.
	 */

	private ArticleAdapter adapter;
	private ExpandableArticleAdapter expandableAdapter;

	private ExpandableListView expandableListView;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ArticleListFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_article_list, null);
		expandableListView = (ExpandableListView) view.findViewById(R.id.expandable_listview);
		expandableAdapter = new ExpandableArticleAdapter(getActivity());
		expandableListView.setAdapter(expandableAdapter);
		expandableListView.setOnChildClickListener(this);
		expandableListView.setChoiceMode(ExpandableListView.CHOICE_MODE_SINGLE); 
		return view;
	}
	
	@Override
	public void onResume() {
		expandableListView.setBackgroundResource(R.color.article_list_background);
		setContent((new CKLDaoSession(getActivity())).getAllArticles());
		super.onResume();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Restore the previously serialized activated item position.
		if (savedInstanceState != null
				&& savedInstanceState.containsKey(STATE_ACTIVATED_CHILD)) {
			setActivatedPosition(savedInstanceState
					.getInt(STATE_ACTIVATED_CHILD));
		}
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Activities containing this fragment must implement its callbacks.
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException("Activity must implement fragment's callbacks.");
		}

		mCallbacks = (Callbacks) activity;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.article_list, menu);
		
		View searchMenuItem = menu.findItem(R.id.search).getActionView();
		EditText editText = (EditText) searchMenuItem.findViewById(R.id.article_filter_edittext);
		editText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				expandableAdapter.getFilter().filter(s.toString());
			}
		});
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_sync:
			System.out.println("OptionsItemSelected");
			JSONRequest jsonRequest = new JSONRequest();
			jsonRequest.getArticles(new Callback<List<ArticleJSON>>() {
				
				@Override
				public void success(List<ArticleJSON> arg0, Response arg1) {
					Converter converter = new Converter();
					CKLDaoSession cklDaoSession = new CKLDaoSession(getActivity());
					List<Article> convertListOfJSON = converter.convertListOfJSON(arg0);
					cklDaoSession.insertArticleList(convertListOfJSON);
					setContent(cklDaoSession.getAllArticles());
				}
				
				@Override
				public void failure(RetrofitError arg0) {
					System.out.println("Failure");
				}
			});
			return true;
		case R.id.reorder:
			FragmentManager fm = getActivity().getFragmentManager();
			ReorderingDialog reorderingDialog = new ReorderingDialog();
			reorderingDialog.setReorderListListener(this);
			reorderingDialog.show(fm, "ReorderDialog");
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onReordering(Property property, boolean asc) {
		CKLDaoSession cklDaoSession = new CKLDaoSession(getActivity());
		setOrderedContent(cklDaoSession.getAllArticlesOrderedBy(property, asc), property);
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		// Reset the active callbacks interface to the dummy implementation.
		mCallbacks = null;
	}

	/*@Override
	public void onListItemClick(ListView listView, View view, int position,
			long id) {
		super.onListItemClick(listView, view, position, id);

		// Notify the active callbacks interface (the activity, if the
		// fragment is attached to one) that an item has been selected.
		if(mCallbacks != null)
			mCallbacks.onItemSelected(adapter.getItem(position).getId());
	}*/
	
	@Override
	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
		System.out.println("ChildClicked!");
		v.setActivated(true);
		if(mCallbacks != null){
			mCallbacks.onItemSelected(expandableAdapter.getChildId(groupPosition, childPosition));
		}
		return false;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mActivatedPosition != ExpandableListView.INVALID_POSITION) {
			// Serialize and persist the activated item position.
			outState.putInt(STATE_ACTIVATED_CHILD, mActivatedPosition);
		}
	}

	public void setContent(List<Article> articles){
		if(expandableAdapter != null){
			expandableAdapter.setOrderByProperty(null);
			expandableAdapter.setData(articles);
			expandAllGroups();
		}
	}
	
	public void setOrderedContent(List<Article> articles, Property property){
		if(expandableAdapter != null){
			expandableAdapter.setOrderByProperty(property);
			expandableAdapter.setData(articles);
			expandAllGroups();
		}
	}
	
	private void expandAllGroups(){
		int groupCount = expandableAdapter.getGroupCount();
		for(int i = 0; i < groupCount; i++)
			expandableListView.expandGroup(i);
	}
	
	/**
	 * Turns on activate-on-click mode. When this mode is on, list items will be
	 * given the 'activated' state when touched.
	 */
	public void setActivateOnItemClick(boolean activateOnItemClick) {
		// When setting CHOICE_MODE_SINGLE, ListView will automatically
		// give items the 'activated' state when touched.
		expandableListView.setChoiceMode(activateOnItemClick ? ListView.CHOICE_MODE_SINGLE : ListView.CHOICE_MODE_NONE);
	}

	private void setActivatedPosition(int position) {
		if (position == ExpandableListView.INVALID_POSITION) {
			expandableListView.setItemChecked(mActivatedPosition, false);
		} else {
			expandableListView.setItemChecked(position, true);
		}

		mActivatedPosition = position;
	}
}
