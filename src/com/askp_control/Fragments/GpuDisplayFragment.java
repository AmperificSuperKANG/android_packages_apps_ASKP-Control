package com.askp_control.Fragments;

import java.util.ArrayList;
import java.util.List;

import com.askp_control.MainActivity;
import com.askp_control.R;
import com.askp_control.Utils.Control;
import com.askp_control.Utils.GpuDisplayValues;
import com.askp_control.Utils.LayoutStyle;
import com.askp_control.Utils.Utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class GpuDisplayFragment extends Fragment implements
		OnSeekBarChangeListener {

	private static LinearLayout mLayout;

	private static SeekBar mGpuMaxFreqBar;
	private static TextView mGpuMaxFreqText;
	private static String mGpuValue;
	private static int mGpuValueRaw;

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
		LayoutStyle.setSeekBar(mGpuMaxFreqBar, 2,
				GpuDisplayValues.mVariableGpu());
		mGpuMaxFreqBar.setOnSeekBarChangeListener(this);

		// Gpu Max Freq Text
		mGpuValue = "";
		switch (GpuDisplayValues.mVariableGpu()) {
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

		if (Utils.existFile(GpuDisplayValues.FILENAME_VARIABLE_GPU)) {
			mLayout.addView(mGpuScalingTitle);
			mLayout.addView(mGpuMaxFreqSubTitle);
			mLayout.addView(mGpuMaxFreqSummary);
			mLayout.addView(mGpuMaxFreqBar);
			mLayout.addView(mGpuMaxFreqText);
		}

		ImageView mColor = new ImageView(getActivity());
		mColor.setImageResource(R.drawable.ic_color);

		if (Utils.existFile(GpuDisplayValues.FILENAME_TRINITY_CONTRAST)
				|| Utils.existFile(GpuDisplayValues.FILENAME_GAMMA_CONTROL)
				|| Utils.existFile(GpuDisplayValues.FILENAME_GAMMA_OFFSET))
			mLayout.addView(mColor);

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
		LayoutStyle.setSeekBar(mTrinityContrastBar, 41,
				GpuDisplayValues.mTrinityContrast() + 25);
		mTrinityContrastBar.setOnSeekBarChangeListener(this);

		// Trinity Constrast Text
		mTrinityContrastText = new TextView(getActivity());
		LayoutStyle.setCenterText(mTrinityContrastText,
				String.valueOf(GpuDisplayValues.mTrinityContrast()),
				getActivity());

		if (Utils.existFile(GpuDisplayValues.FILENAME_TRINITY_CONTRAST)) {
			mLayout.addView(mTrinityContrastTitle);
			mLayout.addView(mTrinityContrastSubTitle);
			mLayout.addView(mTrinityContrastSummary);
			mLayout.addView(mTrinityContrastBar);
			mLayout.addView(mTrinityContrastText);
		}

		ImageView mGray = new ImageView(getActivity());
		mGray.setImageResource(R.drawable.ic_gray);

		if (Utils.existFile(GpuDisplayValues.FILENAME_GAMMA_CONTROL)) {
			mLayout.addView(mGray);
		}

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
		LayoutStyle.setSeekBar(mGammaControlBar, 10,
				GpuDisplayValues.mGammaControl());
		mGammaControlBar.setOnSeekBarChangeListener(this);

		// Gamma Control Text
		mGammaControlText = new TextView(getActivity());
		LayoutStyle
				.setCenterText(mGammaControlText,
						String.valueOf(GpuDisplayValues.mGammaControl()),
						getActivity());

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
			LayoutStyle.setSeekBar(mGammaOffsetBar, 455,
					Integer.parseInt(mAvailableGammaOffset[i]) + 255);
			mGammaOffsetBar.setOnSeekBarChangeListener(this);
			mGammaOffsetBars[i] = mGammaOffsetBar;

			// Gamma Offset Text
			TextView mGammaOffsetText = new TextView(getActivity());
			LayoutStyle.setCenterText(mGammaOffsetText,
					mAvailableGammaOffset[i], getActivity());
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
			// Gamma Offset SubTitle
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

			// Gamma Offset SeekBar
			SeekBar mColorMultiplierBar = new SeekBar(getActivity());
			LayoutStyle.setSeekBar(mColorMultiplierBar, 400,
					Integer.parseInt(mAvailableColorMultiplier[i]) / 10000000);
			mColorMultiplierBar.setOnSeekBarChangeListener(this);
			mColorMultiplierBars[i] = mColorMultiplierBar;

			// Gamma Offset Text
			TextView mColorMultiplierText = new TextView(getActivity());
			LayoutStyle
					.setCenterText(
							mColorMultiplierText,
							String.valueOf(Integer
									.parseInt(mAvailableColorMultiplier[i]) / 10000000),
							getActivity());
			mColorMultiplierTexts[i] = mColorMultiplierText;

			if (Utils.existFile(GpuDisplayValues.FILENAME_COLOR_MULTIPLIER)) {
				mLayout.addView(mColorMultiplierSubTitle);
				mLayout.addView(mColorMultiplierBar);
				mLayout.addView(mColorMultiplierText);
			}
		}

		return rootView;
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		mGammaOffsetValueList.clear();
		for (int i = 0; i < mAvailableGammaOffset.length; i++) {
			if (seekBar.equals(mGammaOffsetBars[i])) {
				mGammaOffsetTexts[i].setText(String.valueOf(progress - 255));
			}
			mGammaOffsetValueList
					.add(mGammaOffsetTexts[i].getText().toString());
		}
		mColorMultiplierValueList.clear();
		for (int i = 0; i < mAvailableColorMultiplier.length; i++) {
			if (seekBar.equals(mColorMultiplierBars[i])) {
				mColorMultiplierTexts[i].setText(String.valueOf(progress));
			}
			mColorMultiplierValueList
					.add(String.valueOf(Integer
							.parseInt(mColorMultiplierTexts[i].getText()
									.toString()) * 10000000));
		}
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
		MainActivity.mChange = true;
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
}
