package com.askp_control.Fragments;

import java.util.Arrays;
import java.util.List;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.askp_control.MainActivity;
import com.askp_control.R;
import com.askp_control.Utils.Control;
import com.askp_control.Utils.CpuValues;
import com.askp_control.Utils.Utils;

public class CpuFragment extends Fragment implements OnSeekBarChangeListener,
		OnItemSelectedListener, OnCheckedChangeListener {

	private static LinearLayout mLayout;

	private static TextView mCurCpuFreq, mMaxCpuFreqText, mMinCpuFreqText,
			mMaxFreqScreenOffText, mMinFreqScreenOnText;

	private static CurCPUThread mCurCPUThread;

	private static SeekBar mMaxCpuFreqBar, mMinCpuFreqBar,
			mMaxScreenFreqOffBar, mMinFreqScreenOnBar;

	private static String[] mAvailableFreq;
	private static List<String> mAvailableFreqList;

	public static int mMinCpuFreqRaw, mMaxCpuFreqRaw;

	private static String mMaxFreqValue, mMinFreqValue, mMaxScreenOffValue,
			mMinScreenOnValue;

	private static Spinner mGovernorSpinner;
	private static String[] mAvailableGovernor;
	private static List<String> mAvailableGovernorList;
	public static String mCurGovernorRaw;

	private static CheckBox mCore, mIVA, mMPU;

	private static TextView mCoreVoltages, mVoltageOne, mVoltageTwo,
			mVoltageThree, mVoltageFour;

	private static SeekBar mVoltageOneBar, mVoltageTwoBar, mVoltageThreeBar,
			mVoltageFourBar;

	public static String[] mCoreVoltagesFreq;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.cpu, container, false);

		mLayout = (LinearLayout) rootView.findViewById(R.id.cpulayout);

		// Current Freq Title
		TextView mCpuFreqTitle = new TextView(getActivity());
		mCpuFreqTitle.setBackgroundColor(getResources().getColor(
				android.R.color.holo_blue_dark));
		mCpuFreqTitle.setTextColor(getResources().getColor(
				android.R.color.white));
		mCpuFreqTitle.setTypeface(null, Typeface.BOLD);
		mCpuFreqTitle.setText(getString(R.string.cpufreq));

		// Current Freq
		mCurCpuFreq = new TextView(getActivity());
		mCurCpuFreq
				.setTextColor(getResources().getColor(android.R.color.white));
		mCurCpuFreq.setGravity(Gravity.CENTER);
		mCurCpuFreq.setTextSize(40);

		if (Utils.existFile(CpuValues.FILENAME_CUR_CPU_FREQ)) {
			mLayout.addView(mCpuFreqTitle);
			mLayout.addView(mCurCpuFreq);
		}

		// Freq scaling Title
		TextView mFreqScalingTitle = new TextView(getActivity());
		mFreqScalingTitle.setBackgroundColor(getResources().getColor(
				android.R.color.holo_blue_dark));
		mFreqScalingTitle.setTextColor(getResources().getColor(
				android.R.color.white));
		mFreqScalingTitle.setTypeface(null, Typeface.BOLD);
		mFreqScalingTitle.setText(getString(R.string.cpuscaling));

		if (Utils.existFile(CpuValues.FILENAME_MAX_FREQ)
				|| Utils.existFile(CpuValues.FILENAME_MIN_FREQ)
				|| Utils.existFile(CpuValues.FILENAME_MAX_SCREEN_OFF)
				|| Utils.existFile(CpuValues.FILENAME_MIN_SCREEN_ON)) {
			mLayout.addView(mFreqScalingTitle);
		}

		// Max Freq Title
		TextView mMaxCpuFreqTitle = new TextView(getActivity());
		mMaxCpuFreqTitle.setTypeface(null, Typeface.BOLD);
		mMaxCpuFreqTitle.setTextColor(getResources().getColor(
				android.R.color.white));
		mMaxCpuFreqTitle.setText(getString(R.string.cpumaxfreq));

		// Max Freq Summary
		TextView mMaxCpuFreqSummary = new TextView(getActivity());
		mMaxCpuFreqSummary.setTypeface(null, Typeface.ITALIC);
		mMaxCpuFreqSummary.setText(getString(R.string.cpumaxfreq_summary));

		// Max Freq SeekBar
		mAvailableFreq = CpuValues.mAvailableFreq().split(" ");

		mAvailableFreqList = Arrays.asList(mAvailableFreq);
		int mMax = mAvailableFreqList.indexOf(String.valueOf(mMaxCpuFreqRaw));
		int mMin = mAvailableFreqList.indexOf(String.valueOf(mMinCpuFreqRaw));

		mMaxCpuFreqBar = new SeekBar(getActivity());
		mMaxCpuFreqBar.setMax(mAvailableFreq.length - 1);
		mMaxCpuFreqBar.setProgress(mMax);
		mMaxCpuFreqBar.setOnSeekBarChangeListener(this);
		mMaxFreqValue = String.valueOf(mMaxCpuFreqRaw);

		// Max Freq TextView
		mMaxCpuFreqText = new TextView(getActivity());
		mMaxCpuFreqText.setText(String.valueOf(mMaxCpuFreqRaw / 1000) + " MHz");
		mMaxCpuFreqText.setGravity(Gravity.CENTER);

		if (Utils.existFile(CpuValues.FILENAME_MAX_FREQ)) {
			mLayout.addView(mMaxCpuFreqTitle);
			mLayout.addView(mMaxCpuFreqSummary);
			mLayout.addView(mMaxCpuFreqBar);
			mLayout.addView(mMaxCpuFreqText);
		}

		// Min Freq Title
		TextView mMinCpuFreqTitle = new TextView(getActivity());
		mMinCpuFreqTitle.setTypeface(null, Typeface.BOLD);
		mMinCpuFreqTitle.setTextColor(getResources().getColor(
				android.R.color.white));
		mMinCpuFreqTitle.setText(getString(R.string.cpuminfreq));

		// Min Freq Summary
		TextView mMinCpuFreqSummary = new TextView(getActivity());
		mMinCpuFreqSummary.setTypeface(null, Typeface.ITALIC);
		mMinCpuFreqSummary.setText(getString(R.string.cpuminfreq_summary));

		// Min Freq SeekBar
		mMinCpuFreqBar = new SeekBar(getActivity());
		mMinCpuFreqBar.setMax(mAvailableFreq.length - 1);
		mMinCpuFreqBar.setProgress(mMin);
		mMinCpuFreqBar.setOnSeekBarChangeListener(this);
		mMinFreqValue = String.valueOf(mMinCpuFreqRaw);

		// Min Freq TextView
		mMinCpuFreqText = new TextView(getActivity());
		mMinCpuFreqText.setText(String.valueOf(mMinCpuFreqRaw / 1000) + " MHz");
		mMinCpuFreqText.setGravity(Gravity.CENTER);

		if (Utils.existFile(CpuValues.FILENAME_MIN_FREQ)) {
			mLayout.addView(mMinCpuFreqTitle);
			mLayout.addView(mMinCpuFreqSummary);
			mLayout.addView(mMinCpuFreqBar);
			mLayout.addView(mMinCpuFreqText);
		}

		// Max Freq Screen Off Title
		TextView mMaxFreqScreenOffTitle = new TextView(getActivity());
		mMaxFreqScreenOffTitle.setTypeface(null, Typeface.BOLD);
		mMaxFreqScreenOffTitle.setTextColor(getResources().getColor(
				android.R.color.white));
		mMaxFreqScreenOffTitle.setText(getString(R.string.maxscreenoff));

		// Max Freq Screen Off Summary
		TextView mMaxFreqScreenOffSummary = new TextView(getActivity());
		mMaxFreqScreenOffSummary.setTypeface(null, Typeface.ITALIC);
		mMaxFreqScreenOffSummary
				.setText(getString(R.string.maxscreenoff_summary));

		// Max Freq Screen Off SeekBar
		mMaxScreenOffValue = String.valueOf(CpuValues.mMaxScreenOffFreq());
		int mMaxScreenOff = mAvailableFreqList.indexOf(mMaxScreenOffValue);

		mMaxScreenFreqOffBar = new SeekBar(getActivity());
		if (CpuValues.mMaxScreenOffFreq() == 0)
			Utils.runCommand("echo " + mAvailableFreq[0] + " > "
					+ CpuValues.FILENAME_MAX_SCREEN_OFF);
		mMaxScreenFreqOffBar.setMax(mAvailableFreq.length - 1);
		mMaxScreenFreqOffBar.setProgress(mMaxScreenOff);
		mMaxScreenFreqOffBar.setOnSeekBarChangeListener(this);

		// Max Freq Screen Off TextView
		mMaxFreqScreenOffText = new TextView(getActivity());
		mMaxFreqScreenOffText.setText(String.valueOf(CpuValues
				.mMaxScreenOffFreq() / 1000) + " MHz");
		mMaxFreqScreenOffText.setGravity(Gravity.CENTER);

		if (Utils.existFile(CpuValues.FILENAME_MAX_SCREEN_OFF)) {
			mLayout.addView(mMaxFreqScreenOffTitle);
			mLayout.addView(mMaxFreqScreenOffSummary);
			mLayout.addView(mMaxScreenFreqOffBar);
			mLayout.addView(mMaxFreqScreenOffText);
		}

		// Min Freq Screen on Title
		TextView mMinFreqScreenOnTitle = new TextView(getActivity());
		mMinFreqScreenOnTitle.setTypeface(null, Typeface.BOLD);
		mMinFreqScreenOnTitle.setTextColor(getResources().getColor(
				android.R.color.white));
		mMinFreqScreenOnTitle.setText(getString(R.string.minscreenon));

		// Min Freq Screen On Summary
		TextView mMinFreqScreenOnSummary = new TextView(getActivity());
		mMinFreqScreenOnSummary.setTypeface(null, Typeface.ITALIC);
		mMinFreqScreenOnSummary
				.setText(getString(R.string.minscreenon_summary));

		// Min Freq Screen On SeekBar
		mMinScreenOnValue = String.valueOf(CpuValues.mMinScreenOnFreq());
		int mMinScreenOn = mAvailableFreqList.indexOf(mMinScreenOnValue);

		mMinFreqScreenOnBar = new SeekBar(getActivity());
		mMinFreqScreenOnBar.setMax(mAvailableFreq.length - 1);
		mMinFreqScreenOnBar.setProgress(mMinScreenOn);
		mMinFreqScreenOnBar.setOnSeekBarChangeListener(this);

		// Min Freq Screen On TextView
		mMinFreqScreenOnText = new TextView(getActivity());
		mMinFreqScreenOnText.setText(String.valueOf(CpuValues
				.mMinScreenOnFreq() / 1000) + " MHz");
		mMinFreqScreenOnText.setGravity(Gravity.CENTER);

		if (Utils.existFile(CpuValues.FILENAME_MIN_SCREEN_ON)) {
			mLayout.addView(mMinFreqScreenOnTitle);
			mLayout.addView(mMinFreqScreenOnSummary);
			mLayout.addView(mMinFreqScreenOnBar);
			mLayout.addView(mMinFreqScreenOnText);
		}

		// Governor Title
		TextView mGovernorTitle = new TextView(getActivity());
		mGovernorTitle.setBackgroundColor(getResources().getColor(
				android.R.color.holo_blue_dark));
		mGovernorTitle.setTextColor(getResources().getColor(
				android.R.color.white));
		mGovernorTitle.setTypeface(null, Typeface.BOLD);
		mGovernorTitle.setText(getString(R.string.governor));

		// Governor Summary
		TextView mGovernorSummary = new TextView(getActivity());
		mGovernorSummary.setTypeface(null, Typeface.ITALIC);
		mGovernorSummary.setText(getString(R.string.governor_summary));

		// Governor Spinner
		mAvailableGovernor = CpuValues.mAvailableGovernor().split(" ");
		mAvailableGovernorList = Arrays.asList(mAvailableGovernor);
		int mCurGovernor = mAvailableGovernorList.indexOf(mCurGovernorRaw);

		mGovernorSpinner = new Spinner(getActivity());
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, mAvailableGovernor);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mGovernorSpinner.setAdapter(adapter);
		mGovernorSpinner.setSelection(mCurGovernor);
		mGovernorSpinner.setOnItemSelectedListener(this);

		if (Utils.existFile(CpuValues.FILENAME_AVAILABLE_GOVERNOR)
				&& Utils.existFile(CpuValues.FILENAME_CUR_GOVERNOR)) {
			mLayout.addView(mGovernorTitle);
			mLayout.addView(mGovernorSummary);
			mLayout.addView(mGovernorSpinner);
		}

		// Smartreflex Title
		TextView mSmartreflexTitle = new TextView(getActivity());
		mSmartreflexTitle.setBackgroundColor(getResources().getColor(
				android.R.color.holo_blue_dark));
		mSmartreflexTitle.setTextColor(getResources().getColor(
				android.R.color.white));
		mSmartreflexTitle.setTypeface(null, Typeface.BOLD);
		mSmartreflexTitle.setText(getString(R.string.smartreflex));

		// Smartreflex Summary
		TextView mSmartreflexSummary = new TextView(getActivity());
		mSmartreflexSummary.setTypeface(null, Typeface.ITALIC);
		mSmartreflexSummary.setText(getString(R.string.smartreflex_summary));

		if (Utils.existFile(CpuValues.FILENAME_CORE)
				|| Utils.existFile(CpuValues.FILENAME_IVA)
				|| Utils.existFile(CpuValues.FILENAME_MPU)) {
			mLayout.addView(mSmartreflexTitle);
			mLayout.addView(mSmartreflexSummary);
		}

		// Smartreflex Core
		mCore = new CheckBox(getActivity());
		mCore.setText(getString(R.string.core));
		mCore.setChecked(CpuValues.mCore());
		mCore.setOnCheckedChangeListener(this);

		if (Utils.existFile(CpuValues.FILENAME_CORE))
			mLayout.addView(mCore);

		// Smartreflex IVA
		mIVA = new CheckBox(getActivity());
		mIVA.setText(getString(R.string.iva));
		mIVA.setChecked(CpuValues.mIVA());
		mIVA.setOnCheckedChangeListener(this);

		if (Utils.existFile(CpuValues.FILENAME_IVA))
			mLayout.addView(mIVA);

		// Smartreflex mMPU
		mMPU = new CheckBox(getActivity());
		mMPU.setText(getString(R.string.mpu));
		mMPU.setChecked(CpuValues.mMPU());
		mMPU.setOnCheckedChangeListener(this);

		if (Utils.existFile(CpuValues.FILENAME_MPU))
			mLayout.addView(mMPU);

		return rootView;
	}

	@Override
	public void onResume() {
		mCurCPUThread = new CurCPUThread();
		mCurCPUThread.start();
		super.onResume();
	}

	protected class CurCPUThread extends Thread {
		@Override
		public void run() {
			try {
				while (true) {
					mCurCPUHandler.sendMessage(mCurCPUHandler.obtainMessage(0,
							CpuValues.mCurCpuFreq()));
					sleep(1000);
				}
			} catch (InterruptedException e) {
			}
		}
	}

	protected Handler mCurCPUHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (!String.valueOf(msg.obj).equals(0)) {
				mCurCpuFreq
						.setText(String.valueOf(CpuValues.mCurCpuFreq() / 1000)
								+ " Mhz");
			}
		}
	};

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		if (seekBar.equals(mMaxCpuFreqBar)) {
			mMaxCpuFreqText.setText(String.valueOf(Integer
					.parseInt(mAvailableFreq[progress]) / 1000) + " MHz");
			mMaxFreqValue = mAvailableFreq[progress];
			if (Integer.parseInt(mMaxFreqValue) < Integer
					.parseInt(mMinFreqValue)) {
				mMaxCpuFreqBar.setProgress(progress);
				mMinCpuFreqBar.setProgress(progress);
			}
		} else if (seekBar.equals(mMinCpuFreqBar)) {
			mMinCpuFreqText.setText(String.valueOf(Integer
					.parseInt(mAvailableFreq[progress]) / 1000) + " MHz");
			mMinFreqValue = mAvailableFreq[progress];
			if (Integer.parseInt(mMaxFreqValue) < Integer
					.parseInt(mMinFreqValue)) {
				mMaxCpuFreqBar.setProgress(progress);
				mMinCpuFreqBar.setProgress(progress);
			}
		} else if (seekBar.equals(mMaxScreenFreqOffBar)) {
			mMaxFreqScreenOffText.setText(String.valueOf(Integer
					.parseInt(mAvailableFreq[progress]) / 1000) + " MHz");
			mMaxScreenOffValue = mAvailableFreq[progress];
		} else if (seekBar.equals(mMinFreqScreenOnBar)) {
			mMinFreqScreenOnText.setText(String.valueOf(Integer
					.parseInt(mAvailableFreq[progress]) / 1000) + " MHz");
			mMinScreenOnValue = mAvailableFreq[progress];
		}

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		MainActivity.mChange = true;
		if (seekBar.equals(mMaxCpuFreqBar)) {
			Control.MAX_CPU_FREQ = mMaxFreqValue;
		} else if (seekBar.equals(mMinCpuFreqBar)) {
			Control.MIN_CPU_FREQ = mMinFreqValue;
		} else if (seekBar.equals(mMaxScreenFreqOffBar)) {
			Control.MAX_SCREEN_OFF = mMaxScreenOffValue;
		} else if (seekBar.equals(mMinFreqScreenOnBar)) {
			Control.MIN_SCREEN_ON = mMinScreenOnValue;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		if (arg0.equals(mGovernorSpinner)) {
			int mCurGovernor = mAvailableGovernorList.indexOf(mCurGovernorRaw);
			if (arg2 != mCurGovernor) {
				MainActivity.mChange = true;
				Control.GOVERNOR = mAvailableGovernor[arg2];
			}
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		MainActivity.mChange = true;
		if (buttonView.equals(mCore)) {
			Control.CORE = isChecked;
		} else if (buttonView.equals(mIVA)) {
			Control.IVA = isChecked;
		} else if (buttonView.equals(mMPU)) {
			Control.MPU = isChecked;
		}
	}
}