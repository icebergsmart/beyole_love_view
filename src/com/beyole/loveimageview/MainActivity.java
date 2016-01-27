package com.beyole.loveimageview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.beyole.view.LoveView;
import com.beyole.view.LoveView.OnClickListener;

public class MainActivity extends Activity {

	private LoveView mLoveView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		mLoveView = (LoveView) findViewById(R.id.id_mylove);
		mLoveView.setMaxRate(7);
		mLoveView.setMinRate(4);
		mLoveView.setStepRate(0.08f);
		mLoveView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Toast.makeText(MainActivity.this, "I love you!", Toast.LENGTH_LONG).show();
				LoveView loveView = (LoveView) view;
				loveView.setColor(0x77ff0000);
			}
		});
	}

}
