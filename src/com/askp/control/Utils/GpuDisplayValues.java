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

import java.io.IOException;

public class GpuDisplayValues {

	public static final String FILENAME_COLOR_MULTIPLIER = "/sys/class/misc/colorcontrol/multiplier";
	public static final String FILENAME_GAMMA_OFFSET = "/sys/class/misc/colorcontrol/v1_offset";
	public static final String FILENAME_GAMMA_CONTROL = "/sys/devices/platform/omapdss/manager0/gamma";
	public static final String FILENAME_TRINITY_CONTRAST = "/sys/module/panel_s6e8aa0/parameters/contrast";
	public static final String FILENAME_ADAPTIVE_BRIGHTNESS = "/sys/class/backlight/s6e8aa0/acl_set";
	public static final String FILENAME_VARIABLE_GPU = "/sys/devices/system/cpu/cpu0/cpufreq/gpu_oc";

	public static String mColorMultiplier() {
		if (Utils.existFile(FILENAME_COLOR_MULTIPLIER))
			try {
				return (Utils.readLine(FILENAME_COLOR_MULTIPLIER));
			} catch (IOException e) {
			}
		return "0 0";
	}

	public static String mGammaOffset() {
		if (Utils.existFile(FILENAME_GAMMA_OFFSET))
			try {
				return (Utils.readLine(FILENAME_GAMMA_OFFSET));
			} catch (IOException e) {
			}
		return "0 0";
	}

	public static int mGammaControl() {
		if (Utils.existFile(FILENAME_GAMMA_CONTROL))
			try {
				return Integer.parseInt(Utils.readLine(FILENAME_GAMMA_CONTROL));
			} catch (IOException e) {
			}
		return 0;
	}

	public static int mTrinityContrast() {
		if (Utils.existFile(FILENAME_TRINITY_CONTRAST))
			try {
				return Integer.parseInt(Utils
						.readLine(FILENAME_TRINITY_CONTRAST));
			} catch (IOException e) {
			}
		return 0;
	}

	public static int mAdaptiveBrightness() {
		if (Utils.existFile(FILENAME_ADAPTIVE_BRIGHTNESS))
			try {
				return Integer.parseInt(Utils
						.readLine(FILENAME_ADAPTIVE_BRIGHTNESS));
			} catch (IOException e) {
			}
		return 0;
	}

	public static int mVariableGpu() {
		if (Utils.existFile(FILENAME_VARIABLE_GPU))
			try {
				return Integer.parseInt(Utils.readLine(FILENAME_VARIABLE_GPU));
			} catch (IOException e) {
			}
		return 0;
	}
}
