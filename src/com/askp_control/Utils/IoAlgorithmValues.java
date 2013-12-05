package com.askp_control.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IoAlgorithmValues {

	public static final String FILENAME_EXTERNAL_SCHEDULER = "/sys/block/mmcblk1/queue/scheduler";
	public static final String FILENAME_INTERNAL_SCHEDULER = "/sys/block/mmcblk0/queue/scheduler";
	public static final String FILENAME_TCP_CONGESTION = "/proc/sys/net/ipv4/tcp_available_congestion_control";

	public static String mCurExternalScheduler() {
		String[] x = IoAlgorithmValues.mExternalScheduler().split(" ");
		List<String> xList = new ArrayList<String>(Arrays.asList(x));
		for (int i = 0; i < xList.size(); i++) {
			if (xList.get(i).indexOf("[") != -1) {
				x[i] = x[i].replace("[", "").replace("]", "");
				return x[i];
			}
		}
		return "0";
	}

	public static String mCurInternalScheduler() {
		String[] x = IoAlgorithmValues.mInternalScheduler().split(" ");
		List<String> xList = new ArrayList<String>(Arrays.asList(x));
		for (int i = 0; i < xList.size(); i++) {
			if (xList.get(i).indexOf("[") != -1) {
				x[i] = x[i].replace("[", "").replace("]", "");
				return x[i];
			}
		}
		return "0";
	}

	public static String mExternalScheduler() {
		if (Utils.existFile(FILENAME_EXTERNAL_SCHEDULER))
			try {
				return Utils.readLine(FILENAME_EXTERNAL_SCHEDULER);
			} catch (IOException e) {
				e.printStackTrace();
			}
		return "0 0";
	}

	public static String mInternalScheduler() {
		if (Utils.existFile(FILENAME_INTERNAL_SCHEDULER))
			try {
				return Utils.readLine(FILENAME_INTERNAL_SCHEDULER);
			} catch (IOException e) {
				e.printStackTrace();
			}
		return "0 0";
	}

	public static String mTCPCongestion() {
		if (Utils.existFile(FILENAME_TCP_CONGESTION))
			try {
				return Utils.readLine(FILENAME_TCP_CONGESTION);
			} catch (IOException e) {
				e.printStackTrace();
			}
		return "0 0";
	}
}
