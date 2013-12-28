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
import com.askp.control.Utils.LayoutStyle;
import com.askp.control.Utils.MiscellaneousValues;
import com.askp.control.Utils.Utils;
import com.askp.control.Utils.ValueEditor;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
		OnSeekBarChangeListener, OnClickListener {
	private static Context context;

	private static OnCheckedChangeListener CheckedChangeListener;
	private static OnItemSelectedListener ItemSelectedListener;
	private static OnSeekBarChangeListener SeekBarChangeListener;
	private static OnClickListener OnClickListener;

	private static LinearLayout mLayout;

	public static CheckBox mWifiHighBox;

	public static String[] mAvailableTCPCongestion;
	public static Spinner mTCPCongestionSpinner;
	public static int mTCPCongestion;

	public static CheckBox mFastChargeBox;

	private static SeekBar mBatteryExtenderBar;
	public static TextView mBatteryExtenderText;

	public static CheckBox mSoundHighBox;

	private static SeekBar mHeadphoneBoostBar;
	public static TextView mHeadphoneBoostText;

	public static CheckBox mBacklightnotificationBox;

	public static CheckBox mDynamicFsyncBox;

	public static CheckBox mFsyncControlBox;

	private static SeekBar mVibrationStrengthBar;
	public static TextView mVibrationStrengthText;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		View rootView = inflater.inflate(R.layout.layout, container, false);

		mLayout = (LinearLayout) rootView.findViewById(R.id.layout);
		CheckedChangeListener = this;
		ItemSelectedListener = this;
		SeekBarChangeListener = this;
		OnClickListener = this;
		setContent();

		return rootView;
	}

	public static void setContent() {
		mLayout.removeAllViews();

		// Network Title
		TextView mNetworkTitle = new TextView(context);
		LayoutStyle.setTextTitle(mNetworkTitle,
				context.getString(R.string.network), context);

		if (Utils.existFile(MiscellaneousValues.FILENAME_WIFI_HIGH)
				|| Utils.existFile(MiscellaneousValues.FILENAME_TCP_CONGESTION))
			mLayout.addView(mNetworkTitle);

		// Wifi High Summary
		TextView mWifiHighSummary = new TextView(context);
		LayoutStyle.setTextSummary(mWifiHighSummary,
				context.getString(R.string.wifihigh_summary));

		// Wifi High CheckBox
		mWifiHighBox = new CheckBox(context);
		LayoutStyle.setCheckBox(mWifiHighBox, context
				.getString(R.string.wifihigh), MiscellaneousValues.mWifiHigh()
				.equals("Y") || MiscellaneousValues.mWifiHigh().equals("y"));
		mWifiHighBox.setOnCheckedChangeListener(CheckedChangeListener);

		if (Utils.existFile(MiscellaneousValues.FILENAME_WIFI_HIGH)) {
			mLayout.addView(mWifiHighBox);
			mLayout.addView(mWifiHighSummary);
		}

		// TCP Congestion SubTitle
		TextView mTCPCongestionTitle = new TextView(context);
		LayoutStyle.setTextSubTitle(mTCPCongestionTitle,
				context.getString(R.string.tcpcongestion), context);

		// TCP Congestion Summary
		TextView mTCPCongestionSummary = new TextView(context);
		LayoutStyle.setTextSummary(mTCPCongestionSummary,
				context.getString(R.string.tcpcongestion_summary));

		// TCP Congestion Spinner
		mAvailableTCPCongestion = MiscellaneousValues.mTCPCongestion().split(
				" ");

		ArrayAdapter<String> adapterTCPCongestion = new ArrayAdapter<String>(
				context, android.R.layout.simple_spinner_item,
				mAvailableTCPCongestion);
		adapterTCPCongestion
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		mTCPCongestion = 0;
		mTCPCongestionSpinner = new Spinner(context);
		LayoutStyle.setSpinner(mTCPCongestionSpinner, adapterTCPCongestion, 0);
		mTCPCongestionSpinner.setOnItemSelectedListener(ItemSelectedListener);

		if (Utils.existFile(MiscellaneousValues.FILENAME_TCP_CONGESTION)) {
			mLayout.addView(mTCPCongestionTitle);
			mLayout.addView(mTCPCongestionSummary);
			mLayout.addView(mTCPCongestionSpinner);
		}

		// Battery Title
		TextView mBatteryTitle = new TextView(context);
		LayoutStyle.setTextTitle(mBatteryTitle,
				context.getString(R.string.battery), context);

		if (Utils.existFile(MiscellaneousValues.FILENAME_FAST_CHARGE)
				|| Utils.existFile(MiscellaneousValues.FILENAME_BATTERY_EXTENDER)) {
			mLayout.addView(mBatteryTitle);
		}

		// Fast Charge CheckBox

		mFastChargeBox = new CheckBox(context);
		LayoutStyle.setCheckBox(mFastChargeBox,
				context.getString(R.string.fastcharge),
				MiscellaneousValues.mFastCharge() == 1);
		mFastChargeBox.setOnCheckedChangeListener(CheckedChangeListener);

		// Fast Charge Summary
		TextView mFastChargeSummary = new TextView(context);
		LayoutStyle.setTextSummary(mFastChargeSummary,
				context.getString(R.string.fastcharge_summary));

		if (Utils.existFile(MiscellaneousValues.FILENAME_FAST_CHARGE)) {
			mLayout.addView(mFastChargeBox);
			mLayout.addView(mFastChargeSummary);
		}

		// Battery Extender SubTitle
		TextView mBatteryExtenderTitle = new TextView(context);
		LayoutStyle.setTextSubTitle(mBatteryExtenderTitle,
				context.getString(R.string.batteryextender), context);

		// Battery Extender Summary
		TextView mBatteryExtenderSummary = new TextView(context);
		LayoutStyle.setTextSummary(mBatteryExtenderSummary,
				context.getString(R.string.batteryextender_summary));

		// Battery Extender SeekBar
		mBatteryExtenderBar = new SeekBar(context);
		LayoutStyle.setSeekBar(mBatteryExtenderBar, 100,
				MiscellaneousValues.mBatterExtender());
		mBatteryExtenderBar.setOnSeekBarChangeListener(SeekBarChangeListener);

		// Battery Extender Text
		mBatteryExtenderText = new TextView(context);
		LayoutStyle.setCenterText(mBatteryExtenderText,
				String.valueOf(MiscellaneousValues.mBatterExtender()));
		mBatteryExtenderText.setOnClickListener(OnClickListener);

		if (Utils.existFile(MiscellaneousValues.FILENAME_BATTERY_EXTENDER)) {
			mLayout.addView(mBatteryExtenderTitle);
			mLayout.addView(mBatteryExtenderSummary);
			mLayout.addView(mBatteryExtenderBar);
			mLayout.addView(mBatteryExtenderText);
		}

		// Audio Title
		TextView mAudioTitle = new TextView(context);
		LayoutStyle.setTextTitle(mAudioTitle,
				context.getString(R.string.audio), context);

		if (Utils.existFile(MiscellaneousValues.FILENAME_SOUND_HIGH)
				|| Utils.existFile(MiscellaneousValues.FILENAME_HEADPHONE_BOOST))
			mLayout.addView(mAudioTitle);

		// Sound High CheckBox
		mSoundHighBox = new CheckBox(context);
		LayoutStyle.setCheckBox(mSoundHighBox,
				context.getString(R.string.soundhigh),
				MiscellaneousValues.mSoundHigh() == 1);
		mSoundHighBox.setOnCheckedChangeListener(CheckedChangeListener);

		if (Utils.existFile(MiscellaneousValues.FILENAME_SOUND_HIGH))
			mLayout.addView(mSoundHighBox);

		// Headphone Boost SubTitle
		TextView mHeadphoneBoostSubTitle = new TextView(context);
		LayoutStyle.setTextSubTitle(mHeadphoneBoostSubTitle,
				context.getString(R.string.headphoneboost), context);

		// Headphone Boost SeekBar
		mHeadphoneBoostBar = new SeekBar(context);
		LayoutStyle.setSeekBar(mHeadphoneBoostBar, 3,
				MiscellaneousValues.mHeadphoneBoost());
		mHeadphoneBoostBar.setOnSeekBarChangeListener(SeekBarChangeListener);

		// Headphone Boost Text
		mHeadphoneBoostText = new TextView(context);
		LayoutStyle.setCenterText(mHeadphoneBoostText,
				String.valueOf(MiscellaneousValues.mHeadphoneBoost()));

		if (Utils.existFile(MiscellaneousValues.FILENAME_HEADPHONE_BOOST)) {
			mLayout.addView(mHeadphoneBoostSubTitle);
			mLayout.addView(mHeadphoneBoostBar);
			mLayout.addView(mHeadphoneBoostText);
		}

		// Notification Title
		TextView mNotificationTitle = new TextView(context);
		LayoutStyle.setTextTitle(mNotificationTitle,
				context.getString(R.string.notification), context);

		if (Utils
				.existFile(MiscellaneousValues.FILENAME_BACKLIGHT_NOTIFICATION))
			mLayout.addView(mNotificationTitle);

		// Backlightnotification CheckBox
		mBacklightnotificationBox = new CheckBox(context);
		LayoutStyle.setCheckBox(mBacklightnotificationBox,
				context.getString(R.string.backlightnotification),
				MiscellaneousValues.mBacklightnotification() == 1);
		mBacklightnotificationBox
				.setOnCheckedChangeListener(CheckedChangeListener);

		// Backlightnotification Summary
		TextView mBacklightnotificationSummary = new TextView(context);
		LayoutStyle.setTextSummary(mBacklightnotificationSummary,
				context.getString(R.string.backlightnotification_summary));

		if (Utils
				.existFile(MiscellaneousValues.FILENAME_BACKLIGHT_NOTIFICATION)) {
			mLayout.addView(mBacklightnotificationBox);
			mLayout.addView(mBacklightnotificationSummary);
		}

		// Other Settings Title
		TextView mOtherSettingsTitle = new TextView(context);
		LayoutStyle.setTextTitle(mOtherSettingsTitle,
				context.getString(R.string.othersettings), context);

		if (Utils.existFile(MiscellaneousValues.FILENAME_DYNAMIC_FSYNC)
				|| Utils.existFile(MiscellaneousValues.FILENAME_FSYNC_CONTROL))
			mLayout.addView(mOtherSettingsTitle);

		// Dynamic Fsync CheckBox
		mDynamicFsyncBox = new CheckBox(context);
		LayoutStyle.setCheckBox(mDynamicFsyncBox,
				context.getString(R.string.dynamicfsync),
				MiscellaneousValues.mDynamicFsync() == 1);
		mDynamicFsyncBox.setOnCheckedChangeListener(CheckedChangeListener);

		// Dynamic Fsync Summary
		TextView mDynamicFsyncSummary = new TextView(context);
		LayoutStyle.setTextSummary(mDynamicFsyncSummary,
				context.getString(R.string.dynamicfsync_summary));

		if (Utils.existFile(MiscellaneousValues.FILENAME_DYNAMIC_FSYNC)) {
			mLayout.addView(mDynamicFsyncBox);
			mLayout.addView(mDynamicFsyncSummary);
		}

		// Fsync Control CheckBox
		mFsyncControlBox = new CheckBox(context);
		LayoutStyle.setCheckBox(mFsyncControlBox,
				context.getString(R.string.fsynccontrol),
				MiscellaneousValues.mFsyncControl() == 1);
		mFsyncControlBox.setOnCheckedChangeListener(CheckedChangeListener);

		// Fsync Control Summary
		TextView mFsyncControlSummary = new TextView(context);
		LayoutStyle.setTextSummary(mFsyncControlSummary,
				context.getString(R.string.fsynccontrol_summary));

		if (Utils.existFile(MiscellaneousValues.FILENAME_FSYNC_CONTROL)) {
			mLayout.addView(mFsyncControlBox);
			mLayout.addView(mFsyncControlSummary);
		}

		// Vibration Strength SubTitle
		TextView mVibrationStrengthSubTitle = new TextView(context);
		LayoutStyle.setTextSubTitle(mVibrationStrengthSubTitle,
				context.getString(R.string.vibrationstrength), context);

		// Vibration Strength Summary
		TextView mVibrationStrengthSummary = new TextView(context);
		LayoutStyle.setTextSummary(mVibrationStrengthSummary,
				context.getString(R.string.vibrationstrength_summary));

		// Vibration Strength SeekBar
		mVibrationStrengthBar = new SeekBar(context);
		LayoutStyle.setSeekBar(mVibrationStrengthBar, 127,
				MiscellaneousValues.mVibrationStrength());
		mVibrationStrengthBar.setOnSeekBarChangeListener(SeekBarChangeListener);

		// Vibration Strength Text
		mVibrationStrengthText = new TextView(context);
		LayoutStyle.setCenterText(mVibrationStrengthText,
				String.valueOf(MiscellaneousValues.mVibrationStrength()));
		mVibrationStrengthText.setOnClickListener(OnClickListener);

		if (Utils.existFile(MiscellaneousValues.FILENAME_VIBRATION_STRENGTH)) {
			mLayout.addView(mVibrationStrengthSubTitle);
			mLayout.addView(mVibrationStrengthSummary);
			mLayout.addView(mVibrationStrengthBar);
			mLayout.addView(mVibrationStrengthText);
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		MainActivity.showButtons(true);
		MainActivity.mMiscellaneousAction = true;
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		if (seekBar.equals(mBatteryExtenderBar))
			mBatteryExtenderText.setText(String.valueOf(progress));
		else if (seekBar.equals(mHeadphoneBoostBar))
			mHeadphoneBoostText.setText(String.valueOf(progress));
		else if (seekBar.equals(mVibrationStrengthBar))
			mVibrationStrengthText.setText(String.valueOf(progress));
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		MainActivity.showButtons(true);
		MainActivity.mMiscellaneousAction = true;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		if (arg0.equals(mTCPCongestionSpinner)) {
			if (arg2 != mTCPCongestion) {
				MainActivity.showButtons(true);
				MainActivity.mMiscellaneousAction = true;
			}
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}

	@Override
	public void onClick(View v) {
		MainActivity.mMiscellaneousAction = true;
		if (v.equals(mBatteryExtenderText))
			ValueEditor.showSeekBarEditor(mBatteryExtenderBar,
					mBatteryExtenderText.getText().toString(),
					context.getString(R.string.batteryextender), 0, context);
		else if (v.equals(mVibrationStrengthText))
			ValueEditor.showSeekBarEditor(mVibrationStrengthBar,
					mVibrationStrengthText.getText().toString(),
					context.getString(R.string.vibrationstrength), 0, context);
	}
}
