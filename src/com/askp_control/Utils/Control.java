package com.askp_control.Utils;

import android.content.Context;

import com.askp_control.R;
import com.askp_control.Fragments.CpuFragment;

public class Control {

	public static String MAX_CPU_FREQ = String
			.valueOf(CpuFragment.mMaxCpuFreqRaw);

	public static String MIN_CPU_FREQ = String
			.valueOf(CpuFragment.mMinCpuFreqRaw);

	public static String MAX_SCREEN_OFF = String.valueOf(CpuValues
			.mMaxScreenOffFreq());

	public static String MIN_SCREEN_ON = String.valueOf(CpuValues
			.mMinScreenOnFreq());

	public static String GOVERNOR = CpuFragment.mCurGovernorRaw;

	public static boolean CORE = CpuValues.mCore();

	public static boolean IVA = CpuValues.mIVA();

	public static boolean MPU = CpuValues.mMPU();

	public static String CORE_VOLTAGE = CpuValues.mCoreVoltagesFreq();

	public static String IVA_VOLTAGE = CpuValues.mIVAVoltagesFreq();

	public static String MPU_VOLTAGE = CpuValues.mMPUVoltagesFreqRaw();

	public static void setValues(Context context) {

		// Max Cpu
		Utils.runCommand("echo " + MAX_CPU_FREQ + " > "
				+ CpuValues.FILENAME_MAX_FREQ);
		Utils.saveString("maxcpuvalue", MAX_CPU_FREQ, context);

		// Min Cpu
		Utils.runCommand("echo " + MIN_CPU_FREQ + " > "
				+ CpuValues.FILENAME_MIN_FREQ);
		Utils.saveString("mincpuvalue", MIN_CPU_FREQ, context);

		// Max Screen Off
		Utils.runCommand("echo " + MAX_SCREEN_OFF + " > "
				+ CpuValues.FILENAME_MAX_SCREEN_OFF);
		Utils.saveString("maxscreenoff", MAX_SCREEN_OFF, context);

		// Min Screen On
		Utils.runCommand("touch " + CpuValues.FILENAME_MIN_SCREEN_ON
				+ " && echo " + MIN_SCREEN_ON + " > "
				+ CpuValues.FILENAME_MIN_SCREEN_ON);
		Utils.saveString("minscreenon", MIN_SCREEN_ON, context);

		// Governor
		Utils.runCommand("echo " + GOVERNOR + " > "
				+ CpuValues.FILENAME_CUR_GOVERNOR);
		Utils.saveString("governor", GOVERNOR, context);
		CpuFragment.mCurGovernorRaw = GOVERNOR;

		// Smartreflex
		if (CORE) {
			Utils.runCommand("echo 1 > " + CpuValues.FILENAME_CORE);
		} else {
			Utils.runCommand("echo 0 > " + CpuValues.FILENAME_CORE);
		}

		if (IVA) {
			Utils.runCommand("echo 1 > " + CpuValues.FILENAME_IVA);
		} else {
			Utils.runCommand("echo 0 > " + CpuValues.FILENAME_IVA);
		}

		if (MPU) {
			Utils.runCommand("echo 1 > " + CpuValues.FILENAME_MPU);
		} else {
			Utils.runCommand("echo 0 > " + CpuValues.FILENAME_MPU);
		}

		// Core Voltage
		Utils.runCommand("echo " + CORE_VOLTAGE + " > "
				+ CpuValues.FILENAME_CORE_VOLTAGES);
		Utils.saveString("corevoltage", CORE_VOLTAGE, context);

		// IVA Voltage
		Utils.runCommand("echo " + IVA_VOLTAGE + " > "
				+ CpuValues.FILENAME_IVA_VOLTAGES);
		Utils.saveString("ivavoltage", IVA_VOLTAGE, context);

		// MPU Voltage
		Utils.runCommand("echo " + MPU_VOLTAGE + " > "
				+ CpuValues.FILENAME_MPU_VOLTAGES);
		Utils.saveString("mpuvoltage", MPU_VOLTAGE, context);

		Utils.toast(context.getString(R.string.valuesapplied),
				context.getApplicationContext());
	}

	public static void setValuesback(Context context) {
		MAX_CPU_FREQ = String.valueOf(CpuFragment.mMaxCpuFreqRaw);
		MIN_CPU_FREQ = String.valueOf(CpuFragment.mMinCpuFreqRaw);
		MAX_SCREEN_OFF = String.valueOf(CpuValues.mMaxScreenOffFreq());
		MIN_SCREEN_ON = String.valueOf(CpuValues.mMinScreenOnFreq());
		GOVERNOR = CpuFragment.mCurGovernorRaw;
		CORE_VOLTAGE = CpuValues.mCoreVoltagesFreq();
		IVA_VOLTAGE = CpuValues.mIVAVoltagesFreq();
		MPU_VOLTAGE = CpuValues.mMPUVoltagesFreqRaw();
	}
}
