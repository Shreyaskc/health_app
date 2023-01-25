package com.app.utils;

import java.util.ArrayList;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.app.hstapp.R;
import com.app.model.Provider;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;


/**
 * Customize MarkerInfoWindowAdapter
 */
public class MarkerInfoWindowAdapter implements InfoWindowAdapter {

	Activity context;
	Provider provider;
	ArrayList<Provider> providerList;
	public MarkerInfoWindowAdapter(Activity context, Provider provider, ArrayList<Provider> providerList) {
		this.context = context;
		this.provider = provider;
		this.providerList = providerList;
	}

	@Override
	public View getInfoContents(Marker marker) {
		final View view = context.getLayoutInflater().inflate(
				R.layout.info_window, null);
		TextView txtName = (TextView) view.findViewById(R.id.txtName);
		TextView txtAddressLine = (TextView) view.findViewById(R.id.txtAddressLine);
		TextView txtAddressLine1 = (TextView) view.findViewById(R.id.txtAddressLine1);
		TextView txtDirectionHere = (TextView)view.findViewById(R.id.txtDirectionHere);
		View divider = (View)view.findViewById(R.id.view);
		if(provider != null) {
		if(marker.getTitle().equals("Current Location")) {
			txtAddressLine.setVisibility(View.GONE);
			txtAddressLine1.setVisibility(View.GONE);
			txtDirectionHere.setVisibility(View.GONE);
			txtName.setText("Current Location");
			divider.setVisibility(View.GONE);
		} else {
			txtAddressLine.setVisibility(View.VISIBLE);
			txtAddressLine1.setVisibility(View.VISIBLE);
			txtDirectionHere.setVisibility(View.VISIBLE);
			divider.setVisibility(View.VISIBLE);
		txtName.setText(provider.getMedical_GroupName());
		txtAddressLine.setText(provider.getAddress1() + " " + provider.getAddress2() );
		txtAddressLine1.setText(provider.getCity() + " " + provider.getState() +  " " + provider.getZip());
		txtDirectionHere.setText("Directions to here");
		}
		} else 
		{
			if(marker.getTitle().equals("Current Location")) {
				txtAddressLine.setVisibility(View.GONE);
				txtAddressLine1.setVisibility(View.GONE);
				txtDirectionHere.setVisibility(View.GONE);
				txtName.setText("Current Location");
				divider.setVisibility(View.GONE);
			} else {
				txtAddressLine.setVisibility(View.VISIBLE);
				txtAddressLine1.setVisibility(View.VISIBLE);
				txtDirectionHere.setVisibility(View.VISIBLE);
				divider.setVisibility(View.VISIBLE);
				Log.v("1"," .........................1");
				for(int j=0; j<providerList.size();j++){
					Log.v("1"," .........................2"+j);
					if(marker.getTitle().equals(String.valueOf(j))){
						provider = providerList.get(j);
						txtName.setText(provider.getMedical_GroupName());
						txtAddressLine.setText(provider.getAddress1() + " " + provider.getAddress2() );
						txtAddressLine1.setText(provider.getCity() + " " + provider.getState() +  " " + provider.getZip());
						txtDirectionHere.setText("Directions to here");
						Log.v("Title","......"+marker.getTitle());
					}
					
				}
			}
		}
		return view;
	}

	@Override
	public View getInfoWindow(Marker marker) {
		return null;
	}

}
