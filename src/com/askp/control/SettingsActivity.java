package com.askp.control;

import com.askp.control.Utils.Utils;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

public class SettingsActivity extends PreferenceActivity implements
		OnPreferenceClickListener {

	private static final CharSequence KEY_SHOW_DRAWER = "key_show_drawer";

	private static CheckBoxPreference mShowDrawer;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings_header);

		mShowDrawer = (CheckBoxPreference) findPreference(KEY_SHOW_DRAWER);
		mShowDrawer.setChecked(Utils.getBoolean("showdrawer", true,
				getApplicationContext()));
		mShowDrawer.setOnPreferenceClickListener(this);
	}

	@Override
	public boolean onPreferenceClick(Preference preference) {
		if (preference.equals(mShowDrawer)) {
			mShowDrawer.setChecked(mShowDrawer.isChecked());
			Utils.saveBoolean("showdrawer", mShowDrawer.isChecked(),
					getApplicationContext());
		}
		return false;
	}
}