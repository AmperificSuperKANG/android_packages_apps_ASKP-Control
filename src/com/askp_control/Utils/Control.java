package com.askp_control.Utils;

import android.content.Context;

import com.askp_control.R;
import com.askp_control.Fragments.CpuFragment;

public class Control {

	public static String GOVERNOR = CpuFragment.mCurGovernorRaw;

	public static String MAX_CPU_FREQ = String
			.valueOf(CpuFragment.mMaxCpuFreqRaw);

	public static String MIN_CPU_FREQ = String
			.valueOf(CpuFragment.mMinCpuFreqRaw);

	public static void setValues(Context context) {

		// Max Cpu
		Utils.runCommand("echo " + MAX_CPU_FREQ + " >"
				+ CpuValues.FILENAME_MAX_FREQ);
		Utils.saveString("maxcpuvalue", MAX_CPU_FREQ, context);

		// Min Cpu
		Utils.runCommand("echo " + MIN_CPU_FREQ + " >"
				+ CpuValues.FILENAME_MIN_FREQ);
		Utils.saveString("mincpuvalue", MIN_CPU_FREQ, context);

		// Governor
		Utils.runCommand("echo " + GOVERNOR + " >"
				+ CpuValues.FILENAME_CUR_GOVERNOR);
		Utils.saveString("governor", GOVERNOR, context);
		CpuValues.mCurGovernor();
	}

	public static void setValuesback(Context context) {
		GOVERNOR = CpuFragment.mCurGovernorRaw;
		MAX_CPU_FREQ = String.valueOf(CpuFragment.mMaxCpuFreqRaw);
		MIN_CPU_FREQ = String.valueOf(CpuFragment.mMinCpuFreqRaw);

		Utils.toast(context.getString(R.string.valuesapplied),
				context.getApplicationContext());
	}
}
