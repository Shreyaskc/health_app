package com.app.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.hstapp.R;
import com.app.model.ProviderSearch;
import com.app.tasks.ProviderCategoryListTask;

import com.app.utils.PreferenceManager;

/**
 * This fragment is for the ProviderList details
 */
public class ProviderListFragment extends Fragment  {

	Context context;
	//public GoogleMap googleMap;
	ListView listFindProvider ;
	OnProviderFragmentClick onProviderFragmentClick;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.find_provider,
				container, false);
		context = getActivity();
		RelativeLayout findProviderLayout = (RelativeLayout)view.findViewById(R.id.findProviderLayout);
/*		findProviderLayout.setBackgroundColor(Color.parseColor(PreferenceManager
				.getBackgroudColor(getActivity())));*/
		TextView policy = (TextView)view.findViewById(R.id.policy);
		TextView txtTermsOfUse = (TextView)view.findViewById(R.id.txtTermsOfUse);
		TextView txtPrivacyPolicy = (TextView)view.findViewById(R.id.txtPrivacyPolicy);
		TextView txtSwitch = (TextView)view.findViewById(R.id.txtSwitch);
		/*policy.setTextColor(Color.parseColor(PreferenceManager
				.getTextColor(getActivity())));
		txtTermsOfUse.setTextColor(Color.parseColor(PreferenceManager
				.getTextColor(getActivity())));
		txtPrivacyPolicy.setTextColor(Color.parseColor(PreferenceManager
				.getTextColor(getActivity())));
		
		txtSwitch.setTextColor(Color.parseColor(PreferenceManager
				.getTextColor(getActivity())));*/
		View verticalLine = (View)view.findViewById(R.id.verticalLine);
		View verticalLine1 = (View)view.findViewById(R.id.verticalLine1);
		verticalLine.setBackgroundColor(Color.parseColor(PreferenceManager
				.getTextColor(getActivity())));
		verticalLine1.setBackgroundColor(Color.parseColor(PreferenceManager
				.getTextColor(getActivity())));
		listFindProvider = (ListView)view.findViewById(R.id.listFindProvider);
		
		ProviderCategoryListTask categoryListTask = new ProviderCategoryListTask(context,listFindProvider,onProviderFragmentClick);
		categoryListTask.execute();
		
	
		return view;
	}
	
	/**
	 * Declaration of a interface to communicate with the fragment
	 */
	public interface OnProviderFragmentClick {
		public void onProviderCategory(ProviderSearch providerSearch);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			onProviderFragmentClick = (OnProviderFragmentClick) activity;
		} catch (Exception e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnSidePanelChangedListener");
		}
	}
}
