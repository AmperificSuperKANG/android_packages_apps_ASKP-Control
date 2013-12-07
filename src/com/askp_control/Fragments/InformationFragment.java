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

package com.askp_control.Fragments;

import java.io.IOException;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.askp_control.R;
import com.askp_control.Utils.Utils;

public class InformationFragment extends Fragment {

	private static LinearLayout mLayout;

	private static TextView mDevice, mCodename, mKernelVersion;

	private static final String mManufacturer = Build.MANUFACTURER;
	public static final String mModel = Build.MODEL;
	private static final String mCode = Build.DEVICE;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.layout, container, false);

		mLayout = (LinearLayout) rootView.findViewById(R.id.layout);

		mDevice = new TextView(getActivity());
		mDevice.setText(getString(R.string.device) + ": "
				+ mManufacturer.substring(0, 1).toUpperCase()
				+ mManufacturer.substring(1) + " " + mModel);

		mCodename = new TextView(getActivity());
		mCodename.setText(getString(R.string.codename) + ": " + mCode);

		mKernelVersion = new TextView(getActivity());
		mKernelVersion.setText("");
		try {
			mKernelVersion.setText(getString(R.string.kernelversion) + ": "
					+ Utils.readLine("/proc/version"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		mLayout.addView(mDevice);
		mLayout.addView(mCodename);
		mLayout.addView(mKernelVersion);

		return rootView;
	}
}