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

package com.askp.control.fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.askp.control.R;
import com.askp.control.cpuspy.CpuSpyApp;
import com.askp.control.cpuspy.CpuStateMonitor;
import com.askp.control.cpuspy.CpuStateMonitor.CpuState;
import com.askp.control.cpuspy.CpuStateMonitor.CpuStateMonitorException;
import com.askp.control.utils.Utils;

public class InformationFragment extends Fragment {

	private static final String mManufacturer = Build.MANUFACTURER;
	public static final String mModel = Build.MODEL;
	private static final String mCode = Build.DEVICE;
	private static View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		rootView = inflater.inflate(R.layout.info, container, false);

		TextView mDeviceText = (TextView) rootView
				.findViewById(R.id.devicetext);
		mDeviceText.setText(mManufacturer.substring(0, 1).toUpperCase()
				+ mManufacturer.substring(1) + " " + mModel);

		TextView mCodenameText = (TextView) rootView
				.findViewById(R.id.codenametext);
		mCodenameText.setText(mCode);

		TextView mKernelVersionText = (TextView) rootView
				.findViewById(R.id.kernelversiontext);
		mKernelVersionText.setText(getString(R.string.unavailable));
		try {
			mKernelVersionText.setText(Utils.readLine("/proc/version"));
		} catch (IOException e) {
		}

		CpuSpy(savedInstanceState);

