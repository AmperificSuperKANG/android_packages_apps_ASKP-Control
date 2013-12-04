package com.askp_control.Utils;

import java.io.IOException;

public class GpuDisplayValues {

	public static String FILENAME_TRINITY_CONTRAST = "/sys/module/panel_s6e8aa0/parameters/contrast";
	public static String FILENAME_VARIABLE_GPU = "/sys/devices/system/cpu/cpu0/cpufreq/gpu_oc";

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
