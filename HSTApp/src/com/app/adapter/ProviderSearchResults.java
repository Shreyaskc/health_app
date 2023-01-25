package com.app.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.fragment.ProviderSearchResultsFragment.OnSelectingSpecificProvider;
import com.app.fragment.ProviderSearchResultsFragment.OnViewMapClick;
import com.app.hstapp.R;
import com.app.model.Provider;
import com.app.model.ProviderSearch;
import com.app.utils.Utility;
import com.google.android.gms.maps.model.LatLng;

public class ProviderSearchResults extends ArrayAdapter<Provider> {
	

	ArrayList<Provider> providerList;
	OnSelectingSpecificProvider mCallback;
	ProviderSearch  providerSearch;
	OnViewMapClick viewMapClick;
	public ProviderSearchResults(Context context, ArrayList<Provider> providerList, OnSelectingSpecificProvider mCallback, ProviderSearch providerSearch, OnViewMapClick onViewMapClick) {
		super(context, R.layout.provider_list_item,providerList);
		this.providerList = providerList;
		this.mCallback = mCallback;
		this.providerSearch = providerSearch;
		this.viewMapClick = onViewMapClick;
	}
	
	@Override
	public View getView(final int position, View view, ViewGroup parent) {
		LayoutInflater inflator = LayoutInflater.from(getContext());
		 if(view == null)
		 {
			 view = inflator.inflate(R.layout.provider_list_item, parent, false);	
		 }
		 final Provider provider = providerList.get(position);
		 TextView txtName = (TextView)view.findViewById(R.id.txtName);
		 TextView txtType = (TextView)view.findViewById(R.id.txtType);
		 TextView txtAddress = (TextView)view.findViewById(R.id.txtAddress);
		 TextView txtAddressLine = (TextView)view.findViewById(R.id.txtAddressLine);
		 TextView txtDistance = (TextView)view.findViewById(R.id.txtDistance);
		 TextView txtPhone = (TextView)view.findViewById(R.id.txtPhone);
		 TextView txtViewMap = (TextView)view.findViewById(R.id.txtViewMap);
		 
		 txtName.setText(provider.getMedical_GroupName());
		 txtType.setText(provider.getSpecialization());
		 txtAddress.setText(provider.getAddress1()+" "+provider.getAddress2());
		 txtAddressLine.setText(provider.getCity()+" "+provider.getState()+" "+provider.getZip());
		 txtDistance.setText(Utility.getDistance(new LatLng(providerSearch.getLat(), providerSearch.getLng()), new LatLng(provider.getLatitude(), provider.getLongitude()))+" mile(s)");
		 txtPhone.setText(Html.fromHtml("<U>"+provider.getPhone()+"</U>"));
		 txtViewMap.setText(Html.fromHtml("<U>View Map</U>"));
		 ImageView imgArrow = (ImageView)view.findViewById(R.id.imgArrow);
		 imgArrow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				ProviderSearch providerSearch = new ProviderSearch();
//				providerSearch.setCategoryID(findProviderList.get(position).getCategoryId());
//				providerSearch.setCategoryName(findProviderList.get(position).getCategoryName());
				mCallback.OnSelectSpecificProvider(providerList.get(position));
			}
		 });
		 txtPhone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + provider.getPhone()));
				getContext().startActivity(intent);
			}
		 });
		 txtViewMap.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			Provider provider = providerList.get(position);
			viewMapClick.OnProviderMap(providerSearch,provider,null);
			}
		});
		 
	  return view;
	}

}

