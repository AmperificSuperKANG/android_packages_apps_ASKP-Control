package com.askp_control.Fragments;

import com.askp_control.Utils.Utils;
import com.example.askp_control.R;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class InformationFragment extends Fragment {

	private static TextView mDeviceText, mCodenameText, mKernelVersionText,
			mDevice, mCodename, mKernelVersion, mCurCpuFreq;

	private static final String mManufacturer = Build.MANUFACTURER;
	private static final String mModel = Build.MODEL;
	private static final String mCode = Build.DEVICE;

	private CurCPUThread mCurCPUThread;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater
				.inflate(R.layout.information, container, false);

		mDeviceText = (TextView) rootView.findViewById(R.id.devicetext);
		mDeviceText.setText(getString(R.string.device) + ": ");
		mCodenameText = (TextView) rootView.findViewById(R.id.codenametext);
		mCodenameText.setText(getString(R.string.codename) + ": ");
		mKernelVersionText = (TextView) rootView
				.findViewById(R.id.kernelversiontext);
		mKernelVersionText.setText(getString(R.string.kernelversion) + ": ");

		mDevice = (TextView) rootView.findViewById(R.id.device);
		mDevice.setText(mManufacturer.substring(0, 1).toUpperCase()
				+ mManufacturer.substring(1) + " " + mModel);
		mCodename = (TextView) rootView.findViewById(R.id.codename);
		mCodename.setText(mCode);
		mKernelVersion = (TextView) rootView.findViewById(R.id.kernelversion);
		mKernelVersion.setText(Utils.getFormattedKernelVersion());

		mCurCpuFreq = (TextView) rootView.findViewById(R.id.curcpufreq);
		return rootView;
	}

	@Override
	public void onResume() {
		mCurCPUThread = new CurCPUThread();
		mCurCPUThread.start();
		super.onResume();
	}

	protected class CurCPUThread extends Thread {
		@Override
		public void run() {
			try {
				while (true) {
					mCurCPUHandler.sendMessage(mCurCPUHandler.obtainMessage(0,
							Utils.mCurCpuFreq()));
					sleep(1000);
				}
			} catch (InterruptedException e) {
			}
		}
	}

	protected Handler mCurCPUHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (!String.valueOf(msg.obj).equals("Unavailable")) {
				int mFreq = Integer.parseInt(String.valueOf(msg.obj)) / 1000;
				mCurCpuFreq.setText(String.valueOf(mFreq) + " Mhz");
			}
		}
	};
}