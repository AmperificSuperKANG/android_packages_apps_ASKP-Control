package com.askp_control.Utils;

import java.io.IOException;

public class IoAlgorithmValues {

	public static final String FILENAME_TCP_CONGESTION = "/proc/sys/net/ipv4/tcp_available_congestion_control";

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
