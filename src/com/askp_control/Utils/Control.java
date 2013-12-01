package com.askp_control.Utils;

import com.askp_control.Fragments.CpuFragment;

public class Control {

	public static String MAX_CPU_FREQ = String.valueOf(CpuValues.mMaxFreq());
	public static String MIN_CPU_FREQ = String
			.valueOf(CpuFragment.mMinCpuFreqRaw);

	public static void setValues() {
		Utils.runCommand("echo " + MAX_CPU_FREQ + " >"
				+ CpuValues.FILENAME_MAX_FREQ);

		Utils.runCommand("echo " + MIN_CPU_FREQ + " >"
				+ CpuValues.FILENAME_MIN_FREQ);
	}

	public static void setValuesback() {
		MAX_CPU_FREQ = String.valueOf(CpuValues.mMaxFreq());
		MIN_CPU_FREQ = String.valueOf(CpuFragment.mMinCpuFreqRaw);
	}
}
