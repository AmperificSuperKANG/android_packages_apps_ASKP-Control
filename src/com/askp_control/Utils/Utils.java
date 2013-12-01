package com.askp_control.Utils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.exceptions.RootDeniedException;
import com.stericson.RootTools.execution.CommandCapture;

import android.content.Context;
import android.widget.Toast;

public class Utils {

	public static void toast(String text, Context context) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	public static void runCommand(String run) {
		try {
			RootTools.getShell(true).add(new CommandCapture(0, run))
					.commandCompleted(0, 0);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (RootDeniedException e) {
			e.printStackTrace();
		}
	}
}
