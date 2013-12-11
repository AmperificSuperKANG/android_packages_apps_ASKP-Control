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

import java.util.ArrayList;
import java.util.List;

import com.askp.control.MainActivity;
import com.askp.control.R;
import com.askp.control.Utils.Control;
import com.askp.control.Utils.GpuDisplayValues;
import com.askp.control.Utils.LayoutStyle;
import com.askp.control.Utils.Utils;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class GpuDisplayFragment extends Fragment implements
		OnSeekBarChangeListener, OnCheckedChangeListener {

	private static Context context;

	private static LinearLayout mLayout;

	private static SeekBar mGpuMaxFreqBar;
	private static TextView mGpuMaxFreqText;
	private static String mGpuValue;
	private static int mGpuValueRaw;

	private static CheckBox mAdaptiveBrightnessBox;
	private static boolean mAdaptiveBrightnessBoolean;

	private static SeekBar mTrinityContrastBar;
	private static TextView mTrinityContrastText;

	private static SeekBar mGammaControlBar;
	private static TextView mGammaControlText;

	private static String[] mAvailableGammaOffset;
	private static SeekBar[] mGammaOffsetBars;
	private static TextView[] mGammaOffsetTexts;
	private static List<String> mGammaOffsetValueList = new ArrayList<String>();

	private static String[] mAvailableColorMultiplier;
	private static SeekBar[] mColorMultiplierBars;
	private static TextView[] mColorMultiplierTexts;
	private static List<String> mColorMultiplierValueList = new ArrayList<String>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.layout, container, false);
		context = getActivity();

		mLayout = (LinearLayout) rootView.findViewById(R.id.layout);

		// Gpu Scaling Title
		TextView mGpuScalingTitle = new TextView(getActivity());
		LayoutStyle.setTextTitle(mGpuScalingTitle,
				getString(R.string.gpuscaling), getActivity());

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
		mGpuMaxFreqBar.setOnSeekBarChangeListener(this);

		// Gpu Max Freq Text
		mGpuValue = "";
		if (GpuDisplayValues.mVariableGpu() == 0)
			mGpuValue = "307";
		if (GpuDisplayValues.mVariableGpu() == 1)
			mGpuValue = "384";
		if (GpuDisplayValues.mVariableGpu() == 2)
			mGpuValue = "512";
		mGpuMaxFreqText = new TextView(getActivity());

		if (Utils.existFile(GpuDisplayValues.FILENAME_VARIABLE_GPU)) {
			mLayout.addView(mGpuScalingTitle);
			mLayout.addView(mGpuMaxFreqSubTitle);
			mLayout.addView(mGpuMaxFreqSummary);
			mLayout.addView(mGpuMaxFreqBar);
			mLayout.addView(mGpuMaxFreqText);
		}

		ImageView mColor = new ImageView(getActivity());
		mColor.setImageResource(R.drawable.ic_color);

		if (Utils.existFile(GpuDisplayValues.FILENAME_ADAPTIVE_BRIGHTNESS)
				|| Utils.existFile(GpuDisplayValues.FILENAME_TRINITY_CONTRAST)
				|| Utils.existFile(GpuDisplayValues.FILENAME_GAMMA_CONTROL)
				|| Utils.existFile(GpuDisplayValues.FILENAME_GAMMA_OFFSET)
				|| Utils.existFile(GpuDisplayValues.FILENAME_COLOR_MULTIPLIER))
			mLayout.addView(mColor);

		// Display Title
		TextView mDisplayTitle = new TextView(getActivity());
		LayoutStyle.setTextTitle(mDisplayTitle, getString(R.string.display),
				getActivity());

		// Adaptive Brightness CheckBox
		mAdaptiveBrightnessBoolean = GpuDisplayValues.mAdaptiveBrightness() == 1;
		mAdaptiveBrightnessBox = new CheckBox(getActivity());

		// Adaptive Brightness Summary
		TextView mAdaptiveBrightnessSummary = new TextView(getActivity());
		LayoutStyle.setTextSummary(mAdaptiveBrightnessSummary,
				getString(R.string.adaptivebrightness_summary), getActivity());

		if (Utils.existFile(GpuDisplayValues.FILENAME_ADAPTIVE_BRIGHTNESS)) {
			mLayout.addView(mDisplayTitle);
			mLayout.addView(mAdaptiveBrightnessBox);
			mLayout.addView(mAdaptiveBrightnessSummary);
		}

		// Trinity Contrast Title
		TextView mTrinityContrastTitle = new TextView(getActivity());
		LayoutStyle.setTextTitle(mTrinityContrastTitle,
				getString(R.string.trinitycontrast).replace("ss", "'s"),
				getActivity());

		// Trinity Contrast SubTitle
		TextView mTrinityContrastSubTitle = new TextView(getActivity());
		LayoutStyle.setTextSubTitle(mTrinityContrastSubTitle,
				getString(R.string.contrast), getActivity());

		// Trinity Constrast Summary
		TextView mTrinityContrastSummary = new TextView(getActivity());
		LayoutStyle.setTextSummary(mTrinityContrastSummary,
				getString(R.string.contrast_summary), getActivity());

		// Trinity Constrast SeekBar
		mTrinityContrastBar = new SeekBar(getActivity());
		mTrinityContrastBar.setOnSeekBarChangeListener(this);

		// Trinity Constrast Text
		mTrinityContrastText = new TextView(getActivity());

		if (Utils.existFile(GpuDisplayValues.FILENAME_TRINITY_CONTRAST)) {
			mLayout.addView(mTrinityContrastTitle);
			mLayout.addView(mTrinityContrastSubTitle);
			mLayout.addView(mTrinityContrastSummary);
			mLayout.addView(mTrinityContrastBar);
			mLayout.addView(mTrinityContrastText);
		}

		ImageView mGray = new ImageView(getActivity());
		mGray.setImageResource(R.drawable.ic_gray);

		if (Utils.existFile(GpuDisplayValues.FILENAME_GAMMA_CONTROL))
			mLayout.addView(mGray);

		// Gamma Control Title
		TextView mGammaControlTitle = new TextView(getActivity());
		LayoutStyle.setTextTitle(mGammaControlTitle,
				getString(R.string.gammacontrol), getActivity());

		// Gamma Control SubTitle
		TextView mGammaControlSubTitle = new TextView(getActivity());
		LayoutStyle.setTextSubTitle(mGammaControlSubTitle,
				getString(R.string.setgamma), getActivity());

		// Gamma Control Summary
		TextView mGammaControlSummary = new TextView(getActivity());
		LayoutStyle.setTextSummary(mGammaControlSummary,
				getString(R.string.gammacontrol_summary), getActivity());

		// Gamma Control SeekBar
		mGammaControlBar = new SeekBar(getActivity());
		mGammaControlBar.setOnSeekBarChangeListener(this);

		// Gamma Control Text
		mGammaControlText = new TextView(getActivity());

		if (Utils.existFile(GpuDisplayValues.FILENAME_GAMMA_CONTROL)) {
			mLayout.addView(mGammaControlTitle);
			mLayout.addView(mGammaControlSubTitle);
			mLayout.addView(mGammaControlSummary);
			mLayout.addView(mGammaControlBar);
			mLayout.addView(mGammaControlText);
		}

		// Gamma Offset Title
		TextView mGammaOffsetTitle = new TextView(getActivity());
		LayoutStyle.setTextTitle(mGammaOffsetTitle,
				getString(R.string.gammaoffset), getActivity());

		// Gamma Offset Summary
		TextView mGammaOffsetSummary = new TextView(getActivity());
		LayoutStyle.setTextSummary(mGammaOffsetSummary,
				getString(R.string.gammaoffset_summary), getActivity());

		if (Utils.existFile(GpuDisplayValues.FILENAME_GAMMA_OFFSET)) {
			mLayout.addView(mGammaOffsetTitle);
			mLayout.addView(mGammaOffsetSummary);
		}

		mAvailableGammaOffset = GpuDisplayValues.mGammaOffset().split(" ");
		mGammaOffsetBars = new SeekBar[mAvailableGammaOffset.length];
		mGammaOffsetTexts = new TextView[mAvailableGammaOffset.length];
		for (int i = 0; i < mAvailableGammaOffset.length; i++) {
			// Gamma Offset SubTitle
			TextView mGammaOffsetSubTitle = new TextView(getActivity());
			LayoutStyle.setTextSubTitle(mGammaOffsetSubTitle,
					getString(R.string.red), getActivity());
			if (i == 1)
				mGammaOffsetSubTitle.setText(getString(R.string.green));
			if (i == 2)
				mGammaOffsetSubTitle.setText(getString(R.string.blue));
			if (i > 2)
				mGammaOffsetSubTitle.setText(getString(R.string.unavailable));

			// Gamma Offset SeekBar
			SeekBar mGammaOffsetBar = new SeekBar(getActivity());
			mGammaOffsetBar.setOnSeekBarChangeListener(this);
			mGammaOffsetBars[i] = mGammaOffsetBar;

			// Gamma Offset Text
			TextView mGammaOffsetText = new TextView(getActivity());
			mGammaOffsetTexts[i] = mGammaOffsetText;

			if (Utils.existFile(GpuDisplayValues.FILENAME_GAMMA_OFFSET)) {
				mLayout.addView(mGammaOffsetSubTitle);
				mLayout.addView(mGammaOffsetBar);
				mLayout.addView(mGammaOffsetText);
			}
		}

		// Color Multiplier Title
		TextView mColorMultiplierTitle = new TextView(getActivity());
		LayoutStyle.setTextTitle(mColorMultiplierTitle,
				getString(R.string.colormultipliers), getActivity());

		// Color Multiplier Summary
		TextView mColorMultiplierSummary = new TextView(getActivity());
		LayoutStyle.setTextSummary(mColorMultiplierSummary,
				getString(R.string.colormultipliers_summary), getActivity());

		if (Utils.existFile(GpuDisplayValues.FILENAME_COLOR_MULTIPLIER)) {
			mLayout.addView(mColorMultiplierTitle);
			mLayout.addView(mColorMultiplierSummary);
		}

		mAvailableColorMultiplier = GpuDisplayValues.mColorMultiplier().split(
				" ");
		mColorMultiplierBars = new SeekBar[mAvailableColorMultiplier.length];
		mColorMultiplierTexts = new TextView[mAvailableColorMultiplier.length];
		for (int i = 0; i < mAvailableColorMultiplier.length; i++) {
			// Color Multiplier SubTitle
			TextView mColorMultiplierSubTitle = new TextView(getActivity());
			LayoutStyle.setTextSubTitle(mColorMultiplierSubTitle,
					getString(R.string.red), getActivity());
			if (i == 1)
				mColorMultiplierSubTitle.setText(getString(R.string.green));
			if (i == 2)
				mColorMultiplierSubTitle.setText(getString(R.string.blue));
			if (i > 2)
				mColorMultiplierSubTitle
						.setText(getString(R.string.unavailable));

			// Color Multiplier SeekBar
			SeekBar mColorMultiplierBar = new SeekBar(getActivity());
			mColorMultiplierBar.setOnSeekBarChangeListener(this);
			mColorMultiplierBars[i] = mColorMultiplierBar;

			// Color Multiplier Text
			TextView mColorMultiplierText = new TextView(getActivity());
			mColorMultiplierTexts[i] = mColorMultiplierText;

			if (Utils.existFile(GpuDisplayValues.FILENAME_COLOR_MULTIPLIER)) {
				mLayout.addView(mColorMultiplierSubTitle);
				mLayout.addView(mColorMultiplierBar);
				mLayout.addView(mColorMultiplierText);
			}
		}

		setValues();

		mAdaptiveBrightnessBox.setOnCheckedChangeListener(this);
		return rootView;
	}

	public static void setValues() {
		// Gpu Max Freq
		LayoutStyle.setSeekBar(mGpuMaxFreqBar, 2,
				GpuDisplayValues.mVariableGpu());
		LayoutStyle.setCenterText(mGpuMaxFreqText, mGpuValue + " MHz", context);

		// Adaptive Brightness CheckBox
		LayoutStyle.setCheckBox(mAdaptiveBrightnessBox,
				context.getString(R.string.adaptivebrightness),
				mAdaptiveBrightnessBoolean);

		// Trinity Constrast
		LayoutStyle.setSeekBar(mTrinityContrastBar, 41,
				GpuDisplayValues.mTrinityContrast() + 25);
		LayoutStyle.setCenterText(mTrinityContrastText,
				String.valueOf(GpuDisplayValues.mTrinityContrast()), context);

		// Gamma Control
		LayoutStyle.setSeekBar(mGammaControlBar, 10,
				GpuDisplayValues.mGammaControl());
		LayoutStyle.setCenterText(mGammaControlText,
				String.valueOf(GpuDisplayValues.mGammaControl()), context);

		// Gamma Offset
		for (int i = 0; i < mAvailableGammaOffset.length; i++) {
			LayoutStyle.setSeekBar(mGammaOffsetBars[i], 30,
					Integer.parseInt(mAvailableGammaOffset[i]) + 15);
			LayoutStyle.setCenterText(mGammaOffsetTexts[i],
					mAvailableGammaOffset[i], context);
		}

		// Color Multiplier
		for (int i = 0; i < mAvailableColorMultiplier.length; i++) {
			LayoutStyle.setSeekBar(mColorMultiplierBars[i], 340, Integer
					.parseInt(mAvailableColorMultiplier[i].replace("0000000",
							"")) - 60);
			LayoutStyle.setCenterText(mColorMultiplierTexts[i], String
					.valueOf(mAvailableColorMultiplier[i]
							.replace("0000000", "")), context);
		}
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		mGammaOffsetValueList.clear();
		for (int i = 0; i < mAvailableGammaOffset.length; i++) {
			if (seekBar.equals(mGammaOffsetBars[i]))
				mGammaOffsetTexts[i].setText(String.valueOf(progress - 15));
			mGammaOffsetValueList
					.add(mGammaOffsetTexts[i].getText().toString());
		}
		mColorMultiplierValueList.clear();
		for (int i = 0; i < mAvailableColorMultiplier.length; i++) {
			if (seekBar.equals(mColorMultiplierBars[i]))
				mColorMultiplierTexts[i].setText(String.valueOf(progress + 60));
			mColorMultiplierValueList.add(String
					.valueOf(mColorMultiplierTexts[i].getText().toString())
					+ "0000000");
		}
		if (seekBar.equals(mGpuMaxFreqBar)) {
			mGpuValueRaw = progress;
			if (progress == 0)
				mGpuValue = "307";
			if (progress == 1)
				mGpuValue = "384";
			if (progress == 2)
				mGpuValue = "512";
			mGpuMaxFreqText.setText(mGpuValue + " MHz");
		} else if (seekBar.equals(mTrinityContrastBar)) {
			mTrinityContrastText.setText(String.valueOf(progress - 25));
		} else if (seekBar.equals(mGammaControlBar)) {
			mGammaControlText.setText(String.valueOf(progress));
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		MainActivity.enableButtons();
		MainActivity.mGpuDisplayAction = true;
		for (int i = 0; i < mAvailableGammaOffset.length; i++) {
			if (seekBar.equals(mGammaOffsetBars[i])) {
				StringBuilder mGammaOffsetValue = new StringBuilder();
				for (String s : mGammaOffsetValueList) {
					mGammaOffsetValue.append(s);
					mGammaOffsetValue.append("\t");
				}
				Control.GAMMA_OFFSET = mGammaOffsetValue.toString();
			}
		}
		for (int i = 0; i < mAvailableColorMultiplier.length; i++) {
			if (seekBar.equals(mColorMultiplierBars[i])) {
				StringBuilder mColorMultiplierValue = new StringBuilder();
				for (String s : mColorMultiplierValueList) {
					mColorMultiplierValue.append(s);
					mColorMultiplierValue.append("\t");
				}
				Control.COLOR_MULTIPLIER = mColorMultiplierValue.toString();
			}
		}
		if (seekBar.equals(mGpuMaxFreqBar)) {
			Control.GPU_VARIABLE = String.valueOf(mGpuValueRaw);
		} else if (seekBar.equals(mTrinityContrastBar)) {
			Control.TRINITY_CONTRAST = mTrinityContrastText.getText()
					.toString();
		} else if (seekBar.equals(mGammaControlBar)) {
			Control.GAMMA_CONTROL = mGammaControlText.getText().toString();
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		MainActivity.enableButtons();
		MainActivity.mGpuDisplayAction = true;
		if (buttonView.equals(mAdaptiveBrightnessBox))
			Control.APAPTIVE_BRIGHTNESS = isChecked ? "1" : "0";
	}
}
