/*
 * Copyright (C) 2013 AmperificSuperKANG Project
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 3 as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */

package com.askp.control;

import java.util.Locale;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.askp.control.Fragments.CpuFragment;
import com.askp.control.Fragments.DownloadFragment;
import com.askp.control.Fragments.GpuDisplayFragment;
import com.askp.control.Fragments.InformationFragment;
import com.askp.control.Fragments.IoAlgorithmFragment;
import com.askp.control.Fragments.MiscellaneousFragment;
import com.askp.control.Fragments.NewsFragment;
import com.askp.control.Utils.Control;
import com.askp.control.Utils.CpuValues;
import com.askp.control.Utils.Utils;
import com.stericson.RootTools.RootTools;

public class MainActivity extends FragmentActivity {

	private static SectionsPagerAdapter mSectionsPagerAdapter;

	private static ViewPager mViewPager;

	public static MenuItem applyButton;
	public static MenuItem cancelButton;
	private static MenuItem setonbootBox;

	public static boolean mCpuAction = false;
	public static boolean mGpuDisplayAction = false;
	public static boolean mIoAlgorithmAction = false;
	public static boolean mMiscellaneousAction = false;

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

		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		applyButton = menu.findItem(R.id.action_apply);
		cancelButton = menu.findItem(R.id.action_cancel);
		applyButton.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_WITH_TEXT
				| MenuItem.SHOW_AS_ACTION_ALWAYS);
		cancelButton.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_WITH_TEXT
				| MenuItem.SHOW_AS_ACTION_ALWAYS);
		disableButtons();
		setonbootBox = menu.findItem(R.id.action_setonboot).setChecked(
				Utils.getBoolean("setonboot", getApplicationContext()));
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_apply:
			if (mCpuAction)
				Control.setCpuValues(getApplicationContext());
			if (mGpuDisplayAction)
				Control.setGpuDisplayValues(getApplicationContext());
			if (mIoAlgorithmAction)
				Control.setIoAlgorithmValues(getApplicationContext());
			if (mMiscellaneousAction)
				Control.setMiscellaneousValues(getApplicationContext());
			mCpuAction = false;
			mGpuDisplayAction = false;
			mIoAlgorithmAction = false;
			mMiscellaneousAction = false;
			Utils.toast(getString(R.string.valuesapplied),
					getApplicationContext());
			Control.setValuesback(getApplicationContext());
			disableButtons();
			break;
		case R.id.action_cancel:
			Control.setValuesback(getApplicationContext());
			if (mCpuAction)
				CpuFragment.setValues();
			if (mGpuDisplayAction)
				GpuDisplayFragment.setValues();
			if (mIoAlgorithmAction)
				IoAlgorithmFragment.setValues();
			if (mMiscellaneousAction)
				MiscellaneousFragment.setValues();
			disableButtons();
			break;
		case R.id.action_setonboot:
			if (Utils.getBoolean("setonboot", getApplicationContext())) {
				setonbootBox.setChecked(false);
				Utils.saveBoolean("setonboot", false, getApplicationContext());
			} else {
				setonbootBox.setChecked(true);
				Utils.saveBoolean("setonboot", true, getApplicationContext());
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = null;
			switch (position) {
			case 0:
				fragment = new CpuFragment();
				break;
			case 1:
				fragment = new GpuDisplayFragment();
				break;
			case 2:
				fragment = new IoAlgorithmFragment();
				break;
			case 3:
				fragment = new MiscellaneousFragment();
				break;
			case 4:
				fragment = new DownloadFragment();
				break;
			case 5:
				fragment = new NewsFragment();
				break;
			case 6:
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
			return 7;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.cpu).toUpperCase(l);
			case 1:
				return getString(R.string.gpu).toUpperCase(l) + " & "
						+ getString(R.string.display).toUpperCase(l);
			case 2:
				return getString(R.string.ioalgorithm).toUpperCase(l);
			case 3:
				return getString(R.string.miscellaneous).toUpperCase(l);
			case 4:
				return getString(R.string.download).toUpperCase(l);
			case 5:
				return getString(R.string.news).toUpperCase(l);
			case 6:
				return getString(R.string.information).toUpperCase(l);
			}
			return null;
		}
	}

	public static void enableButtons() {
		applyButton.setVisible(true);
		cancelButton.setVisible(true);
	}

	public static void disableButtons() {
		applyButton.setVisible(false);
		cancelButton.setVisible(false);
	}
}
