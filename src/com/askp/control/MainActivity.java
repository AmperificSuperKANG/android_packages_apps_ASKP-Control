/*
 * Copyright (C) 2013 AmperificSuperKANG Project
 *
 * getActivity() program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 3 as published by the Free Software Foundation.
 *
 * getActivity() program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with getActivity() program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */

package com.askp.control;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.askp.control.Fragments.CpuFragment;
import com.askp.control.Fragments.DownloadFragment;
import com.askp.control.Fragments.GpuDisplayFragment;
import com.askp.control.Fragments.InformationFragment;
import com.askp.control.Fragments.InstallKernelFragment;
import com.askp.control.Fragments.IoAlgorithmFragment;
import com.askp.control.Fragments.MiscellaneousFragment;
import com.askp.control.Fragments.NewsFragment;
import com.askp.control.Utils.Control;
import com.askp.control.Utils.CpuValues;
import com.askp.control.Utils.LayoutStyle;
import com.askp.control.Utils.Utils;
import com.stericson.RootTools.RootTools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {
	private static DrawerLayout mDrawerLayout;
	private static ListView mDrawerList;
	private static ActionBarDrawerToggle mDrawerToggle;

	private static CharSequence mTitle;
	private static String[] mMenuTitles;

	private static MenuItem applyButton;
	private static MenuItem cancelButton;
	private static MenuItem setonbootBox;

	public static boolean mCpuAction = false;
	public static boolean mGpuDisplayAction = false;
	public static boolean mIoAlgorithmAction = false;
	public static boolean mMiscellaneousAction = false;

	public static int mWidth = 0;
	public static int mHeight = 0;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setBackgroundDrawable(
				getResources().getDrawable(android.R.color.transparent));
		setContentView(R.layout.activity_main);

		RootTools.debugMode = true;
		if (RootTools.isRootAvailable()) {
			if (RootTools.isAccessGiven()) {
				if (!RootTools.isBusyboxAvailable()) {
					Utils.toast(getString(R.string.nobusybox), this);
					RootTools.offerBusyBox(this);
					finish();
				}
			} else {
				Utils.toast(getString(R.string.norootaccess), this);
				finish();
			}
		} else {
			Utils.toast(getString(R.string.noroot), this);
			RootTools.offerSuperUser(this);
			finish();
		}

		Utils.saveString("kernelversion", Utils.getFormattedKernelVersion(),
				this);

		Utils.runCommand("chmod 777 " + CpuValues.FILENAME_MAX_FREQ);
		Utils.runCommand("chmod 777 " + CpuValues.FILENAME_MIN_FREQ);
		Utils.runCommand("chmod 777 " + CpuValues.FILENAME_MAX_SCREEN_OFF);
		Utils.runCommand("chmod 777 " + CpuValues.FILENAME_MIN_SCREEN_ON);
		Utils.runCommand("chmod 777 " + CpuValues.FILENAME_MIN_SCREEN_ON);
		Utils.runCommand("chmod 777 " + CpuValues.FILENAME_CUR_GOVERNOR);

		List<String> mVoltageFolder = new ArrayList<String>();
		for (File file : new File("/sys/devices/system/cpu/cpu0/cpufreq")
				.listFiles())
			mVoltageFolder.add(file.getAbsolutePath());

		for (int i = 0; i < mVoltageFolder.size(); i++)
			if (mVoltageFolder.get(i).indexOf("table") != -1)
				CpuValues.FILENAME_CPU_VOLTAGAES = mVoltageFolder.get(i);

		Display display = this.getWindowManager().getDefaultDisplay();
		mWidth = display.getWidth();
		mHeight = display.getHeight();

		mTitle = getTitle();
		mMenuTitles = new String[] { getString(R.string.information),
				getString(R.string.cpu),
				getString(R.string.gpu) + " & " + getString(R.string.display),
				getString(R.string.ioalgorithm),
				getString(R.string.miscellaneous),
				getString(R.string.download),
				getString(R.string.installkernel), getString(R.string.news) };

		for (int i = 0; i < mMenuTitles.length; i++)
			mMenuTitles[i] = mMenuTitles[i].toUpperCase();

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		mDrawerList.setAdapter(new CustomList(this, mMenuTitles, new Integer[] {
				R.drawable.ic_info, R.drawable.ic_cpu, R.drawable.ic_gpu,
				R.drawable.ic_io, R.drawable.ic_misce, R.drawable.ic_download,
				R.drawable.ic_install, R.drawable.ic_news }));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			selectItem(1);
			if (Utils.getBoolean("showdrawer", true, this))
				mDrawerLayout.openDrawer(mDrawerList);
		}

		if (Utils.getBoolean("firstuse", true, this)) {
			showTut(this);
			Utils.saveBoolean("firstuse", false, this);
		}
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		applyButton = menu.findItem(R.id.action_apply);
		cancelButton = menu.findItem(R.id.action_cancel);
		applyButton.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_WITH_TEXT
				| MenuItem.SHOW_AS_ACTION_ALWAYS);
		cancelButton.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_WITH_TEXT
				| MenuItem.SHOW_AS_ACTION_ALWAYS);
		showButtons(false);
		setonbootBox = menu.findItem(R.id.action_setonboot).setChecked(
				Utils.getBoolean("setonboot", false, getApplicationContext()));
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_apply:
			Control.initControl(getApplicationContext());
			break;
		case R.id.action_cancel:
			Control.exitControl(getApplicationContext());
			break;
		case R.id.action_setonboot:
			setonbootBox.setChecked(!Utils.getBoolean("setonboot", false,
					getApplicationContext()));
			Utils.saveBoolean("setonboot", !Utils.getBoolean("setonboot",
					false, getApplicationContext()), getApplicationContext());
			break;
		case R.id.action_settings:
			startActivity(new Intent(getApplicationContext(),
					SettingsActivity.class));
			break;
		}
		if (mDrawerToggle.onOptionsItemSelected(item))
			return true;
		else
			return false;
	}

	public void selectItem(int position) {
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new InformationFragment();
			break;
		case 1:
			fragment = new CpuFragment();
			break;
		case 2:
			fragment = new GpuDisplayFragment();
			break;
		case 3:
			fragment = new IoAlgorithmFragment();
			break;
		case 4:
			fragment = new MiscellaneousFragment();
			break;
		case 5:
			fragment = new DownloadFragment();
			break;
		case 6:
			fragment = new InstallKernelFragment();
			break;
		case 7:
			fragment = new NewsFragment();
			break;
		}

		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).commit();

		mDrawerList.setItemChecked(position, true);
		setTitle(mMenuTitles[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	public static void showButtons(boolean visible) {
		applyButton.setVisible(visible);
		cancelButton.setVisible(visible);
	}

	public static class CustomList extends ArrayAdapter<String> {

		private static Context mContext;
		private static String[] mText;
		private static Integer[] mImage;

		public CustomList(Context context, String[] text, Integer[] image) {
			super(context, R.layout.list_single, text);
			CustomList.mContext = context;
			CustomList.mText = text;
			CustomList.mImage = image;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			View rowView = inflater.inflate(R.layout.list_single, null, true);
			TextView txtTitle = (TextView) rowView.findViewById(R.id.text);

			ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
			txtTitle.setText(mText[position]);

			imageView.setImageResource(mImage[position]);
			return rowView;
		}
	}

	@SuppressWarnings("deprecation")
	public static void showTut(Context context) {
		LayoutInflater factory = LayoutInflater.from(context);
		View mView = factory.inflate(R.layout.layout, null);
		LinearLayout mLayout = (LinearLayout) mView.findViewById(R.id.layout);

		TextView mWelcomeText = new TextView(context);
		LayoutStyle.setTextTitle(
				mWelcomeText,
				context.getString(R.string.welcome,
						context.getString(R.string.app_name)), context);
		mWelcomeText.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		mWelcomeText.setGravity(Gravity.CENTER);
		mWelcomeText.setTextSize(40);

		TextView mAppSummary = new TextView(context);
		mAppSummary.setText("\n" + context.getString(R.string.app_summary));
		mAppSummary.setPadding(mWidth / 20, 0, mWidth / 20, 0);

		TextView mAppSummaryWarning = new TextView(context);
		mAppSummaryWarning.setText("\n"
				+ context.getString(R.string.app_summary_warning) + "\n");
		mAppSummaryWarning.setTextSize(15);
		mAppSummaryWarning.setTypeface(null, Typeface.BOLD_ITALIC);
		mAppSummaryWarning.setPadding(mWidth / 20, 0, mWidth / 20, 0);

		TextView mQuickGuide = new TextView(context);
		LayoutStyle.setTextTitle(mQuickGuide,
				context.getString(R.string.quickguide), context);
		mQuickGuide.setTextSize(20);

		TextView mQuickGuideSummary = new TextView(context);
		mQuickGuideSummary.setText("\n"
				+ context.getString(R.string.quickguide_summary,
						context.getString(R.string.app_name)) + "\n");
		mQuickGuideSummary.setPadding(mWidth / 20, 0, mWidth / 20, 0);

		ImageView mQuickGuideImage = new ImageView(context);
		mQuickGuideImage.setImageResource(R.drawable.ic_tut);
		mQuickGuideImage.setPadding(mWidth / 20, -mHeight / 20, mWidth / 20, 0);

		ImageView mQuickGuideImage2 = new ImageView(context);
		mQuickGuideImage2.setImageResource(R.drawable.ic_tut2);
		mQuickGuideImage2
				.setPadding(mWidth / 20, -mHeight / 20, mWidth / 20, 0);

		mLayout.addView(mWelcomeText);
		mLayout.addView(mAppSummary);
		mLayout.addView(mAppSummaryWarning);
		mLayout.addView(mQuickGuide);
		mLayout.addView(mQuickGuideSummary);
		mLayout.addView(mQuickGuideImage);
		mLayout.addView(mQuickGuideImage2);

		new AlertDialog.Builder(context).setView(mView).show();
	}
}