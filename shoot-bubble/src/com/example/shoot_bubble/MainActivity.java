package com.example.shoot_bubble;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;

public class MainActivity extends Activity implements OnTouchListener {

	View gameView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		gameView.setOnTouchListener(this);
		
		gameView = new View(this);
		setContentView(gameView);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
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
