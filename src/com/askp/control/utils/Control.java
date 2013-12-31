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

package com.askp.control.utils;

import android.content.Context;

import com.askp.control.R;
import com.askp.control.activities.MainActivity;
import com.askp.control.fragments.CpuFragment;
import com.askp.control.fragments.GpuDisplayFragment;
import com.askp.control.fragments.IoAlgorithmFragment;
import com.askp.control.fragments.MiscellaneousFragment;

public class Control {

	private static String MAX_CPU_FREQ = "", MIN_CPU_FREQ = "",
			MAX_SCREEN_OFF = "", MIN_SCREEN_ON = "", MULTICORE_SAVING = "",
			TEMP_LIMIT = "", GOVERNOR = "", CORE = "", IVA = "", MPU = "",
			CORE_VOLTAGE = "", IVA_VOLTAGE = "", MPU_VOLTAGE = "",
			REGULATOR_VOLTAGE = "", GPU_VARIABLE = "",
			ADAPTIVE_BRIGHTNESS = "", TRINITY_CONTRAST = "",
			GAMMA_CONTROL = "", GAMMA_OFFSET = "", COLOR_MULTIPLIER = "",
			INTERNAL_SCHEDULER = "", EXTERNAL_SCHEDULER = "",
			INTERNAL_READ = "", EXTERNAL_READ = "", WIFI_HIGH = "",
			TCP_CONGESTION = "", FAST_CHARGE = "", BATTERY_EXTENDER = "",
			SOUND_HIGH = "", HEADPHONE_BOOST = "", BACKLIGHT_NOTIFICATION = "",
			DYNAMIC_FSYNC = "", FSYNC_CONTROL = "", VIBRATION_STRENGTH = "";

	public static void initControl(Context context) {
		Control.setValues();
		if (MainActivity.mCpuAction)
			Control.setCpuValues(context);
		if (MainActivity.mGpuDisplayAction)
			Control.setGpuDisplayValues(context);
		if (MainActivity.mIoAlgorithmAction)
			Control.setIoAlgorithmValues(context);
		if (MainActivity.mMiscellaneousAction)
			Control.setMiscellaneousValues(context);
		MainActivity.mCpuAction = false;
		MainActivity.mGpuDisplayAction = false;
		MainActivity.mIoAlgorithmAction = false;
		MainActivity.mMiscellaneousAction = false;
		Utils.toast(context.getString(R.string.valuesapplied), context);
		MainActivity.showButtons(false);
	}

	public static void exitControl(Context context) {
		if (MainActivity.mCpuAction)
			CpuFragment.setContent();
		if (MainActivity.mGpuDisplayAction)
			GpuDisplayFragment.setContent();
		if (MainActivity.mIoAlgorithmAction)
			IoAlgorithmFragment.setContent();
		if (MainActivity.mMiscellaneousAction)
			MiscellaneousFragment.setContent();
		MainActivity.showButtons(false);
	}

