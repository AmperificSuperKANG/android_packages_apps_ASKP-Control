package com.askp_control;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

public class CancelActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AlertDialog.Builder cancelAlert = new AlertDialog.Builder(this);
		cancelAlert
				.setTitle(getString(R.string.app_name))
				.setMessage(getString(R.string.abort_setonboot))
				.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						finish();
					}
				})
				.setNegativeButton(getString(android.R.string.no),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								finish();
							}
						})
				.setPositiveButton(getString(android.R.string.yes),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								BootReceiver.cancelSetValues();
								finish();
							}
						}).show();
	}
}
