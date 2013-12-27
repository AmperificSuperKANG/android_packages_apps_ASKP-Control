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
import com.askp.control.Utils.GpuDisplayValues;
import com.askp.control.Utils.LayoutStyle;
import com.askp.control.Utils.Utils;
import com.askp.control.Utils.ValueEditor;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
		OnSeekBarChangeListener, OnCheckedChangeListener, OnClickListener {
	private static Context context;

	private static OnSeekBarChangeListener SeekBarChangeListener;
	private static OnCheckedChangeListener CheckedChangeListener;
	private static OnClickListener OnClickListener;

	private static LinearLayout mLayout;

	private static SeekBar mGpuMaxFreqBar;
	private static TextView mGpuMaxFreqText;
	private static String mGpuValue;
	public static int mGpuValueRaw;

	public static CheckBox mAdaptiveBrightnessBox;

	private static SeekBar mTrinityContrastBar;
	public static TextView mTrinityContrastText;

	private static SeekBar mGammaControlBar;
	public static TextView mGammaControlText;

	private static String[] mAvailableGammaOffset;
	private static SeekBar[] mGammaOffsetBars;
	private static TextView[] mGammaOffsetTexts;
	public static List<String> mGammaOffsetValueList = new ArrayList<String>();

	private static String[] mAvailableColorMultiplier;
	private static SeekBar[] mColorMultiplierBars;
	private static TextView[] mColorMultiplierTexts;
	public static List<String> mColorMultiplierValueList = new ArrayList<String>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		View rootView = inflater.inflate(R.layout.layout, container, false);

		mLayout = (LinearLayout) rootView.findViewById(R.id.layout);
		SeekBarChangeListener = this;
		CheckedChangeListener = this;
		OnClickListener = this;
		setContent();

		return rootView;
	}

	public static void setContent() {
		mLayout.removeAllViews();

		// Gpu Scaling Title
		TextView mGpuScalingTitle = new TextView(context);
		LayoutStyle.setTextTitle(mGpuScalingTitle,
				context.getString(R.string.gpuscaling), context);

		// Gpu Max Freq SubTitle
		TextView mGpuMaxFreqSubTitle = new TextView(context);
		LayoutStyle.setTextSubTitle(mGpuMaxFreqSubTitle,
				context.getString(R.string.gpumaxfreq), context);

		// Gpu Max Freq Summary
		TextView mGpuMaxFreqSummary = new TextView(context);
		LayoutStyle.setTextSummary(mGpuMaxFreqSummary,
				context.getString(R.string.gpumaxfreq_summary));

		// Gpu Max Freq SeekBar
		mGpuMaxFreqBar = new SeekBar(context);
		LayoutStyle.setSeekBar(mGpuMaxFreqBar, 2,
				GpuDisplayValues.mVariableGpu());
		mGpuMaxFreqBar.setOnSeekBarChangeListener(SeekBarChangeListener);

		// Gpu Max Freq Text
		if (GpuDisplayValues.mVariableGpu() == 0)
			mGpuValue = "307";
		else if (GpuDisplayValues.mVariableGpu() == 1)
			mGpuValue = "384";
		else if (GpuDisplayValues.mVariableGpu() == 2)
			mGpuValue = "512";
		mGpuMaxFreqText = new TextView(context);
		LayoutStyle.setCenterText(mGpuMaxFreqText, mGpuValue + " MHz");

		if (Utils.existFile(GpuDisplayValues.FILENAME_GPU_VARIABLE)) {
			mLayout.addView(mGpuScalingTitle);
			mLayout.addView(mGpuMaxFreqSubTitle);
			mLayout.addView(mGpuMaxFreqSummary);
			mLayout.addView(mGpuMaxFreqBar);
			mLayout.addView(mGpuMaxFreqText);
		}

		ImageView mColor = new ImageView(context);
		mColor.setImageResource(R.drawable.ic_color);

		if (Utils.existFile(GpuDisplayValues.FILENAME_ADAPTIVE_BRIGHTNESS)
				|| Utils.existFile(GpuDisplayValues.FILENAME_TRINITY_CONTRAST)
				|| Utils.existFile(GpuDisplayValues.FILENAME_GAMMA_CONTROL)
				|| Utils.existFile(GpuDisplayValues.FILENAME_GAMMA_OFFSET)
				|| Utils.existFile(GpuDisplayValues.FILENAME_COLOR_MULTIPLIER))
			mLayout.addView(mColor);

		// Adaptive Brightness Title
		TextView mAdaptiveBrightnessTitle = new TextView(context);
		LayoutStyle.setTextTitle(mAdaptiveBrightnessTitle,
				context.getString(R.string.adaptivebrightness), context);

		// Adaptive Brightness Summary
		TextView mAdaptiveBrightnessSummary = new TextView(context);
		LayoutStyle.setTextSummary(mAdaptiveBrightnessSummary,
				context.getString(R.string.adaptivebrightness_summary));

		// Adaptive Brightness Summary
		mAdaptiveBrightnessBox = new CheckBox(context);
		LayoutStyle.setCheckBox(mAdaptiveBrightnessBox,
				context.getString(R.string.adaptivebrightness),
				GpuDisplayValues.mAdaptiveBrightness() == 1);
		mAdaptiveBrightnessBox
				.setOnCheckedChangeListener(CheckedChangeListener);

		if (Utils.existFile(GpuDisplayValues.FILENAME_ADAPTIVE_BRIGHTNESS)) {
			mLayout.addView(mAdaptiveBrightnessTitle);
			mLayout.addView(mAdaptiveBrightnessSummary);
			mLayout.addView(mAdaptiveBrightnessBox);
		}

		// Trinity Contrast Title
		TextView mTrinityContrastTitle = new TextView(context);
		LayoutStyle
				.setTextTitle(
						mTrinityContrastTitle,
						context.getString(R.string.trinitycontrast).replace(
								"ss", "'s"), context);

		// Trinity Contrast SubTitle
		TextView mTrinityContrastSubTitle = new TextView(context);
		LayoutStyle.setTextSubTitle(mTrinityContrastSubTitle,
				context.getString(R.string.contrast), context);

		// Trinity Constrast Summary
		TextView mTrinityContrastSummary = new TextView(context);
		LayoutStyle.setTextSummary(mTrinityContrastSummary,
				context.getString(R.string.contrast_summary));

		// Trinity Constrast SeekBar
		mTrinityContrastBar = new SeekBar(context);
		LayoutStyle.setSeekBar(mTrinityContrastBar, 41,
				GpuDisplayValues.mTrinityContrast() + 25);
		mTrinityContrastBar.setOnSeekBarChangeListener(SeekBarChangeListener);

		// Trinity Constrast Text
		mTrinityContrastText = new TextView(context);
		LayoutStyle.setCenterText(mTrinityContrastText,
				String.valueOf(GpuDisplayValues.mTrinityContrast()));

		if (Utils.existFile(GpuDisplayValues.FILENAME_TRINITY_CONTRAST)) {
			mLayout.addView(mTrinityContrastTitle);
			mLayout.addView(mTrinityContrastSubTitle);
			mLayout.addView(mTrinityContrastSummary);
			mLayout.addView(mTrinityContrastBar);
			mLayout.addView(mTrinityContrastText);
		}

		ImageView mGray = new ImageView(context);
		mGray.setImageResource(R.drawable.ic_gray);

		if (Utils.existFile(GpuDisplayValues.FILENAME_GAMMA_CONTROL))
			mLayout.addView(mGray);

		// Gamma Control Title
		TextView mGammaControlTitle = new TextView(context);
		LayoutStyle.setTextTitle(mGammaControlTitle,
				context.getString(R.string.gammacontrol), context);

		// Gamma Control SubTitle
		TextView mGammaControlSubTitle = new TextView(context);
		LayoutStyle.setTextSubTitle(mGammaControlSubTitle,
				context.getString(R.string.setgamma), context);

		// Gamma Control Summary
		TextView mGammaControlSummary = new TextView(context);
		LayoutStyle.setTextSummary(mGammaControlSummary,
				context.getString(R.string.gammacontrol_summary));

		// Gamma Control SeekBar
		mGammaControlBar = new SeekBar(context);
		LayoutStyle.setSeekBar(mGammaControlBar, 10,
				GpuDisplayValues.mGammaControl());
		mGammaControlBar.setOnSeekBarChangeListener(SeekBarChangeListener);

		// Gamma Control Text
		mGammaControlText = new TextView(context);
		LayoutStyle.setCenterText(mGammaControlText,
				String.valueOf(GpuDisplayValues.mGammaControl()));

		if (Utils.existFile(GpuDisplayValues.FILENAME_GAMMA_CONTROL)) {
			mLayout.addView(mGammaControlTitle);
			mLayout.addView(mGammaControlSubTitle);
			mLayout.addView(mGammaControlSummary);
			mLayout.addView(mGammaControlBar);
			mLayout.addView(mGammaControlText);
		}

		// Gamma Offset Title
		TextView mGammaOffsetTitle = new TextView(context);
		LayoutStyle.setTextTitle(mGammaOffsetTitle,
				context.getString(R.string.gammaoffset), context);

		// Gamma Offset Summary
		TextView mGammaOffsetSummary = new TextView(context);
		LayoutStyle.setTextSummary(mGammaOffsetSummary,
				context.getString(R.string.gammaoffset_summary));

		if (Utils.existFile(GpuDisplayValues.FILENAME_GAMMA_OFFSET)) {
			mLayout.addView(mGammaOffsetTitle);
			mLayout.addView(mGammaOffsetSummary);
		}

		mAvailableGammaOffset = GpuDisplayValues.mGammaOffset().split(" ");
		mGammaOffsetBars = new SeekBar[mAvailableGammaOffset.length];
		mGammaOffsetTexts = new TextView[mAvailableGammaOffset.length];
		for (int i = 0; i < mAvailableGammaOffset.length; i++) {
			// Gamma Offset SubTitle
			TextView mGammaOffsetSubTitle = new TextView(context);
			LayoutStyle.setTextSubTitle(mGammaOffsetSubTitle,
					context.getString(R.string.red), context);
			if (i == 1)
				mGammaOffsetSubTitle.setText(context.getString(R.string.green));
			if (i == 2)
				mGammaOffsetSubTitle.setText(context.getString(R.string.blue));
			if (i > 2)
				mGammaOffsetSubTitle.setText(context
						.getString(R.string.unavailable));

			// Gamma Offset SeekBar
			SeekBar mGammaOffsetBar = new SeekBar(context);
			LayoutStyle.setSeekBar(mGammaOffsetBar, 30,
					Integer.parseInt(mAvailableGammaOffset[i]) + 15);
			mGammaOffsetBar.setOnSeekBarChangeListener(SeekBarChangeListener);
			mGammaOffsetBars[i] = mGammaOffsetBar;

			// Gamma Offset Text
			TextView mGammaOffsetText = new TextView(context);
			LayoutStyle.setCenterText(mGammaOffsetText,
					mAvailableGammaOffset[i]);
			mGammaOffsetTexts[i] = mGammaOffsetText;

			if (Utils.existFile(GpuDisplayValues.FILENAME_GAMMA_OFFSET)) {
				mLayout.addView(mGammaOffsetSubTitle);
				mLayout.addView(mGammaOffsetBar);
				mLayout.addView(mGammaOffsetText);
			}
		}

		// Color Multiplier Title
		TextView mColorMultiplierTitle = new TextView(context);
		LayoutStyle.setTextTitle(mColorMultiplierTitle,
				context.getString(R.string.colormultipliers), context);

		// Color Multiplier Summary
		TextView mColorMultiplierSummary = new TextView(context);
		LayoutStyle.setTextSummary(mColorMultiplierSummary,
				context.getString(R.string.colormultipliers_summary));

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
			TextView mColorMultiplierSubTitle = new TextView(context);
			LayoutStyle.setTextSubTitle(mColorMultiplierSubTitle,
					context.getString(R.string.red), context);
			if (i == 1)
				mColorMultiplierSubTitle.setText(context
						.getString(R.string.green));
			if (i == 2)
				mColorMultiplierSubTitle.setText(context
						.getString(R.string.blue));
			if (i > 2)
				mColorMultiplierSubTitle.setText(context
						.getString(R.string.unavailable));

			// Color Multiplier SeekBar
			SeekBar mColorMultiplierBar = new SeekBar(context);
			LayoutStyle.setSeekBar(mColorMultiplierBar, 340, Integer
					.parseInt(Utils.replaceLastChar(
							mAvailableColorMultiplier[i], 7)) - 60);
			mColorMultiplierBar
					.setOnSeekBarChangeListener(SeekBarChangeListener);
			mColorMultiplierBars[i] = mColorMultiplierBar;

			// Color Multiplier Text
			TextView mColorMultiplierText = new TextView(context);
			LayoutStyle.setCenterText(mColorMultiplierText,
					Utils.replaceLastChar(mAvailableColorMultiplier[i], 7));
			mColorMultiplierText.setOnClickListener(OnClickListener);
			mColorMultiplierTexts[i] = mColorMultiplierText;
			Utils.toast(mAvailableColorMultiplier[i], context);

			if (Utils.existFile(GpuDisplayValues.FILENAME_COLOR_MULTIPLIER)) {
				mLayout.addView(mColorMultiplierSubTitle);
				mLayout.addView(mColorMultiplierBar);
				mLayout.addView(mColorMultiplierText);
			}
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
		MainActivity.showButtons(true);
		MainActivity.mGpuDisplayAction = true;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		MainActivity.showButtons(true);
		MainActivity.mGpuDisplayAction = true;
	}

	@Override
	public void onClick(View v) {
		MainActivity.mGpuDisplayAction = true;
		for (int i = 0; i < mAvailableColorMultiplier.length; i++) {
			if (v.equals(mColorMultiplierTexts[i])) {
				ValueEditor.showSeekBarEditor(mColorMultiplierBars[i],
						mColorMultiplierTexts[i].getText().toString(),
						context.getString(R.string.colormultipliers), 60,
						context);
			}
		}
	}
}
