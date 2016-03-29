package com.kelvin.banksecurety;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity
{
	private static final long TIME_SHOW_SPLASH = 1000l;
	private static final long TIME_SLEEP = 100l;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		_load();
	}

	private void _load()
	{
		Thread logoTimer = new Thread()
		{
			public void run()
			{
				try {
					long logoTimer = 0l;
					while (logoTimer < TIME_SHOW_SPLASH) {
						sleep(TIME_SLEEP);
						logoTimer = logoTimer + 100;
					}
					startActivity(new Intent(getResources().getString(R.string.action_main)));
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					finish();
				}
			}
		};

		logoTimer.start();
	}
}