package com.askp_control.Utils;

import android.content.Context;

import com.askp_control.Fragments.CpuFragment;
import com.askp_control.Fragments.IoAlgorithmFragment;

public class Control {

	public static String MAX_CPU_FREQ = String
			.valueOf(CpuFragment.mMaxCpuFreqRaw);

	public static String MIN_CPU_FREQ = String
			.valueOf(CpuFragment.mMinCpuFreqRaw);

	public static String MAX_SCREEN_OFF = String.valueOf(CpuValues
			.mMaxScreenOffFreq());

	public static String MIN_SCREEN_ON = String.valueOf(CpuValues
			.mMinScreenOnFreq());

	public static String MULTICORE_SAVING = String.valueOf(CpuValues
			.mMulticoreSaving());

	public static String TEMP_LIMIT = String.valueOf(CpuValues.mTempLimit());

	public static String GOVERNOR = CpuFragment.mCurGovernorRaw;

	public static String CORE = String.valueOf(CpuValues.mCore());

	public static String IVA = String.valueOf(CpuValues.mIVA());

	public static String MPU = String.valueOf(CpuValues.mMPU());

	public static String CORE_VOLTAGE = CpuValues.mCoreVoltagesFreq();

	public static String IVA_VOLTAGE = CpuValues.mIVAVoltagesFreq();

	public static String MPU_VOLTAGE = CpuValues.mMPUVoltagesFreqRaw();

	public static String REGULATOR_VOLTAGE = CpuValues
			.mRegulatorVoltagesFreqRaw();

	public static String GPU_VARIABLE = String.valueOf(GpuDisplayValues
			.mVariableGpu());

	public static String APAPTIVE_BRIGHTNESS = String.valueOf(GpuDisplayValues
			.mAdaptiveBrightness());

	public static String TRINITY_CONTRAST = String.valueOf(GpuDisplayValues
			.mTrinityContrast());

	public static String GAMMA_CONTROL = String.valueOf(GpuDisplayValues
			.mGammaControl());

	public static String GAMMA_OFFSET = GpuDisplayValues.mGammaOffset();

	public static String COLOR_MULTIPLIER = GpuDisplayValues.mColorMultiplier();

	public static String TCP_CONGESTION = IoAlgorithmValues.mTCPCongestion()
			.split(" ")[0];

	public static String INTERNAL_SCHEDULER = IoAlgorithmValues
			.mCurInternalScheduler();

	public static String EXTERNAL_SCHEDULER = IoAlgorithmValues
			.mCurExternalScheduler();

	public static String INTERNAL_READ = IoAlgorithmValues.mInternalRead();

	public static String EXTERNAL_READ = IoAlgorithmValues.mExternalRead();

	public static String WIFI_HIGH = MiscellaneousValues.mWifiHigh();

	public static String FAST_CHARGE = String.valueOf(MiscellaneousValues
			.mFastCharge());

	public static String BATTERY_EXTENDER = String.valueOf(MiscellaneousValues
			.mBatterExtender());

	public static String SOUND_HIGH = String.valueOf(MiscellaneousValues
			.mSoundHigh());

	public static String HEADPHONE_BOOST = String.valueOf(MiscellaneousValues
			.mHeadphoneBoost());

	public static String DYNAMIC_FSYNC = String.valueOf(MiscellaneousValues
			.mDynamicFsync());

	public static String FSYNC_CONTROL = String.valueOf(MiscellaneousValues
			.mFsyncControl());

