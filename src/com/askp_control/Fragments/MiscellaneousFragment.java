package com.askp_control.Fragments;

import com.askp_control.MainActivity;
import com.askp_control.R;
import com.askp_control.Utils.Control;
import com.askp_control.Utils.LayoutStyle;
import com.askp_control.Utils.MiscellaneousValues;
import com.askp_control.Utils.Utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MiscellaneousFragment extends Fragment implements
		OnCheckedChangeListener, OnSeekBarChangeListener {

	private static LinearLayout mLayout;

	private static CheckBox mWifiHighBox;

	private static CheckBox mFastChargeBox;

	private static SeekBar mBatteryExtenderBar;
	private static TextView mBatteryExtenderText;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.layout, container, false);

		mLayout = (LinearLayout) rootView.findViewById(R.id.layout);

		// Network Title
		TextView mNetworkTitle = new TextView(getActivity());
		LayoutStyle.setTextTitle(mNetworkTitle, getString(R.string.network),
				getActivity());

		if (Utils.existFile(MiscellaneousValues.FILENAME_WIFI_HIGH)) {
			mLayout.addView(mNetworkTitle);
		}

		// Wifi High SubTitle
		TextView mWifiHighTitle = new TextView(getActivity());
		LayoutStyle.setTextSubTitle(mWifiHighTitle,
				getString(R.string.wifihigh), getActivity());

		// Wifi High Summary
		TextView mWifiHighSummary = new TextView(getActivity());
		LayoutStyle.setTextSummary(mWifiHighSummary,
				getString(R.string.wifihigh_summary), getActivity());

		// Wifi High CheckBox
		boolean mWifiHighBoolean = false;
		if (MiscellaneousValues.mWifiHigh().equals("Y")
				|| MiscellaneousValues.mWifiHigh().equals("y"))
			mWifiHighBoolean = true;

		mWifiHighBox = new CheckBox(getActivity());
		LayoutStyle.setCheckBox(mWifiHighBox, getString(R.string.wifihigh),
				mWifiHighBoolean);
		mWifiHighBox.setOnCheckedChangeListener(this);

		if (Utils.existFile(MiscellaneousValues.FILENAME_WIFI_HIGH)) {
			mLayout.addView(mWifiHighTitle);
			mLayout.addView(mWifiHighSummary);
			mLayout.addView(mWifiHighBox);
		}

		// Battery Title
		TextView mBatteryTitle = new TextView(getActivity());
		LayoutStyle.setTextTitle(mBatteryTitle, getString(R.string.battery),
				getActivity());

		if (Utils.existFile(MiscellaneousValues.FILENAME_FAST_CHARGE)) {
			mLayout.addView(mBatteryTitle);
		}

		// Fast Charge SubTitle
		TextView mFastChargeSubTitle = new TextView(getActivity());
		LayoutStyle.setTextSubTitle(mFastChargeSubTitle,
				getString(R.string.fastcharge), getActivity());

		// Fast Charge Summary
		TextView mFastChargeSummary = new TextView(getActivity());
		LayoutStyle.setTextSummary(mFastChargeSummary,
				getString(R.string.fastcharge_summary), getActivity());

		// Fast Charge CheckBox
		boolean mFastChargeBoolean = false;
		if (MiscellaneousValues.mFastCharge() == 1)
			mFastChargeBoolean = true;

		mFastChargeBox = new CheckBox(getActivity());
		LayoutStyle.setCheckBox(mFastChargeBox, getString(R.string.fastcharge),
				mFastChargeBoolean);
		mFastChargeBox.setOnCheckedChangeListener(this);

		if (Utils.existFile(MiscellaneousValues.FILENAME_FAST_CHARGE)) {
			mLayout.addView(mFastChargeSubTitle);
			mLayout.addView(mFastChargeSummary);
			mLayout.addView(mFastChargeBox);
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
		LayoutStyle.setSeekBar(mBatteryExtenderBar, 100,
				MiscellaneousValues.mBatterExtender());
		mBatteryExtenderBar.setOnSeekBarChangeListener(this);

		// Battery Extender Text
		mBatteryExtenderText = new TextView(getActivity());
		LayoutStyle.setCenterText(mBatteryExtenderText,
				String.valueOf(MiscellaneousValues.mBatterExtender()),
				getActivity());

		if (Utils.existFile(MiscellaneousValues.FILENAME_BATTERY_EXTENDER)) {
			mLayout.addView(mBatteryExtenderTitle);
			mLayout.addView(mBatteryExtenderSummary);
			mLayout.addView(mBatteryExtenderBar);
			mLayout.addView(mBatteryExtenderText);
		}

		return rootView;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		MainActivity.enableButtons();
		MainActivity.mMiscellaneousAction = true;
		if (buttonView.equals(mWifiHighBox)) {
			if (isChecked) {
				Control.WIFI_HIGH = "Y";
			} else {
				Control.WIFI_HIGH = "N";
			}
		} else if (buttonView.equals(mFastChargeBox)) {
			if (isChecked) {
				Control.FAST_CHARGE = "1";
			} else {
				Control.FAST_CHARGE = "0";
			}
		}
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		if (seekBar.equals(mBatteryExtenderBar)) {
			mBatteryExtenderText.setText(String.valueOf(progress));
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
		}
	}
}
