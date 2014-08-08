package br.com.leofarage.ckl.challenge.fragments.dialogs;

import java.util.ArrayList;
import java.util.List;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import br.com.leofarage.ckl.challenge.database.DAO.ArticleDao;
import br.com.leofarage.clk.challenge.R;
import de.greenrobot.dao.Property;

public class ReorderingDialog extends DialogFragment implements OnClickListener, OnItemSelectedListener{

	private ReorderListListener listener;
	private SpinnerAdapter spinnerAdapter;
	private Spinner spinner;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View dialogView = inflater.inflate(R.layout.fragment_reorder_dialog, null);
		
		spinner = (Spinner) dialogView.findViewById(R.id.reorder_spinner);
		spinnerAdapter = new SpinnerAdapter();
		spinner.setAdapter(spinnerAdapter);
		spinner.setOnItemSelectedListener(this);
		
		dialogView.findViewById(R.id.reorder_ok_button).setOnClickListener(this);
		dialogView.findViewById(R.id.reorder_cancel_button).setOnClickListener(this);
		
		getDialog().setTitle("Choose reordering criterias");
		
		return dialogView;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.reorder_ok_button:
			RadioGroup radioGroup = (RadioGroup) getView().findViewById(R.id.reorder_radiogroup);
			int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
			boolean asc = true;
			if(checkedRadioButtonId == R.id.reorder_desc_rbutton)
				asc = false;
			Property property = spinnerAdapter.getItem(spinner.getSelectedItemPosition());
			if(listener != null)
				listener.onReordering(property, asc);
		default:
			dismiss();
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		View okButton = getView().findViewById(R.id.reorder_ok_button);
		if(position != AdapterView.INVALID_POSITION)
			okButton.setEnabled(true);
		else
			okButton.setEnabled(false);
	}
	
	public void setReorderListListener(ReorderListListener listener){
		this.listener = listener;
	}
	
	public interface ReorderListListener {
		void onReordering(Property property, boolean asc);
	}
	
	private class SpinnerAdapter extends BaseAdapter{
		
		private List<Property> data;
		
		public SpinnerAdapter() {
			data = new ArrayList<Property>();
				data.add(ArticleDao.Properties.Authors);
				data.add(ArticleDao.Properties.Date);
				data.add(ArticleDao.Properties.Title);
				data.add(ArticleDao.Properties.Website);
		}

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Property getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = LayoutInflater.from(getActivity()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
			TextView txtView = (TextView) view.findViewById(android.R.id.text1);
			txtView.setText(getItem(position).name);
			return view;
		}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	
}