	public static void setValues() {
		if (MainActivity.mCpuAction) {
			if (Utils.existFile(CpuValues.FILENAME_MAX_FREQ))
				MAX_CPU_FREQ = CpuFragment.mMaxFreqValue;
			if (Utils.existFile(CpuValues.FILENAME_MIN_FREQ))
				MIN_CPU_FREQ = CpuFragment.mMinFreqValue;
			if (Utils.existFile(CpuValues.FILENAME_MAX_SCREEN_OFF))
				MAX_SCREEN_OFF = CpuFragment.mMaxScreenOffValue;
			if (Utils.existFile(CpuValues.FILENAME_MIN_SCREEN_ON))
				MIN_SCREEN_ON = CpuFragment.mMinScreenOnValue;
			if (Utils.existFile(CpuValues.FILENAME_MULTICORE_SAVING))
				MULTICORE_SAVING = CpuFragment.mMulticoreSavingText.getText()
						.toString();
			if (Utils.existFile(CpuValues.FILENAME_TEMP_LIMIT))
				TEMP_LIMIT = CpuFragment.mTempLimitText.getText().toString()
						+ "000";
			if (Utils.existFile(CpuValues.FILENAME_AVAILABLE_GOVERNOR))
				GOVERNOR = CpuFragment.mAvailableGovernor[CpuFragment.mGovernorSpinner
						.getSelectedItemPosition()];
			if (Utils.existFile(CpuValues.FILENAME_CORE))
				CORE = CpuFragment.mCore.isChecked() ? "1" : "0";
			if (Utils.existFile(CpuValues.FILENAME_IVA))
				IVA = CpuFragment.mIVA.isChecked() ? "1" : "0";
			if (Utils.existFile(CpuValues.FILENAME_MPU))
				MPU = CpuFragment.mMPU.isChecked() ? "1" : "0";
			if (Utils.existFile(CpuValues.FILENAME_CPU_VOLTAGAES))
				MPU_VOLTAGE = Utils.listSplit(
						CpuFragment.mCpuVoltagesValuesList).replace(" mV", "");
			if (Utils.existFile(CpuValues.FILENAME_CORE_VOLTAGES))
				CORE_VOLTAGE = Utils.listSplit(
						CpuFragment.mCoreVoltagesValuesList).replace(" mV", "");
			if (Utils.existFile(CpuValues.FILENAME_IVA_VOLTAGES))
				IVA_VOLTAGE = Utils.listSplit(
						CpuFragment.mIVAVoltagesValuesList).replace(" mV", "");
			if (Utils.existFile(CpuValues.FILENAME_REGULATOR_VOLTAGES))
				REGULATOR_VOLTAGE = Utils.listSplit(
						CpuFragment.mRegulatorVoltagesValuesList).replace(
						" mV", "");
		}

		if (MainActivity.mGpuDisplayAction) {
			if (Utils.existFile(GpuDisplayValues.FILENAME_GPU_VARIABLE))
				GPU_VARIABLE = String.valueOf(GpuDisplayFragment.mGpuValueRaw);
			if (Utils.existFile(GpuDisplayValues.FILENAME_ADAPTIVE_BRIGHTNESS))
				ADAPTIVE_BRIGHTNESS = GpuDisplayFragment.mAdaptiveBrightnessBox
						.isChecked() ? "1" : "0";
			if (Utils.existFile(GpuDisplayValues.FILENAME_TRINITY_CONTRAST))
				TRINITY_CONTRAST = GpuDisplayFragment.mTrinityContrastText
						.getText().toString();
			if (Utils.existFile(GpuDisplayValues.FILENAME_GAMMA_CONTROL))
				GAMMA_CONTROL = GpuDisplayFragment.mGammaControlText.getText()
						.toString();
			if (Utils.existFile(GpuDisplayValues.FILENAME_GAMMA_OFFSET))
				GAMMA_OFFSET = Utils
						.listSplit(GpuDisplayFragment.mGammaOffsetValueList);
			if (Utils.existFile(GpuDisplayValues.FILENAME_COLOR_MULTIPLIER))
				COLOR_MULTIPLIER = Utils
						.listSplit(GpuDisplayFragment.mColorMultiplierValueList);
		}

		if (MainActivity.mIoAlgorithmAction) {
			if (Utils.existFile(IoAlgorithmValues.FILENAME_INTERNAL_SCHEDULER))
				INTERNAL_SCHEDULER = IoAlgorithmFragment.mAvailableInternalScheduler[IoAlgorithmFragment.mInternalSchedulerSpinner
						.getSelectedItemPosition()];
			if (Utils.existFile(IoAlgorithmValues.FILENAME_EXTERNAL_SCHEDULER))
				EXTERNAL_SCHEDULER = IoAlgorithmFragment.mAvailableExternalScheduler[IoAlgorithmFragment.mExternalSchedulerSpinner
						.getSelectedItemPosition()];
			if (Utils.existFile(IoAlgorithmValues.FILENAME_INTERNAL_READ))
				INTERNAL_READ = IoAlgorithmFragment.mInternalReadText.getText()
						.toString().replace(" kB", "");
			if (Utils.existFile(IoAlgorithmValues.FILENAME_EXTERNAL_READ))
				EXTERNAL_READ = IoAlgorithmFragment.mExternalReadText.getText()
						.toString().replace(" kB", "");
		}

		if (MainActivity.mMiscellaneousAction) {
			if (Utils.existFile(MiscellaneousValues.FILENAME_WIFI_HIGH))
				WIFI_HIGH = MiscellaneousFragment.mWifiHighBox.isChecked() ? "Y"
						: "N";
			if (Utils.existFile(MiscellaneousValues.FILENAME_TCP_CONGESTION))
				TCP_CONGESTION = MiscellaneousFragment.mAvailableTCPCongestion[MiscellaneousFragment.mTCPCongestionSpinner
						.getSelectedItemPosition()];
			if (Utils.existFile(MiscellaneousValues.FILENAME_FAST_CHARGE))
				FAST_CHARGE = MiscellaneousFragment.mFastChargeBox.isChecked() ? "1"
						: "0";
			if (Utils.existFile(MiscellaneousValues.FILENAME_BATTERY_EXTENDER))
				BATTERY_EXTENDER = MiscellaneousFragment.mBatteryExtenderText
						.getText().toString();
			if (Utils.existFile(MiscellaneousValues.FILENAME_SOUND_HIGH))
				SOUND_HIGH = MiscellaneousFragment.mSoundHighBox.isChecked() ? "1"
						: "0";
			if (Utils.existFile(MiscellaneousValues.FILENAME_HEADPHONE_BOOST))
				HEADPHONE_BOOST = MiscellaneousFragment.mHeadphoneBoostText
						.getText().toString();
			if (Utils
					.existFile(MiscellaneousValues.FILENAME_BACKLIGHT_NOTIFICATION))
				BACKLIGHT_NOTIFICATION = MiscellaneousFragment.mBacklightnotificationBox
						.isChecked() ? "1" : "0";
			if (Utils.existFile(MiscellaneousValues.FILENAME_DYNAMIC_FSYNC))
				DYNAMIC_FSYNC = MiscellaneousFragment.mDynamicFsyncBox
						.isChecked() ? "1" : "0";
			if (Utils.existFile(MiscellaneousValues.FILENAME_FSYNC_CONTROL))
				FSYNC_CONTROL = MiscellaneousFragment.mFsyncControlBox
						.isChecked() ? "1" : "0";
			if (Utils
					.existFile(MiscellaneousValues.FILENAME_VIBRATION_STRENGTH))
				VIBRATION_STRENGTH = MiscellaneousFragment.mVibrationStrengthText
						.getText().toString();
		}
	}

