package com.askp_control.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.askp_control.R;
import com.askp_control.Utils.Utils;

public class InformationFragment extends Fragment {

	private static TextView mDeviceText, mCodenameText, mKernelVersionText,
			mDevice, mCodename, mKernelVersion;

	private static final String mManufacturer = Build.MANUFACTURER;
	private static final String mModel = Build.MODEL;
	private static final String mCode = Build.DEVICE;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater
				.inflate(R.layout.information, container, false);

		mDeviceText = (TextView) rootView.findViewById(R.id.devicetext);
		mDeviceText.setText(getString(R.string.device) + ": ");

		mCodenameText = (TextView) rootView.findViewById(R.id.codenametext);
		mCodenameText.setText(getString(R.string.codename) + ": ");

		mKernelVersionText = (TextView) rootView
				.findViewById(R.id.kernelversiontext);
		mKernelVersionText.setText(getString(R.string.kernelversion) + ": ");

		mDevice = (TextView) rootView.findViewById(R.id.device);
		mDevice.setText(mManufacturer.substring(0, 1).toUpperCase()
				+ mManufacturer.substring(1) + " " + mModel);

		mCodename = (TextView) rootView.findViewById(R.id.codename);
		mCodename.setText(mCode);

		mKernelVersion = (TextView) rootView.findViewById(R.id.kernelversion);
		mKernelVersion.setText(Utils.getFormattedKernelVersion());

		return rootView;
	}
}