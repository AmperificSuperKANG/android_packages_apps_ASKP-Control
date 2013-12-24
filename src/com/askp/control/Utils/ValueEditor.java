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

import com.askp.control.R;
import com.askp.control.Fragments.KernelControlFragment;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

public class ValueEditor {

	private static EditText mValue;
	private static Button mPlus;
	private static Button mMinus;

	public static void showSeekBarEditor(final SeekBar seekbar, String value,
			String title, final int calculate, final Context context) {
		LayoutInflater factory = LayoutInflater.from(context);
		final View btn = factory.inflate(R.layout.editor, null);

		mValue = (EditText) btn.findViewById(R.id.value);
		mValue.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED
				| InputType.TYPE_CLASS_NUMBER);
		mValue.setText(value);

		mPlus = (Button) btn.findViewById(R.id.plus);
		mPlus.setText("+");
		mPlus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mValue.setText(String.valueOf(Integer.parseInt(mValue.getText()
						.toString()) + 1));
			}
		});
		mMinus = (Button) btn.findViewById(R.id.minus);
		mMinus.setText("-");
		mMinus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mValue.setText(String.valueOf(Integer.parseInt(mValue.getText()
						.toString()) - 1));
			}
		});

		Builder builder = new Builder(context);
		builder.setView(btn)
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
									seekbar.setProgress(Integer.parseInt(mValue
											.getText().toString()) - calculate);
									KernelControlFragment.showButtons(true);
								}
							}
						}).show();
	}
}
