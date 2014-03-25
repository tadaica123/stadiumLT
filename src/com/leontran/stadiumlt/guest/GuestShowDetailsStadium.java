package com.leontran.stadiumlt.guest;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leontran.stadiumlt.CustomApplication;
import com.leontran.stadiumlt.CustomProgressDialog;
import com.leontran.stadiumlt.R;
import com.leontran.stadiumlt.home.ScreenMainActivity;
import com.leontran.stadiumlt.map.ScreenShowMapView;
import com.leontran.stadiumlt.model.Map;
import com.leontran.stadiumlt.model.StadiumDetailModel;
import com.leontran.stadiumlt.network.Services;
import com.leontran.stadiumlt.owner.ScreenEditStadium;
import com.leontran.stadiumlt.ultilities.Ultilities;

public class GuestShowDetailsStadium extends Activity {

	private CustomApplication app;

	private TextView txtName;
	private TextView txtAddress;
	private TextView txtDistrict;
	private TextView txtEmail;
	private TextView txtPhone;
	private TextView txtNumberStadium5;
	private TextView txtNumberStadium7;
	private TextView txtPriceMorning;
	private TextView txtPriceAfternoon;
	private TextView txtPriceEvening;
	private TextView txtPriceMorning7;
	private TextView txtPriceAfternoon7;
	private TextView txtPriceEvening7;
	private TextView txtDescription;
	
	private TextView txtTitle;

	private LinearLayout btnBack;
	private LinearLayout btnEdit;
	private LinearLayout layoutAction;
	private RelativeLayout layoutDelete;
	private RelativeLayout layoutEdit;
	private Button btnServiceMore;
	private Button btnRight;
	
//	private ImageView imgLogo;
//
	private CustomProgressDialog dialog;

	public void initComponent() {
		
		txtName = (TextView) findViewById(R.id.txt_name);
		txtAddress = (TextView) findViewById(R.id.txt_address);
		txtDistrict = (TextView) findViewById(R.id.txt_district);
		txtEmail = (TextView) findViewById(R.id.txt_email);
		txtPhone  = (TextView) findViewById(R.id.txt_phone);
		txtNumberStadium5 = (TextView) findViewById(R.id.txt_stadium_five);
		txtNumberStadium7 = (TextView) findViewById(R.id.txt_stadium_seven);
		txtPriceMorning = (TextView) findViewById(R.id.txt_price_morning);
		txtPriceAfternoon = (TextView) findViewById(R.id.txt_price_afternoon);
		txtPriceEvening = (TextView) findViewById(R.id.txt_price_evening);
		txtPriceMorning7 = (TextView) findViewById(R.id.txt_price_morning7);
		txtPriceAfternoon7 = (TextView) findViewById(R.id.txt_price_afternoon7);
		txtPriceEvening7 = (TextView) findViewById(R.id.txt_price_evening7);
		txtDescription = (TextView) findViewById(R.id.txt_description);
		
		txtTitle = (TextView) findViewById(R.id.txt_title);

		btnBack = (LinearLayout) findViewById(R.id.layout_button_left);
		btnEdit = (LinearLayout) findViewById(R.id.layout_button_right);
		layoutAction = (LinearLayout) findViewById(R.id.layout_action);
		layoutDelete = (RelativeLayout) findViewById(R.id.layout_delete);
		layoutEdit = (RelativeLayout) findViewById(R.id.layout_edit);
		btnServiceMore = (Button) findViewById(R.id.btn_other_service);
		btnRight = (Button) findViewById(R.id.btn_right);
//		imgLogo = (ImageView) findViewById(R.id.imgLogo);
//
//		dialog = new CustomProgressDialog(GuestShowDetailsStadium.this);
		
		btnEdit.setVisibility(View.GONE);
		btnRight.setText("Thay đổi");
		txtTitle.setText("Thông tin sân ");
	}

