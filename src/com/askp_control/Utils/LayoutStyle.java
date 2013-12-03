package com.askp_control.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class LayoutStyle {

	public static void setCheckBox(CheckBox i, String text, boolean checked) {
		i.setText(text);
		i.setChecked(checked);
	}

	public static void setSpinner(Spinner i, ArrayAdapter<String> adapter,
			int selection) {
		i.setAdapter(adapter);
		i.setSelection(selection);
	}

	public static void setCenterText(TextView i, String text, Context context) {
		i.setText(text);
		i.setGravity(Gravity.CENTER);
	}

	public static void setSeekBar(SeekBar i, int max, int progress) {
		i.setMax(max);
		i.setProgress(progress);
	}

	public static void setTextSummary(TextView i, String text, Context context) {
		i.setTypeface(null, Typeface.ITALIC);
		i.setText(text);
	}

	public static void setTextSubTitle(TextView i, String text, Context context) {
		i.setTextColor(context.getResources().getColor(android.R.color.white));
		i.setTypeface(null, Typeface.BOLD);
		i.setText(text);
	}

	public static void setTextTitle(TextView i, String text, Context context) {
		i.setBackgroundColor(context.getResources().getColor(
				android.R.color.holo_blue_dark));
		i.setTextColor(context.getResources().getColor(android.R.color.white));
		i.setTypeface(null, Typeface.BOLD);
		i.setText(text);
	}

}
