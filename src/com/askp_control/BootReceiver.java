package com.askp_control;

import com.askp_control.Utils.CpuValues;
import com.askp_control.Utils.Utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;

public class BootReceiver extends BroadcastReceiver {

	private static NotificationManager notificationManager;
	private static boolean forward = true;

	@Override
	public void onReceive(Context context, Intent intent) {
		if (Utils.getBoolean("setonboot", context) == true) {
			notifcation(context);
		}
	}

	public static void notifcation(final Context context) {
		notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Intent intent = new Intent(context, CancelActivity.class);
		PendingIntent pIntent = PendingIntent
				.getActivity(context, 0, intent, 0);

		final Builder noti = new NotificationCompat.Builder(context)
				.setTicker(context.getString(R.string.app_name))
				.setContentTitle(context.getString(R.string.app_name))
				.setContentText("")
				.setAutoCancel(false)
				.setOngoing(true)
				.setSmallIcon(R.drawable.ic_launcher)
				.addAction(R.drawable.navigation_cancel,
						context.getString(android.R.string.cancel), pIntent);

		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int incr = 10; incr > 0; incr--) {
					if (forward) {
						noti.setProgress(10, incr, false).setContentText(
								context.getString(R.string.setvaluesonboot)
										+ " "
										+ String.valueOf(incr)
										+ " "
										+ context.getString(R.string.seconds)
												.toLowerCase());
						notificationManager.notify(0, noti.build());
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
						}
					}
				}
				if (forward)
					setValues(context);
			}
		}).start();
	}

	private static void setValues(Context context) {
		cancelSetValues();
		setOnBoot(context);
		finishNoti(context);
	}

	private static void finishNoti(Context context) {
		Builder finishnoti = new NotificationCompat.Builder(context)
				.setTicker(context.getString(R.string.valuesapplied))
				.setContentTitle(context.getString(R.string.app_name))
				.setContentText(context.getString(R.string.valuesapplied))
				.setSmallIcon(R.drawable.ic_launcher);
		notificationManager.notify(0, finishnoti.build());
	}

	public static void cancelSetValues() {
		forward = false;
		notificationManager.cancel(0);
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
	}
}