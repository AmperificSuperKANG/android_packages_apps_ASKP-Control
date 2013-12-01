package com.askp_control;

import java.util.Locale;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.askp_control.Fragments.CpuFragment;
import com.askp_control.Fragments.InformationFragment;
import com.askp_control.Utils.Control;
import com.askp_control.Utils.CpuValues;
import com.askp_control.Utils.Utils;
import com.stericson.RootTools.RootTools;

public class MainActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	private static SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private static ViewPager mViewPager;

	private static MenuItem applyButton;

	public static boolean mChange = false;
	public static String mAction = "";

	private static ActionThread mActionThread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		CpuValues.mMaxFreq();
		CpuValues.mMinFreq();
		CpuValues.mCurGovernor();

		if (RootTools.isRootAvailable()) {
			if (RootTools.isAccessGiven()) {
				if (!RootTools.isBusyboxAvailable()) {
					Utils.toast(getString(R.string.nobusybox), this);
					RootTools.offerBusyBox(this);
					finish();
				}
			} else {
				Utils.toast(getString(R.string.norootaccess), this);
				finish();
			}
		} else {
			Utils.toast(getString(R.string.noroot), this);
			RootTools.offerSuperUser(this);
			finish();
		}

		RootTools.debugMode = true;
		Utils.saveString("kernelversion", Utils.getFormattedKernelVersion(),
				this);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		applyButton = menu.findItem(R.id.action_apply);
		applyButton.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_WITH_TEXT
				| MenuItem.SHOW_AS_ACTION_ALWAYS);
		applyButton.setVisible(false);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_apply:
			Control.setValues(getApplicationContext());
			Control.setValuesback(getApplicationContext());
			applyButton.setVisible(false);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = null;
			switch (position) {
			case 0:
				fragment = new CpuFragment();
				break;
			case 1:
				fragment = new InformationFragment();
				break;
			default:
				break;
			}
			Bundle args = new Bundle();
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.cpu).toUpperCase(l);
			case 1:
				return getString(R.string.information).toUpperCase(l);
			}
			return null;
		}
	}

	@Override
	public void onResume() {
		mActionThread = new ActionThread();
		mActionThread.start();
		super.onResume();
	}

	protected class ActionThread extends Thread {
		@Override
		public void run() {
			try {
				while (true) {
					mActionHandler.sendMessage(mActionHandler.obtainMessage(0,
							mAction));
					sleep(100);
				}
			} catch (InterruptedException e) {
			}
		}
	}

	protected Handler mActionHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (mChange) {
				applyButton.setVisible(true);
				mChange = false;
			}
		}
	};
}