	public static void setCpuValues(Context context) {
		// Max Cpu
		setFileValue(MAX_CPU_FREQ, CpuValues.FILENAME_MAX_FREQ, "maxcpuvalue",
				context);
		// Min Cpu
		setFileValue(MIN_CPU_FREQ, CpuValues.FILENAME_MIN_FREQ, "mincpuvalue",
				context);
		// Max Screen Off
		setFileValue(MAX_SCREEN_OFF, CpuValues.FILENAME_MAX_SCREEN_OFF,
				"maxscreenoff", context);
		// Min Screen On
		if (!MIN_SCREEN_ON.isEmpty())
			Utils.runCommand("touch " + CpuValues.FILENAME_MIN_SCREEN_ON);
		setFileValue(MIN_SCREEN_ON, CpuValues.FILENAME_MIN_SCREEN_ON,
				"minscreenon", context);
		// Multicore Saving
		setFileValue(MULTICORE_SAVING, CpuValues.FILENAME_MULTICORE_SAVING,
				"multicoresaving", context);
		// Temp Limit
		setFileValue(TEMP_LIMIT, CpuValues.FILENAME_TEMP_LIMIT, "templimit",
				context);
		// Governor
		setFileValue(GOVERNOR, CpuValues.FILENAME_CUR_GOVERNOR, "governor",
				context);
		// Smartreflex
		setFileValue(CORE, CpuValues.FILENAME_CORE, "core", context);
		setFileValue(IVA, CpuValues.FILENAME_IVA, "iva", context);
		setFileValue(MPU, CpuValues.FILENAME_MPU, "mpu", context);
		// Core Voltage
		setFileValue(CORE_VOLTAGE, CpuValues.FILENAME_CORE_VOLTAGES,
				"corevoltage", context);
		// IVA Voltage
		setFileValue(IVA_VOLTAGE, CpuValues.FILENAME_IVA_VOLTAGES,
				"ivavoltage", context);
		// MPU Voltage
		setFileValue(MPU_VOLTAGE, CpuValues.FILENAME_CPU_VOLTAGAES,
				"cpuvoltage", context);
		// Regulator Voltage
		setFileValue(REGULATOR_VOLTAGE, CpuValues.FILENAME_REGULATOR_VOLTAGES,
				"regulatorvoltage", context);
	}

