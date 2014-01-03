/*
 * Copyright (C) 2014 AmperificSuperKANG Project
 *
 * This file is part of ASKP Control.
 *
 * ASKP Control is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ASKP Control is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ASKP Control.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.askp.control.utils;

import java.io.IOException;

public class MiscellaneousValues {

	public static final String FILENAME_ZRAM_SWAP = "proc/swaps";
	public static final String FILENAME_VIBRATION_STRENGTH = "/sys/vibrator/pwmvalue";
	public static final String FILENAME_FSYNC_CONTROL = "/sys/devices/virtual/misc/fsynccontrol/fsync_enabled";
	public static final String FILENAME_DYNAMIC_FSYNC = "/sys/kernel/dyn_fsync/Dyn_fsync_active";
	public static final String FILENAME_BACKLIGHT_NOTIFICATION = "/sys/class/misc/backlightnotification/enabled";
	public static final String FILENAME_HEADPHONE_BOOST = "/sys/devices/virtual/misc/soundcontrol/volume_boost";
	public static final String FILENAME_SOUND_HIGH = "/sys/devices/virtual/misc/soundcontrol/highperf_enabled";
	public static final String FILENAME_BATTERY_EXTENDER = "/sys/devices/virtual/misc/batterylifeextender/charging_limit";
	public static final String FILENAME_FAST_CHARGE = "/sys/kernel/fast_charge/force_fast_charge";
	public static final String FILENAME_TCP_CONGESTION = "/proc/sys/net/ipv4/tcp_available_congestion_control";
	public static final String FILENAME_WIFI_HIGH = "/sys/module/bcmdhd/parameters/wifi_fast";

	public static String mZramSwap() {
		if (Utils.existFile(FILENAME_ZRAM_SWAP))
			try {
				return Utils.readBlock(FILENAME_ZRAM_SWAP);
			} catch (IOException e) {
			}
		return "0";
	}

	public static int mVibrationStrength() {
		if (Utils.existFile(FILENAME_VIBRATION_STRENGTH))
			try {
				return Integer.parseInt(Utils
						.readLine(FILENAME_VIBRATION_STRENGTH));
			} catch (NumberFormatException e) {
			} catch (IOException e) {
			}
		return 0;
	}

	public static int mFsyncControl() {
		if (Utils.existFile(FILENAME_FSYNC_CONTROL))
			try {
				return Integer.parseInt(Utils.readLine(FILENAME_FSYNC_CONTROL));
			} catch (IOException e) {
			}
		return 0;
	}

	public static int mDynamicFsync() {
		if (Utils.existFile(FILENAME_DYNAMIC_FSYNC))
			try {
				return Integer.parseInt(Utils.readLine(FILENAME_DYNAMIC_FSYNC));
			} catch (IOException e) {
			}
		return 0;
	}

	public static int mBacklightnotification() {
		if (Utils.existFile(FILENAME_BACKLIGHT_NOTIFICATION))
			try {
				return Integer.parseInt(Utils
						.readLine(FILENAME_BACKLIGHT_NOTIFICATION));
			} catch (IOException e) {
			}
		return 0;
	}

	public static int mHeadphoneBoost() {
		if (Utils.existFile(FILENAME_HEADPHONE_BOOST))
			try {
				return Integer.parseInt(Utils
						.readLine(FILENAME_HEADPHONE_BOOST));
			} catch (IOException e) {
			}
		return 0;
	}

	public static int mSoundHigh() {
		if (Utils.existFile(FILENAME_SOUND_HIGH))
			try {
				return Integer.parseInt(Utils.readLine(FILENAME_SOUND_HIGH));
			} catch (IOException e) {
			}
		return 0;
	}

	public static int mBatterExtender() {
		if (Utils.existFile(FILENAME_BATTERY_EXTENDER))
			try {
				return Integer.parseInt(Utils
						.readLine(FILENAME_BATTERY_EXTENDER));
			} catch (IOException e) {
			}
		return 0;
	}

	public static int mFastCharge() {
		if (Utils.existFile(FILENAME_FAST_CHARGE))
			try {
				return Integer.parseInt(Utils.readLine(FILENAME_FAST_CHARGE));
			} catch (IOException e) {
			}
		return 0;
	}

	public static String mTCPCongestion() {
		if (Utils.existFile(FILENAME_TCP_CONGESTION))
			try {
				return Utils.readLine(FILENAME_TCP_CONGESTION);
			} catch (IOException e) {
			}
		return "0 0";
	}

	public static String mWifiHigh() {
		if (Utils.existFile(FILENAME_WIFI_HIGH))
			try {
				return Utils.readLine(FILENAME_WIFI_HIGH);
			} catch (IOException e) {
			}
		return "0";
	}
}
