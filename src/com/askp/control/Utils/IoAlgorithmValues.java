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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	public static String mCurExternalScheduler() {
		String[] x = IoAlgorithmValues.mExternalScheduler().split(" ");
		List<String> xList = new ArrayList<String>(Arrays.asList(x));
		for (int i = 0; i < xList.size(); i++)
			if (xList.get(i).indexOf("[") != -1) {
				x[i] = x[i].replace("[", "").replace("]", "");
				return x[i];
			}
		return "0";
	}

	public static String mCurInternalScheduler() {
		String[] x = IoAlgorithmValues.mInternalScheduler().split(" ");
		List<String> xList = new ArrayList<String>(Arrays.asList(x));
		for (int i = 0; i < xList.size(); i++)
			if (xList.get(i).indexOf("[") != -1) {
				x[i] = x[i].replace("[", "").replace("]", "");
				return x[i];
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
