package com.app.fragment;

import java.util.LinkedHashMap;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.hstapp.R;
import com.app.tasks.MemberDemographicInfoTask;
import com.app.utils.PreferenceManager;

/**
 * This fragment is for the MemberDemographic details
 */
public class MemberDemographicFragment extends Fragment {

	/*public TextView txtMemberId, txtMemberIdData, txtGroupId, txtGroupIdData,
			txtGender, txtGenderData, txtHeader, txtAddress1, txtAddress1Data,
			txtAddress2, txtAddress2Data, txtCity, txtCityData, txtState,
			txtStateData, txtZip, txtZipData, txtHomePhone, txtHomePhoneData,
			txtCellPhone, txtCellPhoneData, txtDOB, txtDOBData, txtName,
			txtNameData;*/
	public TextView txtAge, txtGenderData;
	String MemberId, GroupId, Age, Gender;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.member_demographic, container,
				false);
		initControls(view);
		LinkedHashMap<String, Object> postData = new LinkedHashMap<String, Object>();
		postData.put("MemberId", PreferenceManager.getMemberId(getActivity()));
		postData.put("GroupId", PreferenceManager.getGroupId(getActivity()));
		new MemberDemographicInfoTask(getActivity(),
				MemberDemographicFragment.this, postData).execute();
		return view;
	}

	/**
	 * To initialize the controls View parent view
	 */
	private void initControls(View view) {
		/*LinearLayout layout = (LinearLayout) view
				.findViewById(R.id.layoutMemberDemoGraphic);*/
		/*txtMemberId = (TextView) view.findViewById(R.id.txtMemberId);
		txtMemberIdData = (TextView) view.findViewById(R.id.txtMemberIdData);
		txtGroupId = (TextView) view.findViewById(R.id.txtGroupId);
		txtGroupIdData = (TextView) view.findViewById(R.id.txtGroupIdData);*/
		txtAge = (TextView) view.findViewById(R.id.member_age);
		txtGenderData = (TextView) view.findViewById(R.id.member_gender);
		/*
		txtHeader = (TextView) view.findViewById(R.id.txtHeader);
		txtAddress1 = (TextView) view.findViewById(R.id.txtAddress1);
		txtAddress1Data = (TextView) view.findViewById(R.id.txtAddress1Data);
		txtAddress2 = (TextView) view.findViewById(R.id.txtAddress2);
		txtAddress2Data = (TextView) view.findViewById(R.id.txtAddress2Data);
		txtCity = (TextView) view.findViewById(R.id.txtCity);
		txtCityData = (TextView) view.findViewById(R.id.txtCityData);
		txtState = (TextView) view.findViewById(R.id.txtState);
		txtStateData = (TextView) view.findViewById(R.id.txtStateData);
		txtZip = (TextView) view.findViewById(R.id.txtZip);
		txtZipData = (TextView) view.findViewById(R.id.txtZipData);
		txtHomePhone = (TextView) view.findViewById(R.id.txtHomePhone);
		txtHomePhoneData = (TextView) view.findViewById(R.id.txtHomePhoneData);
		txtCellPhone = (TextView) view.findViewById(R.id.txtCellPhone);
		txtCellPhoneData = (TextView) view.findViewById(R.id.txtCellPhoneData);
		txtDOB = (TextView) view.findViewById(R.id.txtDOB);
		txtDOBData = (TextView) view.findViewById(R.id.txtDOBData);
		txtName = (TextView) view.findViewById(R.id.txtName);
		txtNameData = (TextView) view.findViewById(R.id.txtNameData);*/
/*
		txtMemberId.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtMemberIdData.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtGroupId.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtGroupIdData.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtGender.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtGenderData.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtHeader.setTextColor(Color.parseColor(PreferenceManager
				.getTextHeaderColor(getActivity())));
		txtAddress1.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtAddress1Data.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtAddress2.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtAddress2Data.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtCity.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtCityData.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtState.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtStateData.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtZip.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtZipData.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtHomePhone.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtHomePhoneData.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtCellPhone.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtCellPhoneData.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtDOB.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtDOBData.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtName.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtNameData.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));*/
	}
}
