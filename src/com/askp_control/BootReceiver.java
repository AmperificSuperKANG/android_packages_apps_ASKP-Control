package com.askp_control;

import com.askp_control.Utils.CpuValues;
import com.askp_control.Utils.GpuDisplayValues;
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

		// Gpu Variable
		if (Utils.existFile(GpuDisplayValues.FILENAME_TRINITY_CONTRAST))
			if (!Utils.getString("trinitycontrast", context).equals("nothing"))
				Utils.runCommand("echo "
						+ Utils.getString("trinitycontrast", context) + " > "
						+ GpuDisplayValues.FILENAME_TRINITY_CONTRAST);
	}
}