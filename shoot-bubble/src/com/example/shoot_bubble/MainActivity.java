package com.example.shoot_bubble;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;

public class MainActivity extends Activity implements OnTouchListener {

	View v;
	Bitmap ball;
	float x, y;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ball = BitmapFactory
				.decodeResource(getResources(), R.drawable.bubble_1);
		x = y = 50;
		v = new View(this, ball, x, y);
		v.setOnTouchListener(this);
		setContentView(v);
	}

	@Override
	protected void onPause() {
		super.onPause();
		v.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		v.resume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onTouch(android.view.View v, MotionEvent me) {
		// TODO Auto-generated method stub
		return false;
	}
}
