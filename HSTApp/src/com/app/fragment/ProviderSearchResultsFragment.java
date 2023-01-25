package com.app.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.app.adapter.ProviderSearchResults;
import com.app.hstapp.R;
import com.app.model.Provider;
import com.app.model.ProviderSearch;
import com.app.utils.PreferenceManager;

public class ProviderSearchResultsFragment extends Fragment{

	ArrayList<Provider> providerList;
	ListView listFindProvider;
	OnSelectingSpecificProvider mCallback;
	ProviderSearch providerSearch;
	OnViewMapClick onViewMapClick;
	Button btnMapResults,btnRefineSearch;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.provider_list,
				container, false);
		initializeControl(view,savedInstanceState);
		return view;
	}
	
	@SuppressWarnings("unchecked")
	private void initializeControl(View view, Bundle savedInstanceState) {
		RelativeLayout searchLayout = (RelativeLayout)view.findViewById(R.id.searchLayout);
		/*searchLayout.setBackgroundColor(Color.parseColor(PreferenceManager
				.getBackgroudColor(getActivity())));*/
		if(getArguments() != null) {
		providerSearch = (ProviderSearch) getArguments().getSerializable("providerSearch");
		providerList = (ArrayList<Provider>) getArguments().getSerializable("ProviderList");
		listFindProvider = (ListView)view.findViewById(R.id.listFindProvider);
		ProviderSearchResults providerSearchResultsAdapter = new ProviderSearchResults(getActivity(),providerList,mCallback,providerSearch,onViewMapClick);
		listFindProvider.setAdapter(providerSearchResultsAdapter);
		
		btnMapResults = (Button)view.findViewById(R.id.btnMapResults);
		btnMapResults.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onViewMapClick.OnProviderMap(providerSearch,null,providerList);
			}
		});
		btnRefineSearch = (Button)view.findViewById(R.id.btnRefineSearch);
		btnRefineSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onViewMapClick.onRefineSearch();
			}
		});
		/*btnMapResults.setTextColor(Color.parseColor(PreferenceManager
				.getTextColor(getActivity())));
		btnRefineSearch.setTextColor(Color.parseColor(PreferenceManager
				.getTextColor(getActivity())));*/
		listFindProvider.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
			mCallback.OnSelectSpecificProvider(providerList.get(position));
			}
			
		});
	 }
	}
	
	/**
	 * Declaration of a interface to communicate with the fragment
	 */
	public interface OnViewMapClick {
		public void OnProviderMap(ProviderSearch providerSearch,Provider provider,ArrayList<Provider> providerList);
		public void onRefineSearch();
	}
	public interface OnSelectingSpecificProvider {
		public void OnSelectSpecificProvider(Provider provider);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallback = (OnSelectingSpecificProvider) activity;
			onViewMapClick = (OnViewMapClick) activity;
		} catch (Exception e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnSidePanelChangedListener");
		}
	}
}
