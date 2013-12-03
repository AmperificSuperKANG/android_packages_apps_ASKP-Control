package com.askp_control.Utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.askp_control.Fragments.CpuFragment;
import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.exceptions.RootDeniedException;
import com.stericson.RootTools.execution.Command;

public class CpuValues {

	public static final String FILENAME_REGULATOR_VOLTAGES = "/sys/devices/virtual/misc/customvoltage/regulator_voltages";
	public static final String FILENAME_MPU_VOLTAGES = "/sys/devices/virtual/misc/customvoltage/mpu_voltages";
	public static final String FILENAME_IVA_VOLTAGES = "/sys/devices/virtual/misc/customvoltage/iva_voltages";
	public static final String FILENAME_CORE_VOLTAGES = "/sys/devices/virtual/misc/customvoltage/core_voltages";
	public static final String FILENAME_MPU = "/sys/kernel/debug/smartreflex/sr_mpu/autocomp";
	public static final String FILENAME_IVA = "/sys/kernel/debug/smartreflex/sr_iva/autocomp";
	public static final String FILENAME_CORE = "/sys/kernel/debug/smartreflex/sr_core/autocomp";
	public static final String FILENAME_MIN_SCREEN_ON = "/sys/devices/system/cpu/cpu0/cpufreq/screen_on_min_freq";
	public static final String FILENAME_MAX_SCREEN_OFF = "/sys/devices/system/cpu/cpu0/cpufreq/screen_off_max_freq";
	public static final String FILENAME_CUR_GOVERNOR = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_governor";
	public static final String FILENAME_AVAILABLE_GOVERNOR = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_available_governors";
	public static final String FILENAME_AVAILABLE_FREQ = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_available_frequencies";
	public static final String FILENAME_MAX_FREQ = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq";
	public static final String FILENAME_MIN_FREQ = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq";
	public static final String FILENAME_CUR_CPU_FREQ = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq";

	public static String mRegulatorVoltagesFreqRaw() {
		if (Utils.existFile(FILENAME_REGULATOR_VOLTAGES)) {
			List<String> mValueString = new ArrayList<String>();
			String[] mValueList = mRegulatorVoltagesFreq().split(";");
			StringBuilder mValueBuilder = new StringBuilder();
			for (int i = 0; i < mValueList.length; i++) {
				mValueString.add(mValueList[i].split(" ")[1]);
			}
			for (String s : mValueString) {
				mValueBuilder.append(s);
				mValueBuilder.append("\t");
			}
			return mValueBuilder.toString();
		}
		return "0 0";
	}

