package com.askp_control.Utils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.askp_control.Fragments.CpuFragment;
import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.exceptions.RootDeniedException;
import com.stericson.RootTools.execution.Command;

public class CpuValues {

	private static final String FILENAME_AVAILABLE_FREQ = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_available_frequencies";
	public static final String FILENAME_MAX_FREQ = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq";
	public static final String FILENAME_MIN_FREQ = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq";
	private static final String FILENAME_CUR_CPU_FREQ = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq";

	private static final File mCurCpuFreqFile = new File(FILENAME_CUR_CPU_FREQ);
	private static final File mMaxFreqFile = new File(FILENAME_MAX_FREQ);

	public static String mAvailableFreq() {
		try {
			return Utils.readLine(FILENAME_AVAILABLE_FREQ);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int mMaxFreq() {
		if (mMaxFreqFile.exists()) {
			try {
				return Integer.parseInt(Utils.readLine(FILENAME_MAX_FREQ));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	public static void mMinFreq() {
		Command command = new Command(0, "cat " + FILENAME_MIN_FREQ) {
			@Override
			public void commandCompleted(int arg0, int arg1) {
			}

			@Override
			public void commandOutput(int arg0, String arg1) {
				CpuFragment.mMinCpuFreqRaw = Integer.parseInt(arg1);
			}

			@Override
			public void commandTerminated(int arg0, String arg1) {
			}
		};
		try {
			RootTools.getShell(true).add(command);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (RootDeniedException e) {
			e.printStackTrace();
		}
	}

	public static int mCurCpuFreq() {
		if (mCurCpuFreqFile.exists()) {
			try {
				return Integer.parseInt(Utils.readLine(FILENAME_CUR_CPU_FREQ)) / 1000;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
}
