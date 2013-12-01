package com.askp_control.Utils;

public class Control {

	public static void setMaxFreq(String value) {
		Utils.runCommand("echo " + value + " >" + CpuValues.FILENAME_MAX_FREQ);
	}

	public static void setMinFreq(String value) {
		Utils.runCommand("echo " + value + " >" + CpuValues.FILENAME_MIN_FREQ);
	}
}
