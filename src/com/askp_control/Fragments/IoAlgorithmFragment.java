package com.askp_control.Fragments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	private static String[] mAvailableInternalScheduler;
	private static Spinner mInternalSchedulerSpinner;
	private static int mCurInternalScheduler;

	private static String[] mAvailableExternalScheduler;
	private static Spinner mExternalSchedulerSpinner;
	private static int mCurExternalScheduler;

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
		} else if (arg0.equals(mInternalSchedulerSpinner)) {
			if (arg2 != mCurInternalScheduler) {
				MainActivity.mChange = true;
				MainActivity.mIoAlgorithmAction = true;
				Control.INTERNAL_SCHEDULER = mAvailableInternalScheduler[arg2];
			}
		} else if (arg0.equals(mExternalSchedulerSpinner)) {
			if (arg2 != mCurExternalScheduler) {
				MainActivity.mChange = true;
				MainActivity.mIoAlgorithmAction = true;
				Control.EXTERNAL_SCHEDULER = mAvailableExternalScheduler[arg2];
			}
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}
}
