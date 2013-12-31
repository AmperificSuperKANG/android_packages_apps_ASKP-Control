package com.askp.control.activities;

import com.askp.control.R;
import com.askp.control.utils.Utils;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

public class SettingsActivity extends PreferenceActivity implements
		OnPreferenceClickListener {

	private static final CharSequence KEY_SHOW_DRAWER = "key_show_drawer";
	private static final CharSequence KEY_OTA_UPDATES = "key_ota_updates";

	private static CheckBoxPreference mShowDrawer;
	private static CheckBoxPreference mOtaUpdates;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings_header);

		mShowDrawer = (CheckBoxPreference) findPreference(KEY_SHOW_DRAWER);
		mShowDrawer.setChecked(Utils.getBoolean("showdrawer", true,
				getApplicationContext()));
		mShowDrawer.setOnPreferenceClickListener(this);

		mOtaUpdates = (CheckBoxPreference) findPreference(KEY_OTA_UPDATES);
		mOtaUpdates.setChecked(Utils.getBoolean("otaupdates", true, this));
		mOtaUpdates.setOnPreferenceClickListener(this);
	}

	@Override
	public boolean onPreferenceClick(Preference preference) {
		if (preference.equals(mShowDrawer)) {
			mShowDrawer.setChecked(mShowDrawer.isChecked());
			Utils.saveBoolean("showdrawer", mShowDrawer.isChecked(),
					getApplicationContext());
		} else if (preference.equals(mOtaUpdates)) {
			mOtaUpdates.setChecked(mOtaUpdates.isChecked());
			Utils.saveBoolean("otaupdates", mOtaUpdates.isChecked(),
					getApplicationContext());
		}
		return false;
	}
}