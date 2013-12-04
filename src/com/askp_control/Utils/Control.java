package com.askp_control.Utils;

import android.content.Context;

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

	public static String REGULATOR_VOLTAGE = CpuValues
			.mRegulatorVoltagesFreqRaw();

	public static String GPU_VARIABLE = String.valueOf(GpuDisplayValues
			.mVariableGpu());

	public static String TRINITY_CONTRAST = String.valueOf(GpuDisplayValues
			.mTrinityContrast());

	public static void setValues(Context context) {

		// Max Cpu
		if (Utils.existFile(CpuValues.FILENAME_MAX_FREQ)) {
			Utils.runCommand("echo " + MAX_CPU_FREQ + " > "
					+ CpuValues.FILENAME_MAX_FREQ);
			Utils.saveString("maxcpuvalue", MAX_CPU_FREQ, context);
		}

		// Min Cpu
		if (Utils.existFile(CpuValues.FILENAME_MIN_FREQ)) {
			Utils.runCommand("echo " + MIN_CPU_FREQ + " > "
					+ CpuValues.FILENAME_MIN_FREQ);
			Utils.saveString("mincpuvalue", MIN_CPU_FREQ, context);
		}

		// Max Screen Off
		if (Utils.existFile(CpuValues.FILENAME_MAX_SCREEN_OFF)) {
			Utils.runCommand("echo " + MAX_SCREEN_OFF + " > "
					+ CpuValues.FILENAME_MAX_SCREEN_OFF);
			Utils.saveString("maxscreenoff", MAX_SCREEN_OFF, context);
		}

		// Min Screen On
		if (Utils.existFile(CpuValues.FILENAME_MIN_SCREEN_ON)) {
			Utils.runCommand("touch " + CpuValues.FILENAME_MIN_SCREEN_ON
					+ " && echo " + MIN_SCREEN_ON + " > "
					+ CpuValues.FILENAME_MIN_SCREEN_ON);
			Utils.saveString("minscreenon", MIN_SCREEN_ON, context);
		}

		// Governor
		if (Utils.existFile(CpuValues.FILENAME_CUR_GOVERNOR)) {
			Utils.runCommand("echo " + GOVERNOR + " > "
					+ CpuValues.FILENAME_CUR_GOVERNOR);
			Utils.saveString("governor", GOVERNOR, context);
			CpuFragment.mCurGovernorRaw = GOVERNOR;
		}

		// Smartreflex
		if (Utils.existFile(CpuValues.FILENAME_CORE))
			if (CORE) {
				Utils.runCommand("echo 1 > " + CpuValues.FILENAME_CORE);
				Utils.saveString("core", "1", context);
			} else {
				Utils.runCommand("echo 0 > " + CpuValues.FILENAME_CORE);
				Utils.saveString("core", "0", context);
			}

		if (Utils.existFile(CpuValues.FILENAME_IVA))
			if (IVA) {
				Utils.runCommand("echo 1 > " + CpuValues.FILENAME_IVA);
				Utils.saveString("iva", "1", context);
			} else {
				Utils.runCommand("echo 0 > " + CpuValues.FILENAME_IVA);
				Utils.saveString("iva", "0", context);
			}

		if (Utils.existFile(CpuValues.FILENAME_MPU))
			if (MPU) {
				Utils.runCommand("echo 1 > " + CpuValues.FILENAME_MPU);
				Utils.saveString("mpu", "1", context);
			} else {
				Utils.runCommand("echo 0 > " + CpuValues.FILENAME_MPU);
				Utils.saveString("mpu", "0", context);
			}

		// Core Voltage
		if (Utils.existFile(CpuValues.FILENAME_CORE_VOLTAGES)) {
			Utils.runCommand("echo " + CORE_VOLTAGE + " > "
					+ CpuValues.FILENAME_CORE_VOLTAGES);
			Utils.saveString("corevoltage", CORE_VOLTAGE, context);
		}

		// IVA Voltage
		if (Utils.existFile(CpuValues.FILENAME_IVA_VOLTAGES)) {
			Utils.runCommand("echo " + IVA_VOLTAGE + " > "
					+ CpuValues.FILENAME_IVA_VOLTAGES);
			Utils.saveString("ivavoltage", IVA_VOLTAGE, context);
		}

		// MPU Voltage
		if (Utils.existFile(CpuValues.FILENAME_MPU_VOLTAGES)) {
			Utils.runCommand("echo " + MPU_VOLTAGE + " > "
					+ CpuValues.FILENAME_MPU_VOLTAGES);
			Utils.saveString("mpuvoltage", MPU_VOLTAGE, context);
		}

		// Regulator Voltage
		if (Utils.existFile(CpuValues.FILENAME_REGULATOR_VOLTAGES)) {
			Utils.runCommand("echo " + REGULATOR_VOLTAGE + " > "
					+ CpuValues.FILENAME_REGULATOR_VOLTAGES);
			Utils.saveString("regulatorvoltage", REGULATOR_VOLTAGE, context);
		}

		// Gpu Variable
		if (Utils.existFile(GpuDisplayValues.FILENAME_VARIABLE_GPU)) {
			Utils.runCommand("echo " + GPU_VARIABLE + " > "
					+ GpuDisplayValues.FILENAME_VARIABLE_GPU);
			Utils.saveString("gpuvariable", GPU_VARIABLE, context);
		}

		// Trinity Contrast
		if (Utils.existFile(GpuDisplayValues.FILENAME_TRINITY_CONTRAST)) {
			Utils.runCommand("echo " + TRINITY_CONTRAST + " > "
					+ GpuDisplayValues.FILENAME_TRINITY_CONTRAST);
			Utils.saveString("trinitycontrast", TRINITY_CONTRAST, context);
		}
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
		REGULATOR_VOLTAGE = CpuValues.mRegulatorVoltagesFreqRaw();
		GPU_VARIABLE = String.valueOf(GpuDisplayValues.mVariableGpu());
		TRINITY_CONTRAST = String.valueOf(GpuDisplayValues.mTrinityContrast());
	}
}
