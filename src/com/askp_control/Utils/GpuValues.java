package com.askp_control.Utils;

import java.io.IOException;

public class GpuValues {

	public static String FILENAME_VARIABLE_GPU = "/sys/devices/system/cpu/cpu0/cpufreq/gpu_oc";

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
