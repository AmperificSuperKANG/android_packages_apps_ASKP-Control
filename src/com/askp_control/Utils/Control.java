package com.askp_control.Utils;

import android.content.Context;

import com.askp_control.Fragments.CpuFragment;

public class Control {

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
	}

	public static void setValuesback() {
		MAX_CPU_FREQ = String.valueOf(CpuFragment.mMaxCpuFreqRaw);
		MIN_CPU_FREQ = String.valueOf(CpuFragment.mMinCpuFreqRaw);
	}
}
