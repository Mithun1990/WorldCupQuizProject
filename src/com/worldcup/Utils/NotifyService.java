/**
 * 
 */
package com.worldcup.Utils;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.IBinder;
import android.util.Log;

import com.d3bugbd.worldcupQuiz.R;
import com.tourism.menudrawer.MainActivity;

public class NotifyService extends Service {

	Timer timer;
	TimerTask task;
	Intent toDoForNotification;
	PendingIntent pendingIntent;
	int i = 0;
	int log = 0;
	String title, id, date;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		Log.d("MyService", "Service Started...");

		toDoForNotification = new Intent(Intent.ACTION_MAIN);
		toDoForNotification.setClass(this, MainActivity.class);

		pendingIntent = PendingIntent.getActivity(getApplicationContext(),
				6565, toDoForNotification, 0);

		task = new TimerTask() {

			@Override
			public void run() {

				try {
					getMessage();
				} catch (Exception w) {

				}

			}
		};
		timer = new Timer();
		timer.scheduleAtFixedRate(task, 60000, 1200000);

	}

	public void getMessage() {

		JSONObject json = null;

		JSONParser jParser = new JSONParser();
		json = jParser.getJSONFromUrl("http://wcfifa.d3bug.com/notify.php");

		String rest = null;
		try {
			rest = json.getString("notify_message");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		JSONObject message = null;
		try {
			message = new JSONObject(rest);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (message != null) {
			try {

				title = message.getString("title");
				id = message.getString("id");
				date = message.getString("date");

				SharedPreferences pref = getApplicationContext()
						.getSharedPreferences("pref", 0);
				log = pref.getInt("notify_id", 0);

				try {

					if (Integer.parseInt(id) > log) {
						Editor editor = pref.edit();
						editor.putInt("notify_id", Integer.parseInt(id));
						editor.commit();
						showNotification(title);

					} else {

					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			} catch (JSONException e) { // TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("error");
			}
		}

	}

	@SuppressWarnings("deprecation")
	public void showNotification(String Message) {
		i++;
		int icon = R.drawable.ic_launcher;
		String tickerText = "World Cup Quiz Notification";
		String title = "World Cup Quiz Notification";
		String text = Message;
		long when = Calendar.getInstance().getTimeInMillis();

		Notification notification = new Notification(icon, tickerText, when);

		notification.setLatestEventInfo(NotifyService.this, title, text,
				pendingIntent);

		NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		manager.notify(i, notification);

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d("MyService", "Service Stopped...");
		timer.cancel();

	}

}
