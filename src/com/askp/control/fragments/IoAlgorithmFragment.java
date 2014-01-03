/*
 * Copyright (C) 2014 AmperificSuperKANG Project
 *
 * This file is part of ASKP Control.
 *
 * ASKP Control is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ASKP Control is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ASKP Control.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.askp.control.fragments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.askp.control.R;
import com.askp.control.activities.MainActivity;
import com.askp.control.utils.IoAlgorithmValues;
import com.askp.control.utils.LayoutStyle;
import com.askp.control.utils.Utils;
import com.askp.control.utils.ValueEditor;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
		OnItemSelectedListener, OnSeekBarChangeListener, OnClickListener {
	private static Context context;

	private static OnItemSelectedListener ItemSelectedListener;
	private static OnSeekBarChangeListener SeekBarChangeListener;
	private static OnClickListener ClickListener;

	private static LinearLayout mLayout;

	public static String[] mAvailableInternalScheduler;
	public static Spinner mInternalSchedulerSpinner;
	public static int mCurInternalScheduler;

	public static String[] mAvailableExternalScheduler;
	public static Spinner mExternalSchedulerSpinner;
	public static int mCurExternalScheduler;

	private static SeekBar mInternalReadBar;
	public static TextView mInternalReadText;

	private static SeekBar mExternalReadBar;
	public static TextView mExternalReadText;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		View rootView = inflater.inflate(R.layout.layout, container, false);

		mLayout = (LinearLayout) rootView.findViewById(R.id.layout);
		ItemSelectedListener = this;
		SeekBarChangeListener = this;
		ClickListener = this;
		setContent();

		return rootView;
	}

	public static void setContent() {
		mLayout.removeAllViews();

		// I/O Scheduler Title
		TextView mIOSchedulerTitle = new TextView(context);
		LayoutStyle.setTextTitle(mIOSchedulerTitle,
				context.getString(R.string.ioscheduler), context);

		// I/O Scheduler Summary
		TextView mIOSchedulerSummary = new TextView(context);
		LayoutStyle.setTextSummary(mIOSchedulerSummary,
				context.getString(R.string.ioscheduler_summary));

		if (Utils.existFile(IoAlgorithmValues.FILENAME_INTERNAL_SCHEDULER)
				|| Utils.existFile(IoAlgorithmValues.FILENAME_EXTERNAL_SCHEDULER)) {
			mLayout.addView(mIOSchedulerTitle);
			mLayout.addView(mIOSchedulerSummary);
		}

		// Internal Scheduler Title
		TextView mInternalSchedulerTitle = new TextView(context);
		LayoutStyle.setTextSubTitle(mInternalSchedulerTitle,
				context.getString(R.string.internalscheduler), context);

		// Internal Scheduler Spinner
		mAvailableInternalScheduler = IoAlgorithmValues.mInternalScheduler()
				.split(" ");

		List<String> mAvailableInternalSchedulerList = new ArrayList<String>(
				Arrays.asList(mAvailableInternalScheduler));

		mInternalSchedulerSpinner = new Spinner(context);
		for (int i = 0; i < mAvailableInternalSchedulerList.size(); i++) {
			if (mAvailableInternalSchedulerList.get(i).indexOf("[") != -1) {
				mAvailableInternalScheduler[i] = mAvailableInternalScheduler[i]
						.replace("[", "").replace("]", "");
				ArrayAdapter<String> adapterInternalScheduler = new ArrayAdapter<String>(
						context, android.R.layout.simple_spinner_item,
						mAvailableInternalScheduler);
				adapterInternalScheduler
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				LayoutStyle.setSpinner(mInternalSchedulerSpinner,
						adapterInternalScheduler, i);
				mCurInternalScheduler = i;
			}
		}
		mInternalSchedulerSpinner
				.setOnItemSelectedListener(ItemSelectedListener);

		if (Utils.existFile(IoAlgorithmValues.FILENAME_INTERNAL_SCHEDULER)) {
			mLayout.addView(mInternalSchedulerTitle);
			mLayout.addView(mInternalSchedulerSpinner);
		}

		// External Scheduler Title
		TextView mExternalSchedulerTitle = new TextView(context);
		LayoutStyle.setTextSubTitle(mExternalSchedulerTitle,
				context.getString(R.string.externalscheduler), context);

		// External Scheduler Spinner
		mAvailableExternalScheduler = IoAlgorithmValues.mExternalScheduler()
				.split(" ");

		List<String> mAvailableExternalSchedulerList = new ArrayList<String>(
				Arrays.asList(mAvailableExternalScheduler));

		mExternalSchedulerSpinner = new Spinner(context);
		for (int i = 0; i < mAvailableExternalSchedulerList.size(); i++) {
			if (mAvailableExternalSchedulerList.get(i).indexOf("[") != -1) {
				mAvailableExternalScheduler[i] = mAvailableExternalScheduler[i]
						.replace("[", "").replace("]", "");
				ArrayAdapter<String> adapterExternalScheduler = new ArrayAdapter<String>(
						context, android.R.layout.simple_spinner_item,
						mAvailableExternalScheduler);
				adapterExternalScheduler
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				LayoutStyle.setSpinner(mExternalSchedulerSpinner,
						adapterExternalScheduler, i);
				mCurExternalScheduler = i;
			}
		}
		mExternalSchedulerSpinner
				.setOnItemSelectedListener(ItemSelectedListener);

		if (Utils.existFile(IoAlgorithmValues.FILENAME_EXTERNAL_SCHEDULER)) {
			mLayout.addView(mExternalSchedulerTitle);
			mLayout.addView(mExternalSchedulerSpinner);
		}

		// Internal Read Title
		TextView mInternalReadTitle = new TextView(context);
		LayoutStyle.setTextSubTitle(mInternalReadTitle,
				context.getString(R.string.internalreadahead), context);

		// Internal Read SeekBar
		mInternalReadBar = new SeekBar(context);
		LayoutStyle
				.setSeekBar(
						mInternalReadBar,
						31,
						(Integer.parseInt(IoAlgorithmValues.mInternalRead()) - 128) / 128);
		mInternalReadBar.setOnSeekBarChangeListener(SeekBarChangeListener);

		// Internal Read Text
		mInternalReadText = new TextView(context);
		LayoutStyle.setCenterText(mInternalReadText,
				IoAlgorithmValues.mInternalRead() + " kB");
		mInternalReadText.setOnClickListener(ClickListener);

		if (Utils.existFile(IoAlgorithmValues.FILENAME_INTERNAL_READ)) {
			mLayout.addView(mInternalReadTitle);
			mLayout.addView(mInternalReadBar);
			mLayout.addView(mInternalReadText);
		}

		// External Read Title
		TextView mExternalReadTitle = new TextView(context);
		LayoutStyle.setTextSubTitle(mExternalReadTitle,
				context.getString(R.string.externalreadahead), context);

		// External Read SeekBar
		mExternalReadBar = new SeekBar(context);
		LayoutStyle
				.setSeekBar(
						mExternalReadBar,
						31,
						(Integer.parseInt(IoAlgorithmValues.mExternalRead()) - 128) / 128);
		mExternalReadBar.setOnSeekBarChangeListener(SeekBarChangeListener);

		// External Read Text
		mExternalReadText = new TextView(context);
		LayoutStyle.setCenterText(mExternalReadText,
				IoAlgorithmValues.mExternalRead() + " kB");
		mExternalReadText.setOnClickListener(ClickListener);

		if (Utils.existFile(IoAlgorithmValues.FILENAME_EXTERNAL_READ)) {
			mLayout.addView(mExternalReadTitle);
			mLayout.addView(mExternalReadBar);
			mLayout.addView(mExternalReadText);
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		if (arg0.equals(mInternalSchedulerSpinner)) {
			if (arg2 != mCurInternalScheduler) {
				MainActivity.showButtons(true);
				MainActivity.mIoAlgorithmAction = true;
			}
		} else if (arg0.equals(mExternalSchedulerSpinner)) {
			if (arg2 != mCurExternalScheduler) {
				MainActivity.showButtons(true);
				MainActivity.mIoAlgorithmAction = true;
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
		MainActivity.showButtons(true);
		MainActivity.mIoAlgorithmAction = true;
	}

	@Override
	public void onClick(View v) {
		if (v.equals(mInternalReadText))
			ValueEditor.showSeekBarEditor(mInternalReadBar, mInternalReadText
					.getText().toString().replace(" kB", ""),
					getString(R.string.internalreadahead), 128, 128, context);
		else if (v.equals(mExternalReadText))
			ValueEditor.showSeekBarEditor(mExternalReadBar, mExternalReadText
					.getText().toString().replace(" kB", ""),
					getString(R.string.externalreadahead), 128, 128, context);
	}
}
