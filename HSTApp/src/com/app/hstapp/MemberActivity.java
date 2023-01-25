package com.app.hstapp;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.fragment.BlueShieldPlansFragment;
import com.app.fragment.FindProviderByNameFragment;
import com.app.fragment.FindProviderByNameFragment.OnAnotherAddressClick;
import com.app.fragment.FindProviderByNameFragment.OnProviderSearchClick;
import com.app.fragment.MemberDemographicFragment;
import com.app.fragment.MemberInsuranceInformationfragment;
import com.app.fragment.MemberPCPInformationfragment;
import com.app.fragment.ProviderListFragment;
import com.app.fragment.ProviderListFragment.OnProviderFragmentClick;
import com.app.fragment.ProviderMapFragment;
import com.app.fragment.ProviderMapFragment.OnGetAnotherAddress;
import com.app.fragment.ProviderSearchResultsFragment;
import com.app.fragment.ProviderSearchResultsFragment.OnSelectingSpecificProvider;
import com.app.fragment.ProviderSearchResultsFragment.OnViewMapClick;
import com.app.fragment.SettingsFragment;
import com.app.fragment.SideFragment.OnSideFragmentClick;
import com.app.fragment.SpecificProviderListFragment;
import com.app.fragment.SpecificProviderListFragment.OnNextClick;
import com.app.model.Provider;
import com.app.model.ProviderSearch;
import com.app.service.GcmIntentService;
import com.app.tasks.GetDirectionTask;
import com.app.utils.HSTApp;
import com.app.utils.HSTApp.TrackerName;
import com.app.utils.PreferenceManager;
import com.app.utils.Utility;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.maps.model.LatLng;


/**
 * This activity is parent member activity of all fragment
 */
