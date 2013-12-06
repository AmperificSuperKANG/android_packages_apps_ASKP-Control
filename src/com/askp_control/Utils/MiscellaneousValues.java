package com.askp_control.Utils;

import java.io.IOException;

public class MiscellaneousValues {

	public static final String FILENAME_WIFI_HIGH = "/sys/module/bcmdhd/parameters/wifi_fast";

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
