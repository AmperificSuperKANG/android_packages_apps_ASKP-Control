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

import com.askp.control.Fragments.CpuFragment;
import com.askp.control.Fragments.DownloadFragment;
import com.askp.control.Fragments.GpuDisplayFragment;
import com.askp.control.Fragments.InformationFragment;
import com.askp.control.Fragments.InstallKernelFragment;
import com.askp.control.Fragments.IoAlgorithmFragment;
import com.askp.control.Fragments.MiscellaneousFragment;
import com.askp.control.Fragments.NewsFragment;
import com.askp.control.Utils.Control;
import com.askp.control.Utils.Utils;
import com.stericson.RootTools.RootTools;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends FragmentActivity {
	private static DrawerLayout mDrawerLayout;
	private static ListView mDrawerList;
	private static ActionBarDrawerToggle mDrawerToggle;

	private static CharSequence mTitle;
	private static String[] mMenuTitles;

	public static MenuItem applyButton;
	public static MenuItem cancelButton;
	private static MenuItem setonbootBox;

	public static boolean mCpuAction = false;
	public static boolean mGpuDisplayAction = false;
	public static boolean mIoAlgorithmAction = false;
	public static boolean mMiscellaneousAction = false;

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

		mTitle = getTitle();
		mMenuTitles = new String[] { getString(R.string.information),
				getString(R.string.cpu),
				getString(R.string.gpu) + " & " + getString(R.string.display),
				getString(R.string.ioalgorithm),
				getString(R.string.miscellaneous),
				getString(R.string.download),
				getString(R.string.installkernel), getString(R.string.news) };
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mMenuTitles));
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
			mDrawerLayout.openDrawer(mDrawerList);
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
				Utils.getBoolean("setonboot", getApplicationContext()));
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
			setonbootBox.setChecked(Utils.getBoolean("setonboot",
					getApplicationContext()) ? false : true);
			Utils.saveBoolean("setonboot", Utils.getBoolean("setonboot",
					getApplicationContext()) ? false : true,
					getApplicationContext());
			break;
		}
		if (mDrawerToggle.onOptionsItemSelected(item))
			return true;
		else
			return false;
	}

	private void selectItem(int position) {
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
}