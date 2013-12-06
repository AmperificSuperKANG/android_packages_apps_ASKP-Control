package com.askp_control;

import com.askp_control.Utils.CpuValues;
import com.askp_control.Utils.GpuDisplayValues;
import com.askp_control.Utils.IoAlgorithmValues;
import com.askp_control.Utils.MiscellaneousValues;
import com.askp_control.Utils.Utils;
import com.stericson.RootTools.RootTools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (Utils.getBoolean("setonboot", context) == true) {
			if (RootTools.isAccessGiven() && RootTools.isBusyboxAvailable()) {
				if (Utils.getFormattedKernelVersion().equals(
						Utils.getString("kernelversion", context))) {
					setOnBoot(context);
					Utils.toast(context.getString(R.string.valuesapplied),
							context);
				} else {
					Utils.toast(context.getString(R.string.newkernel), context);
				}
			}
		}
	}

	private static void setOnBoot(Context context) {

		// Max Cpu
		if (Utils.existFile(CpuValues.FILENAME_MAX_FREQ))
			if (!Utils.getString("maxcpuvalue", context).equals("nothing"))
				Utils.runCommand("echo "
						+ Utils.getString("maxcpuvalue", context) + " > "
						+ CpuValues.FILENAME_MAX_FREQ);

		// Min Cpu
		if (Utils.existFile(CpuValues.FILENAME_MIN_FREQ))
			if (!Utils.getString("mincpuvalue", context).equals("nothing"))
				Utils.runCommand("echo "
						+ Utils.getString("mincpuvalue", context) + " > "
						+ CpuValues.FILENAME_MIN_FREQ);

		// Max Screen Off
		if (Utils.existFile(CpuValues.FILENAME_MAX_SCREEN_OFF))
			if (!Utils.getString("maxscreenoff", context).equals("nothing"))
				Utils.runCommand("echo "
						+ Utils.getString("maxscreenoff", context) + " > "
						+ CpuValues.FILENAME_MAX_SCREEN_OFF);

		// Min Screen On
		if (Utils.existFile(CpuValues.FILENAME_MIN_SCREEN_ON))
			if (!Utils.getString("minscreenon", context).equals("nothing"))
				Utils.runCommand("touch " + CpuValues.FILENAME_MIN_SCREEN_ON
						+ " && echo " + Utils.getString("minscreenon", context)
						+ " > " + CpuValues.FILENAME_MIN_SCREEN_ON);

		// Multicore Saving
		if (Utils.existFile(CpuValues.FILENAME_MULTICORE_SAVING))
			if (!Utils.getString("multicoresaving", context).equals("nothing"))
				Utils.runCommand("echo "
						+ Utils.getString("multicoresaving", context) + " > "
						+ CpuValues.FILENAME_MULTICORE_SAVING);

		// Temp Limit
		if (Utils.existFile(CpuValues.FILENAME_TEMP_LIMIT))
			if (!Utils.getString("templimit", context).equals("nothing"))
				Utils.runCommand("echo "
						+ Utils.getString("templimit", context) + " > "
						+ CpuValues.FILENAME_TEMP_LIMIT);

		// Governor
		if (Utils.existFile(CpuValues.FILENAME_CUR_GOVERNOR))
			if (!Utils.getString("governor", context).equals("nothing"))
				Utils.runCommand("echo " + Utils.getString("governor", context)
						+ " > " + CpuValues.FILENAME_CUR_GOVERNOR);

		// Smartreflex
		if (Utils.existFile(CpuValues.FILENAME_CORE))
			if (!Utils.getString("core", context).equals("nothing"))
				Utils.runCommand("echo " + Utils.getString("core", context)
						+ " > " + CpuValues.FILENAME_CORE);

		if (Utils.existFile(CpuValues.FILENAME_IVA))
			if (!Utils.getString("iva", context).equals("nothing"))
				Utils.runCommand("echo " + Utils.getString("iva", context)
						+ " > " + CpuValues.FILENAME_IVA);

		if (Utils.existFile(CpuValues.FILENAME_MPU))
			if (!Utils.getString("mpu", context).equals("nothing"))
				Utils.runCommand("echo " + Utils.getString("mpu", context)
						+ " > " + CpuValues.FILENAME_MPU);

		// Core Voltage
		if (Utils.existFile(CpuValues.FILENAME_CORE_VOLTAGES))
			if (!Utils.getString("corevoltage", context).equals("nothing"))
				Utils.runCommand("echo "
						+ Utils.getString("corevoltage", context) + " > "
						+ CpuValues.FILENAME_CORE_VOLTAGES);

		// IVA Voltage
		if (Utils.existFile(CpuValues.FILENAME_IVA_VOLTAGES))
			if (!Utils.getString("ivavoltage", context).equals("nothing"))
				Utils.runCommand("echo "
						+ Utils.getString("ivavoltage", context) + " > "
						+ CpuValues.FILENAME_IVA_VOLTAGES);

		// MPU Voltage
		if (Utils.existFile(CpuValues.FILENAME_MPU_VOLTAGES))
			if (!Utils.getString("mpuvoltage", context).equals("nothing"))
				Utils.runCommand("echo "
						+ Utils.getString("mpuvoltage", context) + " > "
						+ CpuValues.FILENAME_MPU_VOLTAGES);

		// Regulator Voltage
		if (Utils.existFile(CpuValues.FILENAME_REGULATOR_VOLTAGES))
			if (!Utils.getString("regulatorvoltage", context).equals("nothing"))
				Utils.runCommand("echo "
						+ Utils.getString("regulatorvoltage", context) + " > "
						+ CpuValues.FILENAME_REGULATOR_VOLTAGES);

		// Gpu Variable
		if (Utils.existFile(GpuDisplayValues.FILENAME_VARIABLE_GPU))
			if (!Utils.getString("gpuvariable", context).equals("nothing"))
				Utils.runCommand("echo "
						+ Utils.getString("gpuvariable", context) + " > "
						+ GpuDisplayValues.FILENAME_VARIABLE_GPU);

		// Adaptive Brightness
		if (Utils.existFile(GpuDisplayValues.FILENAME_ADAPTIVE_BRIGHTNESS))
			if (!Utils.getString("adaptivebrightness", context).equals(
					"nothing"))
				Utils.runCommand("echo "
						+ Utils.getString("adaptivebrightness", context)
						+ " > " + GpuDisplayValues.FILENAME_ADAPTIVE_BRIGHTNESS);

		// Trinity Contrast
		if (Utils.existFile(GpuDisplayValues.FILENAME_TRINITY_CONTRAST))
			if (!Utils.getString("trinitycontrast", context).equals("nothing"))
				Utils.runCommand("echo "
						+ Utils.getString("trinitycontrast", context) + " > "
						+ GpuDisplayValues.FILENAME_TRINITY_CONTRAST);

		// Gamma Control
		if (Utils.existFile(GpuDisplayValues.FILENAME_GAMMA_CONTROL))
			if (!Utils.getString("gammacontrol", context).equals("nothing"))
				Utils.runCommand("echo "
						+ Utils.getString("gammacontrol", context) + " > "
						+ GpuDisplayValues.FILENAME_GAMMA_CONTROL);

		// Gamma Offset
		if (Utils.existFile(GpuDisplayValues.FILENAME_GAMMA_OFFSET))
			if (!Utils.getString("gammaoffset", context).equals("nothing"))
				Utils.runCommand("echo "
						+ Utils.getString("gammaoffset", context) + " > "
						+ GpuDisplayValues.FILENAME_GAMMA_OFFSET);

		// Gamma Offset
		if (Utils.existFile(GpuDisplayValues.FILENAME_COLOR_MULTIPLIER))
			if (!Utils.getString("colormultiplier", context).equals("nothing"))
				Utils.runCommand("echo "
						+ Utils.getString("colormultiplier", context) + " > "
						+ GpuDisplayValues.FILENAME_COLOR_MULTIPLIER);

		// TCP Congestion
		if (Utils.existFile(IoAlgorithmValues.FILENAME_TCP_CONGESTION))
			if (!Utils.getString("tcpcongestion", context).equals("nothing"))
				Utils.runCommand("sysctl -w net.ipv4.tcp_congestion_control="
						+ Utils.getString("tcpcongestion", context));

		// Internal Scheduler
		if (Utils.existFile(IoAlgorithmValues.FILENAME_INTERNAL_SCHEDULER))
			if (!Utils.getString("internalscheduler", context)
					.equals("nothing"))
				Utils.runCommand("echo "
						+ Utils.getString("internalscheduler", context) + " > "
						+ IoAlgorithmValues.FILENAME_INTERNAL_SCHEDULER);

		// External Scheduler
		if (Utils.existFile(IoAlgorithmValues.FILENAME_EXTERNAL_SCHEDULER))
			if (!Utils.getString("externalscheduler", context)
					.equals("nothing"))
				Utils.runCommand("echo "
						+ Utils.getString("externalscheduler", context) + " > "
						+ IoAlgorithmValues.FILENAME_EXTERNAL_SCHEDULER);

		// Internal Read
		if (Utils.existFile(IoAlgorithmValues.FILENAME_INTERNAL_READ))
			if (!Utils.getString("internalread", context).equals("nothing"))
				Utils.runCommand("echo "
						+ Utils.getString("internalread", context) + " > "
						+ IoAlgorithmValues.FILENAME_INTERNAL_READ);

		// External Read
		if (Utils.existFile(IoAlgorithmValues.FILENAME_EXTERNAL_READ))
			if (!Utils.getString("externalread", context).equals("nothing"))
				Utils.runCommand("echo "
						+ Utils.getString("externalread", context) + " > "
						+ IoAlgorithmValues.FILENAME_EXTERNAL_READ);

		// Wifi High
		if (Utils.existFile(MiscellaneousValues.FILENAME_WIFI_HIGH))
			if (!Utils.getString("wifihigh", context).equals("nothing"))
				Utils.runCommand("echo " + Utils.getString("wifihigh", context)
						+ " > " + MiscellaneousValues.FILENAME_WIFI_HIGH);

		// Fast Charge
		if (Utils.existFile(MiscellaneousValues.FILENAME_FAST_CHARGE))
			if (!Utils.getString("fastcharge", context).equals("nothing"))
				Utils.runCommand("echo "
						+ Utils.getString("fastcharge", context) + " > "
						+ MiscellaneousValues.FILENAME_FAST_CHARGE);

		// Battery Extender
		if (Utils.existFile(MiscellaneousValues.FILENAME_BATTERY_EXTENDER))
			if (!Utils.getString("batteryextender", context).equals("nothing"))
				Utils.runCommand("echo "
						+ Utils.getString("batteryextender", context) + " > "
						+ MiscellaneousValues.FILENAME_BATTERY_EXTENDER);

		// Sound High
		if (Utils.existFile(MiscellaneousValues.FILENAME_SOUND_HIGH))
			if (!Utils.getString("soundhigh", context).equals("nothing"))
				Utils.runCommand("echo "
						+ Utils.getString("soundhigh", context) + " > "
						+ MiscellaneousValues.FILENAME_SOUND_HIGH);

		// Headphone Boost
		if (Utils.existFile(MiscellaneousValues.FILENAME_HEADPHONE_BOOST))
			if (!Utils.getString("headphoneboost", context).equals("nothing"))
				Utils.runCommand("echo "
						+ Utils.getString("headphoneboost", context) + " > "
						+ MiscellaneousValues.FILENAME_HEADPHONE_BOOST);

		// Dynamic Fsync
		if (Utils.existFile(MiscellaneousValues.FILENAME_DYNAMIC_FSYNC))
			if (!Utils.getString("dynamicfsync", context).equals("nothing"))
				Utils.runCommand("echo "
						+ Utils.getString("dynamicfsync", context) + " > "
						+ MiscellaneousValues.FILENAME_DYNAMIC_FSYNC);

		// Fsync Control
		if (Utils.existFile(MiscellaneousValues.FILENAME_FSYNC_CONTROL))
			if (!Utils.getString("fsynccontrol", context).equals("nothing"))
				Utils.runCommand("echo "
						+ Utils.getString("fsynccontrol", context) + " > "
						+ MiscellaneousValues.FILENAME_FSYNC_CONTROL);
	}
}