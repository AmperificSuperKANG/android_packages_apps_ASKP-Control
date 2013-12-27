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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.exceptions.RootDeniedException;
import com.stericson.RootTools.execution.CommandCapture;

public class Utils {

	private static final String FILENAME_PROC_VERSION = "/proc/version";

	private static ProgressDialog mProgressDialog;

	public static String replaceLastChar(String s, int length) {
		int slength = s.length();
		if (slength < length)
			return "Error";
		return s.substring(0, slength - length) + "";
	}

	public static void mkdir(String file) {
		File folder = new File(file);
		folder.mkdirs();
	}

	public static String listSplit(List<String> value) {
		StringBuilder mValue = new StringBuilder();
		for (String s : value) {
			mValue.append(s);
			mValue.append("\t");
		}
		return mValue.toString();
	}

	public static void displayprogress(String message, final Context context) {
		mProgressDialog = new ProgressDialog(context);
		mProgressDialog.setMessage(message);
		mProgressDialog.setIndeterminate(false);
		mProgressDialog
				.setOnDismissListener(new DialogInterface.OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialog) {
						((Activity) context).finish();
					}
				});
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgressDialog.show();
	}

	public static void hideprogress() {
		mProgressDialog.hide();
	}

	public static String getString(String name, Context context) {
		return context.getSharedPreferences("prefs", 0).getString(name,
				"nothing");
	}

	public static boolean getBoolean(String name, Context context) {
		return context.getSharedPreferences("prefs", 0).getBoolean(name, false);
	}

	public static void saveBoolean(String name, boolean value, Context context) {
		SharedPreferences mPref = context.getSharedPreferences("prefs", 0);
		SharedPreferences.Editor editorPref = mPref.edit();
		editorPref.putBoolean(name, value);
		editorPref.commit();
	}

	public static boolean existFile(String file) {
		return new File(file).exists();
	}

	public static void saveString(String name, String value, Context context) {
		SharedPreferences mPref = context.getSharedPreferences("prefs", 0);
		SharedPreferences.Editor editorPref = mPref.edit();
		editorPref.putString(name, value);
		editorPref.commit();
	}

	public static void toast(String text, Context context) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	public static void runCommand(String run) {
		try {
			RootTools.getShell(true).add(new CommandCapture(0, run))
					.commandCompleted(0, 0);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (RootDeniedException e) {
			e.printStackTrace();
		}
	}

	public static String readBlock(String filename) throws IOException {
		BufferedReader buffreader = new BufferedReader(
				new FileReader(filename), 256);
		String line;
		StringBuilder text = new StringBuilder();

		while ((line = buffreader.readLine()) != null)
			text.append(line);
		buffreader.close();
		return text.toString();
	}

	public static String readLine(String filename) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filename),
				256);
		try {
			return reader.readLine();
		} finally {
			reader.close();
		}
	}

	public static String getFormattedKernelVersion() {
		try {
			return formatKernelVersion(readLine(FILENAME_PROC_VERSION));
		} catch (IOException e) {
			return "Unavailable";
		}
	}

	private static String formatKernelVersion(String rawKernelVersion) {
		final String PROC_VERSION_REGEX = "Linux version (\\S+) "
				+ "\\((\\S+?)\\) " + "(?:\\(gcc.+? \\)) " + "(#\\d+) "
				+ "(?:.*?)?" + "((Sun|Mon|Tue|Wed|Thu|Fri|Sat).+)";

		Matcher m = Pattern.compile(PROC_VERSION_REGEX).matcher(
				rawKernelVersion);

		return !m.matches() || m.groupCount() < 4 ? "Unavailable" : m.group(1)
				+ "\n" + m.group(2) + " " + m.group(3) + "\n" + m.group(4);
	}
}
