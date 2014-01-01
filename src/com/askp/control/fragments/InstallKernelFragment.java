package com.askp.control.fragments;

import java.io.File;
import com.askp.control.R;
import com.askp.control.utils.LayoutStyle;
import com.askp.control.utils.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Environment;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class InstallKernelFragment extends Fragment {

	private static LinearLayout mLayout;
	private static String[] mFiles;
	private static ListAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mLayout = new LinearLayout(getActivity());
		Utils.mkdir(Environment.getExternalStorageDirectory() + "/askp-kernel");
		refresh(getActivity());
		return mLayout;
	}

	private static void refresh(final Context context) {
		mLayout.removeAllViews();

		ListView mList = new ListView(context);
		mList.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		File[] files = new File(Environment.getExternalStorageDirectory(),
				"/askp-kernel").listFiles();
		mFiles = new String[files.length];
		for (int i = 0; i < files.length; i++)
			mFiles[i] = files[i].getName();

		adapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_list_item_1, mFiles);
		mList.setAdapter(adapter);

		if (mFiles.length == 0) {
			TextView mNoFile = new TextView(context);
			LayoutStyle.setTextSubTitle(mNoFile,
					context.getString(R.string.nofiles), context);
			mLayout.addView(mNoFile);
		} else
			mLayout.addView(mList);

		mList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				installZip(mFiles[arg2], context);
			}
		});

		mList.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				deleteFile(mFiles[arg2], context);
				return true;
			}
		});
	}

	private static void installZip(final String file, Context context) {
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

	private static void deleteFile(final String file, final Context context) {
		AlertDialog.Builder mConfirm = new AlertDialog.Builder(context);
		mConfirm.setTitle(context.getString(R.string.deletefile))
				.setMessage(context.getString(R.string.confirmdelete, file))
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
								Utils.runCommand("rm -f /sdcard/askp-kernel/"
										+ file);
								refresh(context);
							}
						}).show();
	}
}
