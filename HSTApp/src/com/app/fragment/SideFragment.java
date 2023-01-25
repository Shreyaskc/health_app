package com.app.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.hstapp.R;
import com.app.utils.HSTApp;
import com.app.utils.HSTApp.TrackerName;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * This fragment is for the SideFragment details
 */
public class SideFragment extends Fragment implements OnClickListener{
	public OnSideFragmentClick sidePanelClickListener;
	Button txtDemoGraphic, txtMemberInsuranceInformation,
			txtMemberPCPInformation, txtListOfAuthorizedproviders, txtSettings,txtMemberDemographic;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.sidelayout, container, false);
		txtDemoGraphic = (Button) view
				.findViewById(R.id.demographics_side_panel);
		txtMemberInsuranceInformation = (Button) view
				.findViewById(R.id.insurance_side_panel);
		txtMemberPCPInformation = (Button) view
				.findViewById(R.id.primary_care_provider);
		txtListOfAuthorizedproviders = (Button) view
				.findViewById(R.id.contact_hst_side_panel);
		txtSettings = (Button) view.findViewById(R.id.more_side_panel);
		txtMemberDemographic = (Button) view.findViewById(R.id.member_demographics_side_panel);
/*
		txtDemoGraphic.setTextColor(Color.parseColor(PreferenceManager
				.getTextColor(getActivity())));
		txtMemberInsuranceInformation.setTextColor(Color
				.parseColor(PreferenceManager.getTextColor(getActivity())));
		txtMemberPCPInformation.setTextColor(Color.parseColor(PreferenceManager
				.getTextColor(getActivity())));
		txtListOfAuthorizedproviders.setTextColor(Color
				.parseColor(PreferenceManager.getTextColor(getActivity())));
		txtSettings.setTextColor(Color.parseColor(PreferenceManager
				.getTextColor(getActivity())));
		((GradientDrawable)txtDemoGraphic.getBackground()).setColor(Color.parseColor(PreferenceManager.getBackgroudColor(getActivity())));
		((GradientDrawable)txtMemberInsuranceInformation.getBackground()).setColor(Color.parseColor(PreferenceManager.getBackgroudColor(getActivity())));
		((GradientDrawable)txtMemberPCPInformation.getBackground()).setColor(Color.parseColor(PreferenceManager.getBackgroudColor(getActivity())));
		((GradientDrawable)txtListOfAuthorizedproviders.getBackground()).setColor(Color.parseColor(PreferenceManager.getBackgroudColor(getActivity())));
		((GradientDrawable)txtSettings.getBackground()).setColor(Color.parseColor(PreferenceManager.getBackgroudColor(getActivity())));
		*/
		txtDemoGraphic.setOnClickListener(this);
		txtMemberInsuranceInformation.setOnClickListener(this);
		txtMemberPCPInformation.setOnClickListener(this);
		txtListOfAuthorizedproviders.setOnClickListener(this);
		txtSettings.setOnClickListener(this);
		txtMemberDemographic.setOnClickListener(this);
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			sidePanelClickListener = (OnSideFragmentClick) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnSidePanelChangedListener");
		}
	}

	/**
	 * Declaration of a interface to communicate with the fragment
	 */
	public interface OnSideFragmentClick {
		public void onMemberDemoGraphic();

		public void onMemberInsuranceInformation();

		public void onMemberPCPInformation();

		public void onListOfAuthorizedproviders();

		public void onSettings();
	}

	@Override
	public void onClick(View v) {
		Tracker t = ((HSTApp) getActivity().getApplication())
				.getTracker(TrackerName.APP_TRACKER);
		txtDemoGraphic.setBackgroundResource(R.drawable.side_panel_grey);
		txtMemberInsuranceInformation.setBackgroundResource(R.drawable.side_panel_grey);
		txtMemberPCPInformation.setBackgroundResource(R.drawable.side_panel_grey);
		txtSettings.setBackgroundResource(R.drawable.side_panel_grey);
		txtListOfAuthorizedproviders.setBackgroundResource(R.drawable.side_panel_grey);
		txtMemberDemographic.setBackgroundResource(R.drawable.side_panel_grey);
		switch (v.getId()) {
			case R.id.demographics_side_panel:
				// Build and send an Event.
				t.send(new HitBuilders.EventBuilder().setCategory("Side Panel")
						.setAction("MemberDemoGraphic Click")
						.setLabel(getString(R.string.member_demographics))
						.build());
				txtDemoGraphic.setBackgroundResource(R.drawable.side_panel_green);
				sidePanelClickListener.onMemberDemoGraphic();
				break;
			case R.id.insurance_side_panel:
				// Build and send an Event.
				t.send(new HitBuilders.EventBuilder()
						.setCategory("Side Panel")
						.setAction("MemberInsuranceInformation Click")
						.setLabel(
								getString(R.string.member_insurance_information))
						.build());
				txtMemberInsuranceInformation.setBackgroundResource(R.drawable.side_panel_green);
				sidePanelClickListener.onMemberInsuranceInformation();
				break;
			case R.id.member_demographics_side_panel:
				t.send(new HitBuilders.EventBuilder().setCategory("Side Panel")
						.setAction("MemberPCPInformation Click")
						.setLabel(getString(R.string.member_pcp_information))
						.build());
				txtMemberDemographic.setBackgroundResource(R.drawable.side_panel_green);
				sidePanelClickListener.onMemberDemoGraphic();
				break;
			case R.id.primary_care_provider:
				t.send(new HitBuilders.EventBuilder()
				.setCategory("Side Panel")
				.setAction("ListOfAuthorizedproviders Click")
				.setLabel(
						getString(R.string.list_of_authorized_providers))
				.build());
				txtMemberPCPInformation.setBackgroundResource(R.drawable.side_panel_green);
				sidePanelClickListener.onMemberPCPInformation();
				break;
			case R.id.more_side_panel:
				t.send(new HitBuilders.EventBuilder().setCategory("Side Panel")
						.setAction("Settings Click")
						.setLabel(getString(R.string.settings)).build());
				txtSettings.setBackgroundResource(R.drawable.side_panel_green);
				sidePanelClickListener.onSettings();
				break;
			case R.id.contact_hst_side_panel:
				t.send(new HitBuilders.EventBuilder().setCategory("Side Panel")
						.setAction("Settings Click")
						.setLabel(getString(R.string.settings)).build());
				txtListOfAuthorizedproviders.setBackgroundResource(R.drawable.side_panel_green);
				sidePanelClickListener.onListOfAuthorizedproviders();
				break;
		}
	}
}
