/*
 * Copyright (C) 2013 AmperificSuperKANG Project
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 3 as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */

package com.askp.control.services;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.askp.control.R;
import com.askp.control.activities.MainActivity;
import com.askp.control.fragments.DownloadFragment;
import com.askp.control.fragments.InformationFragment;
import com.askp.control.utils.GetConnection;
import com.askp.control.utils.Utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;

public class OtaService extends Service {

	private static Context context;
	private static Handler handler = new Handler();
	private static Timer t;
	private static TimerTask timeTask;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		context = this;
		usingTimerTask();
		return Service.START_NOT_STICKY;
	}

	protected void usingTimerTask() {
		t = new Timer();
		timeTask = new TimerTask() {
			public void run() {
				handler.post(new Runnable() {
					public void run() {
						if (!Utils.getBoolean("otaupdates", true, context))
							stopSelf();

						GetConnection.mHtmlstring = "";
						GetConnection.getconnection(DownloadFragment.mLink
								+ InformationFragment.mModel.replace(" ", "")
										.toLowerCase());
						new DisplayString().execute();
					}
				});
			}
		};
		t.scheduleAtFixedRate(timeTask, new Date(), 1800000);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	private static class DisplayString extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			return GetConnection.mHtmlstring;
		}

		@Override
		protected void onPostExecute(String result) {
			if (!Utils.getString("kernelstrings", context).equals("nothing")
					&& !Utils.getString("kernelstrings", context).equals(
							GetConnection.mHtmlstring)
					&& !GetConnection.mHtmlstring.isEmpty()) {
				Utils.saveBoolean("otaupdates",
						!GetConnection.mHtmlstring.contains("Contact Support"),
						context);
				showNotification();
			}

			Utils.saveString("kernelstrings", GetConnection.mHtmlstring,
					context);
		}
	}

	@SuppressWarnings("deprecation")
	private static void showNotification() {
		NotificationManager notifyMgr = (NotificationManager) context
				.getSystemService(NOTIFICATION_SERVICE);
		int count = 0;

		Notification notifyObj = new Notification(R.drawable.ic_launcher,
				context.getString(R.string.app_name),
				System.currentTimeMillis());
		PendingIntent intent = PendingIntent.getActivity(context, 0,
				new Intent(context, MainActivity.class), 0);
		notifyObj.setLatestEventInfo(context,
				context.getString(R.string.app_name),
				context.getString(R.string.newkernelavailable), intent);
		notifyObj.number = ++count;
		notifyObj.flags |= Notification.FLAG_AUTO_CANCEL;
		notifyMgr.notify(2, notifyObj);
		MainActivity.selection = 5;
	}
}
