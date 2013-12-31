package com.askp.control.activities;

import com.askp.control.R;
import com.askp.control.utils.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.view.MenuItem;

public class SettingsActivity extends PreferenceActivity implements
		OnPreferenceClickListener {

	private static final CharSequence KEY_SHOW_DRAWER = "key_show_drawer";
	private static final CharSequence KEY_OTA_PERIOD = "key_ota_period";

	private static CheckBoxPreference mShowDrawer;
	private static Preference mOtaPeriod;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings_header);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mShowDrawer = (CheckBoxPreference) findPreference(KEY_SHOW_DRAWER);
		mShowDrawer.setChecked(Utils.getBoolean("showdrawer", true,
				getApplicationContext()));
		mShowDrawer.setOnPreferenceClickListener(this);

		mOtaPeriod = (Preference) findPreference(KEY_OTA_PERIOD);
		if (Utils.getInt("otaperiod", 2, this) == 0)
			mOtaPeriod.setSummary(getString(R.string.never));
		else
			mOtaPeriod.setSummary(getString(R.string.everyhour,
					String.valueOf(Utils.getInt("otaperiod", 2, this))));

		findPreference(KEY_OTA_PERIOD).setOnPreferenceClickListener(this);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onPreferenceClick(Preference preference) {
		if (preference.equals(mShowDrawer)) {
			mShowDrawer.setChecked(mShowDrawer.isChecked());
			Utils.saveBoolean("showdrawer", mShowDrawer.isChecked(),
					SettingsActivity.this);
		} else if (preference.equals(findPreference(KEY_OTA_PERIOD)))
			otaPeriod(SettingsActivity.this);
		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		onBackPressed();
		return true;
	}

	private static void otaPeriod(final Context context) {
		final String[] selection = new String[] {
				context.getString(R.string.never),
				context.getString(R.string.everyhour, "2"),
				context.getString(R.string.everyhour, "4"),
				context.getString(R.string.everyhour, "12"),
				context.getString(R.string.everyhour, "24"),
				context.getString(R.string.everyhour, "48") };
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(context.getString(R.string.otaperiod))
				.setItems(selection, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						mOtaPeriod.setSummary(selection[which]);
						switch (which) {
						case 0:
							Utils.saveInt("otaperiod", 0, context);
							break;
						case 1:
							Utils.saveInt("otaperiod", 2, context);
							break;
						case 2:
							Utils.saveInt("otaperiod", 4, context);
							break;
						case 3:
							Utils.saveInt("otaperiod", 12, context);
							break;
						case 4:
							Utils.saveInt("otaperiod", 24, context);
							break;
						case 5:
							Utils.saveInt("otaperiod", 48, context);
							break;
						}
					}
				}).show();
	}
}