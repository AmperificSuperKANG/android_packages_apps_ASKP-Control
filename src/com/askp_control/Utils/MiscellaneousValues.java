package com.askp_control.Utils;

import java.io.IOException;

public class MiscellaneousValues {

	public static final String FILENAME_BATTERY_EXTENDER = "/sys/devices/virtual/misc/batterylifeextender/charging_limit";
	public static final String FILENAME_FAST_CHARGE = "/sys/kernel/fast_charge/force_fast_charge";
	public static final String FILENAME_WIFI_HIGH = "/sys/module/bcmdhd/parameters/wifi_fast";

	public static int mBatterExtender() {
		if (Utils.existFile(FILENAME_BATTERY_EXTENDER))
			try {
				return Integer.parseInt(Utils
						.readLine(FILENAME_BATTERY_EXTENDER));
			} catch (IOException e) {
				e.printStackTrace();
			}
		return 0;
	}

	public static int mFastCharge() {
		if (Utils.existFile(FILENAME_FAST_CHARGE))
			try {
				return Integer.parseInt(Utils.readLine(FILENAME_FAST_CHARGE));
			} catch (IOException e) {
				e.printStackTrace();
			}
		return 0;
	}

	public static String mWifiHigh() {
		if (Utils.existFile(FILENAME_WIFI_HIGH))
			try {
				return Utils.readLine(FILENAME_WIFI_HIGH);
			} catch (IOException e) {
				e.printStackTrace();
			}
		return "0";
	}
}
