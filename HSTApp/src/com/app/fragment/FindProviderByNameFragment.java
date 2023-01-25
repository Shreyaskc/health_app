package com.app.fragment;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.hstapp.R;
import com.app.model.Provider;
import com.app.model.ProviderSearch;
import com.app.tasks.GetAddressAsyncTask;
import com.app.tasks.ProviderListTask;
import com.app.utils.PreferenceManager;

public class FindProviderByNameFragment  extends Fragment implements LocationListener {

	public TextView txtCurrentLocation,txtAnotherAddress,txtAnotherAddressLocation;
	LocationManager locationManager;
	String provider,address;
	ProviderSearch providerSearch;
	public double curLatitude, curLongitude,anotherLatitude,anotherLongitude;
	public static double latitude,longitude;
	public static String curlocationAddress;
	OnAnotherAddressClick onAnotherAddressClick;
	OnProviderSearchClick onProviderSearchClick;
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // meter
	private static final long MIN_TIME_BW_UPDATES = 1000; // in Milliseconds
	CheckBox chkCurrentLocation,chkAnotherAddress;
	Button btnSearch;
	EditText edittextName;
	Spinner spinnerDistance;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.find_provider_by_name,
				container, false);
		initializeControl(view,savedInstanceState);
		return view;
	}
	private void initializeControl(View view, Bundle savedInstanceState) {
		if(getArguments() != null) {
			providerSearch = (ProviderSearch) getArguments().getSerializable("providerSearch");
			if(providerSearch != null){
				address = providerSearch.getAddress();
				anotherLatitude = providerSearch.getLat();
				anotherLongitude = providerSearch.getLng();
			}
		} 
		ScrollView findProviderLayout = (ScrollView)view.findViewById(R.id.findProviderLayout);
		txtCurrentLocation = (TextView)view.findViewById(R.id.txtCurrentLocation);
		txtAnotherAddress = (TextView)view.findViewById(R.id.txtAnotherAddress);
		txtAnotherAddressLocation = (TextView)view.findViewById(R.id.txtAnotherAddressLocation);
		chkCurrentLocation = (CheckBox)view.findViewById(R.id.chkCurrentLocation);
		chkAnotherAddress = (CheckBox)view.findViewById(R.id.chkAnotherLocation);
		btnSearch = (Button)view.findViewById(R.id.btnSearch);
		/*btnSearch.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));*/
		edittextName = (EditText)view.findViewById(R.id.edittextName);
		/*edittextName.setTextColor(Color.parseColor(PreferenceManager
				.getTextColor(getActivity())));*/
		TextView txtWithin = (TextView)view.findViewById(R.id.txtWithin);
		/*txtWithin.setTextColor(Color.parseColor(PreferenceManager
				.getTextColor(getActivity())));*/
		TextView txtMyCurrentLocation = (TextView)view.findViewById(R.id.txtMyCurrentLocation);
		TextView txtCurrentLocation = (TextView)view.findViewById(R.id.txtCurrentLocation);
	/*	txtMyCurrentLocation.setTextColor(Color.parseColor(PreferenceManager
				.getTextColor(getActivity())));
		txtCurrentLocation.setTextColor(Color.parseColor(PreferenceManager
				.getTextColor(getActivity())));
		txtAnotherAddress.setTextColor(Color.parseColor(PreferenceManager
				.getTextColor(getActivity())));
		txtAnotherAddressLocation.setTextColor(Color.parseColor(PreferenceManager
				.getTextColor(getActivity())));*/
		spinnerDistance = (Spinner)view.findViewById(R.id.spinnerDistance);
		locationManager = (LocationManager) getActivity().getSystemService(
				Context.LOCATION_SERVICE);
		RelativeLayout layoutAnother = (RelativeLayout)view.findViewById(R.id.layoutAnother);
		layoutAnother.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onAnotherAddressClick.OnProviderMap(providerSearch,null,null);
			}
		});
//		if(ProviderMapFragment.curLat == 0) {
			getCurrentLocation();
//		} else {
//			txtCurrentLocation.setText(curlocationAddress);	
//		/}
		if(address != null) {
			chkAnotherAddress.setChecked(true);
			latitude = anotherLatitude;
			longitude = anotherLongitude;
			txtAnotherAddressLocation.setVisibility(View.VISIBLE); 
			txtAnotherAddressLocation.setText(address); 
			RelativeLayout.LayoutParams layoutParams = 
				    (RelativeLayout.LayoutParams)txtAnotherAddress.getLayoutParams();
				layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, 0);
			
				txtAnotherAddress.setLayoutParams(layoutParams);
			//LayoutParams layoutParams = new LayoutParams(source)
		} else {
			RelativeLayout.LayoutParams layoutParams = 
				    (RelativeLayout.LayoutParams)txtAnotherAddress.getLayoutParams();
				layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
			
			chkCurrentLocation.setChecked(true);
			latitude = curLatitude;
			longitude = curLongitude;
			txtAnotherAddress.setLayoutParams(layoutParams);
			txtAnotherAddressLocation.setVisibility(View.GONE); 
			txtAnotherAddress.setGravity(Gravity.CENTER_VERTICAL);
			txtAnotherAddress.setText("Another Address");
		}
		
		chkCurrentLocation.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(chkCurrentLocation.isChecked() == true) {
					chkAnotherAddress.setChecked(false);
					latitude = curLatitude;
					longitude = curLongitude;
				} else {
					if(anotherLatitude > 0 ) {
						latitude = anotherLatitude;
						longitude = anotherLongitude;
					}
					chkAnotherAddress.setChecked(true);
				}
			}
		});
		chkAnotherAddress.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(chkAnotherAddress.isChecked() == true) {
					if(anotherLatitude > 0 ) {
						latitude = anotherLatitude;
						longitude = anotherLongitude; 
					}
					chkCurrentLocation.setChecked(false);
				} else {
					latitude = curLatitude;
					longitude = curLongitude;
					chkCurrentLocation.setChecked(true);
				}
			}
		});
		btnSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
