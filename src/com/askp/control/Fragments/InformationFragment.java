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

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.askp.control.R;
import com.askp.control.utils.LayoutStyle;
import com.askp.control.utils.Utils;

public class InformationFragment extends Fragment {

	private static final String mManufacturer = Build.MANUFACTURER;
	public static final String mModel = Build.MODEL;
	private static final String mCode = Build.DEVICE;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.layout, container, false);

		LinearLayout mLayout = (LinearLayout) rootView
				.findViewById(R.id.layout);

		TextView mDeviceTitle = new TextView(getActivity());
		LayoutStyle.setTextTitle(mDeviceTitle, getString(R.string.device),
				getActivity());

		TextView mDeviceText = new TextView(getActivity());
		mDeviceText.setText(mManufacturer.substring(0, 1).toUpperCase()
				+ mManufacturer.substring(1) + " " + mModel);

		TextView mCodenameTitle = new TextView(getActivity());
		LayoutStyle.setTextTitle(mCodenameTitle, getString(R.string.codename),
				getActivity());

		TextView mCodenameText = new TextView(getActivity());
		mCodenameText.setText(mCode);

		TextView mKernelVersionTitle = new TextView(getActivity());
		LayoutStyle.setTextTitle(mKernelVersionTitle,
				getString(R.string.kernelversion), getActivity());

		TextView mKernelVersionText = new TextView(getActivity());
		mKernelVersionText.setText(getString(R.string.unavailable));
		try {
			mKernelVersionText.setText(Utils.readLine("/proc/version"));
		} catch (IOException e) {
		}

		mLayout.addView(mDeviceTitle);
		mLayout.addView(mDeviceText);
		mLayout.addView(mCodenameTitle);
		mLayout.addView(mCodenameText);
		mLayout.addView(mKernelVersionTitle);
		mLayout.addView(mKernelVersionText);

		return rootView;
	}
}