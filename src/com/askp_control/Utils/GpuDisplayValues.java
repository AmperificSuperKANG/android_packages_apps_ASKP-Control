package com.askp_control.Utils;

import java.io.IOException;

public class GpuDisplayValues {

	public static String FILENAME_COLOR_MULTIPLIER = "/sys/class/misc/colorcontrol/multiplier";
	public static String FILENAME_GAMMA_OFFSET = "/sys/class/misc/colorcontrol/v1_offset";
	public static String FILENAME_GAMMA_CONTROL = "/sys/devices/platform/omapdss/manager0/gamma";
	public static String FILENAME_TRINITY_CONTRAST = "/sys/module/panel_s6e8aa0/parameters/contrast";
	public static String FILENAME_VARIABLE_GPU = "/sys/devices/system/cpu/cpu0/cpufreq/gpu_oc";

	public static String mColorMultiplier() {
		if (Utils.existFile(FILENAME_COLOR_MULTIPLIER))
			try {
				return (Utils.readLine(FILENAME_COLOR_MULTIPLIER));
			} catch (IOException e) {
				e.printStackTrace();
			}
		return "0 0";
	}

	public static String mGammaOffset() {
		if (Utils.existFile(FILENAME_GAMMA_OFFSET))
			try {
				return (Utils.readLine(FILENAME_GAMMA_OFFSET));
			} catch (IOException e) {
				e.printStackTrace();
			}
		return "0 0";
	}

	public static int mGammaControl() {
		if (Utils.existFile(FILENAME_GAMMA_CONTROL))
			try {
				return Integer.parseInt(Utils.readLine(FILENAME_GAMMA_CONTROL));
			} catch (IOException e) {
				e.printStackTrace();
			}
		return 0;
	}

	public static int mTrinityContrast() {
		if (Utils.existFile(FILENAME_TRINITY_CONTRAST))
			try {
				return Integer.parseInt(Utils
						.readLine(FILENAME_TRINITY_CONTRAST));
			} catch (IOException e) {
				e.printStackTrace();
			}
		return 0;
	}

	public static int mVariableGpu() {
		if (Utils.existFile(FILENAME_VARIABLE_GPU))
			try {
				return Integer.parseInt(Utils.readLine(FILENAME_VARIABLE_GPU));
			} catch (IOException e) {
				e.printStackTrace();
			}
		return 0;
	}
}
