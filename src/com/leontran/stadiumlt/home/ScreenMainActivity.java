package com.leontran.stadiumlt.home;

import java.util.ArrayList;

import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.leontran.stadiumlt.CustomApplication;
import com.leontran.stadiumlt.CustomProgressDialog;
import com.leontran.stadiumlt.LoginActivity;
import com.leontran.stadiumlt.R;
import com.leontran.stadiumlt.gson.GetStadiumDetailGson;
import com.leontran.stadiumlt.guest.GuestShowDetailsStadium;
import com.leontran.stadiumlt.model.DistrictModel;
import com.leontran.stadiumlt.model.Map;
import com.leontran.stadiumlt.model.PriceModel;
import com.leontran.stadiumlt.model.StadiumDetailModel;
import com.leontran.stadiumlt.model.StadiumNumberModel;
import com.leontran.stadiumlt.network.ServiceHandler;
import com.leontran.stadiumlt.network.Services;
import com.leontran.stadiumlt.owner.ScreenAddNewStadium;

import com.leontran.stadiumlt.slidingmenu.BaseActivity;
import com.leontran.stadiumlt.slidingmenu.fragments.DistrictFragment;
import com.leontran.stadiumlt.slidingmenu.fragments.DistrictMenuFragment;
import com.leontran.stadiumlt.slidingmenu.fragments.SlideMenuFragment;
import com.leontran.stadiumlt.wiget.RefreshableListView;
import com.leontran.stadiumlt.wiget.RefreshableListView.OnUpdateTask;

public class ScreenMainActivity extends BaseActivity {

	private Fragment mContent;

	public ScreenMainActivity() {
		super(R.string.changing_fragments);
	}

	private CustomApplication app;
	private RefreshableListView listviewMain;
	private ListViewAdapterMain adapter;
	private ArrayList<StadiumDetailModel> listStadiumData;

	private TextView tvTitle;
	private LinearLayout btnLeft;
	private LinearLayout btnRight;

	private int checkshow = 0;

	private CustomProgressDialog dialog;

	public void initComponent() {
		listviewMain = (RefreshableListView) findViewById(R.id.listview);

		tvTitle = (TextView) findViewById(R.id.txt_title);
		btnLeft = (LinearLayout) findViewById(R.id.layout_button_left);
		btnRight = (LinearLayout) findViewById(R.id.layout_button_right);

		dialog = new CustomProgressDialog(ScreenMainActivity.this);
	}

