package com.app.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.fragment.ProviderListFragment.OnProviderFragmentClick;
import com.app.hstapp.R;
import com.app.model.ProviderCategory;
import com.app.model.ProviderSearch;

public class FindProviderCategory extends ArrayAdapter<ProviderCategory> {

	ArrayList<ProviderCategory> findProviderList;
	OnProviderFragmentClick mCallback;
	public FindProviderCategory(Context context, ArrayList<ProviderCategory> findProviderList, OnProviderFragmentClick fragmentClick) {
		super(context, R.layout.find_provider_list_item,findProviderList);
		this.findProviderList = findProviderList;
		this.mCallback = fragmentClick;
	}
	
	@Override
	public View getView(final int position, View view, ViewGroup parent) {
		LayoutInflater inflator = LayoutInflater.from(getContext());
		 if(view == null)
		 {
			 view = inflator.inflate(R.layout.find_provider_list_item, parent, false);	
		 }
		 ProviderCategory providerCategory = findProviderList.get(position);
		 RelativeLayout providerLayout = (RelativeLayout)view.findViewById(R.id.providerLayout);
		 TextView txtProviderName = (TextView)view.findViewById(R.id.txtProviderName);
		 txtProviderName.setText(providerCategory.getCategoryName());
		 ImageView imgArrow = (ImageView)view.findViewById(R.id.imgArrow);
		 imgArrow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ProviderSearch providerSearch = new ProviderSearch();
				providerSearch.setCategoryID(findProviderList.get(position).getCategoryId());
				providerSearch.setCategoryName(findProviderList.get(position).getCategoryName());
				mCallback.onProviderCategory(providerSearch);
			}
		});
		providerLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ProviderSearch providerSearch = new ProviderSearch();
				providerSearch.setCategoryID(findProviderList.get(position).getCategoryId());
				providerSearch.setCategoryName(findProviderList.get(position).getCategoryName());
				mCallback.onProviderCategory(providerSearch);
			}
		});
	  return view;
	}

}
