package com.app.fragment;

import java.util.LinkedHashMap;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.app.hstapp.R;
import com.app.tasks.MemberPCPInfoTask;
import com.app.utils.PreferenceManager;

/**
 * This fragment is for the MemberPCPInformation details
 */
public class MemberPCPInformationfragment extends Fragment {

	public TextView  txtOfficeHours, txtOfficeHoursData,
			txtOtherNotes, txtOtherNotesData, txtName, txtNameData, txtEmail,
			txtEmailData, txtPhone, txtPhoneData, txtHeader;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.member_pcp_information,
				container, false);
		initControls(view);
		LinkedHashMap<String, Object> postData = new LinkedHashMap<String, Object>();
		postData.put("MemberId", PreferenceManager.getMemberId(getActivity()));
		postData.put("GroupId", PreferenceManager.getGroupId(getActivity()));
		new MemberPCPInfoTask(getActivity(), MemberPCPInformationfragment.this,
				postData).execute();
		return view;
	}

	/**
	 * To initialize the controls View parent view
	 */
	private void initControls(View view) {
		ScrollView layoutMemberPCPInformation = (ScrollView) view
				.findViewById(R.id.layoutMemberPCPInformation);
/*		layoutMemberPCPInformation
				.setBackgroundColor(Color.parseColor(PreferenceManager
						.getBackgroudColor(getActivity())));*/
		txtHeader = (TextView) view.findViewById(R.id.txtHeader);
		txtName = (TextView) view.findViewById(R.id.txtName);
		txtNameData = (TextView) view.findViewById(R.id.txtNameData);
		txtEmail = (TextView) view.findViewById(R.id.txtEmail);
		txtEmailData = (TextView) view.findViewById(R.id.txtEmailData);
		txtPhone = (TextView) view.findViewById(R.id.txtPhone);
		txtPhoneData = (TextView) view.findViewById(R.id.txtPhoneData);

		txtOfficeHours = (TextView) view.findViewById(R.id.txtOfficeHours);
		txtOfficeHoursData = (TextView) view
				.findViewById(R.id.txtOfficeHoursData);
		txtOtherNotes = (TextView) view.findViewById(R.id.txtOtherNotes);
		txtOtherNotesData = (TextView) view
				.findViewById(R.id.txtOtherNotesData);
		/*txtHeader.setTextColor(Color.parseColor(PreferenceManager
				.getTextHeaderColor(getActivity())));
		txtContactInformation.setTextColor(Color.parseColor(PreferenceManager
				.getTextHeaderColor(getActivity())));
		txtName.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtNameData.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtEmail.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtEmailData.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtPhone.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtPhoneData.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtOfficeHours.setTextColor(Color.parseColor(PreferenceManager
				.getTextHeaderColor(getActivity())));
		txtOfficeHoursData.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtOtherNotes.setTextColor(Color.parseColor(PreferenceManager
				.getTextHeaderColor(getActivity())));
		txtOtherNotesData.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));*/
	}
}