	public void initListener() {

		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent itent = new Intent(GuestShowDetailsStadium.this , ScreenMainActivity.class);
				startActivity(itent);
				overridePendingTransition(R.anim.slide_right, R.anim.slide_left_leave);
			}
		});
		btnServiceMore.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (app.getStadiumDetails().getMap()!=null){
					if (app.getStadiumDetails().getMap().getLat().equals("") || app.getStadiumDetails().getMap().getLng().equals("")){
						Toast.makeText(GuestShowDetailsStadium.this, "Địa chỉ chưa có thiết lập trên bản đồ !!", Toast.LENGTH_LONG).show();
					} else {
						Map map = new Map(app.getStadiumDetails().getMap().getLat(), app.getStadiumDetails().getMap().getLng());
						app.setMap(map);
						app.setVenueAddress(app.getStadiumDetails().getAddress());
						Intent itent = new Intent(GuestShowDetailsStadium.this , ScreenShowMapView.class);
						startActivity(itent);
						overridePendingTransition(R.anim.slide_left, R.anim.slide_right_leave);
					}
				} else {
					Toast.makeText(GuestShowDetailsStadium.this, "Địa chỉ chưa có thiết lập trên bản đồ !!", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		layoutDelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DeleteStadium delete = new DeleteStadium();
				delete.execute("");
			}
		});
		layoutEdit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						ScreenEditStadium.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_left,
						R.anim.slide_right_leave);
			}
		});

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_show_detail_stadium);
		app = (CustomApplication) getApplication();
		initComponent();
		initListener();
		initData();
	}
	
	public void initData(){
		
		StadiumDetailModel getData = app.getStadiumDetails();
		txtName.setText(getData.getName());
		txtAddress.setText(getData.getAddress());
		txtDistrict.setText(getData.getDistrict().getName());
		txtEmail.setText(getData.getEmail());
		txtPhone.setText(getData.getPhone());
		txtNumberStadium5.setText(getData.getField().getFive_people() + " Sân (Loại sân 5 người)");
		txtNumberStadium7.setText(getData.getField().getSeven_people() + " Sân (Loại sân 7 người)");
		txtDescription.setText(getData.getDescription());
		txtPriceMorning.setText(getData.getPrice5().getPriceMorning()+ "đ Sáng (6h00 - 14h00)");
		txtPriceAfternoon.setText(getData.getPrice5().getPriceAfternoon()+ "đ Chiều (14h00 - 18h00)");
		txtPriceEvening.setText(getData.getPrice5().getPriceEvening()+ "đ Tối (18h00 - 23h00)");
		txtPriceMorning7.setText(getData.getPrice7().getPriceMorning()+ "đ Sáng (6h00 - 14h00)");
		txtPriceAfternoon7.setText(getData.getPrice7().getPriceAfternoon()+ "đ Chiều (14h00 - 18h00)");
		txtPriceEvening7.setText(getData.getPrice7().getPriceEvening()+ "đ Tối (18h00 - 23h00)");
		if (!getData.getOwnerId().equals(app.getToken_api())){
			btnEdit.setVisibility(View.INVISIBLE);
		} else{
			btnEdit.setVisibility(View.VISIBLE);
			btnEdit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (layoutAction.getVisibility() == View.GONE) {
						layoutAction.setVisibility(View.VISIBLE);
						final Animation animation2 = AnimationUtils.loadAnimation(
								GuestShowDetailsStadium.this, R.anim.fade_in);
						layoutAction.startAnimation(animation2);
						Handler handle2 = new Handler();
						handle2.postDelayed(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								try {
									layoutAction.setVisibility(View.VISIBLE);
								} catch (Exception ex) {
								}
								animation2.cancel();
							}
						}, 1020);
					} else {
						layoutAction.setVisibility(View.VISIBLE);
						final Animation animation2 = AnimationUtils.loadAnimation(
								GuestShowDetailsStadium.this, R.anim.fade_out);
						layoutAction.startAnimation(animation2);
						Handler handle2 = new Handler();
						handle2.postDelayed(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								try {
									layoutAction.setVisibility(View.GONE);
								} catch (Exception ex) {
								}
								animation2.cancel();
							}
						}, 1020);
					}
				}
			});
		}
	}
	
	public class DeleteStadium extends AsyncTask<String, Void, String> {
		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected String doInBackground(String... urls) {
			String result = "";
			Services service = new Services();
			String url = "";
			// call webservice are there
				url = app.getLoginServer()
						+ getResources().getString(
								R.string.Server_Get_All_Data) + "/" + app.getStadiumDetails().getIdToken();


				result = service.Delete(GuestShowDetailsStadium.this, url);

			return result;
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
			dialog = new CustomProgressDialog(GuestShowDetailsStadium.this);
			dialog.show();
		}

		@Override
		protected void onPostExecute(String result) {
			dialog.dismiss();

			if (result.contains("success")) {
					Intent intent = new Intent(GuestShowDetailsStadium.this,
							ScreenMainActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_left,
							R.anim.slide_right_leave);
			} else {
				String showError = "";
				if (result.contains(":")) {
					showError = Ultilities.getError(result);
				}
				Toast.makeText(GuestShowDetailsStadium.this, showError,
						Toast.LENGTH_SHORT).show();
			}
		}
	}
}
