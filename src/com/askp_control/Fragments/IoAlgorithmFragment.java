package com.askp_control.Fragments;

import com.askp_control.MainActivity;
import com.askp_control.R;
import com.askp_control.Utils.Control;
import com.askp_control.Utils.IoAlgorithmValues;
import com.askp_control.Utils.LayoutStyle;
import com.askp_control.Utils.Utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class IoAlgorithmFragment extends Fragment implements
		OnItemSelectedListener {

	private static LinearLayout mLayout;

	private static String[] mAvailableTCPCongestion;
	private static Spinner mTCPCongestionSpinner;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.layout, container, false);

		mLayout = (LinearLayout) rootView.findViewById(R.id.layout);

		// TCP Congestion Title
		TextView mTCPCongestionTitle = new TextView(getActivity());
		LayoutStyle.setTextTitle(mTCPCongestionTitle,
				getString(R.string.tcpcongestion), getActivity());

		// TCP Congestion Summary
		TextView mTCPCongestionSummary = new TextView(getActivity());
		LayoutStyle.setTextSummary(mTCPCongestionSummary,
				getString(R.string.tcpcongestion_summary), getActivity());

		// TCP Congestion Spinner
		mAvailableTCPCongestion = IoAlgorithmValues.mTCPCongestion().split(" ");

		ArrayAdapter<String> adapterTCPCongestion = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item,
				mAvailableTCPCongestion);
		adapterTCPCongestion
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		mTCPCongestionSpinner = new Spinner(getActivity());
		LayoutStyle.setSpinner(mTCPCongestionSpinner, adapterTCPCongestion, 0);
		mTCPCongestionSpinner.setOnItemSelectedListener(this);

		if (Utils.existFile(IoAlgorithmValues.FILENAME_TCP_CONGESTION)) {
			mLayout.addView(mTCPCongestionTitle);
			mLayout.addView(mTCPCongestionSummary);
			mLayout.addView(mTCPCongestionSpinner);
		}

		return rootView;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		if (arg0.equals(mTCPCongestionSpinner)) {
			if (arg2 != 0) {
				MainActivity.mChange = true;
				MainActivity.mIoAlgorithmAction = true;
				Control.TCP_CONGESTION = mAvailableTCPCongestion[arg2];
			}
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}
}
