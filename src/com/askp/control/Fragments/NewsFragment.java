package com.askp.control.Fragments;

import com.askp.control.R;
import com.askp.control.Utils.GetConnection;
import com.askp.control.Utils.LayoutStyle;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class NewsFragment extends Fragment {

	private static Context context;

	private static LinearLayout mLayout;
	private static final String mNewsLink = DownloadFragment.mLink + "news";

	private static ProgressBar mProgress;

	private static TextView mDate;
	private static TextView mTitle;
	private static TextView mText;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.layout, container, false);
		context = getActivity();

		mLayout = (LinearLayout) rootView.findViewById(R.id.layout);

		mProgress = new ProgressBar(getActivity());
		mLayout.addView(mProgress);

		mDate = new TextView(getActivity());
		mTitle = new TextView(getActivity());
		mText = new TextView(getActivity());

		GetConnection.getconnection(mNewsLink);
		DisplayString task = new DisplayString();
		task.execute();

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

			if (GetConnection.mHtmlstring.isEmpty()) {
				LayoutStyle.setCenterText(mText,
						context.getString(R.string.nointernet), context);
				mText.setTextSize(20);
				mLayout.addView(mText);
			} else {
				String rawText = GetConnection.mHtmlstring;
				String mDateText = rawText.split("Title: ")[0].replace(
						"Date: ", "");
				LayoutStyle.setTextTitle(mDate, mDateText, context);
				mLayout.addView(mDate);

				String mTitleText = rawText.split("Title: ")[1].split("Text: ")[0];
				LayoutStyle.setTextSubTitle(mTitle, mTitleText, context);
				mLayout.addView(mTitle);

				String mTextText = rawText.split("Title: ")[1].split("Text: ")[1];
				LayoutStyle.setTextSummary(mText, mTextText, context);
				mLayout.addView(mText);
			}
		}
	}
}
