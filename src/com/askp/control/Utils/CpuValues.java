/*
 * Copyright (C) 2013 AmperificSuperKANG Project
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 3 as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */

package com.askp.control.Utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.askp.control.Fragments.CpuFragment;
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
	public static final String FILENAME_CUR_GOVERNOR = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_governor";
	public static final String FILENAME_AVAILABLE_GOVERNOR = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_available_governors";
	public static final String FILENAME_AVAILABLE_FREQ = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_available_frequencies";
	public static final String FILENAME_TEMP_LIMIT = "/sys/devices/virtual/misc/tempcontrol/templimit";
	public static final String FILENAME_MULTICORE_SAVING = "/sys/devices/system/cpu/sched_mc_power_savings";
	public static final String FILENAME_MIN_SCREEN_ON = "/sys/devices/system/cpu/cpu0/cpufreq/screen_on_min_freq";
	public static final String FILENAME_MAX_SCREEN_OFF = "/sys/devices/system/cpu/cpu0/cpufreq/screen_off_max_freq";
	public static final String FILENAME_MAX_FREQ = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq";
	public static final String FILENAME_MIN_FREQ = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq";
	public static final String FILENAME_CUR_CPU_FREQ = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq";

	public static String mRegulatorVoltagesFreqRaw() {
		if (Utils.existFile(FILENAME_REGULATOR_VOLTAGES)) {
			List<String> mValueString = new ArrayList<String>();
			String[] mValueList = mRegulatorVoltagesFreq().split(";");
			StringBuilder mValueBuilder = new StringBuilder();
			for (int i = 0; i < mValueList.length; i++)
				mValueString.add(mValueList[i].split(" ")[1]);
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

				while ((line = buffreader.readLine()) != null)
					text.append(line);
				buffreader.close();
				return text.toString().replace(" mV", ";").replace(":", "");
			} catch (FileNotFoundException e) {
			} catch (IOException e) {
			}
		return "0 0";
	}

	public static String mMPUVoltagesFreqRaw() {
		if (Utils.existFile(FILENAME_MPU_VOLTAGES)) {
			List<String> mValueString = new ArrayList<String>();
			String[] mValueList = mMPUVoltagesFreq().split(";");
			StringBuilder mValueBuilder = new StringBuilder();
			for (int i = 0; i < mValueList.length; i++)
				mValueString.add(mValueList[i].split(" ")[1]);
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

				while ((line = buffreader.readLine()) != null)
					text.append(line);
				buffreader.close();
				return text.toString().replace(" mV", ";").replace("mhz:", "");
			} catch (FileNotFoundException e) {
			} catch (IOException e) {
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

				while ((line = buffreader.readLine()) != null)
					text.append(line);
				buffreader.close();
				return text.toString().replace("mV", "");
			} catch (FileNotFoundException e) {
			} catch (IOException e) {
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

				while ((line = buffreader.readLine()) != null)
					text.append(line);
				buffreader.close();
				return text.toString().replace("mV", "");
			} catch (FileNotFoundException e) {
			} catch (IOException e) {
			}
		return "0 0";
	}

	public static int mMPU() {
		if (Utils.existFile(FILENAME_MPU))
			try {
				return Integer.parseInt(Utils.readLine(FILENAME_MPU));
			} catch (IOException e) {
			}
		return 0;
	}

	public static int mIVA() {
		if (Utils.existFile(FILENAME_IVA))
			try {
				return Integer.parseInt(Utils.readLine(FILENAME_IVA));
			} catch (IOException e) {
			}
		return 0;
	}

	public static int mCore() {
		if (Utils.existFile(FILENAME_CORE))
			try {
				return Integer.parseInt(Utils.readLine(FILENAME_CORE));
			} catch (IOException e) {
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
				CpuFragment.mCurGovernorRaw = Utils
						.existFile(FILENAME_CUR_GOVERNOR) ? arg1 : "0";
			}

			@Override
			public void commandTerminated(int arg0, String arg1) {
			}
		};
		try {
			RootTools.getShell(true).add(command);
		} catch (IOException e) {
		} catch (TimeoutException e) {
		} catch (RootDeniedException e) {
		}
	}

	public static String mAvailableGovernor() {
		if (Utils.existFile(FILENAME_AVAILABLE_GOVERNOR))
			try {
				return Utils.readLine(FILENAME_AVAILABLE_GOVERNOR);
			} catch (IOException e) {
			}
		return "0 0";
	}

	public static String mAvailableFreq() {
		if (Utils.existFile(FILENAME_AVAILABLE_FREQ))
			try {
				return Utils.readLine(FILENAME_AVAILABLE_FREQ);
			} catch (IOException e) {
			}
		return "0 0";
	}

	public static int mTempLimit() {
		if (Utils.existFile(FILENAME_TEMP_LIMIT))
			try {
				return Integer.parseInt(Utils.readLine(FILENAME_TEMP_LIMIT));
			} catch (IOException e) {
			}
		return 0;
	}

	public static int mMulticoreSaving() {
		if (Utils.existFile(FILENAME_MULTICORE_SAVING))
			try {
				return Integer.parseInt(Utils
						.readLine(FILENAME_MULTICORE_SAVING));
			} catch (IOException e) {
			}
		return 0;
	}

	public static int mMinScreenOnFreq() {
		if (Utils.existFile(FILENAME_MIN_SCREEN_ON))
			try {
				if (Utils.readLine(FILENAME_MIN_SCREEN_ON).equals("0"))
					Utils.runCommand("echo " + mAvailableFreq().split(" ")[0]
							+ " > " + FILENAME_MIN_SCREEN_ON);
				return Integer.parseInt(Utils.readLine(FILENAME_MIN_SCREEN_ON));
			} catch (IOException e1) {
			}
		return 0;
	}

	public static int mMaxScreenOffFreq() {
		if (Utils.existFile(FILENAME_MAX_SCREEN_OFF))
			try {
				if (Utils.readLine(FILENAME_MAX_SCREEN_OFF).equals("0"))
					Utils.runCommand("echo " + mAvailableFreq().split(" ")[0]
							+ " > " + CpuValues.FILENAME_MAX_SCREEN_OFF);
				return Integer
						.parseInt(Utils.readLine(FILENAME_MAX_SCREEN_OFF));
			} catch (IOException e) {
			}
		return 0;
	}

	public static void mMaxFreq() {
		Command command = new Command(0, "cat " + FILENAME_MAX_FREQ) {
			@Override
			public void commandCompleted(int arg0, int arg1) {
			}

			@Override
			public void commandOutput(int arg0, String arg1) {
				CpuFragment.mMaxCpuFreqRaw = Utils.existFile(FILENAME_MAX_FREQ) ? Integer
						.parseInt(arg1) : 0;
			}

			@Override
			public void commandTerminated(int arg0, String arg1) {
			}
		};
		try {
			RootTools.getShell(true).add(command);
		} catch (IOException e) {
		} catch (TimeoutException e) {
		} catch (RootDeniedException e) {
		}
	}

	public static void mMinFreq() {
		Command command = new Command(0, "cat " + FILENAME_MIN_FREQ) {
			@Override
			public void commandCompleted(int arg0, int arg1) {
			}

			@Override
			public void commandOutput(int arg0, String arg1) {
				CpuFragment.mMinCpuFreqRaw = Utils.existFile(FILENAME_MIN_FREQ) ? Integer
						.parseInt(arg1) : 0;
			}

			@Override
			public void commandTerminated(int arg0, String arg1) {
			}
		};
		try {
			RootTools.getShell(true).add(command);
		} catch (IOException e) {
		} catch (TimeoutException e) {
		} catch (RootDeniedException e) {
		}
	}

	public static int mCurCpuFreq() {
		if (Utils.existFile(FILENAME_CUR_CPU_FREQ))
			try {
				return Integer.parseInt(Utils.readLine(FILENAME_CUR_CPU_FREQ));
			} catch (IOException e) {
			}
		return 0;
	}
}
