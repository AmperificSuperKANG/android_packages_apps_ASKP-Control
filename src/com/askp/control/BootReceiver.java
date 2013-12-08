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
		if (Utils.existFile(CpuValues.FILENAME_MAX_FREQ)
				&& !Utils.getString("maxcpuvalue", context).equals("nothing"))
			Utils.runCommand("echo " + Utils.getString("maxcpuvalue", context)
					+ " > " + CpuValues.FILENAME_MAX_FREQ);

		// Min Cpu
		if (Utils.existFile(CpuValues.FILENAME_MIN_FREQ)
				&& !Utils.getString("mincpuvalue", context).equals("nothing"))
			Utils.runCommand("echo " + Utils.getString("mincpuvalue", context)
					+ " > " + CpuValues.FILENAME_MIN_FREQ);

		// Max Screen Off
		if (Utils.existFile(CpuValues.FILENAME_MAX_SCREEN_OFF)
				&& !Utils.getString("maxscreenoff", context).equals("nothing"))
			Utils.runCommand("echo " + Utils.getString("maxscreenoff", context)
					+ " > " + CpuValues.FILENAME_MAX_SCREEN_OFF);

		// Min Screen On
		if (Utils.existFile(CpuValues.FILENAME_MIN_SCREEN_ON)
				&& !Utils.getString("minscreenon", context).equals("nothing"))
			Utils.runCommand("touch " + CpuValues.FILENAME_MIN_SCREEN_ON
					+ " && echo " + Utils.getString("minscreenon", context)
					+ " > " + CpuValues.FILENAME_MIN_SCREEN_ON);

		// Multicore Saving
		if (Utils.existFile(CpuValues.FILENAME_MULTICORE_SAVING)
				&& !Utils.getString("multicoresaving", context).equals(
						"nothing"))
			Utils.runCommand("echo "
					+ Utils.getString("multicoresaving", context) + " > "
					+ CpuValues.FILENAME_MULTICORE_SAVING);

		// Temp Limit
		if (Utils.existFile(CpuValues.FILENAME_TEMP_LIMIT)
				&& !Utils.getString("templimit", context).equals("nothing"))
			Utils.runCommand("echo " + Utils.getString("templimit", context)
					+ " > " + CpuValues.FILENAME_TEMP_LIMIT);

		// Governor
		if (Utils.existFile(CpuValues.FILENAME_CUR_GOVERNOR)
				&& !Utils.getString("governor", context).equals("nothing"))
			Utils.runCommand("echo " + Utils.getString("governor", context)
					+ " > " + CpuValues.FILENAME_CUR_GOVERNOR);

		// Smartreflex
		if (Utils.existFile(CpuValues.FILENAME_CORE)
				&& !Utils.getString("core", context).equals("nothing"))
			Utils.runCommand("echo " + Utils.getString("core", context) + " > "
					+ CpuValues.FILENAME_CORE);

		if (Utils.existFile(CpuValues.FILENAME_IVA)
				&& !Utils.getString("iva", context).equals("nothing"))
			Utils.runCommand("echo " + Utils.getString("iva", context) + " > "
					+ CpuValues.FILENAME_IVA);

		if (Utils.existFile(CpuValues.FILENAME_MPU)
				&& !Utils.getString("mpu", context).equals("nothing"))
			Utils.runCommand("echo " + Utils.getString("mpu", context) + " > "
					+ CpuValues.FILENAME_MPU);

		// Core Voltage
		if (Utils.existFile(CpuValues.FILENAME_CORE_VOLTAGES)
				&& !Utils.getString("corevoltage", context).equals("nothing"))
			Utils.runCommand("echo " + Utils.getString("corevoltage", context)
					+ " > " + CpuValues.FILENAME_CORE_VOLTAGES);

		// IVA Voltage
		if (Utils.existFile(CpuValues.FILENAME_IVA_VOLTAGES)
				&& !Utils.getString("ivavoltage", context).equals("nothing"))
			Utils.runCommand("echo " + Utils.getString("ivavoltage", context)
					+ " > " + CpuValues.FILENAME_IVA_VOLTAGES);

		// MPU Voltage
		if (Utils.existFile(CpuValues.FILENAME_MPU_VOLTAGES)
				&& !Utils.getString("mpuvoltage", context).equals("nothing"))
			Utils.runCommand("echo " + Utils.getString("mpuvoltage", context)
					+ " > " + CpuValues.FILENAME_MPU_VOLTAGES);

		// Regulator Voltage
		if (Utils.existFile(CpuValues.FILENAME_REGULATOR_VOLTAGES)
				&& !Utils.getString("regulatorvoltage", context).equals(
						"nothing"))
			Utils.runCommand("echo "
					+ Utils.getString("regulatorvoltage", context) + " > "
					+ CpuValues.FILENAME_REGULATOR_VOLTAGES);

		// Gpu Variable
		if (Utils.existFile(GpuDisplayValues.FILENAME_VARIABLE_GPU)
				&& !Utils.getString("gpuvariable", context).equals("nothing"))
			Utils.runCommand("echo " + Utils.getString("gpuvariable", context)
					+ " > " + GpuDisplayValues.FILENAME_VARIABLE_GPU);

		// Adaptive Brightness
		if (Utils.existFile(GpuDisplayValues.FILENAME_ADAPTIVE_BRIGHTNESS)
				&& !Utils.getString("adaptivebrightness", context).equals(
						"nothing"))
			Utils.runCommand("echo "
					+ Utils.getString("adaptivebrightness", context) + " > "
					+ GpuDisplayValues.FILENAME_ADAPTIVE_BRIGHTNESS);

		// Trinity Contrast
		if (Utils.existFile(GpuDisplayValues.FILENAME_TRINITY_CONTRAST)
				&& !Utils.getString("trinitycontrast", context).equals(
						"nothing"))
			Utils.runCommand("echo "
					+ Utils.getString("trinitycontrast", context) + " > "
					+ GpuDisplayValues.FILENAME_TRINITY_CONTRAST);

		// Gamma Control
		if (Utils.existFile(GpuDisplayValues.FILENAME_GAMMA_CONTROL)
				&& !Utils.getString("gammacontrol", context).equals("nothing"))
			Utils.runCommand("echo " + Utils.getString("gammacontrol", context)
					+ " > " + GpuDisplayValues.FILENAME_GAMMA_CONTROL);

		// Gamma Offset
		if (Utils.existFile(GpuDisplayValues.FILENAME_GAMMA_OFFSET)
				&& !Utils.getString("gammaoffset", context).equals("nothing"))
			Utils.runCommand("echo " + Utils.getString("gammaoffset", context)
					+ " > " + GpuDisplayValues.FILENAME_GAMMA_OFFSET);

		// Gamma Offset
		if (Utils.existFile(GpuDisplayValues.FILENAME_COLOR_MULTIPLIER)
				&& !Utils.getString("colormultiplier", context).equals(
						"nothing"))
			Utils.runCommand("echo "
					+ Utils.getString("colormultiplier", context) + " > "
					+ GpuDisplayValues.FILENAME_COLOR_MULTIPLIER);

		// TCP Congestion
		if (Utils.existFile(IoAlgorithmValues.FILENAME_TCP_CONGESTION)
				&& !Utils.getString("tcpcongestion", context).equals("nothing"))
			Utils.runCommand("sysctl -w net.ipv4.tcp_congestion_control="
					+ Utils.getString("tcpcongestion", context));

		// Internal Scheduler
		if (Utils.existFile(IoAlgorithmValues.FILENAME_INTERNAL_SCHEDULER)
				&& !Utils.getString("internalscheduler", context).equals(
						"nothing"))
			Utils.runCommand("echo "
					+ Utils.getString("internalscheduler", context) + " > "
					+ IoAlgorithmValues.FILENAME_INTERNAL_SCHEDULER);

		// External Scheduler
		if (Utils.existFile(IoAlgorithmValues.FILENAME_EXTERNAL_SCHEDULER)
				&& !Utils.getString("externalscheduler", context).equals(
						"nothing"))
			Utils.runCommand("echo "
					+ Utils.getString("externalscheduler", context) + " > "
					+ IoAlgorithmValues.FILENAME_EXTERNAL_SCHEDULER);

		// Internal Read
		if (Utils.existFile(IoAlgorithmValues.FILENAME_INTERNAL_READ)
				&& !Utils.getString("internalread", context).equals("nothing"))
			Utils.runCommand("echo " + Utils.getString("internalread", context)
					+ " > " + IoAlgorithmValues.FILENAME_INTERNAL_READ);

		// External Read
		if (Utils.existFile(IoAlgorithmValues.FILENAME_EXTERNAL_READ)
				&& !Utils.getString("externalread", context).equals("nothing"))
			Utils.runCommand("echo " + Utils.getString("externalread", context)
					+ " > " + IoAlgorithmValues.FILENAME_EXTERNAL_READ);

		// Wifi High
		if (Utils.existFile(MiscellaneousValues.FILENAME_WIFI_HIGH)
				&& !Utils.getString("wifihigh", context).equals("nothing"))
			Utils.runCommand("echo " + Utils.getString("wifihigh", context)
					+ " > " + MiscellaneousValues.FILENAME_WIFI_HIGH);

		// Fast Charge
		if (Utils.existFile(MiscellaneousValues.FILENAME_FAST_CHARGE)
				&& !Utils.getString("fastcharge", context).equals("nothing"))
			Utils.runCommand("echo " + Utils.getString("fastcharge", context)
					+ " > " + MiscellaneousValues.FILENAME_FAST_CHARGE);

		// Battery Extender
		if (Utils.existFile(MiscellaneousValues.FILENAME_BATTERY_EXTENDER)
				&& !Utils.getString("batteryextender", context).equals(
						"nothing"))
			Utils.runCommand("echo "
					+ Utils.getString("batteryextender", context) + " > "
					+ MiscellaneousValues.FILENAME_BATTERY_EXTENDER);

		// Sound High
		if (Utils.existFile(MiscellaneousValues.FILENAME_SOUND_HIGH)
				&& !Utils.getString("soundhigh", context).equals("nothing"))
			Utils.runCommand("echo " + Utils.getString("soundhigh", context)
					+ " > " + MiscellaneousValues.FILENAME_SOUND_HIGH);

		// Headphone Boost
		if (Utils.existFile(MiscellaneousValues.FILENAME_HEADPHONE_BOOST)
				&& !Utils.getString("headphoneboost", context)
						.equals("nothing"))
			Utils.runCommand("echo "
					+ Utils.getString("headphoneboost", context) + " > "
					+ MiscellaneousValues.FILENAME_HEADPHONE_BOOST);

		// Dynamic Fsync
		if (Utils.existFile(MiscellaneousValues.FILENAME_DYNAMIC_FSYNC)
				&& !Utils.getString("dynamicfsync", context).equals("nothing"))
			Utils.runCommand("echo " + Utils.getString("dynamicfsync", context)
					+ " > " + MiscellaneousValues.FILENAME_DYNAMIC_FSYNC);

		// Fsync Control
		if (Utils.existFile(MiscellaneousValues.FILENAME_FSYNC_CONTROL)
				&& !Utils.getString("fsynccontrol", context).equals("nothing"))
			Utils.runCommand("echo " + Utils.getString("fsynccontrol", context)
					+ " > " + MiscellaneousValues.FILENAME_FSYNC_CONTROL);
	}
}