package com.leontran.stadiumlt.slidingmenu.anim;

import android.os.Bundle;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.CanvasTransformer;
import com.leontran.stadiumlt.R;
import com.leontran.stadiumlt.slidingmenu.BaseActivity;
import com.leontran.stadiumlt.slidingmenu.SampleListFragment;

public abstract class CustomAnimation extends BaseActivity {
	
	private CanvasTransformer mTransformer;
	
	public CustomAnimation(int titleRes, CanvasTransformer transformer) {
		super(titleRes);
		mTransformer = transformer;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set the Above View
		setContentView(R.layout.content_frame);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, new SampleListFragment())
		.commit();
		
		SlidingMenu sm = getSlidingMenu();
		setSlidingActionBarEnabled(true);
		sm.setBehindScrollScale(0.0f);
		sm.setBehindCanvasTransformer(mTransformer);
	}

}
