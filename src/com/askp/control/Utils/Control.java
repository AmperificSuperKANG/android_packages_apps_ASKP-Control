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

import android.content.Context;

import com.askp.control.MainActivity;
import com.askp.control.R;
import com.askp.control.Fragments.CpuFragment;
import com.askp.control.Fragments.GpuDisplayFragment;
import com.askp.control.Fragments.IoAlgorithmFragment;
import com.askp.control.Fragments.MiscellaneousFragment;

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
			SOUND_HIGH = "", HEADPHONE_BOOST = "", DYNAMIC_FSYNC = "",
			FSYNC_CONTROL = "", VIBRATION_STRENGTH = "";

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
		if (Utils.existFile(CpuValues.FILENAME_CORE_VOLTAGES))
			CORE_VOLTAGE = Utils.listSplit(CpuFragment.mCoreVoltagesValuesList)
					.replace(" mV", "");
		if (Utils.existFile(CpuValues.FILENAME_IVA_VOLTAGES))
			IVA_VOLTAGE = Utils.listSplit(CpuFragment.mIVAVoltagesValuesList)
					.replace(" mV", "");
		if (Utils.existFile(CpuValues.FILENAME_MPU_VOLTAGES))
			MPU_VOLTAGE = Utils.listSplit(CpuFragment.mMPUVoltagesValuesList)
					.replace(" mV", "");
		if (Utils.existFile(CpuValues.FILENAME_REGULATOR_VOLTAGES))
			REGULATOR_VOLTAGE = Utils.listSplit(
					CpuFragment.mRegulatorVoltagesValuesList)
					.replace(" mV", "");

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
		if (Utils.existFile(MiscellaneousValues.FILENAME_DYNAMIC_FSYNC))
			DYNAMIC_FSYNC = MiscellaneousFragment.mDynamicFsyncBox.isChecked() ? "1"
					: "0";
		if (Utils.existFile(MiscellaneousValues.FILENAME_FSYNC_CONTROL))
			FSYNC_CONTROL = MiscellaneousFragment.mFsyncControlBox.isChecked() ? "1"
					: "0";
		if (Utils.existFile(MiscellaneousValues.FILENAME_VIBRATION_STRENGTH))
			VIBRATION_STRENGTH = MiscellaneousFragment.mVibrationStrengthText
					.getText().toString();
	}

	public static void setCpuValues(Context context) {
		// Max Cpu
		if (!MAX_CPU_FREQ.isEmpty()) {
			Utils.runCommand("echo " + MAX_CPU_FREQ + " > "
					+ CpuValues.FILENAME_MAX_FREQ);
			Utils.saveString("maxcpuvalue", MAX_CPU_FREQ, context);
		}

		// Min Cpu
		if (!MIN_CPU_FREQ.isEmpty()) {
			Utils.runCommand("echo " + MIN_CPU_FREQ + " > "
					+ CpuValues.FILENAME_MIN_FREQ);
			Utils.saveString("mincpuvalue", MIN_CPU_FREQ, context);
		}

		// Max Screen Off
		if (!MAX_SCREEN_OFF.isEmpty()) {
			Utils.runCommand("echo " + MAX_SCREEN_OFF + " > "
					+ CpuValues.FILENAME_MAX_SCREEN_OFF);
			Utils.saveString("maxscreenoff", MAX_SCREEN_OFF, context);
		}

		// Min Screen On
		if (!MIN_SCREEN_ON.isEmpty()) {
			Utils.runCommand("touch " + CpuValues.FILENAME_MIN_SCREEN_ON
					+ " && echo " + MIN_SCREEN_ON + " > "
					+ CpuValues.FILENAME_MIN_SCREEN_ON);
			Utils.saveString("minscreenon", MIN_SCREEN_ON, context);
		}

		// Multicore Saving
		if (!MULTICORE_SAVING.isEmpty()) {
			Utils.runCommand("echo " + MULTICORE_SAVING + " > "
					+ CpuValues.FILENAME_MULTICORE_SAVING);
			Utils.saveString("multicoresaving", MULTICORE_SAVING, context);
		}

		// Temp Limit
		if (!TEMP_LIMIT.isEmpty()) {
			Utils.runCommand("echo " + TEMP_LIMIT + " > "
					+ CpuValues.FILENAME_TEMP_LIMIT);
			Utils.saveString("templimit", TEMP_LIMIT, context);
		}

		// Governor
		if (!GOVERNOR.isEmpty()) {
			Utils.runCommand("echo " + GOVERNOR + " > "
					+ CpuValues.FILENAME_CUR_GOVERNOR);
			Utils.saveString("governor", GOVERNOR, context);
		}

		// Smartreflex
		if (!CORE.isEmpty())
			Utils.runCommand("echo " + CORE + " > " + CpuValues.FILENAME_CORE);
		Utils.saveString("core", CORE, context);

		if (!IVA.isEmpty())
			Utils.runCommand("echo " + IVA + " > " + CpuValues.FILENAME_IVA);
		Utils.saveString("iva", IVA, context);

		if (!MPU.isEmpty())
			Utils.runCommand("echo " + MPU + " > " + CpuValues.FILENAME_MPU);
		Utils.saveString("mpu", MPU, context);

		// Core Voltage
		if (!CORE_VOLTAGE.isEmpty()) {
			Utils.runCommand("echo " + CORE_VOLTAGE + " > "
					+ CpuValues.FILENAME_CORE_VOLTAGES);
			Utils.saveString("corevoltage", CORE_VOLTAGE, context);
		}

		// IVA Voltage
		if (!IVA_VOLTAGE.isEmpty()) {
			Utils.runCommand("echo " + IVA_VOLTAGE + " > "
					+ CpuValues.FILENAME_IVA_VOLTAGES);
			Utils.saveString("ivavoltage", IVA_VOLTAGE, context);
		}

		// MPU Voltage
		if (!MPU_VOLTAGE.isEmpty()) {
			Utils.runCommand("echo " + MPU_VOLTAGE + " > "
					+ CpuValues.FILENAME_MPU_VOLTAGES);
			Utils.saveString("mpuvoltage", MPU_VOLTAGE, context);
		}

		// Regulator Voltage
		if (!REGULATOR_VOLTAGE.isEmpty()) {
			Utils.runCommand("echo " + REGULATOR_VOLTAGE + " > "
					+ CpuValues.FILENAME_REGULATOR_VOLTAGES);
			Utils.saveString("regulatorvoltage", REGULATOR_VOLTAGE, context);
		}
	}

	public static void setGpuDisplayValues(Context context) {
		// Gpu Variable
		if (!GPU_VARIABLE.isEmpty()) {
			Utils.runCommand("echo " + GPU_VARIABLE + " > "
					+ GpuDisplayValues.FILENAME_GPU_VARIABLE);
			Utils.saveString("gpuvariable", GPU_VARIABLE, context);
		}

		// Adaptive Brightness
		if (!ADAPTIVE_BRIGHTNESS.isEmpty()) {
			Utils.runCommand("echo " + ADAPTIVE_BRIGHTNESS + " > "
					+ GpuDisplayValues.FILENAME_ADAPTIVE_BRIGHTNESS);
			Utils.saveString("adaptivebrightness", ADAPTIVE_BRIGHTNESS, context);
		}

		// Trinity Contrast
		if (!TRINITY_CONTRAST.isEmpty()) {
			Utils.runCommand("echo " + TRINITY_CONTRAST + " > "
					+ GpuDisplayValues.FILENAME_TRINITY_CONTRAST);
			Utils.saveString("trinitycontrast", TRINITY_CONTRAST, context);
		}

		// Gamma Control
		if (!GAMMA_CONTROL.isEmpty()) {
			Utils.runCommand("echo " + GAMMA_CONTROL + " > "
					+ GpuDisplayValues.FILENAME_GAMMA_CONTROL);
			Utils.saveString("gammacontrol", GAMMA_CONTROL, context);
		}

		// Gamma Offset
		if (!GAMMA_OFFSET.isEmpty()) {
			Utils.runCommand("echo " + GAMMA_OFFSET + " > "
					+ GpuDisplayValues.FILENAME_GAMMA_OFFSET);
			Utils.saveString("gammaoffset", GAMMA_OFFSET, context);
		}

		// Color Multiplier
		if (!COLOR_MULTIPLIER.isEmpty()) {
			Utils.runCommand("echo " + COLOR_MULTIPLIER + " > "
					+ GpuDisplayValues.FILENAME_COLOR_MULTIPLIER);
			Utils.saveString("colormultiplier", COLOR_MULTIPLIER, context);
		}
	}

	public static void setIoAlgorithmValues(Context context) {
		// Internal Scheduler
		if (!INTERNAL_SCHEDULER.isEmpty()) {
			Utils.runCommand("echo " + INTERNAL_SCHEDULER + " > "
					+ IoAlgorithmValues.FILENAME_INTERNAL_SCHEDULER);
			Utils.saveString("internalscheduler", INTERNAL_SCHEDULER, context);
			IoAlgorithmFragment.mCurInternalScheduler = IoAlgorithmFragment.mInternalSchedulerSpinner
					.getSelectedItemPosition();
		}

		// External Scheduler
		if (!EXTERNAL_SCHEDULER.isEmpty()) {
			Utils.runCommand("echo " + EXTERNAL_SCHEDULER + " > "
					+ IoAlgorithmValues.FILENAME_EXTERNAL_SCHEDULER);
			Utils.saveString("externalscheduler", EXTERNAL_SCHEDULER, context);
			IoAlgorithmFragment.mCurExternalScheduler = IoAlgorithmFragment.mExternalSchedulerSpinner
					.getSelectedItemPosition();
		}

		// Internal Read
		if (!INTERNAL_READ.isEmpty()) {
			Utils.runCommand("echo " + INTERNAL_READ + " > "
					+ IoAlgorithmValues.FILENAME_INTERNAL_READ);
			Utils.saveString("internalread", INTERNAL_READ, context);
		}

		// External Read
		if (!EXTERNAL_READ.isEmpty()) {
			Utils.runCommand("echo " + EXTERNAL_READ + " > "
					+ IoAlgorithmValues.FILENAME_EXTERNAL_READ);
			Utils.saveString("externalread", EXTERNAL_READ, context);
		}
	}

	public static void setMiscellaneousValues(Context context) {
		// Wifi High
		if (!WIFI_HIGH.isEmpty()) {
			Utils.runCommand("echo " + WIFI_HIGH + " > "
					+ MiscellaneousValues.FILENAME_WIFI_HIGH);
			Utils.saveString("wifihigh", WIFI_HIGH, context);
		}

		// TCP Congestion
		if (!TCP_CONGESTION.isEmpty()) {
			Utils.runCommand("sysctl -w net.ipv4.tcp_congestion_control="
					+ TCP_CONGESTION);
			Utils.saveString("tcpcongestion", TCP_CONGESTION, context);
			MiscellaneousFragment.mTCPCongestion = MiscellaneousFragment.mTCPCongestionSpinner
					.getSelectedItemPosition();
		}

		// Fast Charge
		if (!FAST_CHARGE.isEmpty()) {
			Utils.runCommand("echo " + FAST_CHARGE + " > "
					+ MiscellaneousValues.FILENAME_FAST_CHARGE);
			Utils.saveString("fastcharge", FAST_CHARGE, context);
		}

		// Battery Extender
		if (!BATTERY_EXTENDER.isEmpty()) {
			Utils.runCommand("echo " + BATTERY_EXTENDER + " > "
					+ MiscellaneousValues.FILENAME_BATTERY_EXTENDER);
			Utils.saveString("batteryextender", BATTERY_EXTENDER, context);
		}

		// Sound High
		if (!SOUND_HIGH.isEmpty()) {
			Utils.runCommand("echo " + SOUND_HIGH + " > "
					+ MiscellaneousValues.FILENAME_SOUND_HIGH);
			Utils.saveString("soundhigh", SOUND_HIGH, context);
		}

		// Headphone Boost
		if (!HEADPHONE_BOOST.isEmpty()) {
			Utils.runCommand("echo " + HEADPHONE_BOOST + " > "
					+ MiscellaneousValues.FILENAME_HEADPHONE_BOOST);
			Utils.saveString("headphoneboost", HEADPHONE_BOOST, context);
		}

		// Dynamic Fsync
		if (!DYNAMIC_FSYNC.isEmpty()) {
			Utils.runCommand("echo " + DYNAMIC_FSYNC + " > "
					+ MiscellaneousValues.FILENAME_DYNAMIC_FSYNC);
			Utils.saveString("dynamicfsync", DYNAMIC_FSYNC, context);
		}

		// Fsync Control
		if (!FSYNC_CONTROL.isEmpty()) {
			Utils.runCommand("echo " + FSYNC_CONTROL + " > "
					+ MiscellaneousValues.FILENAME_FSYNC_CONTROL);
			Utils.saveString("fsynccontrol", FSYNC_CONTROL, context);
		}

		// Vibration Strength
		if (!VIBRATION_STRENGTH.isEmpty()) {
			Utils.runCommand("echo " + VIBRATION_STRENGTH + " > "
					+ MiscellaneousValues.FILENAME_VIBRATION_STRENGTH);
			Utils.saveString("vibrationstrength", VIBRATION_STRENGTH, context);
		}
	}
}