	public static void setCpuValues(Context context) {
		// Max Cpu
		if (Utils.existFile(CpuValues.FILENAME_MAX_FREQ)) {
			Utils.runCommand("echo " + MAX_CPU_FREQ + " > "
					+ CpuValues.FILENAME_MAX_FREQ);
			Utils.saveString("maxcpuvalue", MAX_CPU_FREQ, context);
		}

		// Min Cpu
		if (Utils.existFile(CpuValues.FILENAME_MIN_FREQ)) {
			Utils.runCommand("echo " + MIN_CPU_FREQ + " > "
					+ CpuValues.FILENAME_MIN_FREQ);
			Utils.saveString("mincpuvalue", MIN_CPU_FREQ, context);
		}

		// Max Screen Off
		if (Utils.existFile(CpuValues.FILENAME_MAX_SCREEN_OFF)) {
			Utils.runCommand("echo " + MAX_SCREEN_OFF + " > "
					+ CpuValues.FILENAME_MAX_SCREEN_OFF);
			Utils.saveString("maxscreenoff", MAX_SCREEN_OFF, context);
		}

		// Min Screen On
		if (Utils.existFile(CpuValues.FILENAME_MIN_SCREEN_ON)) {
			Utils.runCommand("touch " + CpuValues.FILENAME_MIN_SCREEN_ON
					+ " && echo " + MIN_SCREEN_ON + " > "
					+ CpuValues.FILENAME_MIN_SCREEN_ON);
			Utils.saveString("minscreenon", MIN_SCREEN_ON, context);
		}

		// Multicore Saving
		if (Utils.existFile(CpuValues.FILENAME_MULTICORE_SAVING)) {
			Utils.runCommand("echo " + MULTICORE_SAVING + " > "
					+ CpuValues.FILENAME_MULTICORE_SAVING);
			Utils.saveString("multicoresaving", MULTICORE_SAVING, context);
		}

		// Temp Limit
		if (Utils.existFile(CpuValues.FILENAME_TEMP_LIMIT)) {
			Utils.runCommand("echo " + TEMP_LIMIT + " > "
					+ CpuValues.FILENAME_TEMP_LIMIT);
			Utils.saveString("templimit", TEMP_LIMIT, context);
		}

		// Governor
		if (Utils.existFile(CpuValues.FILENAME_CUR_GOVERNOR)) {
			Utils.runCommand("echo " + GOVERNOR + " > "
					+ CpuValues.FILENAME_CUR_GOVERNOR);
			Utils.saveString("governor", GOVERNOR, context);
			CpuFragment.mCurGovernorRaw = GOVERNOR;
		}

		// Smartreflex
		if (Utils.existFile(CpuValues.FILENAME_CORE))
			Utils.runCommand("echo " + CORE + " > " + CpuValues.FILENAME_CORE);
		Utils.saveString("core", CORE, context);

		if (Utils.existFile(CpuValues.FILENAME_IVA))
			Utils.runCommand("echo " + IVA + " > " + CpuValues.FILENAME_IVA);
		Utils.saveString("iva", IVA, context);

		if (Utils.existFile(CpuValues.FILENAME_MPU))
			Utils.runCommand("echo " + MPU + " > " + CpuValues.FILENAME_MPU);
		Utils.saveString("mpu", MPU, context);

		// Core Voltage
		if (Utils.existFile(CpuValues.FILENAME_CORE_VOLTAGES)) {
			Utils.runCommand("echo " + CORE_VOLTAGE + " > "
					+ CpuValues.FILENAME_CORE_VOLTAGES);
			Utils.saveString("corevoltage", CORE_VOLTAGE, context);
		}

		// IVA Voltage
		if (Utils.existFile(CpuValues.FILENAME_IVA_VOLTAGES)) {
			Utils.runCommand("echo " + IVA_VOLTAGE + " > "
					+ CpuValues.FILENAME_IVA_VOLTAGES);
			Utils.saveString("ivavoltage", IVA_VOLTAGE, context);
		}

		// MPU Voltage
		if (Utils.existFile(CpuValues.FILENAME_MPU_VOLTAGES)) {
			Utils.runCommand("echo " + MPU_VOLTAGE + " > "
					+ CpuValues.FILENAME_MPU_VOLTAGES);
			Utils.saveString("mpuvoltage", MPU_VOLTAGE, context);
		}

		// Regulator Voltage
		if (Utils.existFile(CpuValues.FILENAME_REGULATOR_VOLTAGES)) {
			Utils.runCommand("echo " + REGULATOR_VOLTAGE + " > "
					+ CpuValues.FILENAME_REGULATOR_VOLTAGES);
			Utils.saveString("regulatorvoltage", REGULATOR_VOLTAGE, context);
		}
	}

