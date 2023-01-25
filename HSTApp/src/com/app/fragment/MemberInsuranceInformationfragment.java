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
import com.app.tasks.MemberInsuranceInfoTask;
import com.app.utils.PreferenceManager;

/**
 * This fragment is for the MemberInsuranceInformation details
 */
public class MemberInsuranceInformationfragment extends Fragment {

	public TextView txtId, txtIdData, txtHeader, txtMemberId, txtMemberIdData,
			txtGroupId, txtGroupIdData, txtEffectiveDate, txtEffectiveDateData,
			txtPlan, txtPlanData, txtOfficeVisitCoPayAmount,
			txtOfficeVisitCoPayAmountData, txtDrugCoPayAmount,
			txtDrugCoPayAmountData, txtHospitalCoPayAmount,
			txtHospitalCoPayAmountData, txtEmergencyVisitCoPayAmount,
			txtEmergencyVisitCoPayAmountData, txtDeductible, txtDeductibleData;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.member_insurance_information,
				container, false);
		initControls(view);
		LinkedHashMap<String, Object> postData = new LinkedHashMap<String, Object>();
		postData.put("MemberId", PreferenceManager.getMemberId(getActivity()));
		postData.put("GroupId", PreferenceManager.getGroupId(getActivity()));
		new MemberInsuranceInfoTask(getActivity(),
				MemberInsuranceInformationfragment.this, postData).execute();
		return view;
	}

	/**
	 * To initialize the controls View parent view
	 */
	private void initControls(View view) {
		LinearLayout layoutMemberInsuranceInformation = (LinearLayout) view
				.findViewById(R.id.layoutMemberInsuranceInformation);
		/*layoutMemberInsuranceInformation
				.setBackgroundColor(Color.parseColor(PreferenceManager
						.getBackgroudColor(getActivity())));
*/
		txtId = (TextView) view.findViewById(R.id.txtId);
		txtIdData = (TextView) view.findViewById(R.id.txtIdData);
		txtMemberId = (TextView) view.findViewById(R.id.txtMemberId);
		txtMemberIdData = (TextView) view.findViewById(R.id.txtMemberIdData);
		txtGroupId = (TextView) view.findViewById(R.id.txtGroupId);
		txtGroupIdData = (TextView) view.findViewById(R.id.txtGroupIdData);
		txtEffectiveDate = (TextView) view.findViewById(R.id.txtEffectiveDate);
		txtEffectiveDateData = (TextView) view
				.findViewById(R.id.txtEffectiveDateData);
		txtPlan = (TextView) view.findViewById(R.id.txtPlan);
		txtPlanData = (TextView) view.findViewById(R.id.txtPlanData);
		txtOfficeVisitCoPayAmount = (TextView) view
				.findViewById(R.id.txtOfficeVisitCoPayAmount);
		txtOfficeVisitCoPayAmountData = (TextView) view
				.findViewById(R.id.txtOfficeVisitCoPayAmountData);
		txtDrugCoPayAmount = (TextView) view
				.findViewById(R.id.txtDrugCoPayAmount);
		txtDrugCoPayAmountData = (TextView) view
				.findViewById(R.id.txtDrugCoPayAmountData);
		txtHospitalCoPayAmount = (TextView) view
				.findViewById(R.id.txtHospitalCoPayAmount);
		txtHospitalCoPayAmountData = (TextView) view
				.findViewById(R.id.txtHospitalCoPayAmountData);
		txtEmergencyVisitCoPayAmount = (TextView) view
				.findViewById(R.id.txtEmergencyVisitCoPayAmount);
		txtEmergencyVisitCoPayAmountData = (TextView) view
				.findViewById(R.id.txtEmergencyVisitCoPayAmountData);
		txtDeductible = (TextView) view.findViewById(R.id.txtDeductible);
		txtDeductibleData = (TextView) view
				.findViewById(R.id.txtDeductibleData);
		txtHeader = (TextView) view.findViewById(R.id.txtHeader);

		/*txtId.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtIdData.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtMemberId.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtMemberIdData.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtGroupId.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtGroupIdData.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtEffectiveDate.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtEffectiveDateData.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtPlan.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtPlanData.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtOfficeVisitCoPayAmount.setTextColor(Color
				.parseColor(PreferenceManager.getButtonColor(getActivity())));
		txtOfficeVisitCoPayAmountData.setTextColor(Color
				.parseColor(PreferenceManager.getButtonColor(getActivity())));
		txtDrugCoPayAmount.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtDrugCoPayAmountData.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtHospitalCoPayAmount.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtHospitalCoPayAmountData.setTextColor(Color
				.parseColor(PreferenceManager.getButtonColor(getActivity())));
		txtEmergencyVisitCoPayAmount.setTextColor(Color
				.parseColor(PreferenceManager.getButtonColor(getActivity())));
		txtEmergencyVisitCoPayAmountData.setTextColor(Color
				.parseColor(PreferenceManager.getButtonColor(getActivity())));
		txtDeductible.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtDeductibleData.setTextColor(Color.parseColor(PreferenceManager
				.getButtonColor(getActivity())));
		txtHeader.setTextColor(Color.parseColor(PreferenceManager
				.getTextHeaderColor(getActivity())));*/
	}
}
