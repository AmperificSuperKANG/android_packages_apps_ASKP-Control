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

package com.askp.control;

import com.askp.control.Utils.CpuValues;
import com.askp.control.Utils.GpuDisplayValues;
import com.askp.control.Utils.IoAlgorithmValues;
import com.askp.control.Utils.MiscellaneousValues;
import com.askp.control.Utils.Utils;
import com.stericson.RootTools.RootTools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (Utils.getBoolean("setonboot", context) == true
				&& RootTools.isAccessGiven() && RootTools.isBusyboxAvailable()) {
			if (Utils.getFormattedKernelVersion().equals(
					Utils.getString("kernelversion", context))) {
				setOnBoot(context);
				Utils.toast(context.getString(R.string.valuesapplied), context);
			} else {
				Utils.toast(context.getString(R.string.newkernel), context);
			}
		}
	}

	private static void setOnBoot(Context context) {
		// Max Cpu
		setValue(CpuValues.FILENAME_MAX_FREQ, "maxcpuvalue", context);

		// Min Cpu
		setValue(CpuValues.FILENAME_MIN_FREQ, "mincpuvalue", context);

		// Max Screen Off
		setValue(CpuValues.FILENAME_MAX_SCREEN_OFF, "maxscreenoff", context);

		// Min Screen On
		if (Utils.existFile(CpuValues.FILENAME_MIN_SCREEN_ON)
				&& !Utils.getString("minscreenon", context).equals("nothing"))
			Utils.runCommand("touch " + CpuValues.FILENAME_MIN_SCREEN_ON);
		setValue(CpuValues.FILENAME_MIN_SCREEN_ON, "minscreenon", context);

		// Multicore Saving
		setValue(CpuValues.FILENAME_MULTICORE_SAVING, "multicoresaving",
				context);

		// Temp Limit
		setValue(CpuValues.FILENAME_TEMP_LIMIT, "templimit", context);

		// Governor
		setValue(CpuValues.FILENAME_CUR_GOVERNOR, "governor", context);

		// Smartreflex
		setValue(CpuValues.FILENAME_CORE, "core", context);
		setValue(CpuValues.FILENAME_IVA, "iva", context);
		setValue(CpuValues.FILENAME_MPU, "mpu", context);

		// Core Voltage
		setValue(CpuValues.FILENAME_CORE_VOLTAGES, "corevoltage", context);

		// IVA Voltage
		setValue(CpuValues.FILENAME_IVA_VOLTAGES, "ivavoltage", context);

		// MPU Voltage
		setValue(CpuValues.FILENAME_MPU_VOLTAGES, "mpuvoltage", context);

		// Regulator Voltage
		setValue(CpuValues.FILENAME_REGULATOR_VOLTAGES, "regulatorvoltage",
				context);

		// Gpu Variable
		setValue(GpuDisplayValues.FILENAME_GPU_VARIABLE, "gpuvariable", context);

		// Adaptive Brightness
		setValue(GpuDisplayValues.FILENAME_ADAPTIVE_BRIGHTNESS,
				"adaptivebrightness", context);

		// Trinity Contrast
		setValue(GpuDisplayValues.FILENAME_TRINITY_CONTRAST, "trinitycontrast",
				context);

		// Gamma Control
		setValue(GpuDisplayValues.FILENAME_GAMMA_CONTROL, "gammacontrol",
				context);

		// Gamma Offset
		setValue(GpuDisplayValues.FILENAME_GAMMA_OFFSET, "gammaoffset", context);

		// Gamma Offset
		setValue(GpuDisplayValues.FILENAME_COLOR_MULTIPLIER, "colormultiplier",
				context);

		// Internal Scheduler
		setValue(IoAlgorithmValues.FILENAME_INTERNAL_SCHEDULER,
				"internalscheduler", context);

		// External Scheduler
		setValue(IoAlgorithmValues.FILENAME_EXTERNAL_SCHEDULER,
				"externalscheduler", context);

		// Internal Read
		setValue(IoAlgorithmValues.FILENAME_INTERNAL_READ, "internalread",
				context);

		// External Read
		setValue(IoAlgorithmValues.FILENAME_EXTERNAL_READ, "externalread",
				context);

		// Wifi High
		setValue(MiscellaneousValues.FILENAME_WIFI_HIGH, "wifihigh", context);

		// TCP Congestion
		if (Utils.existFile(MiscellaneousValues.FILENAME_TCP_CONGESTION)
				&& !Utils.getString("tcpcongestion", context).equals("nothing"))
			Utils.runCommand("sysctl -w net.ipv4.tcp_congestion_control="
					+ Utils.getString("tcpcongestion", context));

		// Fast Charge
		setValue(MiscellaneousValues.FILENAME_FAST_CHARGE, "fastcharge",
				context);

		// Battery Extender
		setValue(MiscellaneousValues.FILENAME_BATTERY_EXTENDER,
				"batteryextender", context);

		// Sound High
		setValue(MiscellaneousValues.FILENAME_SOUND_HIGH, "soundhigh", context);

		// Headphone Boost
		setValue(MiscellaneousValues.FILENAME_HEADPHONE_BOOST,
				"headphoneboost", context);

		// Backlightnotification
		setValue(MiscellaneousValues.FILENAME_BACKLIGHT_NOTIFICATION,
				"backlightnotification", context);

		// Dynamic Fsync
		setValue(MiscellaneousValues.FILENAME_DYNAMIC_FSYNC, "dynamicfsync",
				context);

		// Fsync Control
		setValue(MiscellaneousValues.FILENAME_FSYNC_CONTROL, "fsynccontrol",
				context);

		// Vibration Strength
		setValue(MiscellaneousValues.FILENAME_VIBRATION_STRENGTH,
				"vibrationstrength", context);
	}

	private static void setValue(String file, String name, Context context) {
		if (Utils.existFile(file)
				&& !Utils.getString(name, context).equals("nothing"))
			Utils.runCommand("echo " + Utils.getString(name, context) + " > "
					+ file);
	}
}