	public static void setGpuDisplayValues(Context context) {
		// Gpu Variable
		if (Utils.existFile(GpuDisplayValues.FILENAME_VARIABLE_GPU)) {
			Utils.runCommand("echo " + GPU_VARIABLE + " > "
					+ GpuDisplayValues.FILENAME_VARIABLE_GPU);
			Utils.saveString("gpuvariable", GPU_VARIABLE, context);
		}

		// Adaptive Brightness
		if (Utils.existFile(GpuDisplayValues.FILENAME_ADAPTIVE_BRIGHTNESS)) {
			Utils.runCommand("echo " + APAPTIVE_BRIGHTNESS + " > "
					+ GpuDisplayValues.FILENAME_ADAPTIVE_BRIGHTNESS);
			Utils.saveString("adaptivebrightness", APAPTIVE_BRIGHTNESS, context);
		}

		// Trinity Contrast
		if (Utils.existFile(GpuDisplayValues.FILENAME_TRINITY_CONTRAST)) {
			Utils.runCommand("echo " + TRINITY_CONTRAST + " > "
					+ GpuDisplayValues.FILENAME_TRINITY_CONTRAST);
			Utils.saveString("trinitycontrast", TRINITY_CONTRAST, context);
		}

		// Gamma Control
		if (Utils.existFile(GpuDisplayValues.FILENAME_GAMMA_CONTROL)) {
			Utils.runCommand("echo " + GAMMA_CONTROL + " > "
					+ GpuDisplayValues.FILENAME_GAMMA_CONTROL);
			Utils.saveString("gammacontrol", GAMMA_CONTROL, context);
		}

		// Gamma Offset
		if (Utils.existFile(GpuDisplayValues.FILENAME_GAMMA_OFFSET)) {
			Utils.runCommand("echo " + GAMMA_OFFSET + " > "
					+ GpuDisplayValues.FILENAME_GAMMA_OFFSET);
			Utils.saveString("gammaoffset", GAMMA_OFFSET, context);
		}

		// Color Multiplier
		if (Utils.existFile(GpuDisplayValues.FILENAME_COLOR_MULTIPLIER)) {
			Utils.runCommand("echo " + COLOR_MULTIPLIER + " > "
					+ GpuDisplayValues.FILENAME_COLOR_MULTIPLIER);
			Utils.saveString("colormultiplier", COLOR_MULTIPLIER, context);
		}
	}

	public static void setIoAlgorithmValues(Context context) {
		// TCP Congestion
		if (Utils.existFile(IoAlgorithmValues.FILENAME_TCP_CONGESTION)) {
			Utils.runCommand("sysctl -w net.ipv4.tcp_congestion_control="
					+ TCP_CONGESTION);
			Utils.saveString("tcpcongestion", TCP_CONGESTION, context);
			IoAlgorithmFragment.mTCPCongestion = IoAlgorithmFragment.mTCPCongestionRaw;
		}

		// Internal Scheduler
		if (Utils.existFile(IoAlgorithmValues.FILENAME_INTERNAL_SCHEDULER)) {
			Utils.runCommand("echo " + INTERNAL_SCHEDULER + " > "
					+ IoAlgorithmValues.FILENAME_INTERNAL_SCHEDULER);
			Utils.saveString("internalscheduler", INTERNAL_SCHEDULER, context);
			IoAlgorithmFragment.mCurInternalScheduler = IoAlgorithmFragment.mCurInternalSchedulerRaw;
		}

		// External Scheduler
		if (Utils.existFile(IoAlgorithmValues.FILENAME_EXTERNAL_SCHEDULER)) {
			Utils.runCommand("echo " + EXTERNAL_SCHEDULER + " > "
					+ IoAlgorithmValues.FILENAME_EXTERNAL_SCHEDULER);
			Utils.saveString("externalscheduler", EXTERNAL_SCHEDULER, context);
			IoAlgorithmFragment.mCurExternalScheduler = IoAlgorithmFragment.mCurExternalSchedulerRaw;
		}

		// Internal Read
		if (Utils.existFile(IoAlgorithmValues.FILENAME_INTERNAL_READ)) {
			Utils.runCommand("echo " + INTERNAL_READ + " > "
					+ IoAlgorithmValues.FILENAME_INTERNAL_READ);
			Utils.saveString("internalread", INTERNAL_READ, context);
		}

		// External Read
		if (Utils.existFile(IoAlgorithmValues.FILENAME_EXTERNAL_READ)) {
			Utils.runCommand("echo " + EXTERNAL_READ + " > "
					+ IoAlgorithmValues.FILENAME_EXTERNAL_READ);
			Utils.saveString("externalread", EXTERNAL_READ, context);
		}
	}

