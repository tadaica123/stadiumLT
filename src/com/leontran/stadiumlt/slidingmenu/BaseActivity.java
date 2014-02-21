package com.leontran.stadiumlt.slidingmenu;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.leontran.stadiumlt.CustomApplication;
import com.leontran.stadiumlt.R;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;

public class BaseActivity extends SlidingFragmentActivity {

	private int mTitleRes;
	protected ListFragment mFrag;
	CustomApplication apps;
	int menuResource;

	public BaseActivity(int titleRes , int resource) {
		mTitleRes = titleRes;
		menuResource = resource;
	}
	
	public BaseActivity(int titleRes ) {
		mTitleRes = titleRes;
	}
	
	public int getSlidingMenuResource(){
		return menuResource;
	}
	
	public void setSlidingMenuResource(int resource){
		 menuResource = resource;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle(mTitleRes);
		apps = (CustomApplication) getApplication();
		
		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		if (savedInstanceState == null) {
			FragmentTransaction t = this.getSupportFragmentManager()
					.beginTransaction();
			mFrag = new SampleListFragment();
			t.replace(R.id.menu_frame, mFrag);
			t.commit();
		} else {
			mFrag = (ListFragment) this.getSupportFragmentManager()
					.findFragmentById(R.id.menu_frame);
		}
   
		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu(); 
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		
	}

}