	public static void setGpuDisplayValues(Context context) {
		// Gpu Variable
		setFileValue(GPU_VARIABLE, GpuDisplayValues.FILENAME_GPU_VARIABLE,
				"gpuvariable", context);
		// Adaptive Brightness
		setFileValue(ADAPTIVE_BRIGHTNESS,
				GpuDisplayValues.FILENAME_ADAPTIVE_BRIGHTNESS,
				"adaptivebrightness", context);
		// Trinity Contrast
		setFileValue(TRINITY_CONTRAST,
				GpuDisplayValues.FILENAME_TRINITY_CONTRAST, "trinitycontrast",
				context);
		// Gamma Control
		setFileValue(GAMMA_CONTROL, GpuDisplayValues.FILENAME_GAMMA_CONTROL,
				"gammacontrol", context);
		// Gamma Offset
		setFileValue(GAMMA_OFFSET, GpuDisplayValues.FILENAME_GAMMA_OFFSET,
				"gammaoffset", context);
		// Color Multiplier
		setFileValue(COLOR_MULTIPLIER,
				GpuDisplayValues.FILENAME_COLOR_MULTIPLIER, "colormultiplier",
				context);
	}

	public static void setIoAlgorithmValues(Context context) {
		// Internal Scheduler
		if (!INTERNAL_SCHEDULER.isEmpty())
			IoAlgorithmFragment.mCurInternalScheduler = IoAlgorithmFragment.mInternalSchedulerSpinner
					.getSelectedItemPosition();
		setFileValue(INTERNAL_SCHEDULER,
				IoAlgorithmValues.FILENAME_INTERNAL_SCHEDULER,
				"internalscheduler", context);
		// External Scheduler
		if (!EXTERNAL_SCHEDULER.isEmpty())
			IoAlgorithmFragment.mCurExternalScheduler = IoAlgorithmFragment.mExternalSchedulerSpinner
					.getSelectedItemPosition();
		setFileValue(EXTERNAL_SCHEDULER,
				IoAlgorithmValues.FILENAME_EXTERNAL_SCHEDULER,
				"externalscheduler", context);
		// Internal Read
		setFileValue(INTERNAL_READ, IoAlgorithmValues.FILENAME_INTERNAL_READ,
				"internalread", context);
		// External Read
		setFileValue(EXTERNAL_READ, IoAlgorithmValues.FILENAME_EXTERNAL_READ,
				"externalread", context);
	}

	public static void setMiscellaneousValues(Context context) {
		// Wifi High
		setFileValue(WIFI_HIGH, MiscellaneousValues.FILENAME_WIFI_HIGH,
				"wifihigh", context);
		// TCP Congestion
		if (!TCP_CONGESTION.isEmpty()) {
			Utils.runCommand("sysctl -w net.ipv4.tcp_congestion_control="
					+ TCP_CONGESTION);
			Utils.saveString("tcpcongestion", TCP_CONGESTION, context);
			MiscellaneousFragment.mTCPCongestion = MiscellaneousFragment.mTCPCongestionSpinner
					.getSelectedItemPosition();
		}
		// Fast Charge
		setFileValue(FAST_CHARGE, MiscellaneousValues.FILENAME_FAST_CHARGE,
				"fastcharge", context);
		// Battery Extender
		setFileValue(BATTERY_EXTENDER,
				MiscellaneousValues.FILENAME_BATTERY_EXTENDER,
				"batteryextender", context);
		// Sound High
		setFileValue(SOUND_HIGH, MiscellaneousValues.FILENAME_SOUND_HIGH,
				"soundhigh", context);
		// Headphone Boost
		setFileValue(HEADPHONE_BOOST,
				MiscellaneousValues.FILENAME_HEADPHONE_BOOST, "headphoneboost",
				context);
		// Backlightnotification
		setFileValue(BACKLIGHT_NOTIFICATION,
				MiscellaneousValues.FILENAME_BACKLIGHT_NOTIFICATION,
				"backlightnotification", context);
		// Dynamic Fsync
		setFileValue(DYNAMIC_FSYNC, MiscellaneousValues.FILENAME_DYNAMIC_FSYNC,
				"dynamicfsync", context);
		// Fsync Control
		setFileValue(FSYNC_CONTROL, MiscellaneousValues.FILENAME_FSYNC_CONTROL,
				"fsynccontrol", context);
		// Vibration Strength
		setFileValue(VIBRATION_STRENGTH,
				MiscellaneousValues.FILENAME_VIBRATION_STRENGTH,
				"vibrationstrength", context);
	}

	private static void setFileValue(String value, String file,
			String prefname, Context context) {
		if (!value.isEmpty()) {
			Utils.runCommand("echo " + value + " > " + file);
			Utils.saveString(prefname, value, context);
		}
	}
}
