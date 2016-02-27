package com.mx3.knyghtryder;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MainActivity extends ActionBarActivity  {

	private HashMap<String, TextView> vehicleDataLabels;
	private ArrayList<String> vehicleDataKeys;

	private static Thread backgroundUpdater;

	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	private GoogleApiClient client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

		vehicleDataLabels = new HashMap<>();

		vehicleDataLabels.put("rpm", (TextView) findViewById(R.id.rpmValue));
		vehicleDataLabels.put("speed", (TextView)findViewById(R.id.speedValue));

		vehicleDataKeys = new ArrayList<String>(Arrays.asList(new String[]{
			"rpm",
			"speed"
		}));

		if(backgroundUpdater == null) {
			backgroundUpdater = new Thread(new VehicleDataUpdater(this));
			backgroundUpdater.start();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onStart() {
		super.onStart();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client.connect();
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"Main Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app deep link URI is correct.
				Uri.parse("android-app://com.mx3.knyghtryder/http/host/path")
		);
		AppIndex.AppIndexApi.start(client, viewAction);
	}

	@Override
	public void onStop() {
		super.onStop();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"Main Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app deep link URI is correct.
				Uri.parse("android-app://com.mx3.knyghtryder/http/host/path")
		);
		AppIndex.AppIndexApi.end(client, viewAction);
		client.disconnect();
	}

	class VehicleDataUpdater implements  Runnable
	{
		private SdlService sdlService;
		private MainActivity mainActivity;
		public boolean run;

		public VehicleDataUpdater(MainActivity a)
		{
			mainActivity = a;
			run = true;
		}

		public boolean updateService()
		{
			if(sdlService == null)
			{
				sdlService = SdlService.getInstance();
			}

			return (sdlService != null);
		}

		public void run()
		{
			try {
				while(run) {
					Thread.sleep(500);
					if (!updateService()) {
						continue;
					}

					MainActivity.this.runOnUiThread(new Runnable() {
						public void run() {
							updateLabels();
						}
					});
				}
			} catch (InterruptedException e) {}
		}

		private void updateLabels()
		{
			try {
				for(String key : vehicleDataKeys)
				{
					updateLabel(key);
				}
			}
			catch(Exception e){}
		}

		private void updateLabel(String key)
		{
			vehicleDataLabels.get(key).setText(SdlService.getInstance().vehicleData.get(key));
		}
	}
}
