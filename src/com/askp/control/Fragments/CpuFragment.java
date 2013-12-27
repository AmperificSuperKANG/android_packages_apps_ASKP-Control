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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.LinearLayout;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.askp.control.MainActivity;
import com.askp.control.R;
import com.askp.control.Utils.CpuValues;
import com.askp.control.Utils.LayoutStyle;
import com.askp.control.Utils.Utils;
import com.askp.control.Utils.ValueEditor;

public class CpuFragment extends Fragment implements OnSeekBarChangeListener,
		OnItemSelectedListener, OnCheckedChangeListener, OnClickListener {
	private static Context context;

	private static OnSeekBarChangeListener SeekBarChangeListener;
	private static OnItemSelectedListener ItemSelectedListener;
	private static OnCheckedChangeListener CheckedChangeListener;
	private static OnClickListener ClickListener;

	private static LinearLayout mLayout;

	private static String[] mPresentCpu;
	private static CurCPUThread mCurCPUThread;
	private static TextView[] mCurCpuFreqTexts;

	private static TextView mMaxCpuFreqText, mMinCpuFreqText,
			mMaxFreqScreenOffText, mMinFreqScreenOnText;

	private static SeekBar mMaxCpuFreqBar, mMinCpuFreqBar,
			mMaxScreenFreqOffBar, mMinFreqScreenOnBar;

	public static String[] mAvailableFreq;
	private static List<String> mAvailableFreqList = new ArrayList<String>();

	public static String mMaxFreqValue, mMinFreqValue, mMaxScreenOffValue,
			mMinScreenOnValue;

	private static SeekBar mMulticoreSavingBar;
	public static TextView mMulticoreSavingText;

	private static SeekBar mTempLimitBar;
	public static TextView mTempLimitText;

	public static Spinner mGovernorSpinner;
	public static String[] mAvailableGovernor;
	private static List<String> mAvailableGovernorList = new ArrayList<String>();

	public static CheckBox mCore, mIVA, mMPU;

	public static TextView[] mCoreVoltagesTexts;
	private static String[] mCoreVoltagesList;
	private static SeekBar[] mCoreVoltagesBars;
	public static List<String> mCoreVoltagesValuesList = new ArrayList<String>();

	public static TextView[] mIVAVoltagesTexts;
	private static String[] mIVAVoltagesList;
	private static SeekBar[] mIVAVoltagesBars;
	public static List<String> mIVAVoltagesValuesList = new ArrayList<String>();

	public static TextView[] mMPUVoltagesTexts;
	private static String[] mMPUVoltagesList;
	private static SeekBar[] mMPUVoltagesBars;
	public static List<String> mMPUVoltagesValuesList = new ArrayList<String>();

	public static TextView[] mRegulatorVoltagesTexts;
	private static String[] mRegulatorVoltagesList;
	private static SeekBar[] mRegulatorVoltagesBars;
	public static List<String> mRegulatorVoltagesValuesList = new ArrayList<String>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		View rootView = inflater.inflate(R.layout.layout, container, false);

		mLayout = (LinearLayout) rootView.findViewById(R.id.layout);
		SeekBarChangeListener = this;
		ItemSelectedListener = this;
		CheckedChangeListener = this;
		ClickListener = this;
		setContent();

		return rootView;
	}

	public static void setContent() {
		mLayout.removeAllViews();

		// Current Freq Title
		TextView mCpuFreqTitle = new TextView(context);
		LayoutStyle.setTextTitle(mCpuFreqTitle,
				context.getString(R.string.cpufreq), context);

		if (Utils.existFile(CpuValues.FILENAME_CUR_CPU_FREQ.replace(
				"presentcpu", "cpu0")))
			mLayout.addView(mCpuFreqTitle);

		mPresentCpu = CpuValues.mPresentCpu().split("-");
		mCurCpuFreqTexts = new TextView[mPresentCpu.length];
		for (int i = 0; i < mPresentCpu.length; i++) {
			// Current Freq SubTitle
			TextView mCurCpuFreqSubTitle = new TextView(context);
			LayoutStyle.setTextSubTitle(
					mCurCpuFreqSubTitle,
					context.getString(R.string.core) + " "
							+ String.valueOf(i + 1), context);

			// Current Freq Text
			TextView mCurCpuFreqText = new TextView(context);
			LayoutStyle.setCenterText(mCurCpuFreqText, "");
			mCurCpuFreqText.setTextSize(20);
			mCurCpuFreqText.setTextColor(context.getResources().getColor(
					android.R.color.white));
			mCurCpuFreqTexts[i] = mCurCpuFreqText;

			if (Utils.existFile(CpuValues.FILENAME_CUR_CPU_FREQ.replace(
					"presentcpu", "cpu" + String.valueOf(i)))) {
				mLayout.addView(mCurCpuFreqSubTitle);
				mLayout.addView(mCurCpuFreqText);
			}
		}

		// Freq scaling Title
		TextView mFreqScalingTitle = new TextView(context);
		LayoutStyle.setTextTitle(mFreqScalingTitle,
				context.getString(R.string.cpuscaling), context);

		if (Utils.existFile(CpuValues.FILENAME_MAX_FREQ)
				|| Utils.existFile(CpuValues.FILENAME_MIN_FREQ)
				|| Utils.existFile(CpuValues.FILENAME_MAX_SCREEN_OFF)
				|| Utils.existFile(CpuValues.FILENAME_MIN_SCREEN_ON))
			mLayout.addView(mFreqScalingTitle);

		// Max Freq Title
		TextView mMaxCpuFreqTitle = new TextView(context);
		LayoutStyle.setTextSubTitle(mMaxCpuFreqTitle,
				context.getString(R.string.cpumaxfreq), context);

		// Max Freq Summary
		TextView mMaxCpuFreqSummary = new TextView(context);
		LayoutStyle.setTextSummary(mMaxCpuFreqSummary,
				context.getString(R.string.cpumaxfreq_summary));

		// Max Freq SeekBar
		mAvailableFreq = CpuValues.mAvailableFreq().split(" ");

		mAvailableFreqList = Arrays.asList(mAvailableFreq);
		int mMax = mAvailableFreqList.indexOf(String.valueOf(CpuValues
				.mMaxFreq()));
		int mMin = mAvailableFreqList.indexOf(String.valueOf(CpuValues
				.mMinFreq()));

		mMaxCpuFreqBar = new SeekBar(context);
		LayoutStyle.setSeekBar(mMaxCpuFreqBar, mAvailableFreq.length - 1, mMax);
		mMaxCpuFreqBar.setOnSeekBarChangeListener(SeekBarChangeListener);
		mMaxFreqValue = String.valueOf(CpuValues.mMaxFreq());

		// Max Freq TextView
		mMaxCpuFreqText = new TextView(context);
		LayoutStyle.setCenterText(mMaxCpuFreqText,
				String.valueOf(CpuValues.mMaxFreq() / 1000) + " MHz");

		if (Utils.existFile(CpuValues.FILENAME_MAX_FREQ)) {
			mLayout.addView(mMaxCpuFreqTitle);
			mLayout.addView(mMaxCpuFreqSummary);
			mLayout.addView(mMaxCpuFreqBar);
			mLayout.addView(mMaxCpuFreqText);
		}

		// Min Freq Title
		TextView mMinCpuFreqTitle = new TextView(context);
		LayoutStyle.setTextSubTitle(mMinCpuFreqTitle,
				context.getString(R.string.cpuminfreq), context);

		// Min Freq Summary
		TextView mMinCpuFreqSummary = new TextView(context);
		LayoutStyle.setTextSummary(mMinCpuFreqSummary,
				context.getString(R.string.cpuminfreq_summary));

		// Min Freq SeekBar
		mMinCpuFreqBar = new SeekBar(context);
		LayoutStyle.setSeekBar(mMinCpuFreqBar, mAvailableFreq.length - 1, mMin);
		mMinCpuFreqBar.setOnSeekBarChangeListener(SeekBarChangeListener);
		mMinFreqValue = String.valueOf(CpuValues.mMinFreq());

		// Min Freq TextView
		mMinCpuFreqText = new TextView(context);
		LayoutStyle.setCenterText(mMinCpuFreqText,
				String.valueOf(CpuValues.mMinFreq() / 1000) + " MHz");

		if (Utils.existFile(CpuValues.FILENAME_MIN_FREQ)) {
			mLayout.addView(mMinCpuFreqTitle);
			mLayout.addView(mMinCpuFreqSummary);
			mLayout.addView(mMinCpuFreqBar);
			mLayout.addView(mMinCpuFreqText);
		}

		// Max Freq Screen Off Title
		TextView mMaxFreqScreenOffTitle = new TextView(context);
		LayoutStyle.setTextSubTitle(mMaxFreqScreenOffTitle,
				context.getString(R.string.maxscreenoff), context);

		// Max Freq Screen Off Summary
		TextView mMaxFreqScreenOffSummary = new TextView(context);
		LayoutStyle.setTextSummary(mMaxFreqScreenOffSummary,
				context.getString(R.string.maxscreenoff_summary));

		// Max Freq Screen Off SeekBar
		mMaxScreenOffValue = String.valueOf(CpuValues.mMaxScreenOffFreq());
		int mMaxScreenOff = mAvailableFreqList.indexOf(mMaxScreenOffValue);

		mMaxScreenFreqOffBar = new SeekBar(context);
		LayoutStyle.setSeekBar(mMaxScreenFreqOffBar, mAvailableFreq.length - 1,
				mMaxScreenOff);
		mMaxScreenFreqOffBar.setOnSeekBarChangeListener(SeekBarChangeListener);

		// Max Freq Screen Off TextView
		mMaxFreqScreenOffText = new TextView(context);
		LayoutStyle.setCenterText(mMaxFreqScreenOffText,
				String.valueOf(CpuValues.mMaxScreenOffFreq() / 1000) + " MHz");

		if (Utils.existFile(CpuValues.FILENAME_MAX_SCREEN_OFF)) {
			mLayout.addView(mMaxFreqScreenOffTitle);
			mLayout.addView(mMaxFreqScreenOffSummary);
			mLayout.addView(mMaxScreenFreqOffBar);
			mLayout.addView(mMaxFreqScreenOffText);
		}

		// Min Freq Screen on Title
		TextView mMinFreqScreenOnTitle = new TextView(context);
		LayoutStyle.setTextSubTitle(mMinFreqScreenOnTitle,
				context.getString(R.string.minscreenon), context);

		// Min Freq Screen On Summary
		TextView mMinFreqScreenOnSummary = new TextView(context);
		LayoutStyle.setTextSummary(mMinFreqScreenOnSummary,
				context.getString(R.string.minscreenon_summary));

		// Min Freq Screen On SeekBar
		mMinScreenOnValue = String.valueOf(CpuValues.mMinScreenOnFreq());
		int mMinScreenOn = mAvailableFreqList.indexOf(mMinScreenOnValue);

		mMinFreqScreenOnBar = new SeekBar(context);
		LayoutStyle.setSeekBar(mMinFreqScreenOnBar, mAvailableFreq.length - 1,
				mMinScreenOn);
		mMinFreqScreenOnBar.setOnSeekBarChangeListener(SeekBarChangeListener);

		// Min Freq Screen On TextView
		mMinFreqScreenOnText = new TextView(context);
		LayoutStyle.setCenterText(mMinFreqScreenOnText,
				String.valueOf(CpuValues.mMinScreenOnFreq() / 1000) + " MHz");

		if (Utils.existFile(CpuValues.FILENAME_MIN_SCREEN_ON)) {
			mLayout.addView(mMinFreqScreenOnTitle);
			mLayout.addView(mMinFreqScreenOnSummary);
			mLayout.addView(mMinFreqScreenOnBar);
			mLayout.addView(mMinFreqScreenOnText);
		}

		// Multicore Saving Title
		TextView mMulticoreSavingTitle = new TextView(context);
		LayoutStyle.setTextTitle(mMulticoreSavingTitle,
				context.getString(R.string.multicoresaving), context);

		// Multicore Saving Summary
		TextView mMulticoreSavingSummary = new TextView(context);
		LayoutStyle.setTextSummary(mMulticoreSavingSummary,
				context.getString(R.string.multicoresaving_summary));

		// Multicore Saving SeekBar
		mMulticoreSavingBar = new SeekBar(context);
		LayoutStyle.setSeekBar(mMulticoreSavingBar, 2,
				CpuValues.mMulticoreSaving());
		mMulticoreSavingBar.setOnSeekBarChangeListener(SeekBarChangeListener);

		// Multicore Saving Text
		mMulticoreSavingText = new TextView(context);
		LayoutStyle.setCenterText(mMulticoreSavingText,
				String.valueOf(CpuValues.mMulticoreSaving()));

		if (Utils.existFile(CpuValues.FILENAME_MULTICORE_SAVING)) {
			mLayout.addView(mMulticoreSavingTitle);
			mLayout.addView(mMulticoreSavingSummary);
			mLayout.addView(mMulticoreSavingBar);
			mLayout.addView(mMulticoreSavingText);
		}

		// Temp Limit Title
		TextView mTempLimitTitle = new TextView(context);
		LayoutStyle.setTextTitle(mTempLimitTitle,
				context.getString(R.string.templimit), context);

		// Temp Limit Summary
		TextView mTempLimitSummary = new TextView(context);
		LayoutStyle.setTextSummary(mTempLimitSummary,
				context.getString(R.string.templimit_summary));

		// Temp Limit SeekBar
		mTempLimitBar = new SeekBar(context);
		LayoutStyle.setSeekBar(mTempLimitBar, 20,
				CpuValues.mTempLimit() / 1000 - 60);
		mTempLimitBar.setOnSeekBarChangeListener(SeekBarChangeListener);

		// Temp Limit Text
		mTempLimitText = new TextView(context);
		LayoutStyle.setCenterText(mTempLimitText,
				String.valueOf(CpuValues.mTempLimit() / 1000));
		mTempLimitText.setOnClickListener(ClickListener);

		if (Utils.existFile(CpuValues.FILENAME_TEMP_LIMIT)) {
			mLayout.addView(mTempLimitTitle);
			mLayout.addView(mTempLimitSummary);
			mLayout.addView(mTempLimitBar);
			mLayout.addView(mTempLimitText);
		}

		// Governor Title
		TextView mGovernorTitle = new TextView(context);
		LayoutStyle.setTextTitle(mGovernorTitle,
				context.getString(R.string.governor), context);

		// Governor Summary
		TextView mGovernorSummary = new TextView(context);
		LayoutStyle.setTextSummary(mGovernorSummary,
				context.getString(R.string.governor_summary));

		// Governor Spinner
		mAvailableGovernor = CpuValues.mAvailableGovernor().split(" ");
		mAvailableGovernorList = Arrays.asList(mAvailableGovernor);

		ArrayAdapter<String> adapterGovernor = new ArrayAdapter<String>(
				context, android.R.layout.simple_spinner_item,
				mAvailableGovernor);
		adapterGovernor
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		mGovernorSpinner = new Spinner(context);
		LayoutStyle.setSpinner(mGovernorSpinner, adapterGovernor,
				mAvailableGovernorList.indexOf(CpuValues.mCurGovernor()));
		mGovernorSpinner.setOnItemSelectedListener(ItemSelectedListener);

		if (Utils.existFile(CpuValues.FILENAME_AVAILABLE_GOVERNOR)
				&& Utils.existFile(CpuValues.FILENAME_CUR_GOVERNOR)) {
			mLayout.addView(mGovernorTitle);
			mLayout.addView(mGovernorSummary);
			mLayout.addView(mGovernorSpinner);
		}

		// Smartreflex Title
		TextView mSmartreflexTitle = new TextView(context);
		LayoutStyle.setTextTitle(mSmartreflexTitle,
				context.getString(R.string.smartreflex), context);

		// Smartreflex Summary
		TextView mSmartreflexSummary = new TextView(context);
		LayoutStyle.setTextSummary(mSmartreflexSummary,
				context.getString(R.string.smartreflex_summary));

		if (Utils.existFile(CpuValues.FILENAME_CORE)
				|| Utils.existFile(CpuValues.FILENAME_IVA)
				|| Utils.existFile(CpuValues.FILENAME_MPU)) {
			mLayout.addView(mSmartreflexTitle);
			mLayout.addView(mSmartreflexSummary);
		}

		// Smartreflex Core
		mCore = new CheckBox(context);
		LayoutStyle.setCheckBox(mCore, context.getString(R.string.core),
				CpuValues.mCore() == 1);
		mCore.setOnCheckedChangeListener(CheckedChangeListener);

		if (Utils.existFile(CpuValues.FILENAME_CORE))
			mLayout.addView(mCore);

		// Smartreflex IVA
		mIVA = new CheckBox(context);
		LayoutStyle.setCheckBox(mIVA, context.getString(R.string.iva),
				CpuValues.mIVA() == 1);
		mIVA.setOnCheckedChangeListener(CheckedChangeListener);

		if (Utils.existFile(CpuValues.FILENAME_IVA))
			mLayout.addView(mIVA);

		// Smartreflex mMPU
		mMPU = new CheckBox(context);
		LayoutStyle.setCheckBox(mMPU, context.getString(R.string.mpu),
				CpuValues.mMPU() == 1);
		mMPU.setOnCheckedChangeListener(CheckedChangeListener);

		if (Utils.existFile(CpuValues.FILENAME_MPU))
			mLayout.addView(mMPU);

		// Core Voltages Title
		TextView mCoreVoltagesTitle = new TextView(context);
		LayoutStyle.setTextTitle(mCoreVoltagesTitle,
				context.getString(R.string.corevoltages), context);

		// Core Voltages Summary
		TextView mCoreVoltagesSummary = new TextView(context);
		LayoutStyle.setTextSummary(mCoreVoltagesSummary,
				context.getString(R.string.warning));

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
			TextView mCoreVoltagesSubtitle = new TextView(context);
			LayoutStyle.setTextSubTitle(mCoreVoltagesSubtitle,
					context.getString(R.string.voltage) + " " + mVoltageNumber,
					context);

			// Core Voltages SeekBar
			SeekBar mCoreVoltagesBar = new SeekBar(context);
			LayoutStyle.setSeekBar(mCoreVoltagesBar, 612,
					Integer.parseInt(mCoreVoltagesList[i]) - 700);
			mCoreVoltagesBar.setOnSeekBarChangeListener(SeekBarChangeListener);
			mCoreVoltagesBars[i] = mCoreVoltagesBar;

			// Core Voltages TextView
			TextView mCoreVoltagesText = new TextView(context);
			LayoutStyle.setCenterText(mCoreVoltagesText, mCoreVoltagesList[i]
					+ " mV");
			mCoreVoltagesText.setOnClickListener(ClickListener);
			mCoreVoltagesTexts[i] = mCoreVoltagesText;

			if (Utils.existFile(CpuValues.FILENAME_CORE_VOLTAGES)) {
				mLayout.addView(mCoreVoltagesSubtitle);
				mLayout.addView(mCoreVoltagesBar);
				mLayout.addView(mCoreVoltagesText);
			}
		}

		// IVA Voltages Title
		TextView mIVAVoltagesTitle = new TextView(context);
		LayoutStyle.setTextTitle(mIVAVoltagesTitle,
				context.getString(R.string.ivavoltages), context);

		// IVA Voltages Summary
		TextView mIVAVoltagesSummary = new TextView(context);
		LayoutStyle.setTextSummary(mIVAVoltagesSummary,
				context.getString(R.string.warning));

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
			TextView mIVAVoltagesSubtitle = new TextView(context);
			LayoutStyle.setTextSubTitle(mIVAVoltagesSubtitle,
					context.getString(R.string.voltage) + " " + mVoltageNumber,
					context);

			// IVA Voltages SeekBar
			SeekBar mIVAVoltagesBar = new SeekBar(context);
			LayoutStyle.setSeekBar(mIVAVoltagesBar, 816,
					Integer.parseInt(mIVAVoltagesList[i]) - 700);
			mIVAVoltagesBar.setOnSeekBarChangeListener(SeekBarChangeListener);
			mIVAVoltagesBars[i] = mIVAVoltagesBar;

			// IVA Voltages TextView
			TextView mIVAVoltagesText = new TextView(context);
			LayoutStyle.setCenterText(mIVAVoltagesText, mIVAVoltagesList[i]
					+ " mV");
			mIVAVoltagesText.setOnClickListener(ClickListener);
			mIVAVoltagesTexts[i] = mIVAVoltagesText;

			if (Utils.existFile(CpuValues.FILENAME_IVA_VOLTAGES)) {
				mLayout.addView(mIVAVoltagesSubtitle);
				mLayout.addView(mIVAVoltagesBar);
				mLayout.addView(mIVAVoltagesText);
			}
		}

		// MPU Voltages Title
		TextView mMPUVoltagesTitle = new TextView(context);
		LayoutStyle.setTextTitle(mMPUVoltagesTitle,
				context.getString(R.string.mpuvoltages), context);

		// MPU Voltages Summary
		TextView mMPUVoltagesSummary = new TextView(context);
		LayoutStyle.setTextSummary(mMPUVoltagesSummary,
				context.getString(R.string.warning));

		if (Utils.existFile(CpuValues.FILENAME_MPU_VOLTAGES)) {
			mLayout.addView(mMPUVoltagesTitle);
			mLayout.addView(mMPUVoltagesSummary);
		}

		mMPUVoltagesList = CpuValues.mMPUVoltagesFreq().split(";");
		mMPUVoltagesBars = new SeekBar[mMPUVoltagesList.length];
		mMPUVoltagesTexts = new TextView[mMPUVoltagesList.length];
		for (int i = 0; i < mMPUVoltagesList.length; i++) {

			// MPU Voltages Subtitle
			TextView mMPUVoltagesSubtitle = new TextView(context);
			LayoutStyle.setTextSubTitle(mMPUVoltagesSubtitle,
					mMPUVoltagesList[i].split(" ")[0] + " MHz", context);

			// MPU Voltages SeekBar
			SeekBar mMPUVoltagesBar = new SeekBar(context);
			LayoutStyle.setSeekBar(mMPUVoltagesBar, 918,
					Integer.parseInt(mMPUVoltagesList[i].split(" ")[1]) - 700);
			mMPUVoltagesBar.setOnSeekBarChangeListener(SeekBarChangeListener);
			mMPUVoltagesBars[i] = mMPUVoltagesBar;

			// MPU Voltages TextView
			TextView mMPUVoltagesText = new TextView(context);
			LayoutStyle.setCenterText(mMPUVoltagesText,
					mMPUVoltagesList[i].split(" ")[1] + " mV");
			mMPUVoltagesText.setOnClickListener(ClickListener);
			mMPUVoltagesTexts[i] = mMPUVoltagesText;

			if (Utils.existFile(CpuValues.FILENAME_MPU_VOLTAGES)) {
				mLayout.addView(mMPUVoltagesSubtitle);
				mLayout.addView(mMPUVoltagesBar);
				mLayout.addView(mMPUVoltagesText);
			}
		}

		// Regulator Voltages Title
		TextView mRegulatorVoltagesTitle = new TextView(context);
		LayoutStyle.setTextTitle(mRegulatorVoltagesTitle,
				context.getString(R.string.regulatorvoltages), context);

		// Regulator Voltages Summary
		TextView mRegulatorVoltagesSummary = new TextView(context);
		LayoutStyle.setTextSummary(mRegulatorVoltagesSummary,
				context.getString(R.string.warning));

		if (Utils.existFile(CpuValues.FILENAME_REGULATOR_VOLTAGES)) {
			mLayout.addView(mRegulatorVoltagesTitle);
			mLayout.addView(mRegulatorVoltagesSummary);
		}

		mRegulatorVoltagesList = CpuValues.mRegulatorVoltagesFreq().split(";");
		mRegulatorVoltagesBars = new SeekBar[mRegulatorVoltagesList.length];
		mRegulatorVoltagesTexts = new TextView[mRegulatorVoltagesList.length];
		for (int i = 0; i < mRegulatorVoltagesList.length; i++) {

			// Regulator Voltages Subtitle
			TextView mRegulatorVoltagesSubtitle = new TextView(context);
			LayoutStyle.setTextSubTitle(mRegulatorVoltagesSubtitle,
					mRegulatorVoltagesList[i].split(" ")[0], context);

			// Regulator Voltages SeekBar
			SeekBar mRegulatorVoltagesBar = new SeekBar(context);
			LayoutStyle
					.setSeekBar(mRegulatorVoltagesBar, 2100,
							Integer.parseInt(mRegulatorVoltagesList[i]
									.split(" ")[1]) - 1500);
			mRegulatorVoltagesBar
					.setOnSeekBarChangeListener(SeekBarChangeListener);
			mRegulatorVoltagesBars[i] = mRegulatorVoltagesBar;

			// Regulator Voltages TextView
			TextView mRegulatorVoltagesText = new TextView(context);
			LayoutStyle.setCenterText(mRegulatorVoltagesText,
					mRegulatorVoltagesList[i].split(" ")[1] + " mV");
			mRegulatorVoltagesText.setOnClickListener(ClickListener);
			mRegulatorVoltagesTexts[i] = mRegulatorVoltagesText;

			if (Utils.existFile(CpuValues.FILENAME_REGULATOR_VOLTAGES)) {
				mLayout.addView(mRegulatorVoltagesSubtitle);
				mLayout.addView(mRegulatorVoltagesBar);
				mLayout.addView(mRegulatorVoltagesText);
			}
		}
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
							""));
					sleep(500);
				}
			} catch (InterruptedException e) {
			}
		}
	}

	protected Handler mCurCPUHandler = new Handler() {
		public void handleMessage(Message msg) {
			for (int i = 0; i < mPresentCpu.length; i++) {
				int mFreq = 0;
				try {
					mFreq = Integer.parseInt(Utils
							.readLine(CpuValues.FILENAME_CUR_CPU_FREQ.replace(
									"presentcpu", "cpu" + String.valueOf(i))));
					mCurCpuFreqTexts[i].setText(mFreq != 0 ? String
							.valueOf(mFreq / 1000) + " MHz"
							: getString(R.string.offline));
				} catch (IOException e) {
				}
			}
		}
	};

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		mCoreVoltagesValuesList.clear();
		for (int i = 0; i < mCoreVoltagesList.length; i++) {
			if (seekBar.equals(mCoreVoltagesBars[i]))
				mCoreVoltagesTexts[i].setText(String.valueOf(progress + 700)
						+ " mV");
			mCoreVoltagesValuesList.add(mCoreVoltagesTexts[i].getText()
					.toString());
		}
		mIVAVoltagesValuesList.clear();
		for (int i = 0; i < mIVAVoltagesList.length; i++) {
			if (seekBar.equals(mIVAVoltagesBars[i]))
				mIVAVoltagesTexts[i].setText(String.valueOf(progress + 700)
						+ " mV");
			mIVAVoltagesValuesList.add(mIVAVoltagesTexts[i].getText()
					.toString());
		}
		mMPUVoltagesValuesList.clear();
		for (int i = 0; i < mMPUVoltagesList.length; i++) {
			if (seekBar.equals(mMPUVoltagesBars[i]))
				mMPUVoltagesTexts[i].setText(String.valueOf(progress + 700)
						+ " mV");
			mMPUVoltagesValuesList.add(mMPUVoltagesTexts[i].getText()
					.toString().replace(" mV", ""));
		}
		mRegulatorVoltagesValuesList.clear();
		for (int i = 0; i < mRegulatorVoltagesList.length; i++) {
			if (seekBar.equals(mRegulatorVoltagesBars[i]))
				mRegulatorVoltagesTexts[i].setText(String
						.valueOf(progress + 1500) + " mV");
			mRegulatorVoltagesValuesList.add(mRegulatorVoltagesTexts[i]
					.getText().toString().replace(" mV", ""));
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
		} else if (seekBar.equals(mMulticoreSavingBar)) {
			mMulticoreSavingText.setText(String.valueOf(progress));
		} else if (seekBar.equals(mTempLimitBar)) {
			mTempLimitText.setText(String.valueOf(progress + 60));
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		MainActivity.showButtons(true);
		MainActivity.mCpuAction = true;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		if (arg0.equals(mGovernorSpinner)) {
			if (arg2 != mAvailableGovernorList
					.indexOf(CpuValues.mCurGovernor())) {
				MainActivity.showButtons(true);
				MainActivity.mCpuAction = true;
			}
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		MainActivity.showButtons(true);
		MainActivity.mCpuAction = true;
	}

	@Override
	public void onClick(View v) {
		MainActivity.mCpuAction = true;
		for (int i = 0; i < mCoreVoltagesList.length; i++)
			if (v.equals(mCoreVoltagesTexts[i]))
				ValueEditor.showSeekBarEditor(
						mCoreVoltagesBars[i],
						mCoreVoltagesTexts[i].getText().toString()
								.replace(" mV", ""),
						context.getString(R.string.corevoltages), 700, context);
		for (int i = 0; i < mIVAVoltagesList.length; i++)
			if (v.equals(mIVAVoltagesTexts[i]))
				ValueEditor.showSeekBarEditor(
						mIVAVoltagesBars[i],
						mIVAVoltagesTexts[i].getText().toString()
								.replace(" mV", ""),
						context.getString(R.string.ivavoltages), 700, context);
		for (int i = 0; i < mMPUVoltagesList.length; i++)
			if (v.equals(mMPUVoltagesTexts[i]))
				ValueEditor.showSeekBarEditor(
						mMPUVoltagesBars[i],
						mMPUVoltagesTexts[i].getText().toString()
								.replace(" mV", ""),
						context.getString(R.string.mpuvoltages), 700, context);
		for (int i = 0; i < mRegulatorVoltagesList.length; i++)
			if (v.equals(mRegulatorVoltagesTexts[i]))
				ValueEditor.showSeekBarEditor(mRegulatorVoltagesBars[i],
						mRegulatorVoltagesTexts[i].getText().toString()
								.replace(" mV", ""),
						context.getString(R.string.regulatorvoltages), 1500,
						context);
		if (v.equals(mTempLimitText))
			ValueEditor.showSeekBarEditor(mTempLimitBar, mTempLimitText
					.getText().toString(), context
					.getString(R.string.templimit), 60, context);
	}
}