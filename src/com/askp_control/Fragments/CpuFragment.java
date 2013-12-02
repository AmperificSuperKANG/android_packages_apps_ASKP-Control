package com.askp_control.Fragments;

import java.util.ArrayList;
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

	public static String[] mAvailableFreq;
	private static List<String> mAvailableFreqList;

	public static int mMinCpuFreqRaw, mMaxCpuFreqRaw;

	private static String mMaxFreqValue, mMinFreqValue, mMaxScreenOffValue,
			mMinScreenOnValue;

	private static Spinner mGovernorSpinner;
	private static String[] mAvailableGovernor;
	private static List<String> mAvailableGovernorList;
	public static String mCurGovernorRaw;

	private static CheckBox mCore, mIVA, mMPU;

	private static TextView[] mCoreVoltagesTexts;
	private static String[] mCoreVoltagesList;
	private static SeekBar[] mCoreVoltagesBars;
	private static List<String> mCoreVoltagesValuesList = new ArrayList<String>();

	private static TextView[] mIVAVoltagesTexts;
	private static String[] mIVAVoltagesList;
	private static SeekBar[] mIVAVoltagesBars;
	private static List<String> mIVAVoltagesValuesList = new ArrayList<String>();

	private static TextView[] mMPUVoltagesTexts;
	private static String[] mMPUVoltagesList;
	private static SeekBar[] mMPUVoltagesBars;
	private static List<String> mMPUVoltagesValuesList = new ArrayList<String>();
	private static String[] mMPUVoltagesLine;

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

		// Core Voltages Title
		TextView mCoreVoltagesTitle = new TextView(getActivity());
		mCoreVoltagesTitle.setBackgroundColor(getResources().getColor(
				android.R.color.holo_blue_dark));
		mCoreVoltagesTitle.setTextColor(getResources().getColor(
				android.R.color.white));
		mCoreVoltagesTitle.setTypeface(null, Typeface.BOLD);
		mCoreVoltagesTitle.setText(getString(R.string.corevoltages));

		// Core Voltages Summary
		TextView mCoreVoltagesSummary = new TextView(getActivity());
		mCoreVoltagesSummary.setTypeface(null, Typeface.ITALIC);
		mCoreVoltagesSummary.setText(getString(R.string.warning));

		if (Utils.existFile(CpuValues.FILENAME_CORE_VOLTAGES)) {
			mLayout.addView(mCoreVoltagesTitle);
			mLayout.addView(mCoreVoltagesSummary);
		}

		mCoreVoltagesList = CpuValues.mCoreVoltagesFreq().split(" ");
		mCoreVoltagesBars = new SeekBar[mCoreVoltagesList.length];
		mCoreVoltagesTexts = new TextView[mCoreVoltagesList.length];
		for (int i = 0; i < mCoreVoltagesList.length; i++) {
			// Core Voltages Subtitle
			int mVoltageNumber = i + 1;
			TextView mCoreVoltagesSubtitle = new TextView(getActivity());
			mCoreVoltagesSubtitle.setTypeface(null, Typeface.BOLD);
			mCoreVoltagesSubtitle.setTextColor(getResources().getColor(
					android.R.color.white));
			mCoreVoltagesSubtitle.setText(getString(R.string.voltage) + " "
					+ mVoltageNumber);

			// Core Voltages SeekBar
			SeekBar mCoreVoltagesBar = new SeekBar(getActivity());
			mCoreVoltagesBar
					.setMax(Integer.parseInt(mCoreVoltagesList[0]) + 500);
			mCoreVoltagesBar
					.setProgress(Integer.parseInt(mCoreVoltagesList[i]));
			mCoreVoltagesBar.setOnSeekBarChangeListener(this);
			mCoreVoltagesBars[i] = mCoreVoltagesBar;

			// Core Voltages TextView
			TextView mCoreVoltagesText = new TextView(getActivity());
			mCoreVoltagesText.setText(mCoreVoltagesList[i] + " mV");
			mCoreVoltagesText.setGravity(Gravity.CENTER);
			mCoreVoltagesTexts[i] = mCoreVoltagesText;

			if (Utils.existFile(CpuValues.FILENAME_CORE_VOLTAGES)) {
				mLayout.addView(mCoreVoltagesSubtitle);
				mLayout.addView(mCoreVoltagesBar);
				mLayout.addView(mCoreVoltagesText);
			}
		}

		// IVA Voltages Title
		TextView mIVAVoltagesTitle = new TextView(getActivity());
		mIVAVoltagesTitle.setBackgroundColor(getResources().getColor(
				android.R.color.holo_blue_dark));
		mIVAVoltagesTitle.setTextColor(getResources().getColor(
				android.R.color.white));
		mIVAVoltagesTitle.setTypeface(null, Typeface.BOLD);
		mIVAVoltagesTitle.setText(getString(R.string.ivavoltages));

		// IVA Voltages Summary
		TextView mIVAVoltagesSummary = new TextView(getActivity());
		mIVAVoltagesSummary.setTypeface(null, Typeface.ITALIC);
		mIVAVoltagesSummary.setText(getString(R.string.warning));

		if (Utils.existFile(CpuValues.FILENAME_IVA_VOLTAGES)) {
			mLayout.addView(mIVAVoltagesTitle);
			mLayout.addView(mIVAVoltagesSummary);
		}

		mIVAVoltagesList = CpuValues.mIVAVoltagesFreq().split(" ");
		mIVAVoltagesBars = new SeekBar[mIVAVoltagesList.length];
		mIVAVoltagesTexts = new TextView[mIVAVoltagesList.length];
		for (int i = 0; i < mIVAVoltagesList.length; i++) {
			// IVA Voltages Subtitle
			int mVoltageNumber = i + 1;
			TextView mIVAVoltagesSubtitle = new TextView(getActivity());
			mIVAVoltagesSubtitle.setTypeface(null, Typeface.BOLD);
			mIVAVoltagesSubtitle.setTextColor(getResources().getColor(
					android.R.color.white));
			mIVAVoltagesSubtitle.setText(getString(R.string.voltage) + " "
					+ mVoltageNumber);

			// IVA Voltages SeekBar
			SeekBar mIVAVoltagesBar = new SeekBar(getActivity());
			mIVAVoltagesBar.setMax(Integer.parseInt(mIVAVoltagesList[0]) + 500);
			mIVAVoltagesBar.setProgress(Integer.parseInt(mIVAVoltagesList[i]));
			mIVAVoltagesBar.setOnSeekBarChangeListener(this);
			mIVAVoltagesBars[i] = mIVAVoltagesBar;

			// IVA Voltages TextView
			TextView mIVAVoltagesText = new TextView(getActivity());
			mIVAVoltagesText.setText(mIVAVoltagesList[i] + " mV");
			mIVAVoltagesText.setGravity(Gravity.CENTER);
			mIVAVoltagesTexts[i] = mIVAVoltagesText;

			if (Utils.existFile(CpuValues.FILENAME_IVA_VOLTAGES)) {
				mLayout.addView(mIVAVoltagesSubtitle);
				mLayout.addView(mIVAVoltagesBar);
				mLayout.addView(mIVAVoltagesText);
			}
		}

		// MPU Voltages Title
		TextView mMPUVoltagesTitle = new TextView(getActivity());
		mMPUVoltagesTitle.setBackgroundColor(getResources().getColor(
				android.R.color.holo_blue_dark));
		mMPUVoltagesTitle.setTextColor(getResources().getColor(
				android.R.color.white));
		mMPUVoltagesTitle.setTypeface(null, Typeface.BOLD);
		mMPUVoltagesTitle.setText(getString(R.string.mpuvoltages));

		// MPU Voltages Summary
		TextView mMPUVoltagesSummary = new TextView(getActivity());
		mMPUVoltagesSummary.setTypeface(null, Typeface.ITALIC);
		mMPUVoltagesSummary.setText(getString(R.string.warning));

		if (Utils.existFile(CpuValues.FILENAME_MPU_VOLTAGES)) {
			mLayout.addView(mMPUVoltagesTitle);
			mLayout.addView(mMPUVoltagesSummary);
		}

		mMPUVoltagesList = CpuValues.mMPUVoltagesFreq().split(";");
		mMPUVoltagesBars = new SeekBar[mMPUVoltagesList.length];
		mMPUVoltagesTexts = new TextView[mMPUVoltagesList.length];
		for (int i = 0; i < mMPUVoltagesList.length; i++) {
			// MPU Voltages Subtitle
			mMPUVoltagesLine = mMPUVoltagesList[i].split(" ");

			TextView mMPUVoltagesSubtitle = new TextView(getActivity());
			mMPUVoltagesSubtitle.setTypeface(null, Typeface.BOLD);
			mMPUVoltagesSubtitle.setTextColor(getResources().getColor(
					android.R.color.white));
			mMPUVoltagesSubtitle.setText(mMPUVoltagesLine[0] + " mV");

			// MPU Voltages SeekBar
			SeekBar mMPUVoltagesBar = new SeekBar(getActivity());
			mMPUVoltagesBar.setMax(Integer.parseInt(mMPUVoltagesList[0]
					.split(" ")[1]) + 500);
			mMPUVoltagesBar.setProgress(Integer.parseInt(mMPUVoltagesLine[1]));
			mMPUVoltagesBar.setOnSeekBarChangeListener(this);
			mMPUVoltagesBars[i] = mMPUVoltagesBar;

			// MPU Voltages TextView
			TextView mMPUVoltagesText = new TextView(getActivity());
			mMPUVoltagesText.setText(mMPUVoltagesLine[1] + " mV");
			mMPUVoltagesText.setGravity(Gravity.CENTER);
			mMPUVoltagesTexts[i] = mMPUVoltagesText;

			if (Utils.existFile(CpuValues.FILENAME_MPU_VOLTAGES)) {
				mLayout.addView(mMPUVoltagesSubtitle);
				mLayout.addView(mMPUVoltagesBar);
				mLayout.addView(mMPUVoltagesText);
			}
		}

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
		mCoreVoltagesValuesList.clear();
		for (int i = 0; i < mCoreVoltagesList.length; i++) {
			if (seekBar.equals(mCoreVoltagesBars[i])) {
				mCoreVoltagesTexts[i].setText(String.valueOf(progress) + " mV");
			}
			mCoreVoltagesValuesList.add(mCoreVoltagesTexts[i].getText()
					.toString());
		}
		mIVAVoltagesValuesList.clear();
		for (int i = 0; i < mIVAVoltagesList.length; i++) {
			if (seekBar.equals(mIVAVoltagesBars[i])) {
				mIVAVoltagesTexts[i].setText(String.valueOf(progress) + " mV");
			}
			mIVAVoltagesValuesList.add(mIVAVoltagesTexts[i].getText()
					.toString());
		}
		mMPUVoltagesValuesList.clear();
		for (int i = 0; i < mMPUVoltagesList.length; i++) {
			if (seekBar.equals(mMPUVoltagesBars[i])) {
				mMPUVoltagesTexts[i].setText(String.valueOf(progress) + " mV");
			}
			mMPUVoltagesValuesList.add(mMPUVoltagesTexts[i].getText()
					.toString().replace(" mV", ""));
		}
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
		for (int i = 0; i < mCoreVoltagesList.length; i++) {
			if (seekBar.equals(mCoreVoltagesBars[i])) {
				StringBuilder mCoreVoltagesValue = new StringBuilder();
				for (String s : mCoreVoltagesValuesList) {
					mCoreVoltagesValue.append(s);
					mCoreVoltagesValue.append("\t");
				}
				Control.CORE_VOLTAGE = mCoreVoltagesValue.toString();
			}
		}
		for (int i = 0; i < mIVAVoltagesList.length; i++) {
			if (seekBar.equals(mIVAVoltagesBars[i])) {
				StringBuilder mIVAVoltagesValue = new StringBuilder();
				for (String s : mIVAVoltagesValuesList) {
					mIVAVoltagesValue.append(s);
					mIVAVoltagesValue.append("\t");
				}
				Control.IVA_VOLTAGE = mIVAVoltagesValue.toString();
			}
		}
		for (int i = 0; i < mMPUVoltagesList.length; i++) {
			if (seekBar.equals(mMPUVoltagesBars[i])) {
				StringBuilder mMPUVoltagesValue = new StringBuilder();
				for (String s : mMPUVoltagesValuesList) {
					mMPUVoltagesValue.append(s);
					mMPUVoltagesValue.append("\t");
				}
				Control.MPU_VOLTAGE = mMPUVoltagesValue.toString();
			}
		}
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