package com.leontran.stadiumlt.slidingmenu.fragments;

import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.leontran.stadiumlt.CustomApplication;
import com.leontran.stadiumlt.R;
import com.leontran.stadiumlt.home.ListViewAdapterMainMenu;
import com.leontran.stadiumlt.home.ScreenMainActivity;
import com.leontran.stadiumlt.model.SlideMenuItem;

public class SlideMenuFragment extends ListFragment {

	ArrayList<SlideMenuItem> menuItemList = new ArrayList<SlideMenuItem>();
	CustomApplication app;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		app = (CustomApplication) getActivity().getApplication();
		if (app.getTyleLogin() ==0){
			parseXml(R.menu.slide_left);
		} else {
			parseXml(R.menu.slide_left);
		}
		
		ArrayList<SlideMenuItem> district = new ArrayList<SlideMenuItem>();
		for (int i = 0; i < menuItemList.size(); i++) {
			district.add(menuItemList.get(i));
			
		}
		ListViewAdapterMainMenu colorAdapter = new ListViewAdapterMainMenu(
				getActivity(), district);
		setListAdapter(colorAdapter);
		colorAdapter.notifyDataSetChanged();
	}

	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		SlideMenuItem item = menuItemList.get(position);
		int getId = item.getId();
		switchFragment(getId);
	}

	// the meat of switching the above fragment
	private void switchFragment(int filter) {
		if (getActivity() == null)
			return;

		if (getActivity() instanceof ScreenMainActivity) {
			ScreenMainActivity fca = (ScreenMainActivity) getActivity();
			fca.switchContent(filter);
		}
	}

	private void parseXml(int menu) {

		menuItemList = new ArrayList<SlideMenuItem>();

		// use 0 id to indicate no menu (as specified in JavaDoc)
		if (menu == 0)
			return;

		try {
			XmlResourceParser xpp = getActivity().getResources().getXml(menu);

			xpp.next();
			int eventType = xpp.getEventType();

			while (eventType != XmlPullParser.END_DOCUMENT) {

				if (eventType == XmlPullParser.START_TAG) {

					String elemName = xpp.getName();

					if (elemName.equals("item")) {

						String textId = xpp.getAttributeValue(
								"http://schemas.android.com/apk/res/android",
								"title");
						 String iconId = xpp.getAttributeValue(
						 "http://schemas.android.com/apk/res/android",
						 "icon");
						String resId = xpp.getAttributeValue(
								"http://schemas.android.com/apk/res/android",
								"id");

						SlideMenuItem item = new SlideMenuItem();
						item.id = Integer.valueOf(resId.replace("@", ""));
						 item.icon = getActivity().getResources().getDrawable(
						 Integer.valueOf(iconId.replace("@", "")));
						item.label = resourceIdToString(textId);

						menuItemList.add(item);
					}
				}
				eventType = xpp.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String resourceIdToString(String text) {
		if (!text.contains("@")) {
			return text;
		} else {
			String id = text.replace("@", "");
			return getActivity().getResources().getString(Integer.valueOf(id));

		}
	}

}