	public void initListener() {
		listviewMain.setOnUpdateTask(new OnUpdateTask() {

			@Override
			public void updateBackground() {

				String url = app.getLoginServer()
						+ getResources()
								.getString(R.string.Server_Get_All_Data);
				Log.d("Url login", url);
				String json = null;

				try {

					try {
						Services service = new Services();
						json = service.doGet(url);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} catch (NotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.d("json", json);
				ServiceHandler service = new ServiceHandler();
				GetStadiumDetailGson response = service.getStadiumData(json);

				if (response != null) {
					listStadiumData = new ArrayList<StadiumDetailModel>();
					for (int i = 0; i < response.size(); i++) {
						StadiumDetailModel rowData = new StadiumDetailModel();
						rowData.setName(response.get(i).name);
						rowData.setAddress(response.get(i).address);
						DistrictModel district = new DistrictModel();
						district.setId(response.get(i).district.id);
						district.setName(response.get(i).district.name);
						rowData.setDistrict(district);
						rowData.setEmail(response.get(i).email);
						rowData.setPhone(response.get(i).phone);
						StadiumNumberModel field = new StadiumNumberModel();
						field.setFive_people(response.get(i).field_number.five_people);
						field.setSeven_people(response.get(i).field_number.seven_people);
						rowData.setField(field);
						PriceModel price = new PriceModel();
						price.setPriceMorning(response.get(i).price_five.morning);
						price.setPriceAfternoon(response.get(i).price_five.afternoon);
						price.setPriceEvening(response.get(i).price_five.evening);
						rowData.setPrice5(price);
						price = new PriceModel();
						price.setPriceMorning(response.get(i).price_seven.morning);
						price.setPriceAfternoon(response.get(i).price_seven.afternoon);
						price.setPriceEvening(response.get(i).price_seven.evening);
						rowData.setPrice7(price);
						rowData.setDescription(response.get(i).description);
						rowData.setOwnerId(response.get(i).ownerId);
						Map map = new Map();
						map.setLat(response.get(i).map.lat);
						map.setLng(response.get(i).map.lng);
						rowData.setMap(map);
						listStadiumData.add(rowData);
					}
				}
			}

			@Override
			public void updateUI() {
				adapter = new ListViewAdapterMain(ScreenMainActivity.this,
						listStadiumData, app);
				listviewMain.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				tvTitle.setText("Tất Cả Các Sân ");
			}

			@Override
			public void onUpdateStart() {

			}

		});

		btnLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getSlidingMenu().showMenu();

			}
		});
		btnRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getSlidingMenu().showSecondaryMenu();

			}
		});

		listviewMain.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				app.setStadiumDetails(listStadiumData.get(position - 1));
				Intent intent = new Intent(ScreenMainActivity.this,
						GuestShowDetailsStadium.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_left,
						R.anim.slide_right_leave);
			}
		});
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");
		if (mContent == null)
			mContent = new DistrictFragment(R.color.red);

		getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		// set the Above View
		setContentView(R.layout.activity_main);

		// set the Behind View
		setBehindContentView(R.layout.menu_frame_two);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame_two, new SlideMenuFragment()).commit();

		getSlidingMenu().setSecondaryMenu(R.layout.menu_frame);
		getSlidingMenu().setSecondaryShadowDrawable(R.drawable.shadowright);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new DistrictMenuFragment()).commit();

		// customize the SlidingMenu

		app = (CustomApplication) getApplication();
		initComponent();
		initListener();
		// initData();
		tvTitle.setText("Tất Cả Các Sân ");
		listStadiumData = new ArrayList<StadiumDetailModel>();

		LoadDataFromServer loadData = new LoadDataFromServer();
		loadData.execute("");

	}

	public void switchContent(int filter) {
		switch (filter) {
		case R.id.q_all: {
			adapter.getFilter().filter("");
			tvTitle.setText("Tất Cả Các Sân ");
			getSlidingMenu().showContent();
			break;
		}
		case R.id.q_1: {
			adapter.getFilter().filter("q_1");
			tvTitle.setText("Sân Vận Động Quận 1");
			getSlidingMenu().showContent();
			break;
		}
		case R.id.q_2: {
			adapter.getFilter().filter("q_2");
			tvTitle.setText("Sân Vận Động Quận 2");
			getSlidingMenu().showContent();
			break;
		}
		case R.id.q_3: {
			adapter.getFilter().filter("q_3");
			tvTitle.setText("Sân Vận Động Quận 3");
			getSlidingMenu().showContent();
			break;
		}
		case R.id.q_4: {
			adapter.getFilter().filter("q_4");
			tvTitle.setText("Sân Vận Động Quận 4");
			getSlidingMenu().showContent();
			break;
		}
		case R.id.q_5: {
			adapter.getFilter().filter("q_5");
			tvTitle.setText("Sân Vận Động Quận 5");
			getSlidingMenu().showContent();
			break;
		}
		case R.id.q_6: {
			adapter.getFilter().filter("q_6");
			tvTitle.setText("Sân Vận Động Quận 6");
			getSlidingMenu().showContent();
			break;
		}
		case R.id.q_7: {
			adapter.getFilter().filter("q_7");
			tvTitle.setText("Sân Vận Động Quận 7");
			getSlidingMenu().showContent();
			break;
		}
		case R.id.q_8: {
			adapter.getFilter().filter("q_8");
			tvTitle.setText("Sân Vận Động Quận 8");
			getSlidingMenu().showContent();
			break;
		}
		case R.id.q_9: {
			adapter.getFilter().filter("q_9");
			tvTitle.setText("Sân Vận Động Quận 9");
			getSlidingMenu().showContent();
			break;
		}
		case R.id.q_10: {
			adapter.getFilter().filter("q_10");
			tvTitle.setText("Sân Vận Động Quận 10");
			getSlidingMenu().showContent();
			break;
		}
		case R.id.q_11: {
			adapter.getFilter().filter("q_11");
			tvTitle.setText("Sân Vận Động Quận 11");
			getSlidingMenu().showContent();
			break;
		}
		case R.id.q_12: {
			adapter.getFilter().filter("q_12");
			tvTitle.setText("Sân Vận Động Quận 12");
			getSlidingMenu().showContent();
			break;
		}
		case R.id.q_gv: {
			adapter.getFilter().filter("q_gv");
			tvTitle.setText("SVD Quận Gò Vấp ");
			getSlidingMenu().showContent();
			break;
		}
		case R.id.q_tb: {
			adapter.getFilter().filter("q_tb");
			tvTitle.setText("SVD Quận Tân ");
			getSlidingMenu().showContent();
			break;
		}
		case R.id.q_tp: {
			adapter.getFilter().filter("q_tp");
			tvTitle.setText("SVD Quận Tân ");
			getSlidingMenu().showContent();
			break;
		}
		case R.id.q_bth: {
			adapter.getFilter().filter("q_bth");
			tvTitle.setText("SVD Quận Bình Thạnh ");
			getSlidingMenu().showContent();
			break;
		}
		case R.id.q_td: {
			adapter.getFilter().filter("q_td");
			tvTitle.setText("SVD Quận Thủ Đức ");
			getSlidingMenu().showContent();
			break;
		}
		case R.id.q_pn: {
			adapter.getFilter().filter("q_pn");
			tvTitle.setText("SVD Quận Phú Nhuận ");
			getSlidingMenu().showContent();
			break;
		}
		case R.id.q_bta: {
			adapter.getFilter().filter("q_bta");
			tvTitle.setText("SVD Quận Bình Tân ");
			getSlidingMenu().showContent();
			break;
		}
		case R.id.add_new: {
			Intent intent = new Intent(ScreenMainActivity.this,
					ScreenAddNewStadium.class);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_left,
					R.anim.slide_right_leave);
			break;
		}
		case R.id.my_stadium: {
			if (checkshow == 0) {
				checkshow = 1;
				LoadDataFromServerWithOwner loadData = new LoadDataFromServerWithOwner();
				loadData.execute("");
				tvTitle.setText("Tất Cả Các Sân Của Tôi ");
				getSlidingMenu().showContent();
			}
			break;
		}
		case R.id.all_stadium: {

			if (checkshow == 1) {
				checkshow = 0;
				LoadDataFromServer loadData = new LoadDataFromServer();
				loadData.execute("");
				tvTitle.setText("Tất Cả Các Sân ");
				getSlidingMenu().showContent();
			}
			break;
		}
		case R.id.sign_out: {
			Intent intent = new Intent(ScreenMainActivity.this,
					LoginActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_right,
					R.anim.slide_left_leave);
			break;
		}
		}
	}

	private class LoadDataFromServer extends AsyncTask<String, Void, String> {
		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected String doInBackground(String... urls) {
			// bottom_layout.setVisibility(View.GONE);
			try {
				String url = app.getLoginServer()
						+ getResources()
								.getString(R.string.Server_Get_All_Data);
				Log.d("Url login", url);
				String json = null;

				try {

					try {
						Services service = new Services();
						json = service.doGet(url);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} catch (NotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.d("json", json);
				ServiceHandler service = new ServiceHandler();
				GetStadiumDetailGson response = service.getStadiumData(json);

				if (response != null) {
					listStadiumData = new ArrayList<StadiumDetailModel>();
					for (int i = 0; i < response.size(); i++) {
						StadiumDetailModel rowData = new StadiumDetailModel();
						rowData.setName(response.get(i).name);
						rowData.setAddress(response.get(i).address);
						DistrictModel district = new DistrictModel();
						district.setId(response.get(i).district.id);
						district.setName(response.get(i).district.name);
						rowData.setDistrict(district);
						rowData.setEmail(response.get(i).email);
						rowData.setPhone(response.get(i).phone);
						StadiumNumberModel field = new StadiumNumberModel();
						field.setFive_people(response.get(i).field_number.five_people);
						field.setSeven_people(response.get(i).field_number.seven_people);
						rowData.setField(field);
						PriceModel price = new PriceModel();
						price.setPriceMorning(response.get(i).price_five.morning);
						price.setPriceAfternoon(response.get(i).price_five.afternoon);
						price.setPriceEvening(response.get(i).price_five.evening);
						rowData.setPrice5(price);
						price = new PriceModel();
						price.setPriceMorning(response.get(i).price_seven.morning);
						price.setPriceAfternoon(response.get(i).price_seven.afternoon);
						price.setPriceEvening(response.get(i).price_seven.evening);
						rowData.setPrice7(price);
						rowData.setDescription(response.get(i).description);
						rowData.setOwnerId(response.get(i).ownerId);
						Map map = new Map();
						map.setLat(response.get(i).map.lat);
						map.setLng(response.get(i).map.lng);
						rowData.setMap(map);
						listStadiumData.add(rowData);
					}
				}
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog.show();
		}

		@Override
		protected void onPostExecute(String result) {
			adapter = new ListViewAdapterMain(ScreenMainActivity.this,
					listStadiumData, app);
			listviewMain.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			dialog.dismiss();
		}
	}

	private class LoadDataFromServerWithOwner extends
			AsyncTask<String, Void, String> {
		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected String doInBackground(String... urls) {
			// bottom_layout.setVisibility(View.GONE);
			try {
				String url = app.getLoginServer()
						+ getResources().getString(
								R.string.Server_Get_Owner_Data) + "/"
						+ app.getToken_api();
				Log.d("Url login", url);
				String json = null;

				try {

					try {
						Services service = new Services();
						json = service.doGet(url);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} catch (NotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.d("json", json);
				ServiceHandler service = new ServiceHandler();
				GetStadiumDetailGson response = service.getStadiumData(json);

				if (response != null) {
					listStadiumData = new ArrayList<StadiumDetailModel>();
					for (int i = 0; i < response.size(); i++) {
						StadiumDetailModel rowData = new StadiumDetailModel();
						rowData.setName(response.get(i).name);
						rowData.setAddress(response.get(i).address);
						DistrictModel district = new DistrictModel();
						district.setId(response.get(i).district.id);
						district.setName(response.get(i).district.name);
						rowData.setDistrict(district);
						rowData.setEmail(response.get(i).email);
						rowData.setPhone(response.get(i).phone);
						StadiumNumberModel field = new StadiumNumberModel();
						field.setFive_people(response.get(i).field_number.five_people);
						field.setSeven_people(response.get(i).field_number.seven_people);
						rowData.setField(field);
						PriceModel price = new PriceModel();
						price.setPriceMorning(response.get(i).price_five.morning);
						price.setPriceAfternoon(response.get(i).price_five.afternoon);
						price.setPriceEvening(response.get(i).price_five.evening);
						rowData.setPrice5(price);
						price = new PriceModel();
						price.setPriceMorning(response.get(i).price_seven.morning);
						price.setPriceAfternoon(response.get(i).price_seven.afternoon);
						price.setPriceEvening(response.get(i).price_seven.evening);
						rowData.setPrice7(price);
						rowData.setDescription(response.get(i).description);
						rowData.setOwnerId(response.get(i).ownerId);
						Map map = new Map();
						map.setLat(response.get(i).map.lat);
						map.setLng(response.get(i).map.lng);
						rowData.setMap(map);
						listStadiumData.add(rowData);
					}
				}
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog.show();
		}

		@Override
		protected void onPostExecute(String result) {
			adapter = new ListViewAdapterMain(ScreenMainActivity.this,
					listStadiumData, app);
			listviewMain.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			dialog.dismiss();
		}
	}

}
