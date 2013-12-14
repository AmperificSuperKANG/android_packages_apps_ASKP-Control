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

package com.askp.control.Fragments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.askp.control.MainActivity;
import com.askp.control.R;
import com.askp.control.Utils.Control;
import com.askp.control.Utils.IoAlgorithmValues;
import com.askp.control.Utils.LayoutStyle;
import com.askp.control.Utils.Utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class IoAlgorithmFragment extends Fragment implements
		OnItemSelectedListener, OnSeekBarChangeListener {

	private static LinearLayout mLayout;

	private static String[] mAvailableInternalScheduler;
	private static Spinner mInternalSchedulerSpinner;
	public static int mCurInternalScheduler;
	public static int mCurInternalSchedulerRaw;

	private static String[] mAvailableExternalScheduler;
	private static Spinner mExternalSchedulerSpinner;
	public static int mCurExternalScheduler;
	public static int mCurExternalSchedulerRaw;

	private static SeekBar mInternalReadBar;
	private static TextView mInternalReadText;

	private static SeekBar mExternalReadBar;
	private static TextView mExternalReadText;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.layout, container, false);

		mLayout = (LinearLayout) rootView.findViewById(R.id.layout);

		// I/O Scheduler Title
		TextView mIOSchedulerTitle = new TextView(getActivity());
		LayoutStyle.setTextTitle(mIOSchedulerTitle,
				getString(R.string.ioscheduler), getActivity());

		// I/O Scheduler Summary
		TextView mIOSchedulerSummary = new TextView(getActivity());
		LayoutStyle.setTextSummary(mIOSchedulerSummary,
				getString(R.string.ioscheduler_summary), getActivity());

		if (Utils.existFile(IoAlgorithmValues.FILENAME_INTERNAL_SCHEDULER)
				|| Utils.existFile(IoAlgorithmValues.FILENAME_EXTERNAL_SCHEDULER)) {
			mLayout.addView(mIOSchedulerTitle);
			mLayout.addView(mIOSchedulerSummary);
		}

		// Internal Scheduler Title
		TextView mInternalSchedulerTitle = new TextView(getActivity());
		LayoutStyle.setTextSubTitle(mInternalSchedulerTitle,
				getString(R.string.internalscheduler), getActivity());

		// Internal Scheduler Spinner
		mAvailableInternalScheduler = IoAlgorithmValues.mInternalScheduler()
				.split(" ");

		List<String> mAvailableInternalSchedulerList = new ArrayList<String>(
				Arrays.asList(mAvailableInternalScheduler));

		mInternalSchedulerSpinner = new Spinner(getActivity());
		for (int i = 0; i < mAvailableInternalSchedulerList.size(); i++) {
			if (mAvailableInternalSchedulerList.get(i).indexOf("[") != -1) {
				mAvailableInternalScheduler[i] = mAvailableInternalScheduler[i]
						.replace("[", "").replace("]", "");
				ArrayAdapter<String> adapterInternalScheduler = new ArrayAdapter<String>(
						getActivity(), android.R.layout.simple_spinner_item,
						mAvailableInternalScheduler);
				adapterInternalScheduler
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				LayoutStyle.setSpinner(mInternalSchedulerSpinner,
						adapterInternalScheduler, i);
				mCurInternalScheduler = i;
			}
		}
		mInternalSchedulerSpinner.setOnItemSelectedListener(this);

		if (Utils.existFile(IoAlgorithmValues.FILENAME_INTERNAL_SCHEDULER)) {
			mLayout.addView(mInternalSchedulerTitle);
			mLayout.addView(mInternalSchedulerSpinner);
		}

		// External Scheduler Title
		TextView mExternalSchedulerTitle = new TextView(getActivity());
		LayoutStyle.setTextSubTitle(mExternalSchedulerTitle,
				getString(R.string.externalscheduler), getActivity());

		// External Scheduler Spinner
		mAvailableExternalScheduler = IoAlgorithmValues.mExternalScheduler()
				.split(" ");

		List<String> mAvailableExternalSchedulerList = new ArrayList<String>(
				Arrays.asList(mAvailableExternalScheduler));

		mExternalSchedulerSpinner = new Spinner(getActivity());
		for (int i = 0; i < mAvailableExternalSchedulerList.size(); i++) {
			if (mAvailableExternalSchedulerList.get(i).indexOf("[") != -1) {
				mAvailableExternalScheduler[i] = mAvailableExternalScheduler[i]
						.replace("[", "").replace("]", "");
				ArrayAdapter<String> adapterExternalScheduler = new ArrayAdapter<String>(
						getActivity(), android.R.layout.simple_spinner_item,
						mAvailableExternalScheduler);
				adapterExternalScheduler
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				LayoutStyle.setSpinner(mExternalSchedulerSpinner,
						adapterExternalScheduler, i);
				mCurExternalScheduler = i;
			}
		}
		mExternalSchedulerSpinner.setOnItemSelectedListener(this);

		if (Utils.existFile(IoAlgorithmValues.FILENAME_EXTERNAL_SCHEDULER)) {
			mLayout.addView(mExternalSchedulerTitle);
			mLayout.addView(mExternalSchedulerSpinner);
		}

		// Internal Read Title
		TextView mInternalReadTitle = new TextView(getActivity());
		LayoutStyle.setTextSubTitle(mInternalReadTitle,
				getString(R.string.internalreadahead), getActivity());

		// Internal Read SeekBar
		mInternalReadBar = new SeekBar(getActivity());
		LayoutStyle
				.setSeekBar(mInternalReadBar, 31, Integer.parseInt(String
						.valueOf(Integer.parseInt(IoAlgorithmValues
								.mInternalRead()) - 128)) / 128);
		mInternalReadBar.setOnSeekBarChangeListener(this);

		// Internal Read Text
		mInternalReadText = new TextView(getActivity());
		LayoutStyle.setCenterText(mInternalReadText,
				IoAlgorithmValues.mInternalRead() + " kB", getActivity());

		if (Utils.existFile(IoAlgorithmValues.FILENAME_INTERNAL_READ)) {
			mLayout.addView(mInternalReadTitle);
			mLayout.addView(mInternalReadBar);
			mLayout.addView(mInternalReadText);
		}

		// External Read Title
		TextView mExternalReadTitle = new TextView(getActivity());
		LayoutStyle.setTextSubTitle(mExternalReadTitle,
				getString(R.string.externalreadahead), getActivity());

		// External Read SeekBar
		mExternalReadBar = new SeekBar(getActivity());
		LayoutStyle
				.setSeekBar(mExternalReadBar, 31, Integer.parseInt(String
						.valueOf(Integer.parseInt(IoAlgorithmValues
								.mExternalRead()) - 128)) / 128);
		mExternalReadBar.setOnSeekBarChangeListener(this);

		// External Read Text
		mExternalReadText = new TextView(getActivity());
		LayoutStyle.setCenterText(mExternalReadText,
				IoAlgorithmValues.mExternalRead() + " kB", getActivity());

		if (Utils.existFile(IoAlgorithmValues.FILENAME_EXTERNAL_READ)) {
			mLayout.addView(mExternalReadTitle);
			mLayout.addView(mExternalReadBar);
			mLayout.addView(mExternalReadText);
		}

		return rootView;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		if (arg0.equals(mInternalSchedulerSpinner)) {
			mCurInternalSchedulerRaw = arg2;
			if (arg2 != mCurInternalScheduler) {
				MainActivity.enableButtons();
				MainActivity.mIoAlgorithmAction = true;
				Control.INTERNAL_SCHEDULER = mAvailableInternalScheduler[arg2];
			}
		} else if (arg0.equals(mExternalSchedulerSpinner)) {
			mCurExternalSchedulerRaw = arg2;
			if (arg2 != mCurExternalScheduler) {
				MainActivity.enableButtons();
				MainActivity.mIoAlgorithmAction = true;
				Control.EXTERNAL_SCHEDULER = mAvailableExternalScheduler[arg2];
			}
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		if (seekBar.equals(mInternalReadBar)) {
			mInternalReadText.setText(String.valueOf(progress * 128 + 128)
					+ " kB");
		} else if (seekBar.equals(mExternalReadBar)) {
			mExternalReadText.setText(String.valueOf(progress * 128 + 128)
					+ " kB");
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		MainActivity.enableButtons();
		MainActivity.mIoAlgorithmAction = true;
		if (seekBar.equals(mInternalReadBar)) {
			Control.INTERNAL_READ = mInternalReadText.getText().toString()
					.replace(" kB", "");
		} else if (seekBar.equals(mExternalReadBar)) {
			Control.EXTERNAL_READ = mExternalReadText.getText().toString()
					.replace(" kB", "");
		}
	}
}
