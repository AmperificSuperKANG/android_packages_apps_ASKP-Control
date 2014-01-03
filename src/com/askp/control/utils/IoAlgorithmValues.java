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

public class IoAlgorithmValues {

	public static final String FILENAME_EXTERNAL_READ = "/sys/block/mmcblk1/queue/read_ahead_kb";
	public static final String FILENAME_INTERNAL_READ = "/sys/block/mmcblk0/queue/read_ahead_kb";
	public static final String FILENAME_EXTERNAL_SCHEDULER = "/sys/block/mmcblk1/queue/scheduler";
	public static final String FILENAME_INTERNAL_SCHEDULER = "/sys/block/mmcblk0/queue/scheduler";

	public static String mExternalRead() {
		if (Utils.existFile(FILENAME_EXTERNAL_READ))
			try {
				return Utils.readLine(FILENAME_EXTERNAL_READ);
			} catch (IOException e) {
			}
		return "0";
	}

	public static String mInternalRead() {
		if (Utils.existFile(FILENAME_INTERNAL_READ))
			try {
				return Utils.readLine(FILENAME_INTERNAL_READ);
			} catch (IOException e) {
			}
		return "0";
	}

	public static String mExternalScheduler() {
		if (Utils.existFile(FILENAME_EXTERNAL_SCHEDULER))
			try {
				return Utils.readLine(FILENAME_EXTERNAL_SCHEDULER);
			} catch (IOException e) {
			}
		return "0 0";
	}

	public static String mInternalScheduler() {
		if (Utils.existFile(FILENAME_INTERNAL_SCHEDULER))
			try {
				return Utils.readLine(FILENAME_INTERNAL_SCHEDULER);
			} catch (IOException e) {
			}
		return "0 0";
	}
}
