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

package com.askp.control.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.os.AsyncTask;
import android.text.Html;

public class GetConnection {

	public static String mHtmlstring = "";

	public static void getconnection(String url) {
		new RequestTask().execute(url);
	}

	private static class RequestTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... url) {
			int timeoutSocket = 5000;
			int timeoutConnection = 5000;

			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters,
					timeoutConnection);
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
			HttpClient client = new DefaultHttpClient(httpParameters);

			HttpGet httpget = new HttpGet(url[0]);

			try {
				HttpResponse getResponse = client.execute(httpget);
				final int statusCode = getResponse.getStatusLine()
						.getStatusCode();

				if (statusCode != HttpStatus.SC_OK)
					return null;

				String line = "";
				StringBuilder total = new StringBuilder();

				HttpEntity getResponseEntity = getResponse.getEntity();

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(getResponseEntity.getContent()));

				while ((line = reader.readLine()) != null)
					total.append(line);

				line = total.toString();
				return line;
			} catch (Exception e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			mHtmlstring = Html.fromHtml(result).toString();
		}
	}
}
