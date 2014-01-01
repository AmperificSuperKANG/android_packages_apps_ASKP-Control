package com.askp.control.fragments;

import com.askp.control.R;
import com.askp.control.utils.GetConnection;
import com.askp.control.utils.LayoutStyle;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class NewsFragment extends Fragment implements OnClickListener {

	private static Context context;

	private static LinearLayout mLayout;
	private static final String mNewsLink = DownloadFragment.mLink + "news";

	private static ProgressBar mProgress;

	private static Button mRefresh;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		View rootView = inflater.inflate(R.layout.news, container, false);

		mLayout = (LinearLayout) rootView.findViewById(R.id.layout);

		mRefresh = (Button) rootView.findViewById(R.id.refresh);
		LayoutStyle.setButton(mRefresh, getString(R.string.refresh),
				getActivity());
		mRefresh.setOnClickListener(this);

		refresh();
		return rootView;
	}

	public static void refresh() {
		GetConnection.mHtmlstring = "";
		mLayout.removeAllViews();
		mProgress = new ProgressBar(context);
		mRefresh.setVisibility(View.GONE);
		mLayout.addView(mProgress);

		GetConnection.getconnection(mNewsLink);
		new DisplayString().execute();
	}

	private static class DisplayString extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			return GetConnection.mHtmlstring;
		}

		@Override
		protected void onPostExecute(String result) {
			mRefresh.setVisibility(View.VISIBLE);
			mProgress.setVisibility(View.GONE);

			if (GetConnection.mHtmlstring.isEmpty()) {
				TextView mError = new TextView(context);
				LayoutStyle.setCenterText(mError,
						context.getString(R.string.nointernet));
				mError.setTextSize(20);
				mLayout.addView(mError);
			} else {
				String rawText = GetConnection.mHtmlstring;
				for (int i = 1; i < rawText.split("Date: ").length; i++) {
					TextView mDate = new TextView(context);
					LayoutStyle.setCenterText(mDate,
							rawText.split("Date: ")[i].split("Title: ")[0]);
					mDate.setTextSize(20);

					TextView mTitle = new TextView(context);
					LayoutStyle.setTextSubTitle(mTitle, "", context);
					LayoutStyle.setCenterText(mTitle,
							rawText.split("Date: ")[i].split("Title: ")[1]
									.split("Text: ")[0]);

					TextView mText = new TextView(context);
					LayoutStyle.setTextSummary(mText, "");
					LayoutStyle.setCenterText(mText, rawText.split("Date: ")[i]
							.split("Title: ")[1].split("Text: ")[1]);

					TextView mPlaceHolder = new TextView(context);

					mLayout.addView(mDate);
					mLayout.addView(mTitle);
					mLayout.addView(mText);
					if (i != rawText.split("Date: ").length - 1)
						mLayout.addView(mPlaceHolder);
				}
			}
		}
	}

	@Override
	public void onClick(View v) {
		if (v.equals(mRefresh))
			refresh();
	}
}
