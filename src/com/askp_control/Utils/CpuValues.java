package com.askp_control.Utils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.askp_control.Fragments.CpuFragment;
import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.exceptions.RootDeniedException;
import com.stericson.RootTools.execution.Command;

public class CpuValues {

	public static final String FILENAME_MPU = "/sys/kernel/debug/smartreflex/sr_mpu/autocomp";
	public static final String FILENAME_IVA = "/sys/kernel/debug/smartreflex/sr_iva/autocomp";
	public static final String FILENAME_CORE = "/sys/kernel/debug/smartreflex/sr_core/autocomp";
	public static final String FILENAME_MIN_SCREEN_ON = "/sys/devices/system/cpu/cpu0/cpufreq/screen_on_min_freq";
	public static final String FILENAME_MAX_SCREEN_OFF = "/sys/devices/system/cpu/cpu0/cpufreq/screen_off_max_freq";
	public static final String FILENAME_CUR_GOVERNOR = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_governor";
	private static final String FILENAME_AVAILABLE_GOVERNOR = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_available_governors";
	private static final String FILENAME_AVAILABLE_FREQ = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_available_frequencies";
	public static final String FILENAME_MAX_FREQ = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq";
	public static final String FILENAME_MIN_FREQ = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq";
	private static final String FILENAME_CUR_CPU_FREQ = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq";

	public static final File mMPUFile = new File(FILENAME_MPU);
	public static final File mIVAFile = new File(FILENAME_IVA);
	public static final File mCoreFile = new File(FILENAME_CORE);
	public static final File mMinScreenOnFreqFile = new File(
			FILENAME_MIN_SCREEN_ON);
	public static final File mMaxScreenOffFreqFile = new File(
			FILENAME_MAX_SCREEN_OFF);
	private static final File mCurCpuFreqFile = new File(FILENAME_CUR_CPU_FREQ);

	public static boolean mMPU() {
		try {
			if (Utils.readLine(FILENAME_MPU).equals("1")) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean mIVA() {
		try {
			if (Utils.readLine(FILENAME_IVA).equals("1")) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean mCore() {
		try {
			if (Utils.readLine(FILENAME_CORE).equals("1")) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static int mMinScreenOnFreq() {
		if (mMinScreenOnFreqFile.exists()) {
			try {
				return Integer.parseInt(Utils.readLine(FILENAME_MIN_SCREEN_ON));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	public static int mMaxScreenOffFreq() {
		if (mMaxScreenOffFreqFile.exists()) {
			try {
				return Integer
						.parseInt(Utils.readLine(FILENAME_MAX_SCREEN_OFF));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	public static void mCurGovernor() {
		Command command = new Command(0, "cat " + FILENAME_CUR_GOVERNOR) {
			@Override
			public void commandCompleted(int arg0, int arg1) {
			}

			@Override
			public void commandOutput(int arg0, String arg1) {
				CpuFragment.mCurGovernorRaw = arg1;
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

	public static String mAvailableGovernor() {
		try {
			return Utils.readLine(FILENAME_AVAILABLE_GOVERNOR);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String mAvailableFreq() {
		try {
			return Utils.readLine(FILENAME_AVAILABLE_FREQ);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void mMaxFreq() {
		Command command = new Command(0, "cat " + FILENAME_MAX_FREQ) {
			@Override
			public void commandCompleted(int arg0, int arg1) {
			}

			@Override
			public void commandOutput(int arg0, String arg1) {
				CpuFragment.mMaxCpuFreqRaw = Integer.parseInt(arg1);
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
				return Integer.parseInt(Utils.readLine(FILENAME_CUR_CPU_FREQ));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
}
