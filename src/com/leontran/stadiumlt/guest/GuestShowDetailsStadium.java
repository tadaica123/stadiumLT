package com.leontran.stadiumlt.guest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leontran.stadiumlt.CustomApplication;
import com.leontran.stadiumlt.R;
import com.leontran.stadiumlt.home.ScreenMainActivity;
import com.leontran.stadiumlt.map.ScreenShowMapView;
import com.leontran.stadiumlt.model.Map;
import com.leontran.stadiumlt.model.StadiumDetailModel;
import com.leontran.stadiumlt.owner.ScreenEditStadium;

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
	private Button btnServiceMore;
	
//	private ImageView imgLogo;
//
//	private CustomProgressDialog dialog;

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
		btnServiceMore = (Button) findViewById(R.id.btn_other_service);
		
//		imgLogo = (ImageView) findViewById(R.id.imgLogo);
//
//		dialog = new CustomProgressDialog(GuestShowDetailsStadium.this);
		
		btnEdit.setVisibility(View.GONE);
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
		txtPriceMorning.setText(getData.getPrice5().getPriceMorning()+ " Sáng (6h00 - 14h00)");
		txtPriceAfternoon.setText(getData.getPrice5().getPriceAfternoon()+ " Chiều (14h00 - 18h00)");
		txtPriceEvening.setText(getData.getPrice5().getPriceEvening()+ " Tối (18h00 - 23h00)");
		txtPriceMorning7.setText(getData.getPrice7().getPriceMorning()+ " Sáng (6h00 - 14h00)");
		txtPriceAfternoon7.setText(getData.getPrice7().getPriceAfternoon()+ " Chiều (14h00 - 18h00)");
		txtPriceEvening7.setText(getData.getPrice7().getPriceEvening()+ " Tối (18h00 - 23h00)");
		if (getData.getOwnerId().equals(app.getToken_api())){
			btnEdit.setVisibility(View.INVISIBLE);
		} else{
			btnEdit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent itent = new Intent(GuestShowDetailsStadium.this , ScreenEditStadium.class);
					startActivity(itent);
					overridePendingTransition(R.anim.slide_left, R.anim.slide_right_leave);
				}
			});
		}
	}
	
}
