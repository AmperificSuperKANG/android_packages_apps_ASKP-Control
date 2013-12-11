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

import com.askp.control.MainActivity;
import com.askp.control.R;
import com.askp.control.Utils.Control;
import com.askp.control.Utils.LayoutStyle;
import com.askp.control.Utils.MiscellaneousValues;
import com.askp.control.Utils.Utils;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MiscellaneousFragment extends Fragment implements
		OnCheckedChangeListener, OnItemSelectedListener,
		OnSeekBarChangeListener {
	private static Context context;

	private static LinearLayout mLayout;

	private static CheckBox mWifiHighBox;
	private static boolean mWifiHighBoolean;

	private static String[] mAvailableTCPCongestion;
	private static Spinner mTCPCongestionSpinner;
	public static int mTCPCongestion;
	public static int mTCPCongestionRaw;
	private static ArrayAdapter<String> adapterTCPCongestion;

	private static CheckBox mFastChargeBox;
	private static boolean mFastChargeBoolean;

	private static SeekBar mBatteryExtenderBar;
	private static TextView mBatteryExtenderText;

	private static CheckBox mSoundHighBox;
	private static boolean mSoundHighBoolean;

	private static SeekBar mHeadphoneBoostBar;
	private static TextView mHeadphoneBoostText;

	private static CheckBox mDynamicFsyncBox;
	private static boolean mDynamicFsyncBoolean;

	private static CheckBox mFsyncControlBox;
	private static boolean mFsyncControlBoolean;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.layout, container, false);
		context = getActivity();

		mLayout = (LinearLayout) rootView.findViewById(R.id.layout);

		// Network Title
		TextView mNetworkTitle = new TextView(getActivity());
		LayoutStyle.setTextTitle(mNetworkTitle, getString(R.string.network),
				getActivity());

		if (Utils.existFile(MiscellaneousValues.FILENAME_WIFI_HIGH))
			mLayout.addView(mNetworkTitle);

		// Wifi High Summary
		TextView mWifiHighSummary = new TextView(getActivity());
		LayoutStyle.setTextSummary(mWifiHighSummary,
				getString(R.string.wifihigh_summary), getActivity());

		// Wifi High CheckBox
		mWifiHighBoolean = MiscellaneousValues.mWifiHigh().equals("Y")
				|| MiscellaneousValues.mWifiHigh().equals("y");

		mWifiHighBox = new CheckBox(getActivity());

		if (Utils.existFile(MiscellaneousValues.FILENAME_WIFI_HIGH)) {
			mLayout.addView(mWifiHighBox);
			mLayout.addView(mWifiHighSummary);
		}

		// TCP Congestion SubTitle
		TextView mTCPCongestionTitle = new TextView(getActivity());
		LayoutStyle.setTextSubTitle(mTCPCongestionTitle,
				getString(R.string.tcpcongestion), getActivity());

		// TCP Congestion Summary
		TextView mTCPCongestionSummary = new TextView(getActivity());
		LayoutStyle.setTextSummary(mTCPCongestionSummary,
				getString(R.string.tcpcongestion_summary), getActivity());

		// TCP Congestion Spinner
		mAvailableTCPCongestion = MiscellaneousValues.mTCPCongestion().split(
				" ");
		mTCPCongestion = 0;

		adapterTCPCongestion = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, mAvailableTCPCongestion);
		adapterTCPCongestion
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		mTCPCongestionSpinner = new Spinner(getActivity());
		mTCPCongestionSpinner.setOnItemSelectedListener(this);

		if (Utils.existFile(MiscellaneousValues.FILENAME_TCP_CONGESTION)) {
			mLayout.addView(mTCPCongestionTitle);
			mLayout.addView(mTCPCongestionSummary);
			mLayout.addView(mTCPCongestionSpinner);
		}

		// Battery Title
		TextView mBatteryTitle = new TextView(getActivity());
		LayoutStyle.setTextTitle(mBatteryTitle, getString(R.string.battery),
				getActivity());

		if (Utils.existFile(MiscellaneousValues.FILENAME_FAST_CHARGE)
				|| Utils.existFile(MiscellaneousValues.FILENAME_BATTERY_EXTENDER)) {
			mLayout.addView(mBatteryTitle);
		}

		// Fast Charge CheckBox
		mFastChargeBoolean = MiscellaneousValues.mFastCharge() == 1;

		mFastChargeBox = new CheckBox(getActivity());

		// Fast Charge Summary
		TextView mFastChargeSummary = new TextView(getActivity());
		LayoutStyle.setTextSummary(mFastChargeSummary,
				getString(R.string.fastcharge_summary), getActivity());

		if (Utils.existFile(MiscellaneousValues.FILENAME_FAST_CHARGE)) {
			mLayout.addView(mFastChargeBox);
			mLayout.addView(mFastChargeSummary);
		}

		// Battery Extender SubTitle
		TextView mBatteryExtenderTitle = new TextView(getActivity());
		LayoutStyle.setTextSubTitle(mBatteryExtenderTitle,
				getString(R.string.batteryextender), getActivity());

		// Battery Extender Summary
		TextView mBatteryExtenderSummary = new TextView(getActivity());
		LayoutStyle.setTextSummary(mBatteryExtenderSummary,
				getString(R.string.batteryextender_summary), getActivity());

		// Battery Extender SeekBar
		mBatteryExtenderBar = new SeekBar(getActivity());
		mBatteryExtenderBar.setOnSeekBarChangeListener(this);

		// Battery Extender Text
		mBatteryExtenderText = new TextView(getActivity());

		if (Utils.existFile(MiscellaneousValues.FILENAME_BATTERY_EXTENDER)) {
			mLayout.addView(mBatteryExtenderTitle);
			mLayout.addView(mBatteryExtenderSummary);
			mLayout.addView(mBatteryExtenderBar);
			mLayout.addView(mBatteryExtenderText);
		}

		// Audio Title
		TextView mAudioTitle = new TextView(getActivity());
		LayoutStyle.setTextTitle(mAudioTitle, getString(R.string.audio),
				getActivity());

		if (Utils.existFile(MiscellaneousValues.FILENAME_SOUND_HIGH)
				|| Utils.existFile(MiscellaneousValues.FILENAME_HEADPHONE_BOOST))
			mLayout.addView(mAudioTitle);

		// Sound High CheckBox
		mSoundHighBoolean = MiscellaneousValues.mSoundHigh() == 1;

		mSoundHighBox = new CheckBox(getActivity());

		if (Utils.existFile(MiscellaneousValues.FILENAME_SOUND_HIGH))
			mLayout.addView(mSoundHighBox);

		// Headphone Boost SubTitle
		TextView mHeadphoneBoostSubTitle = new TextView(getActivity());
		LayoutStyle.setTextSubTitle(mHeadphoneBoostSubTitle,
				getString(R.string.headphoneboost), getActivity());

		// Headphone Boost SeekBar
		mHeadphoneBoostBar = new SeekBar(getActivity());
		mHeadphoneBoostBar.setOnSeekBarChangeListener(this);

		// Headphone Boost Text
		mHeadphoneBoostText = new TextView(getActivity());

		if (Utils.existFile(MiscellaneousValues.FILENAME_HEADPHONE_BOOST)) {
			mLayout.addView(mHeadphoneBoostSubTitle);
			mLayout.addView(mHeadphoneBoostBar);
			mLayout.addView(mHeadphoneBoostText);
		}

		// Other Settings Title
		TextView mOtherSettingsTitle = new TextView(getActivity());
		LayoutStyle.setTextTitle(mOtherSettingsTitle,
				getString(R.string.othersettings), getActivity());

		if (Utils.existFile(MiscellaneousValues.FILENAME_DYNAMIC_FSYNC)
				|| Utils.existFile(MiscellaneousValues.FILENAME_FSYNC_CONTROL))
			mLayout.addView(mOtherSettingsTitle);

		// Dynamic Fsync CheckBox
		mDynamicFsyncBoolean = MiscellaneousValues.mDynamicFsync() == 1;

		mDynamicFsyncBox = new CheckBox(getActivity());

		// Dynamic Fsync Summary
		TextView mDynamicFsyncSummary = new TextView(getActivity());
		LayoutStyle.setTextSummary(mDynamicFsyncSummary,
				getString(R.string.dynamicfsync_summary), getActivity());

		if (Utils.existFile(MiscellaneousValues.FILENAME_DYNAMIC_FSYNC)) {
			mLayout.addView(mDynamicFsyncBox);
			mLayout.addView(mDynamicFsyncSummary);
		}

		// Fsync Control CheckBox
		mFsyncControlBoolean = MiscellaneousValues.mFsyncControl() == 1;

		mFsyncControlBox = new CheckBox(getActivity());

		// Fsync Control Summary
		TextView mFsyncControlSummary = new TextView(getActivity());
		LayoutStyle.setTextSummary(mFsyncControlSummary,
				getString(R.string.fsynccontrol_summary), getActivity());

		if (Utils.existFile(MiscellaneousValues.FILENAME_FSYNC_CONTROL)) {
			mLayout.addView(mFsyncControlBox);
			mLayout.addView(mFsyncControlSummary);
		}

		setValues();

		mWifiHighBox.setOnCheckedChangeListener(this);
		mFastChargeBox.setOnCheckedChangeListener(this);
		mSoundHighBox.setOnCheckedChangeListener(this);
		mDynamicFsyncBox.setOnCheckedChangeListener(this);
		mFsyncControlBox.setOnCheckedChangeListener(this);
		return rootView;
	}

	public static void setValues() {
		// Wifi High
		LayoutStyle.setCheckBox(mWifiHighBox,
				context.getString(R.string.wifihigh), mWifiHighBoolean);

		// TCP Congestion
		LayoutStyle.setSpinner(mTCPCongestionSpinner, adapterTCPCongestion,
				mTCPCongestion);

		// Fast Charge
		LayoutStyle.setCheckBox(mFastChargeBox,
				context.getString(R.string.fastcharge), mFastChargeBoolean);

		// Battery Extender
		LayoutStyle.setSeekBar(mBatteryExtenderBar, 100,
				MiscellaneousValues.mBatterExtender());
		LayoutStyle.setCenterText(mBatteryExtenderText,
				String.valueOf(MiscellaneousValues.mBatterExtender()), context);

		// Sound High
		LayoutStyle.setCheckBox(mSoundHighBox,
				context.getString(R.string.soundhigh), mSoundHighBoolean);

		// Headphone Boost
		LayoutStyle.setSeekBar(mHeadphoneBoostBar, 3,
				MiscellaneousValues.mHeadphoneBoost());
		LayoutStyle.setCenterText(mHeadphoneBoostText,
				String.valueOf(MiscellaneousValues.mHeadphoneBoost()), context);

		// Dynamic Fsync
		LayoutStyle.setCheckBox(mDynamicFsyncBox,
				context.getString(R.string.dynamicfsync), mDynamicFsyncBoolean);

		// Fsync Control
		LayoutStyle.setCheckBox(mFsyncControlBox,
				context.getString(R.string.fsynccontrol), mFsyncControlBoolean);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		MainActivity.enableButtons();
		MainActivity.mMiscellaneousAction = true;
		if (buttonView.equals(mWifiHighBox)) {
			Control.WIFI_HIGH = isChecked ? "Y" : "N";
		} else if (buttonView.equals(mFastChargeBox)) {
			Control.FAST_CHARGE = isChecked ? "1" : "0";
		} else if (buttonView.equals(mSoundHighBox)) {
			Control.SOUND_HIGH = isChecked ? "1" : "0";
		} else if (buttonView.equals(mDynamicFsyncBox)) {
			Control.DYNAMIC_FSYNC = isChecked ? "1" : "0";
		} else if (buttonView.equals(mFsyncControlBox)) {
			Control.FSYNC_CONTROL = isChecked ? "1" : "0";
		}
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		if (seekBar.equals(mBatteryExtenderBar)) {
			mBatteryExtenderText.setText(String.valueOf(progress));
		} else if (seekBar.equals(mHeadphoneBoostBar)) {
			mHeadphoneBoostText.setText(String.valueOf(progress));
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		MainActivity.enableButtons();
		MainActivity.mMiscellaneousAction = true;
		if (seekBar.equals(mBatteryExtenderBar)) {
			Control.BATTERY_EXTENDER = mBatteryExtenderText.getText()
					.toString();
		} else if (seekBar.equals(mHeadphoneBoostBar)) {
			Control.HEADPHONE_BOOST = mHeadphoneBoostText.getText().toString();
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		if (arg0.equals(mTCPCongestionSpinner)) {
			mTCPCongestionRaw = arg2;
			if (arg2 != mTCPCongestion) {
				MainActivity.enableButtons();
				MainActivity.mMiscellaneousAction = true;
				Control.TCP_CONGESTION = mAvailableTCPCongestion[arg2];
			}
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}
}
