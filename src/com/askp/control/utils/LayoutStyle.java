/*
 * Copyright (C) 2014 AmperificSuperKANG Project
 *
 * This file is part of ASKP Control.
 *
 * ASKP Control is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ASKP Control is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ASKP Control.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.askp.control.utils;

import com.askp.control.activities.MainActivity;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class LayoutStyle {

	public static int ColorBlue = android.R.color.holo_blue_dark;
	public static int ColorWhite = android.R.color.white;

	public static void setProgressBar(ProgressBar i, int max, int progress) {
		i.setMax(max);
		i.setProgress(progress);
	}

	public static void setButton(Button i, String text, Context context) {
		i.setBackgroundColor(context.getResources().getColor(ColorBlue));
		i.setText(text);
	}

	public static void setCheckBox(CheckBox i, String text, boolean checked) {
		i.setText(text);
		i.setChecked(checked);
	}

	public static void setSpinner(Spinner i, ArrayAdapter<String> adapter,
			int selection) {
		i.setAdapter(adapter);
		i.setSelection(selection);
	}

	public static void setCenterText(TextView i, String text) {
		i.setText(text);
		i.setGravity(Gravity.CENTER);
	}

	public static void setSeekBar(SeekBar i, int max, int progress) {
		i.setMax(max);
		i.setProgress(progress);
		i.setPadding(MainActivity.mWidth / 15, 0, MainActivity.mWidth / 15, 0);
	}

	public static void setTextSummary(TextView i, String text) {
		i.setTypeface(null, Typeface.ITALIC);
		i.setText(text);
	}

	public static void setTextSubTitle(TextView i, String text, Context context) {
		i.setTextColor(context.getResources().getColor(ColorWhite));
		i.setTypeface(null, Typeface.BOLD);
		i.setText(text);
	}

	public static void setTextTitle(TextView i, String text, Context context) {
		i.setBackgroundColor(context.getResources().getColor(ColorBlue));
		i.setTextColor(context.getResources().getColor(android.R.color.white));
		i.setTypeface(null, Typeface.BOLD);
		i.setText(text);
	}
}