	public static String mRegulatorVoltagesFreq() {
		if (Utils.existFile(FILENAME_REGULATOR_VOLTAGES))
			try {
				BufferedReader buffreader;
				buffreader = new BufferedReader(new FileReader(
						FILENAME_REGULATOR_VOLTAGES), 256);
				String line;
				StringBuilder text = new StringBuilder();

				while ((line = buffreader.readLine()) != null) {
					text.append(line);
				}
				buffreader.close();
				return text.toString().replace(" mV", ";").replace(":", "");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return "0 0";
	}

	public static String mMPUVoltagesFreqRaw() {
		if (Utils.existFile(FILENAME_MPU_VOLTAGES)) {
			List<String> mValueString = new ArrayList<String>();
			String[] mValueList = mMPUVoltagesFreq().split(";");
			StringBuilder mValueBuilder = new StringBuilder();
			for (int i = 0; i < mValueList.length; i++) {
				mValueString.add(mValueList[i].split(" ")[1]);
			}
			for (String s : mValueString) {
				mValueBuilder.append(s);
				mValueBuilder.append("\t");
			}
			return mValueBuilder.toString();
		}
		return "0 0";
	}

	public static String mMPUVoltagesFreq() {
		if (Utils.existFile(FILENAME_MPU_VOLTAGES))
			try {
				BufferedReader buffreader;
				buffreader = new BufferedReader(new FileReader(
						FILENAME_MPU_VOLTAGES), 256);
				String line;
				StringBuilder text = new StringBuilder();

				while ((line = buffreader.readLine()) != null) {
					text.append(line);
				}
				buffreader.close();
				return text.toString().replace(" mV", ";").replace("mhz:", "");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return "0 0";
	}

	public static String mIVAVoltagesFreq() {
		if (Utils.existFile(FILENAME_IVA_VOLTAGES))
			try {
				BufferedReader buffreader;
				buffreader = new BufferedReader(new FileReader(
						FILENAME_IVA_VOLTAGES), 256);
				String line;
				StringBuilder text = new StringBuilder();

				while ((line = buffreader.readLine()) != null) {
					text.append(line);
				}
				buffreader.close();
				return text.toString().replace("mV", "");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return "0 0";
	}

	public static String mCoreVoltagesFreq() {
		if (Utils.existFile(FILENAME_CORE_VOLTAGES))
			try {
				BufferedReader buffreader;
				buffreader = new BufferedReader(new FileReader(
						FILENAME_CORE_VOLTAGES), 256);
				String line;
				StringBuilder text = new StringBuilder();

				while ((line = buffreader.readLine()) != null) {
					text.append(line);
				}
				buffreader.close();
				return text.toString().replace("mV", "");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return "0 0";
	}

	public static boolean mMPU() {
		if (Utils.existFile(FILENAME_MPU))
			try {
				if (Utils.readLine(FILENAME_MPU).equals("1"))
					return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		return false;
	}

	public static boolean mIVA() {
		if (Utils.existFile(FILENAME_IVA))
			try {
				if (Utils.readLine(FILENAME_IVA).equals("1"))
					return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		return false;
	}

	public static boolean mCore() {
		if (Utils.existFile(FILENAME_CORE))
			try {
				if (Utils.readLine(FILENAME_CORE).equals("1"))
					return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		return false;
	}

	public static int mMinScreenOnFreq() {
		if (Utils.existFile(FILENAME_MIN_SCREEN_ON))
			try {
				if (Utils.readLine(FILENAME_MIN_SCREEN_ON).equals("0"))
					Utils.runCommand("echo " + CpuFragment.mAvailableFreq[0]
							+ " > " + FILENAME_MIN_SCREEN_ON);
				return Integer.parseInt(Utils.readLine(FILENAME_MIN_SCREEN_ON));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		return 0;
	}

	public static int mMaxScreenOffFreq() {
		if (Utils.existFile(FILENAME_MAX_SCREEN_OFF))
			try {
				if (Utils.readLine(FILENAME_MAX_SCREEN_OFF).equals("0"))
					Utils.runCommand("echo " + CpuFragment.mAvailableFreq[0]
							+ " > " + CpuValues.FILENAME_MAX_SCREEN_OFF);
				return Integer
						.parseInt(Utils.readLine(FILENAME_MAX_SCREEN_OFF));
			} catch (IOException e) {
				e.printStackTrace();
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
				if (Utils.existFile(FILENAME_CUR_GOVERNOR)) {
					CpuFragment.mCurGovernorRaw = arg1;
				} else {
					CpuFragment.mCurGovernorRaw = "0";
				}
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
		if (Utils.existFile(FILENAME_AVAILABLE_GOVERNOR))
			try {
				return Utils.readLine(FILENAME_AVAILABLE_GOVERNOR);
			} catch (IOException e) {
				e.printStackTrace();
			}
		return "0 0";
	}

	public static String mAvailableFreq() {
		if (Utils.existFile(FILENAME_AVAILABLE_FREQ))
			try {
				return Utils.readLine(FILENAME_AVAILABLE_FREQ);
			} catch (IOException e) {
				e.printStackTrace();
			}
		return "0 0";
	}

	public static void mMaxFreq() {
		Command command = new Command(0, "cat " + FILENAME_MAX_FREQ) {
			@Override
			public void commandCompleted(int arg0, int arg1) {
			}

			@Override
			public void commandOutput(int arg0, String arg1) {
				if (Utils.existFile(FILENAME_MAX_FREQ)) {
					CpuFragment.mMaxCpuFreqRaw = Integer.parseInt(arg1);
				} else {
					CpuFragment.mMaxCpuFreqRaw = 0;
				}
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
				if (Utils.existFile(FILENAME_MIN_FREQ)) {
					CpuFragment.mMinCpuFreqRaw = Integer.parseInt(arg1);
				} else {
					CpuFragment.mMinCpuFreqRaw = 0;
				}
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
		if (Utils.existFile(FILENAME_CUR_CPU_FREQ))
			try {
				return Integer.parseInt(Utils.readLine(FILENAME_CUR_CPU_FREQ));
			} catch (IOException e) {
				e.printStackTrace();
			}
		return 0;
	}
}
