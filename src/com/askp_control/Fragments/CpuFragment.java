package com.askp_control.Fragments;

import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.askp_control.MainActivity;
import com.askp_control.R;
import com.askp_control.Utils.Control;
import com.askp_control.Utils.CpuValues;
import com.askp_control.Utils.Utils;

public class CpuFragment extends Fragment implements OnSeekBarChangeListener,
		OnItemSelectedListener, OnCheckedChangeListener {

	private static TextView mCurCpuFreq, mMaxCpuFreq, mMinCpuFreq,
			mMaxScreenOff, mMinScreenOn;

	private static CurCPUThread mCurCPUThread;

	private static SeekBar mMaxCpuFreqBar, mMinCpuFreqBar, mMaxScreenOffBar,
			mMinScreenOnBar;

	private static String[] mAvailableFreq;
	private static List<String> mAvailableFreqList;

	public static int mMinCpuFreqRaw, mMaxCpuFreqRaw;

	private static String mMaxFreqValue, mMinFreqValue, mMaxScreenOffValue,
			mMinScreenOnValue;

	private static Spinner mGovernor;
	private static String[] mAvailableGovernor;
	private static List<String> mAvailableGovernorList;
	public static String mCurGovernorRaw;

	private static TextView mSmartreflextext, mSmartreflexSummaryText;
	private static CheckBox mCore, mIVA, mMPU;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.cpu, container, false);

		// Current Freq
		mCurCpuFreq = (TextView) rootView.findViewById(R.id.curcpufreq);

		// Freq scaling
		mMaxCpuFreq = (TextView) rootView.findViewById(R.id.curmaxcpufreq);
		mMaxCpuFreq.setText(getActivity().getString(R.string.curmaxfreq) + ": "
				+ String.valueOf(mMaxCpuFreqRaw / 1000) + " MHz");
		mMaxFreqValue = String.valueOf(mMaxCpuFreqRaw);

		mMinCpuFreq = (TextView) rootView.findViewById(R.id.curmincpufreq);
		mMinCpuFreq.setText(getActivity().getString(R.string.curminfreq) + ": "
				+ String.valueOf(mMinCpuFreqRaw / 1000) + " MHz");
		mMinFreqValue = String.valueOf(mMinCpuFreqRaw);

		mAvailableFreq = CpuValues.mAvailableFreq().split(" ");

		mAvailableFreqList = Arrays.asList(mAvailableFreq);
		int mMax = mAvailableFreqList.indexOf(String.valueOf(mMaxCpuFreqRaw));
		int mMin = mAvailableFreqList.indexOf(String.valueOf(mMinCpuFreqRaw));

		mMaxCpuFreqBar = (SeekBar) rootView.findViewById(R.id.maxcpuseekbar);
		mMaxCpuFreqBar.setMax(mAvailableFreq.length - 1);
		mMaxCpuFreqBar.setProgress(mMax);
		mMaxCpuFreqBar.setOnSeekBarChangeListener(this);

		mMinCpuFreqBar = (SeekBar) rootView.findViewById(R.id.mincpuseekbar);
		mMinCpuFreqBar.setMax(mAvailableFreq.length - 1);
		mMinCpuFreqBar.setProgress(mMin);
		mMinCpuFreqBar.setOnSeekBarChangeListener(this);

		mMaxScreenOff = (TextView) rootView.findViewById(R.id.maxscreenofftext);
		mMaxScreenOffBar = (SeekBar) rootView
				.findViewById(R.id.maxscreenoffseekbar);

		if (!CpuValues.mMaxScreenOffFreqFile.exists()) {
			mMaxScreenOff.setVisibility(View.GONE);
			mMaxScreenOffBar.setVisibility(View.GONE);
		} else {
			if (CpuValues.mMaxScreenOffFreq() == 0)
				Utils.runCommand("echo " + mAvailableFreq[0] + " > "
						+ CpuValues.FILENAME_MAX_SCREEN_OFF);

			mMaxScreenOff.setText(getString(R.string.maxscreenoff) + ": "
					+ String.valueOf(CpuValues.mMaxScreenOffFreq() / 1000)
					+ " MHz");

			mMaxScreenOffValue = String.valueOf(CpuValues.mMaxScreenOffFreq());

			int mMaxScreenOff = mAvailableFreqList.indexOf(mMaxScreenOffValue);
			mMaxScreenOffBar.setMax(mAvailableFreq.length - 1);
			mMaxScreenOffBar.setProgress(mMaxScreenOff);
			mMaxScreenOffBar.setOnSeekBarChangeListener(this);
		}

		mMinScreenOn = (TextView) rootView.findViewById(R.id.minscreenontext);
		mMinScreenOnBar = (SeekBar) rootView
				.findViewById(R.id.minscreenonseekbar);

		if (!CpuValues.mMinScreenOnFreqFile.exists()) {
			mMinScreenOn.setVisibility(View.GONE);
			mMinScreenOnBar.setVisibility(View.GONE);
		} else {

			mMinScreenOn.setText(getString(R.string.minscreenon) + ": "
					+ String.valueOf(CpuValues.mMinScreenOnFreq() / 1000)
					+ " MHz");

			mMinScreenOnValue = String.valueOf(CpuValues.mMinScreenOnFreq());

			int mMinScreenOn = mAvailableFreqList.indexOf(mMinScreenOnValue);
			mMinScreenOnBar.setMax(mAvailableFreq.length - 1);
			mMinScreenOnBar.setProgress(mMinScreenOn);
			mMinScreenOnBar.setOnSeekBarChangeListener(this);
		}

		// Governor
		mAvailableGovernor = CpuValues.mAvailableGovernor().split(" ");
		mAvailableGovernorList = Arrays.asList(mAvailableGovernor);
		int mCurGovernor = mAvailableGovernorList.indexOf(mCurGovernorRaw);

		mGovernor = (Spinner) rootView.findViewById(R.id.governor_spinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, mAvailableGovernor);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mGovernor.setAdapter(adapter);
		mGovernor.setSelection(mCurGovernor);
		mGovernor.setOnItemSelectedListener(this);

		mCore = (CheckBox) rootView.findViewById(R.id.corebox);
		mIVA = (CheckBox) rootView.findViewById(R.id.ivabox);
		mMPU = (CheckBox) rootView.findViewById(R.id.mpubox);

		if (!CpuValues.mCoreFile.exists())
			mCore.setVisibility(View.GONE);
		if (!CpuValues.mIVAFile.exists())
			mIVA.setVisibility(View.GONE);
		if (!CpuValues.mMPUFile.exists())
			mMPU.setVisibility(View.GONE);

		if (!CpuValues.mCoreFile.exists() && !CpuValues.mIVAFile.exists()
				&& !CpuValues.mMPUFile.exists()) {
			mSmartreflextext = (TextView) rootView
					.findViewById(R.id.smartreflextext);
			mSmartreflextext.setVisibility(View.GONE);
			mSmartreflexSummaryText = (TextView) rootView
					.findViewById(R.id.smartreflex_summarytext);
			mSmartreflexSummaryText.setVisibility(View.GONE);
		}

		mCore.setChecked(CpuValues.mCore());
		mCore.setOnCheckedChangeListener(this);
		mIVA.setChecked(CpuValues.mIVA());
		mIVA.setOnCheckedChangeListener(this);
		mMPU.setChecked(CpuValues.mMPU());
		mMPU.setOnCheckedChangeListener(this);

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
							CpuValues.mCurCpuFreq()));
					sleep(1000);
				}
			} catch (InterruptedException e) {
			}
		}
	}

	protected Handler mCurCPUHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (!String.valueOf(msg.obj).equals(0)) {
				mCurCpuFreq
						.setText(String.valueOf(CpuValues.mCurCpuFreq() / 1000)
								+ " Mhz");
			}
		}
	};

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		if (seekBar.equals(mMaxCpuFreqBar)) {
			mMaxCpuFreq
					.setText(getActivity().getString(R.string.curmaxfreq)
							+ ": "
							+ String.valueOf(Integer
									.parseInt(mAvailableFreq[progress]) / 1000)
							+ " MHz");
			mMaxFreqValue = mAvailableFreq[progress];
			if (Integer.parseInt(mMaxFreqValue) < Integer
					.parseInt(mMinFreqValue)) {
				mMaxCpuFreqBar.setProgress(progress);
				mMinCpuFreqBar.setProgress(progress);
			}
		} else if (seekBar.equals(mMinCpuFreqBar)) {
			mMinCpuFreq
					.setText(getActivity().getString(R.string.curminfreq)
							+ ": "
							+ String.valueOf(Integer
									.parseInt(mAvailableFreq[progress]) / 1000)
							+ " MHz");
			mMinFreqValue = mAvailableFreq[progress];
			if (Integer.parseInt(mMaxFreqValue) < Integer
					.parseInt(mMinFreqValue)) {
				mMaxCpuFreqBar.setProgress(progress);
				mMinCpuFreqBar.setProgress(progress);
			}
		} else if (seekBar.equals(mMaxScreenOffBar)) {
			mMaxScreenOff
					.setText(getActivity().getString(R.string.maxscreenoff)
							+ ": "
							+ String.valueOf(Integer
									.parseInt(mAvailableFreq[progress]) / 1000)
							+ " MHz");
			mMaxScreenOffValue = mAvailableFreq[progress];
		} else if (seekBar.equals(mMinScreenOnBar)) {
			mMinScreenOn
					.setText(getActivity().getString(R.string.minscreenon)
							+ ": "
							+ String.valueOf(Integer
									.parseInt(mAvailableFreq[progress]) / 1000)
							+ " MHz");
			mMinScreenOnValue = mAvailableFreq[progress];
		}

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		MainActivity.mChange = true;
		if (seekBar.equals(mMaxCpuFreqBar)) {
			Control.MAX_CPU_FREQ = mMaxFreqValue;
		} else if (seekBar.equals(mMinCpuFreqBar)) {
			Control.MIN_CPU_FREQ = mMinFreqValue;
		} else if (seekBar.equals(mMaxScreenOffBar)) {
			Control.MAX_SCREEN_OFF = mMaxScreenOffValue;
		} else if (seekBar.equals(mMinScreenOnBar)) {
			Control.MIN_SCREEN_ON = mMinScreenOnValue;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		if (arg0.equals(mGovernor)) {
			int mCurGovernor = mAvailableGovernorList.indexOf(mCurGovernorRaw);
			if (arg2 != mCurGovernor) {
				MainActivity.mChange = true;
				Control.GOVERNOR = mAvailableGovernor[arg2];
			}
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		MainActivity.mChange = true;
		if (buttonView.equals(mCore)) {
			Control.CORE = isChecked;
		} else if (buttonView.equals(mIVA)) {
			Control.IVA = isChecked;
		} else if (buttonView.equals(mMPU)) {
			Control.MPU = isChecked;
		}
	}
}