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

package com.askp.control.Utils;

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
