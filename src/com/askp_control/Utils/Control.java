package com.askp_control.Utils;

import android.content.Context;

import com.askp_control.R;
import com.askp_control.Fragments.CpuFragment;

public class Control {

	public static String MAX_CPU_FREQ = String
			.valueOf(CpuFragment.mMaxCpuFreqRaw);

	public static String MIN_CPU_FREQ = String
			.valueOf(CpuFragment.mMinCpuFreqRaw);

	public static String MAX_SCREEN_OFF = String.valueOf(CpuValues
			.mMaxScreenOffFreq());

	public static String MIN_SCREEN_ON = String.valueOf(CpuValues
			.mMinScreenOnFreq());

	public static String GOVERNOR = CpuFragment.mCurGovernorRaw;

	public static void setValues(Context context) {

		// Max Cpu
		Utils.runCommand("echo " + MAX_CPU_FREQ + " > "
				+ CpuValues.FILENAME_MAX_FREQ);
		Utils.saveString("maxcpuvalue", MAX_CPU_FREQ, context);

		// Min Cpu
		Utils.runCommand("echo " + MIN_CPU_FREQ + " > "
				+ CpuValues.FILENAME_MIN_FREQ);
		Utils.saveString("mincpuvalue", MIN_CPU_FREQ, context);

		// Max Screen Off
		Utils.runCommand("echo " + MAX_SCREEN_OFF + " > "
				+ CpuValues.FILENAME_MAX_SCREEN_OFF);
		Utils.saveString("maxscreenoff", MAX_SCREEN_OFF, context);

		// Min Screen On
		Utils.runCommand("touch " + CpuValues.FILENAME_MIN_SCREEN_ON
				+ " && echo " + MIN_SCREEN_ON + " > "
				+ CpuValues.FILENAME_MIN_SCREEN_ON);
		Utils.saveString("minscreenon", MIN_SCREEN_ON, context);

		// Governor
		Utils.runCommand("echo " + GOVERNOR + " > "
				+ CpuValues.FILENAME_CUR_GOVERNOR);
		Utils.saveString("governor", GOVERNOR, context);
		CpuValues.mCurGovernor();
	}

	public static void setValuesback(Context context) {
		MAX_CPU_FREQ = String.valueOf(CpuFragment.mMaxCpuFreqRaw);
		MIN_CPU_FREQ = String.valueOf(CpuFragment.mMinCpuFreqRaw);
		MAX_SCREEN_OFF = String.valueOf(CpuValues.mMaxScreenOffFreq());
		MIN_SCREEN_ON = String.valueOf(CpuValues.mMinScreenOnFreq());
		GOVERNOR = CpuFragment.mCurGovernorRaw;

		Utils.toast(context.getString(R.string.valuesapplied),
				context.getApplicationContext());
	}
}
