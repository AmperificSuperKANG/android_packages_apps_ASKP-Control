package com.askp.control.Fragments;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.askp.control.R;
import com.askp.control.Utils.LayoutStyle;
import com.askp.control.Utils.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class InstallKernelFragment extends Fragment {

	private List<String> fileList = new ArrayList<String>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LinearLayout mLayout = new LinearLayout(getActivity());
		Utils.mkdir(Environment.getExternalStorageDirectory() + "/askp-kernel");

		ListView mList = new ListView(getActivity());
		mList.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		mLayout.addView(mList);

		File root = new File(Environment.getExternalStorageDirectory(),
				"/askp-kernel");

		File[] files = root.listFiles();
		for (int i = 0; i < files.length; i++)
			fileList.add(files[i].getName());

		ListAdapter adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, fileList);
		mList.setAdapter(adapter);
		if (fileList.size() == 0) {
			TextView mNoFile = new TextView(getActivity());
			LayoutStyle.setTextSubTitle(mNoFile, getString(R.string.nofiles),
					getActivity());
			mLayout.removeAllViews();
			mLayout.addView(mNoFile);
		}

		mList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				installZip(fileList.get(arg2), getActivity());
			}
		});
		return mLayout;
	}

	public static void installZip(final String file, Context context) {
		AlertDialog.Builder mConfirm = new AlertDialog.Builder(context);
		mConfirm.setTitle(context.getString(R.string.installkernel))
				.setMessage(context.getString(R.string.confirminstall, file))
				.setNegativeButton(context.getString(android.R.string.cancel),
						new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
							}
						})
				.setPositiveButton(context.getString(android.R.string.ok),
						new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Utils.runCommand("echo install /sdcard/askp-kernel/"
										+ file
										+ " > /cache/recovery/openrecoveryscript && reboot recovery");
							}
						}).show();
	}
}
