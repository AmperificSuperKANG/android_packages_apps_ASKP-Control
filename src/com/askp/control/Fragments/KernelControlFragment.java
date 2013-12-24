/*
 * Copyright (C) 2013 AmperificSuperKANG Project
 *
 * getActivity() program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 3 as published by the Free Software Foundation.
 *
 * getActivity() program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with getActivity() program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */

package com.askp.control.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.askp.control.R;
import com.askp.control.Utils.Control;
import com.askp.control.Utils.Utils;
import com.stericson.RootTools.RootTools;

public class KernelControlFragment extends Fragment {

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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.kernelcontrol, container,
				false);

		if (RootTools.isRootAvailable()) {
			if (RootTools.isAccessGiven()) {
				if (!RootTools.isBusyboxAvailable()) {
					Utils.toast(getString(R.string.nobusybox), getActivity());
					RootTools.offerBusyBox(getActivity());
					getActivity().finish();
				}
			} else {
				Utils.toast(getString(R.string.norootaccess), getActivity());
				getActivity().finish();
			}
		} else {
			Utils.toast(getString(R.string.noroot), getActivity());
			RootTools.offerSuperUser(getActivity());
			getActivity().finish();
		}

		RootTools.debugMode = true;
		Utils.saveString("kernelversion", Utils.getFormattedKernelVersion(),
				getActivity());

		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.kernelcontrol, menu);
		applyButton = menu.findItem(R.id.action_apply);
		cancelButton = menu.findItem(R.id.action_cancel);
		applyButton.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_WITH_TEXT
				| MenuItem.SHOW_AS_ACTION_ALWAYS);
		cancelButton.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_WITH_TEXT
				| MenuItem.SHOW_AS_ACTION_ALWAYS);
		showButtons(false);
		setonbootBox = menu.findItem(R.id.action_setonboot).setChecked(
				Utils.getBoolean("setonboot", getActivity()));
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_apply:
			Control.initControl(getActivity());
			break;
		case R.id.action_cancel:
			Control.exitControl(getActivity());
			break;
		case R.id.action_setonboot:
			setonbootBox.setChecked(Utils
					.getBoolean("setonboot", getActivity()) ? false : true);
			Utils.saveBoolean(
					"setonboot",
					Utils.getBoolean("setonboot", getActivity()) ? false : true,
					getActivity());
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
			}
			return fragment;
		}

		@Override
		public int getCount() {
			return 4;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return getString(R.string.cpu).toUpperCase();
			case 1:
				return getString(R.string.gpu).toUpperCase() + " & "
						+ getString(R.string.display).toUpperCase();
			case 2:
				return getString(R.string.ioalgorithm).toUpperCase();
			case 3:
				return getString(R.string.miscellaneous).toUpperCase();
			}
			return getString(R.string.unavailable);
		}
	}

	public static void showButtons(boolean visible) {
		applyButton.setVisible(visible);
		cancelButton.setVisible(visible);
	}
}