	public static void setMiscellaneousValues(Context context) {
		// Wifi High
		if (Utils.existFile(MiscellaneousValues.FILENAME_WIFI_HIGH)) {
			Utils.runCommand("echo " + WIFI_HIGH + " > "
					+ MiscellaneousValues.FILENAME_WIFI_HIGH);
			Utils.saveString("wifihigh", WIFI_HIGH, context);
		}

		// Fast Charge
		if (Utils.existFile(MiscellaneousValues.FILENAME_FAST_CHARGE)) {
			Utils.runCommand("echo " + FAST_CHARGE + " > "
					+ MiscellaneousValues.FILENAME_FAST_CHARGE);
			Utils.saveString("fastcharge", FAST_CHARGE, context);
		}

		// Battery Extender
		if (Utils.existFile(MiscellaneousValues.FILENAME_BATTERY_EXTENDER)) {
			Utils.runCommand("echo " + BATTERY_EXTENDER + " > "
					+ MiscellaneousValues.FILENAME_BATTERY_EXTENDER);
			Utils.saveString("batteryextender", BATTERY_EXTENDER, context);
		}

		// Sound High
		if (Utils.existFile(MiscellaneousValues.FILENAME_SOUND_HIGH)) {
			Utils.runCommand("echo " + SOUND_HIGH + " > "
					+ MiscellaneousValues.FILENAME_SOUND_HIGH);
			Utils.saveString("soundhigh", SOUND_HIGH, context);
		}

		// Headphone Boost
		if (Utils.existFile(MiscellaneousValues.FILENAME_HEADPHONE_BOOST)) {
			Utils.runCommand("echo " + HEADPHONE_BOOST + " > "
					+ MiscellaneousValues.FILENAME_HEADPHONE_BOOST);
			Utils.saveString("headphoneboost", HEADPHONE_BOOST, context);
		}

		// Dynamic Fsync
		if (Utils.existFile(MiscellaneousValues.FILENAME_DYNAMIC_FSYNC)) {
			Utils.runCommand("echo " + DYNAMIC_FSYNC + " > "
					+ MiscellaneousValues.FILENAME_DYNAMIC_FSYNC);
			Utils.saveString("dynamicfsync", DYNAMIC_FSYNC, context);
		}

		// Fsync Control
		if (Utils.existFile(MiscellaneousValues.FILENAME_FSYNC_CONTROL)) {
			Utils.runCommand("echo " + FSYNC_CONTROL + " > "
					+ MiscellaneousValues.FILENAME_FSYNC_CONTROL);
			Utils.saveString("fsynccontrol", FSYNC_CONTROL, context);
		}
	}

	public static void setValuesback(Context context) {
		MAX_CPU_FREQ = String.valueOf(CpuFragment.mMaxCpuFreqRaw);
		MIN_CPU_FREQ = String.valueOf(CpuFragment.mMinCpuFreqRaw);
		MAX_SCREEN_OFF = String.valueOf(CpuValues.mMaxScreenOffFreq());
		MIN_SCREEN_ON = String.valueOf(CpuValues.mMinScreenOnFreq());
		GOVERNOR = CpuFragment.mCurGovernorRaw;
		CORE = String.valueOf(CpuValues.mCore());
		IVA = String.valueOf(CpuValues.mIVA());
		MPU = String.valueOf(CpuValues.mMPU());
		CORE_VOLTAGE = CpuValues.mCoreVoltagesFreq();
		IVA_VOLTAGE = CpuValues.mIVAVoltagesFreq();
		MPU_VOLTAGE = CpuValues.mMPUVoltagesFreqRaw();
		REGULATOR_VOLTAGE = CpuValues.mRegulatorVoltagesFreqRaw();
		GPU_VARIABLE = String.valueOf(GpuDisplayValues.mVariableGpu());
		APAPTIVE_BRIGHTNESS = String.valueOf(GpuDisplayValues
				.mAdaptiveBrightness());
		TRINITY_CONTRAST = String.valueOf(GpuDisplayValues.mTrinityContrast());
		GAMMA_CONTROL = String.valueOf(GpuDisplayValues.mGammaControl());
		GAMMA_OFFSET = GpuDisplayValues.mGammaOffset();
		COLOR_MULTIPLIER = GpuDisplayValues.mColorMultiplier();
		TCP_CONGESTION = IoAlgorithmValues.mTCPCongestion().split(" ")[0];
		INTERNAL_SCHEDULER = IoAlgorithmValues.mCurInternalScheduler();
		EXTERNAL_SCHEDULER = IoAlgorithmValues.mCurExternalScheduler();
		INTERNAL_READ = IoAlgorithmValues.mInternalRead();
		EXTERNAL_READ = IoAlgorithmValues.mExternalRead();
		WIFI_HIGH = MiscellaneousValues.mWifiHigh();
		FAST_CHARGE = String.valueOf(MiscellaneousValues.mFastCharge());
		BATTERY_EXTENDER = String
				.valueOf(MiscellaneousValues.mBatterExtender());
		SOUND_HIGH = String.valueOf(MiscellaneousValues.mSoundHigh());
		HEADPHONE_BOOST = String.valueOf(MiscellaneousValues.mHeadphoneBoost());
		DYNAMIC_FSYNC = String.valueOf(MiscellaneousValues.mDynamicFsync());
		FSYNC_CONTROL = String.valueOf(MiscellaneousValues.mFsyncControl());
	}
}
