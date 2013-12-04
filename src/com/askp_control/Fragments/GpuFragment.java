package com.askp_control.Fragments;

import com.askp_control.MainActivity;
import com.askp_control.R;
import com.askp_control.Utils.Control;
import com.askp_control.Utils.GpuValues;
import com.askp_control.Utils.LayoutStyle;
import com.askp_control.Utils.Utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class GpuFragment extends Fragment implements OnSeekBarChangeListener {

	private static LinearLayout mLayout;

	private static SeekBar mGpuMaxFreqBar;
	private static TextView mGpuMaxFreqText;
	private static String mGpuValue;
	private static int mGpuValueRaw;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.layout, container, false);

		mLayout = (LinearLayout) rootView.findViewById(R.id.layout);

		// Gpu Scaling Title
		TextView mGpuScalingTitle = new TextView(getActivity());
		LayoutStyle.setTextTitle(mGpuScalingTitle,
				getString(R.string.gpuscaling), getActivity());

		if (Utils.existFile(GpuValues.FILENAME_VARIABLE_GPU))
			mLayout.addView(mGpuScalingTitle);

		// Gpu Max Freq SubTitle
		TextView mGpuMaxFreqSubTitle = new TextView(getActivity());
		LayoutStyle.setTextSubTitle(mGpuMaxFreqSubTitle,
				getString(R.string.gpumaxfreq), getActivity());

		// Gpu Max Freq Summary
		TextView mGpuMaxFreqSummary = new TextView(getActivity());
		LayoutStyle.setTextSummary(mGpuMaxFreqSummary,
				getString(R.string.gpumaxfreq_summary), getActivity());

		// Gpu Max Freq SeekBar
		mGpuMaxFreqBar = new SeekBar(getActivity());
		LayoutStyle.setSeekBar(mGpuMaxFreqBar, 2, GpuValues.mVariableGpu());
		mGpuMaxFreqBar.setOnSeekBarChangeListener(this);

		// Gpu Max Freq Text
		mGpuValue = "";
		switch (GpuValues.mVariableGpu()) {
		case 0:
			mGpuValue = "307";
			break;
		case 1:
			mGpuValue = "384";
			break;
		case 2:
			mGpuValue = "512";
			break;
		}
		mGpuMaxFreqText = new TextView(getActivity());
		LayoutStyle.setCenterText(mGpuMaxFreqText, mGpuValue + " MHz",
				getActivity());

		if (Utils.existFile(GpuValues.FILENAME_VARIABLE_GPU)) {
			mLayout.addView(mGpuMaxFreqSubTitle);
			mLayout.addView(mGpuMaxFreqSummary);
			mLayout.addView(mGpuMaxFreqBar);
			mLayout.addView(mGpuMaxFreqText);
		}

		return rootView;
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		if (seekBar.equals(mGpuMaxFreqBar)) {
			mGpuValueRaw = progress;
			switch (progress) {
			case 0:
				mGpuValue = "307";
				break;
			case 1:
				mGpuValue = "384";
				break;
			case 2:
				mGpuValue = "512";
				break;
			}
			mGpuMaxFreqText.setText(mGpuValue + " MHz");
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		MainActivity.mChange = true;
		if (seekBar.equals(mGpuMaxFreqBar)) {
			Control.GPU_VARIABLE = String.valueOf(mGpuValueRaw);
		}
	}
}
