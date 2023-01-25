package com.app.fragment;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.app.hstapp.R;
import com.app.utils.PreferenceManager;

public class BlueShieldPlansFragment extends Fragment {
	
	ListView listBlueShiledPlans;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.blueshield_plans_list,
				container, false);
		initializeControl(view,savedInstanceState);
		return view;
	}

	private void initializeControl(View view, Bundle savedInstanceState) {
		
		TextView txtAcceptedBlueShield = (TextView)view.findViewById(R.id.txtAcceptedBlueShield);
	/*	txtAcceptedBlueShield.setTextColor(Color.parseColor(PreferenceManager
				.getTextColor(getActivity())));*/
		if(getArguments() != null) {
		@SuppressWarnings("unchecked")
		ArrayList<String> arrayList= (ArrayList<String>) getArguments().getSerializable("plans");
		listBlueShiledPlans = (ListView) view.findViewById(R.id.listBlueShiledPlans);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,arrayList){
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
			    View view = super.getView(position, convertView, parent);
			    TextView text = (TextView) view.findViewById(android.R.id.text1);
			   /* text.setTextColor(Color.parseColor(PreferenceManager
						.getTextColor(getActivity())));*/
			    return view;
			}
		};
		listBlueShiledPlans.setAdapter(adapter);
	}
	}
}