public class MemberActivity extends BaseActivity implements OnSideFragmentClick,OnProviderFragmentClick,OnAnotherAddressClick,OnGetAnotherAddress,OnProviderSearchClick,OnSelectingSpecificProvider,OnNextClick,OnViewMapClick {
	TextView txtHeader;
	public static String TAG = "";

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		TAG = this.getClass().getName();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.member);
		String messages = PreferenceManager.getMissedMessages(this);
		if (!messages.equals("")) {
			Utility.showAlert(messages, this);
			PreferenceManager.setMissedMessages(this, "");
		}
		initControl();
		Tracker t = ((HSTApp) getApplication())
				.getTracker(TrackerName.APP_TRACKER);
		t.setScreenName(this.getClass().getName());
		t.send(new HitBuilders.AppViewBuilder().build());
	}

	/**
	 * To initialize the controls
	 */
	private void initControl() {
		RelativeLayout layoutHeader = (RelativeLayout) findViewById(R.id.layoutHeader);
		/*layoutHeader.setBackgroundColor(Color.parseColor(PreferenceManager
				.getButtonColor(this)));*/
		ImageView imgSlider = (ImageView) findViewById(R.id.imgSlider);
		ImageView imgLogo = (ImageView) findViewById(R.id.imgLogo);
		txtHeader = (TextView) findViewById(R.id.txtHeader);

		imgSlider.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showSlidingMenu();
			}
		});
		txtHeader.setText("Member Demographic");
		/*txtHeader.setTextColor(Color.parseColor(PreferenceManager
				.getTextHeaderColor(MemberActivity.this)));
		InputStream inputStream;
		try {
			byte[] decodedString = Base64.decode(
					PreferenceManager.getLogo(MemberActivity.this).getBytes(
							"UTF-8"), Base64.DEFAULT);
			inputStream = new ByteArrayInputStream(decodedString);
			Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
			imgLogo.setImageBitmap(bitmap);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}*/
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.layoutFragment, new MemberPCPInformationfragment());
		ft.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onPause() {
		Log.v(TAG, "onPause");
		super.onPause();
	}

	@Override
	protected void onStop() {
		Log.v(TAG, "On Stop");
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		Log.v(TAG, "onDestroy");
		super.onDestroy();
	}

	public void showSlidingMenu() {
		if (getSlidingMenu().isMenuShowing())
			showContent();
		else
			showMenu();
	}

	public void onNewIntent(Intent intent) {
		Log.v("TTT", "onNewIntent is called!" + intent.getExtras().toString());
		super.onNewIntent(intent);
		setIntent(intent);
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.v("TTT", "onresume");
		Intent intent = getIntent();
		if (intent != null) {
			Bundle extras = intent.getExtras();
			Log.v("TTT", "TTT" + "extras" + extras);
			if (extras != null) {
				Log.v("TTT", "TTT" + "extras not null" + extras.toString());
				for (String key : extras.keySet()) {
					Log.v("TTT", "TTT" + "extras" + extras.keySet() + ""
							+ extras.getString("message"));
					if (key.equals("message")) {
						Utility.showAlert(extras.getString("message"), this);
					}
				}
			}
		}
		NotificationManager mNotification = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mNotification.cancel(GcmIntentService.NOTIFICATION_ID);
		Intent intent2open = new Intent(this, MemberActivity.class);
		// intent2open.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent2open.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
				| Intent.FLAG_ACTIVITY_CLEAR_TOP);
		setIntent(intent2open);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onMemberDemoGraphic() {
		txtHeader.setText("Member Demographic");
		// MemberDemographicFragment memberDemographicFragment = new
		// MemberDemographicFragment();
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.layoutFragment, new MemberDemographicFragment());
		ft.commit();

	}

	@Override
	public void onMemberInsuranceInformation() {
		txtHeader.setText("Member Insurance");
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.layoutFragment,
				new MemberInsuranceInformationfragment());
		ft.commit();
	}

	@Override
	public void onMemberPCPInformation() {
		txtHeader.setText("Member PCP Information");
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.layoutFragment, new MemberPCPInformationfragment());
		ft.commit();
	}

	@Override
	public void onListOfAuthorizedproviders() {
		txtHeader.setText("Find Providers");
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.layoutFragment, new ProviderListFragment());
		ft.addToBackStack(FragmentName.providerListFragment.toString()).commit();
	}

	@Override
	public void onSettings() {
		txtHeader.setText("Settings");
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.layoutFragment, new SettingsFragment());
		ft.commit();
	}

	@Override
	public void onProviderCategory(ProviderSearch providerSearch) {
		if(providerSearch != null)
			txtHeader.setText("Find a "+providerSearch.getCategoryName());
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		Bundle bundle = new Bundle();
		bundle.putSerializable("providerSearch",providerSearch);
		// set Fragment class Arguments
		FindProviderByNameFragment findProviderByNameFragment = new FindProviderByNameFragment();
		findProviderByNameFragment.setArguments(bundle);
		ft.replace(R.id.layoutFragment, findProviderByNameFragment);
		ft.addToBackStack(FragmentName.findProviderByNameFragment.toString()).commit();
	}

	@Override
	public void OnProviderMap(ProviderSearch providerSearch,Provider provider,ArrayList<Provider> providerList) {
		txtHeader.setText("Map For Another Address");
		Bundle bundle = new Bundle();
		bundle.putSerializable("providerSearch",providerSearch);
		bundle.putSerializable("provider",provider);
		bundle.putSerializable("ProviderList", providerList);
//		bundle.putBoolean("ISAnother", true);
		// set Fragment class Arguments
		ProviderMapFragment providerMapFragment = new ProviderMapFragment();
		providerMapFragment.setArguments(bundle);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.layoutFragment, providerMapFragment);
		ft.addToBackStack(FragmentName.providerMapFragment.toString()).commit();
	}

	@Override
	public void OnProviderSearch(ArrayList<Provider> providerList , ProviderSearch providerSearch) {
		txtHeader.setText("Search Results");
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		Bundle bundle = new Bundle();
		bundle.putSerializable("ProviderList", providerList);
		bundle.putSerializable("providerSearch",providerSearch);
		Log.v("TTT",providerSearch.getLat() + "  ");
		// set Fragmentclass Arguments
		ProviderSearchResultsFragment providerSearchResultsFragment = new ProviderSearchResultsFragment();
		providerSearchResultsFragment.setArguments(bundle);
		ft.replace(R.id.layoutFragment, providerSearchResultsFragment);
		ft.addToBackStack(FragmentName.providerSearchResultsFragment.toString()).commit();
	}

	@Override
	public void OnSelectSpecificProvider(Provider provider) {
		if(provider != null)
			txtHeader.setText(provider.getFirstName() + " " + provider.getLastName());
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		Bundle bundle = new Bundle();
		bundle.putSerializable("provider", provider);
		
		// set Fragment class Arguments
		SpecificProviderListFragment specificProviderListFragment = new SpecificProviderListFragment();
		specificProviderListFragment.setArguments(bundle);
		ft.replace(R.id.layoutFragment, specificProviderListFragment);
		ft.addToBackStack(FragmentName.specificProviderListFragment.toString()).commit();
	}

	@Override
	public void OnProviderMap(Provider provider) {
		txtHeader.setText("Map For Doctor");
		Bundle bundle = new Bundle();
		bundle.putSerializable("providerSearch",null);
		bundle.putSerializable("provider",provider);
		bundle.putSerializable("ProviderList", null);
		// set Fragment class Arguments
		ProviderMapFragment providerMapFragment = new ProviderMapFragment();
		providerMapFragment.setArguments(bundle);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.layoutFragment, providerMapFragment);
		new GetDirectionTask(MemberActivity.this, new LatLng(provider.getLatitude(), provider.getLongitude()),providerMapFragment).execute();
		ft.addToBackStack(FragmentName.providerMapFragment.toString()).commit();
	}

	@Override
	public void OnBlueShiledPlans(ArrayList<String> strings) {
		txtHeader.setText("Blue Shield Plans");
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		Bundle bundle = new Bundle();
		bundle.putStringArrayList("plans", strings);
		
		// set Fragment class Arguments
		BlueShieldPlansFragment blueShieldPlansFragment = new BlueShieldPlansFragment();
		blueShieldPlansFragment.setArguments(bundle);
		ft.replace(R.id.layoutFragment, blueShieldPlansFragment);
		ft.addToBackStack(FragmentName.blueShieldPlansFragment.toString()).commit();
	}
	
	@Override
	public void onRefineSearch() {
		txtHeader.setText("Find Providers");
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.layoutFragment, new ProviderListFragment());
		ft.addToBackStack(FragmentName.providerListFragment.toString()).commit();
	
	}
	

	/**
	 * @Purpose Enum of Fragment name
	 */
	public enum FragmentName{
		providerListFragment(1),
		findProviderByNameFragment(2),
		providerSearchResultsFragment(3),
		providerMapFragment(4),
		specificProviderListFragment(5),
		blueShieldPlansFragment(6);

		public final int value;
		FragmentName(int value) {
			this.value = value;
		}

	    public int getValue() {
	        return value;
	    }

	    public static FragmentName getNameByCode(int code){
	    	for(FragmentName e : FragmentName.values()){
	    		if(code == e.value) return e;
	    	}
	    	return null;
    	}
	}



}