//				Log.v("TTT","Lat ="+ProviderMapFragment.curLat+ "     "+ latitude+"=Long="+longitude);
				Log.v("TTT",""+latitude+"..."+longitude+"Member ID=="+PreferenceManager.getMemberId(getActivity())+"GroupId:"+PreferenceManager.getGroupId(getActivity())+"Doctor Name="+edittextName.getText().toString()+"Category Id:"+providerSearch.getCategoryID());
				LinkedHashMap<String, Object> postData = new LinkedHashMap<String, Object>();
				postData.put("MemberId",
						String.valueOf(PreferenceManager.getMemberId(getActivity())));
				postData.put("GroupId",
						String.valueOf(PreferenceManager.getGroupId(getActivity())));
				postData.put("Latitude", latitude);
				postData.put("Longitude", longitude);
				postData.put("Distance", String.valueOf(NumberFormat.getInstance().parse(spinnerDistance.getSelectedItem().toString()).intValue()));
				postData.put("Name", edittextName.getText().toString());
				postData.put("CategoryId", Integer.parseInt(providerSearch.getCategoryID()));
				Log.v("TTT",""+latitude+"..."+longitude+"Member ID=="+PreferenceManager.getMemberId(getActivity())+"GroupId:"+PreferenceManager.getGroupId(getActivity())+"Doctor Name="+edittextName.getText().toString()+"Category Id:"+providerSearch.getCategoryID());
				providerSearch.setLat(latitude);
				providerSearch.setLng(longitude);
				new ProviderListTask(getActivity(),
						FindProviderByNameFragment.this, postData,onProviderSearchClick,providerSearch).execute();
				//onProviderSearchClick.OnProviderSearch(latitude,longitude,5,edittextName.getText().toString(),"1");
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}
	private void getCurrentLocation() {
		try {
			Boolean gps_enabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
			Boolean network_enabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			if (gps_enabled) {
				locationManager.requestLocationUpdates(
						LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
						MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
			}
			if (network_enabled) {
				locationManager.requestLocationUpdates(
						LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES,
						MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
			}
			if (!gps_enabled) {
				new AlertDialog.Builder(getActivity())
						.setMessage(
								"GPS signal not found would u like to ON GPS?")
						.setCancelable(false)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.cancel();
										Intent intent = new Intent(
												Settings.ACTION_LOCATION_SOURCE_SETTINGS);
										startActivity(intent);
										// System.exit(0);
									}
								})
						.setNegativeButton("No",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.cancel();
										// System.exit(0);
									}
								}).show();

			} else {
				Criteria criteria = new Criteria();
				provider = locationManager.getBestProvider(criteria, false);
				Location location = locationManager
						.getLastKnownLocation(provider);
				if (location != null) {
					curLatitude = (double) (location.getLatitude());
					curLongitude = (double) (location.getLongitude());
					ProviderMapFragment.curLat = curLatitude;
					ProviderMapFragment.curLng = curLongitude;
					
					Log.v("TTT", "latitute..." + curLatitude + "...Long..."
							+ curLongitude);
						
					GetAddressAsyncTask getAddressAsyncTask = new GetAddressAsyncTask(getActivity(),curLatitude, curLongitude,FindProviderByNameFragment.this);
			        getAddressAsyncTask.execute();
					
				} else {
					txtCurrentLocation.setText("Location Not found");
					// Log.v("TTT", "Location not available");
					new AlertDialog.Builder(getActivity())
							.setMessage("Location Not found")
							.setCancelable(false)
							.setPositiveButton("OK",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.cancel();
											// System.exit(0);
										}
									}).show();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

@Override
public void onLocationChanged(Location location) {
	curLatitude = location.getLatitude();
	curLongitude = location.getLongitude();
}

@Override
public void onProviderDisabled(String provider) {

}

@Override
public void onProviderEnabled(String provider) {

}

@Override
public void onStatusChanged(String provider, int status, Bundle extras) {

}

/**
 * Declaration of a interface to communicate with the fragment
 */
public interface OnAnotherAddressClick {
	public void OnProviderMap(ProviderSearch providerSearch,Provider provider,ArrayList<Provider> providerList);
}

public interface OnProviderSearchClick {
	public void OnProviderSearch(ArrayList<Provider> providerList, ProviderSearch providerSearch);
}

@Override
public void onAttach(Activity activity) {
	super.onAttach(activity);
	try {
		onAnotherAddressClick = (OnAnotherAddressClick) activity;
		onProviderSearchClick = (OnProviderSearchClick)	activity;
	} catch (Exception e) {
		throw new ClassCastException(activity.toString()
				+ " must implement OnSidePanelChangedListener");
	}
}
}