		return rootView;
	}

	// -----------------------------------------------------------------------------
	//
	// (C) Brandon Valosek, 2011 <bvalosek@gmail.com>
	//
	// -----------------------------------------------------------------------------

	// the views
	private static LinearLayout _uiStatesView = null;
	private static TextView _uiAdditionalStates = null;
	private static TextView _uiTotalStateTime = null;
	private static TextView _uiHeaderAdditionalStates = null;
	private static TextView _uiHeaderTotalStateTime = null;

	/** whether or not we're updating the data in the background */
	private boolean _updatingData = false;

	private void CpuSpy(Bundle savedInstanceState) {
		// inflate the view, stash the app context, and get all UI elements
		findViews();

		// see if we're updating data during a config change (rotate screen)
		if (savedInstanceState != null) {
			_updatingData = savedInstanceState.getBoolean("updatingData");
		}
	}

	private static void findViews() {
		_uiStatesView = (LinearLayout) rootView
				.findViewById(R.id.ui_states_view);
		_uiAdditionalStates = (TextView) rootView
				.findViewById(R.id.ui_additional_states);
		_uiHeaderAdditionalStates = (TextView) rootView
				.findViewById(R.id.ui_header_additional_states);
		_uiHeaderTotalStateTime = (TextView) rootView
				.findViewById(R.id.ui_header_total_state_time);
		_uiTotalStateTime = (TextView) rootView
				.findViewById(R.id.ui_total_state_time);
	}

	/** When the activity is about to change orientation */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean("updatingData", _updatingData);
	}

	/** Update the view when the application regains focus */
	@Override
	public void onResume() {
		super.onResume();
		refreshData();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.info, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// what it do mayne
		switch (item.getItemId()) {
		/* pressed the load menu button */
		case R.id.action_refresh:
			refreshData();
			break;
		case R.id.action_reset:
			try {
				CpuSpyApp.getCpuStateMonitor().setOffsets();
			} catch (CpuStateMonitorException e) {
			}
			CpuSpyApp.saveOffsets(getActivity());
			updateView();
			break;
		case R.id.action_restore:
			CpuSpyApp.getCpuStateMonitor().removeOffsets();
			CpuSpyApp.saveOffsets(getActivity());
			updateView();
			break;
		}
		// made it
		return false;
	}

	/** Generate and update all UI elements */
	public void updateView() {
		/**
		 * Get the CpuStateMonitor from the app, and iterate over all states,
		 * creating a row if the duration is > 0 or otherwise marking it in
		 * extraStates (missing)
		 */
		CpuStateMonitor monitor = CpuSpyApp.getCpuStateMonitor();
		_uiStatesView.removeAllViews();
		List<String> extraStates = new ArrayList<String>();
		for (CpuState state : monitor.getStates())
			if (state.duration > 0)
				generateStateRow(state, _uiStatesView);
			else if (state.freq == 0)
				extraStates.add(getString(R.string.deepsleep));
			else
				extraStates.add(state.freq / 1000 + " MHz");

		// show the red warning label if no states found
		if (monitor.getStates().size() == 0) {
			_uiHeaderTotalStateTime.setVisibility(View.GONE);
			_uiTotalStateTime.setVisibility(View.GONE);
			_uiStatesView.setVisibility(View.GONE);
		}

		// update the total state time
		long totTime = monitor.getTotalStateTime() / 100;
		_uiTotalStateTime.setText(sToString(totTime));

		// for all the 0 duration states, add the the Unused State area
		if (extraStates.size() > 0) {
			int n = 0;
			String str = "";

			for (String s : extraStates) {
				if (n++ > 0)
					str += ", ";
				str += s;
			}

			_uiAdditionalStates.setVisibility(View.VISIBLE);
			_uiHeaderAdditionalStates.setVisibility(View.VISIBLE);
			_uiAdditionalStates.setText(str);
		} else {
			_uiAdditionalStates.setVisibility(View.GONE);
			_uiHeaderAdditionalStates.setVisibility(View.GONE);
		}
	}

	/** Attempt to update the time-in-state info */
	public void refreshData() {
		if (!_updatingData)
			new RefreshStateDataTask().execute((Void) null);
	}

	/** @return A nicely formatted String representing tSec seconds */
	private static String sToString(long tSec) {
		long h = (long) Math.floor(tSec / (60 * 60));
		long m = (long) Math.floor((tSec - h * 60 * 60) / 60);
		long s = tSec % 60;
		String sDur;
		sDur = h + ":";
		if (m < 10)
			sDur += "0";
		sDur += m + ":";
		if (s < 10)
			sDur += "0";
		sDur += s;

		return sDur;
	}

	/**
	 * @return a View that correpsonds to a CPU freq state row as specified by
	 *         the state parameter
	 */
	private View generateStateRow(CpuState state, ViewGroup parent) {
		// inflate the XML into a view in the parent
		LayoutInflater inflater = ((Activity) getActivity())
				.getLayoutInflater();
		LinearLayout theRow = (LinearLayout) inflater.inflate(
				R.layout.state_row, parent, false);

		// what percetnage we've got
		CpuStateMonitor monitor = CpuSpyApp.getCpuStateMonitor();
		float per = (float) state.duration * 100 / monitor.getTotalStateTime();
		String sPer = (int) per + "%";

		// state name
		String sFreq;
		if (state.freq == 0)
			sFreq = "Deep Sleep";
		else
			sFreq = state.freq / 1000 + " MHz";

		// duration
		long tSec = state.duration / 100;
		String sDur = sToString(tSec);

		// map UI elements to objects
		TextView freqText = (TextView) theRow.findViewById(R.id.ui_freq_text);
		TextView durText = (TextView) theRow
				.findViewById(R.id.ui_duration_text);
		TextView perText = (TextView) theRow
				.findViewById(R.id.ui_percentage_text);
		ProgressBar bar = (ProgressBar) theRow.findViewById(R.id.ui_bar);

		// modify the row
		freqText.setText(sFreq);
		perText.setText(sPer);
		durText.setText(sDur);
		bar.setProgress((int) per);

		// add it to parent and return
		parent.addView(theRow);
		return theRow;
	}

	/** Keep updating the state data off the UI thread for slow devices */
	protected class RefreshStateDataTask extends AsyncTask<Void, Void, Void> {

		/** Stuff to do on a seperate thread */
		@Override
		protected Void doInBackground(Void... v) {
			CpuStateMonitor monitor = CpuSpyApp.getCpuStateMonitor();
			try {
				monitor.updateStates();
			} catch (CpuStateMonitorException e) {
				Log.e(getString(R.string.app_name),
						"Problem getting CPU states");
			}

			return null;
		}

		/** Executed on the UI thread right before starting the task */
		@Override
		protected void onPreExecute() {
			log("starting data update");
			_updatingData = true;
		}

		/** Executed on UI thread after task */
		@Override
		protected void onPostExecute(Void v) {
			log("finished data update");
			_updatingData = false;
			updateView();
		}
	}

	/** logging */
	private void log(String s) {
		Log.d(getString(R.string.app_name), s);
	}
}