package com.askp_control.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.askp_control.Fragments.CpuFragment;
import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.exceptions.RootDeniedException;
import com.stericson.RootTools.execution.Command;

public class CpuValues {

	private static final String FILENAME_AVAILABLE_FREQ = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_available_frequencies";
	public static final String FILENAME_MAX_FREQ = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq";
	public static final String FILENAME_MIN_FREQ = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq";
	private static final String FILENAME_CUR_CPU_FREQ = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq";
	private static final String FILENAME_PROC_VERSION = "/proc/version";

	private static final File mCurCpuFreqFile = new File(FILENAME_CUR_CPU_FREQ);
	private static final File mMaxFreqFile = new File(FILENAME_MAX_FREQ);

	public static String mAvailableFreq() {
		try {
			return readLine(FILENAME_AVAILABLE_FREQ);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int mMaxFreq() {
		if (mMaxFreqFile.exists()) {
			try {
				return Integer.parseInt(readLine(FILENAME_MAX_FREQ));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	public static void mMinFreq() {
		Command command = new Command(0, "cat " + FILENAME_MIN_FREQ) {
			@Override
			public void commandCompleted(int arg0, int arg1) {
			}

			@Override
			public void commandOutput(int arg0, String arg1) {
				CpuFragment.mMinCpuFreqRaw = Integer.parseInt(arg1);
			}

			@Override
			public void commandTerminated(int arg0, String arg1) {
			}
		};
		try {
			RootTools.getShell(true).add(command);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (RootDeniedException e) {
			e.printStackTrace();
		}
	}

	public static int mCurCpuFreq() {
		if (mCurCpuFreqFile.exists()) {
			try {
				return Integer.parseInt(readLine(FILENAME_CUR_CPU_FREQ)) / 1000;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	/**
	 * Reads a line from the specified file.
	 * 
	 * @param filename
	 *            the file to read from
	 * @return the first line, if any.
	 * @throws IOException
	 *             if the file couldn't be read
	 */
	private static String readLine(String filename) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filename),
				256);
		try {
			return reader.readLine();
		} finally {
			reader.close();
		}
	}

	public static String getFormattedKernelVersion() {
		try {
			return formatKernelVersion(readLine(FILENAME_PROC_VERSION));
		} catch (IOException e) {
			return "Unavailable";
		}
	}

	private static String formatKernelVersion(String rawKernelVersion) {
		// Example (see tests for more):
		// Linux version 3.0.31-g6fb96c9 (android-build@xxx.xxx.xxx.xxx.com) \
		// (gcc version 4.6.x-xxx 20120106 (prerelease) (GCC) ) #1 SMP PREEMPT \
		// Thu Jun 28 11:02:39 PDT 2012

		final String PROC_VERSION_REGEX = "Linux version (\\S+) " + /*
																	 * group 1:
																	 * "3.0.31-g6fb96c9"
																	 */
		"\\((\\S+?)\\) " + /* group 2: "x@y.com" (kernel builder) */
		"(?:\\(gcc.+? \\)) " + /* ignore: GCC version information */
		"(#\\d+) " + /* group 3: "#1" */
		"(?:.*?)?" + /* ignore: optional SMP, PREEMPT, and any CONFIG_FLAGS */
		"((Sun|Mon|Tue|Wed|Thu|Fri|Sat).+)"; /*
											 * group 4:
											 * "Thu Jun 28 11:02:39 PDT 2012"
											 */

		Matcher m = Pattern.compile(PROC_VERSION_REGEX).matcher(
				rawKernelVersion);
		if (!m.matches() || m.groupCount() < 4) {
			return "Unavailable";
		}
		return m.group(1) + "\n" + // 3.0.31-g6fb96c9
				m.group(2) + " " + m.group(3) + "\n" + // x@y.com #1
				m.group(4); // Thu Jun 28 11:02:39 PDT 2012
	}

}
