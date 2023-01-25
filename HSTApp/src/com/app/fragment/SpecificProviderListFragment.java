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
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.app.hstapp.R;
import com.app.model.Provider;
import com.app.tasks.GetBlueShieldPlansTask;
import com.app.utils.PreferenceManager;


public class SpecificProviderListFragment extends Fragment{

	Provider provider;
	public OnNextClick onNextClick;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.provider_detail,
				container, false);
		initializeControl(view,savedInstanceState);
		return view;
	}
	private void initializeControl(View view, Bundle savedInstanceState) {
		ScrollView specificProviderLayout = (ScrollView)view.findViewById(R.id.specificProviderLayout);
	/*	specificProviderLayout.setBackgroundColor(Color.parseColor(PreferenceManager
				.getBackgroudColor(getActivity())));*/
		if(getArguments() != null) {
		provider = (Provider) getArguments().getSerializable("provider");
		
		TextView lablePhone = (TextView)view.findViewById(R.id.lablePhone);
	/*	lablePhone.setTextColor(Color.parseColor(PreferenceManager
				.getTextColor(getActivity())));*/
		TextView labelAddress = (TextView)view.findViewById(R.id.labelAddress);
		/*labelAddress.setTextColor(Color.parseColor(PreferenceManager
				.getTextColor(getActivity())));*/
		TextView txtPhone = (TextView) view.findViewById(R.id.txtPhone);
		TextView txtAddress = (TextView) view.findViewById(R.id.txtAddress);
		/*txtAddress.setTextColor(Color.parseColor(PreferenceManager
				.getTextColor(getActivity())));*/
		TextView txtProvider = (TextView) view.findViewById(R.id.txtProvider);
		/*txtProvider.setTextColor(Color.parseColor(PreferenceManager
				.getTextColor(getActivity())));*/
		TextView txtGender = (TextView) view.findViewById(R.id.txtGender);
	/*	txtGender.setTextColor(Color.parseColor(PreferenceManager
				.getTextColor(getActivity())));*/
		TextView txtLanguage = (TextView) view.findViewById(R.id.txtLanguage);
		/*txtLanguage.setTextColor(Color.parseColor(PreferenceManager
				.getTextColor(getActivity())));*/
		TextView lblLanguages = (TextView)view.findViewById(R.id.lblLanguages);
		/*lblLanguages.setTextColor(Color.parseColor(PreferenceManager
				.getTextColor(getActivity())));*/
		TextView lblGender = (TextView)view.findViewById(R.id.lblGender);
		/*lblGender.setTextColor(Color.parseColor(PreferenceManager
				.getTextColor(getActivity())));*/
		TextView lblProvider = (TextView)view.findViewById(R.id.lblProvider);
	/*	lblProvider.setTextColor(Color.parseColor(PreferenceManager
				.getTextColor(getActivity())));*/
		TextView view_accepted_plans = (TextView)view.findViewById(R.id.view_accepted_plans);
	/*	view_accepted_plans.setTextColor(Color.parseColor(PreferenceManager
				.getTextColor(getActivity())));*/
		TextView txtDesignation = (TextView) view.findViewById(R.id.txtDesignation);
		TextView txtDoctorName = (TextView) view.findViewById(R.id.txtDoctorName);
		TextView txtSpecializtion = (TextView) view.findViewById(R.id.txtSpecializtion);
		/*txtDesignation.setTextColor(Color.parseColor(PreferenceManager
				.getTextColor(getActivity())));*/
	/*	txtDoctorName.setTextColor(Color.parseColor(PreferenceManager
				.getTextColor(getActivity())));*/
		/*txtSpecializtion.setTextColor(Color.parseColor(PreferenceManager
				.getTextColor(getActivity())));*/
		TextView copyright_info = (TextView)view.findViewById(R.id.copyright_info);
/*		copyright_info.setTextColor(Color.parseColor(PreferenceManager
				.getTextColor(getActivity())));*/
		RelativeLayout layoutDirections = (RelativeLayout) view.findViewById(R.id.layoutDirections);
		RelativeLayout layoutAcceptedPlans = (RelativeLayout) view.findViewById(R.id.layoutAcceptedPlans);
		txtDesignation.setText(provider.getMedical_GroupName());
		txtDoctorName.setText(provider.getFirstName() + " " + provider.getLastName());
		txtSpecializtion.setText(provider.getSpecialization());
		txtPhone.setText(provider.getPhone());
		txtAddress.setText(provider.getAddress1() + " " + provider.getAddress2() + "," + provider.getCity() + " " + provider.getState() +  " " + provider.getZip());
		txtProvider.setText(provider.getProviderId());
		txtPhone.setText(provider.getPhone());
		txtGender.setText(provider.getGender());
		txtLanguage.setText(provider.getLanguages());
		layoutDirections.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onNextClick.OnProviderMap(provider); 
			}
		});
		layoutAcceptedPlans.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new GetBlueShieldPlansTask(getActivity(),provider.getDoctor_Id(),SpecificProviderListFragment.this).execute();
			}
		});
		}
	}
	
	/**
	 * Declaration of a interface to communicate with the fragment
	 */
	public interface OnNextClick {
		public void OnProviderMap(Provider provider);
		public void OnBlueShiledPlans(ArrayList<String> strings);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			onNextClick = (OnNextClick) activity;
		} catch (Exception e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnSidePanelChangedListener");
		}
	}
}
