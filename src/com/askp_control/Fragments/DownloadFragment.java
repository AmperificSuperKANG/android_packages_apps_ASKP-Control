package com.askp_control.Fragments;

import java.util.ArrayList;
import java.util.List;

import com.askp_control.R;
import com.askp_control.Utils.GetConnection;
import com.askp_control.Utils.Utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

public class DownloadFragment extends Fragment {

	private static Context context;

	private static final String mLink = "https://raw.github.com/AmperificSuperKANG/ASKP-Support/master/";
	private static LinearLayout mLayout;
	private static ProgressBar mProgress;
	private static ListView mListView;

	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.layout, container, false);
		context = getActivity();
		mLayout = (LinearLayout) rootView.findViewById(R.id.layout);
		mProgress = new ProgressBar(getActivity());
		mListView = new ListView(getActivity());
		Display display = getActivity().getWindowManager().getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();
		mListView.setLayoutParams(new LinearLayout.LayoutParams(width,
				height - 300));
		refresh();
		return rootView;
	}

	private static class DisplayString extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			return GetConnection.mHtmlstring;
		}

		@Override
		protected void onPostExecute(String result) {
			mProgress.setVisibility(View.GONE);

			String[] resultRaw = GetConnection.mHtmlstring.split(System
					.getProperty("line.separator"));

			List<String> valueNameList = new ArrayList<String>();
			for (int i = 0; i < resultRaw.length; i++) {
				valueNameList.add(resultRaw[i].split(": ")[0]);
			}

			final List<String> valueLinkList = new ArrayList<String>();
			for (int i = 0; i < resultRaw.length; i++) {
				valueLinkList.add(resultRaw[i].split(": ")[1]);
			}

			ListAdapter adapter = new ArrayAdapter<String>(context,
					android.R.layout.simple_list_item_1, valueNameList);

			mListView.setAdapter(adapter);

			mListView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					Utils.toast(valueLinkList.get(arg2), context);
				}
			});
		}
	}

	public static void refresh() {
		mLayout.addView(mProgress);
		mLayout.addView(mListView);
		GetConnection.getconnection(mLink
				+ InformationFragment.mModel.replace(" ", "").toLowerCase());
		DisplayString task = new DisplayString();
		task.execute();
	}
}
