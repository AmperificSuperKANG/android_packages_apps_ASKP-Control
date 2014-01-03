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

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.SeekBar;

public class ValueEditor {

	private static EditText mValue;
	private static Button mPlus;
	private static Button mMinus;

	@SuppressWarnings("deprecation")
	public static void showSeekBarEditor(final SeekBar seekbar, String value,
			String title, final int calculate, final int steps,
			final Context context) {
		LinearLayout mLayout = new LinearLayout(context);

		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.FILL_PARENT);
		params.weight = 1.0f;
		params.gravity = Gravity.CENTER;

		mValue = new EditText(context);
		mValue.setLayoutParams(params);
		mValue.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED
				| InputType.TYPE_CLASS_NUMBER);
		mValue.setText(value);
		mValue.setGravity(Gravity.CENTER);

		mMinus = new Button(context);
		mMinus.setLayoutParams(params);
		mMinus.setGravity(Gravity.CENTER);
		mMinus.setText("-");
		mMinus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mValue.getText().toString().isEmpty())
					mValue.setText("0");
				mValue.setText(String.valueOf(Integer.parseInt(mValue.getText()
						.toString()) - steps));
			}
		});

		mPlus = new Button(context);
		mPlus.setLayoutParams(params);
		mPlus.setGravity(Gravity.CENTER);
		mPlus.setText("+");
		mPlus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mValue.getText().toString().isEmpty())
					mValue.setText("0");
				mValue.setText(String.valueOf(Integer.parseInt(mValue.getText()
						.toString()) + steps));
			}
		});

		mLayout.addView(mMinus);
		mLayout.addView(mValue);
		mLayout.addView(mPlus);

		Builder builder = new Builder(context);
		builder.setView(mLayout)
				.setTitle(title)
				.setNegativeButton(context.getString(android.R.string.cancel),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
							}
						})
				.setPositiveButton(context.getString(android.R.string.ok),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (mValue.getText().toString()
										.matches("[0-9]+")) {
									seekbar.setProgress((Integer
											.parseInt(mValue.getText()
													.toString()) - calculate)
											/ steps);
									MainActivity.showButtons(true);
								}
							}
						}).show();
	}
}
