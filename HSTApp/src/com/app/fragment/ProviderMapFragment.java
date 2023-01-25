package com.app.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.app.hstapp.R;
import com.app.model.Provider;
import com.app.model.ProviderSearch;
import com.app.tasks.GetAddressAsyncTask;
import com.app.tasks.GetDirectionTask;
import com.app.utils.MarkerInfoWindowAdapter;
import com.app.utils.PreferenceManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class ProviderMapFragment extends Fragment{

	MapView mapView;
	public GoogleMap googleMap;
	OnGetAnotherAddress onGetAnotherAddress;
	ProviderSearch providerSearch;
	String categoryName,categoryId;
	Provider provider;
	ArrayList<Provider> providerList;
	public static double curLat,curLng;
	public static String current_location= "Current Location";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.provider_list_map,
				container, false);
		initializeControl(view,savedInstanceState);
		return view;
	}
	
	@SuppressWarnings("unchecked")
	private void initializeControl(View view, Bundle savedInstanceState) {
		RelativeLayout layoutMap = (RelativeLayout)view.findViewById(R.id.layoutMap);
	/*	layoutMap.setBackgroundColor(Color.parseColor(PreferenceManager
				.getBackgroudColor(getActivity())));*/
		if(getArguments() != null) {
			providerSearch = (ProviderSearch) getArguments().getSerializable("providerSearch");
			if(providerSearch != null){
				categoryName = providerSearch.getCategoryName();
				categoryId = providerSearch.getCategoryID();
			}
			provider = (Provider) getArguments().getSerializable("provider");
			providerList = (ArrayList<Provider>) getArguments().getSerializable("ProviderList");
		}
		MapsInitializer.initialize(this.getActivity());
		mapView = (MapView) view.findViewById(R.id.mapView);
		mapView.onCreate(savedInstanceState);
		googleMap = mapView.getMap();
		getCurrentLocation();
		if(providerList == null) {
		if(provider == null) {
			googleMap.setOnMapClickListener(new OnMapClickListener() {
	            @Override
	            public void onMapClick(LatLng latLng) {
            		BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_red);
	            	MarkerOptions markerOptions = new MarkerOptions();
	            	markerOptions.position(latLng);
	            	markerOptions.icon(icon);
	            	GetAddressAsyncTask getAddressAsyncTask = new GetAddressAsyncTask(getActivity(),latLng.latitude, latLng.longitude,ProviderMapFragment.this);
	            	getAddressAsyncTask.execute();
	            	googleMap.clear();
	            	googleMap.addMarker(markerOptions);
	            }
	        });
			view.setFocusableInTouchMode(true);
			view.requestFocus();
			view.setOnKeyListener(new OnKeyListener() {
				
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					 if( keyCode == KeyEvent.KEYCODE_BACK ) {
						 getActivity().onBackPressed();
//		                 onGetAnotherAddress.onProviderCategory(providerSearch);	
		                //getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		                return true;
		            } else {
		                return false;
		            }
				}
			});
		} else {
			BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_red);
			LatLng currentPosition = new LatLng(provider.getLatitude(),provider.getLongitude());
	        MarkerOptions markerOptions = new MarkerOptions();
	        markerOptions.position(currentPosition);
	        markerOptions.title("");
	        markerOptions.icon(icon);
	        //googleMap.clear();
	        googleMap.addMarker(markerOptions);
	        try{
				 googleMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter(getActivity(),provider,null));
				 googleMap.setOnMarkerClickListener(new OnMarkerClickListener() {
					@Override
					public boolean onMarkerClick(Marker marker) {
						//display InfoWindow
						marker.showInfoWindow();
						return false;
					}
				});
			 }catch(Exception e){
				 e.printStackTrace();
			 }
	        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentPosition));
		}
		} else {
			for(int i = 0; i< providerList.size(); i++) {
				BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_red);
				LatLng currentPosition = new LatLng(providerList.get(i).getLatitude(),providerList.get(i).getLongitude());
		        MarkerOptions markerOptions = new MarkerOptions();
		        markerOptions.position(currentPosition);
		        markerOptions.title(String.valueOf(i));
		        markerOptions.icon(icon);
		        googleMap.addMarker(markerOptions);
		        
			}
			//googleMap.clear();
	        try {
				 googleMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter(getActivity(),null,providerList));
				 googleMap.setOnMarkerClickListener(new OnMarkerClickListener() {
					@Override
					public boolean onMarkerClick(Marker marker) {
						//display InfoWindow
						marker.showInfoWindow();
						return false;
					}
				});
			 }catch(Exception e){
				 e.printStackTrace();
			 }
		}
		googleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
			
			@Override
			public void onInfoWindowClick(Marker arg0) {
				if(!arg0.getTitle().equals(current_location)){
					new GetDirectionTask(getActivity(), arg0.getPosition(),ProviderMapFragment.this).execute();
				}
			}
		});
		
	}
	private void getCurrentLocation() {
		if(FindProviderByNameFragment.latitude > 0) {
			BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.current_location_marker);
			LatLng currentPosition = new LatLng(FindProviderByNameFragment.latitude,FindProviderByNameFragment.longitude);
	        MarkerOptions markerOptions = new MarkerOptions();
	        markerOptions.position(currentPosition);
	        markerOptions.icon(icon);
	        markerOptions.title(current_location);
	        googleMap.clear();
	        googleMap.addMarker(markerOptions);
			
	        // Showing the current location in Google Map
	        //Move the camera to the user's location and zoom in!
	        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentPosition.latitude, currentPosition.longitude), 12.0f));
	       // googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentPosition));
			} else if(curLat > 0) {
				BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.current_location_marker);
				LatLng currentPosition = new LatLng(curLat,curLng);
		        MarkerOptions markerOptions = new MarkerOptions();
		        markerOptions.position(currentPosition);
		        markerOptions.icon(icon);
		        markerOptions.title("Current Location");
		        googleMap.clear();
		        googleMap.addMarker(markerOptions);
				
		        // Showing the current location in Google Map
		        //Move the camera to the user's location and zoom in!
		        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(curLat, curLng), 12.0f));
			}
	}
	
	@Override
	public void onDestroy() {
		//Log.v("TTT", "onDestroy");
		mapView.onDestroy();
		super.onDestroy();
	}

	@Override
	public void onLowMemory() {
		//Log.v("TTT", "onLowMemory");
		super.onLowMemory();
		mapView.onLowMemory();
	}

	@Override
	public void onResume() {
		super.onResume();
		//Log.v("OnResume","OnResume");
		mapView.onResume();
	}
	
	public void showFragment(String address,double lat, double lng) {
		if(providerSearch == null);
			providerSearch = new ProviderSearch();
		providerSearch.setCategoryID(categoryId);
		providerSearch.setCategoryName(categoryName);
		providerSearch.setAddress(address); 
		providerSearch.setLat(lat);
		providerSearch.setLng(lng); 
		onGetAnotherAddress.onProviderCategory(providerSearch);	
	}
	/**
	 * Declaration of a interface to communicate with the fragment
	 */
	public interface OnGetAnotherAddress {
		public void onProviderCategory(ProviderSearch providerSearch);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			onGetAnotherAddress = (OnGetAnotherAddress) activity;
		} catch (Exception e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnSidePanelChangedListener");
		}
	}
	


}
