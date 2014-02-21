package com.leontran.stadiumlt.guest;

import android.app.Activity;
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
import com.leontran.stadiumlt.model.StadiumDetailModel;

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
		txtDescription = (TextView) findViewById(R.id.txt_description);
		
		txtTitle = (TextView) findViewById(R.id.txt_title);

		btnBack = (LinearLayout) findViewById(R.id.layout_button_left);
		btnEdit = (LinearLayout) findViewById(R.id.layout_button_right);
		btnServiceMore = (Button) findViewById(R.id.btn_other_service);
		
//		imgLogo = (ImageView) findViewById(R.id.imgLogo);
//
//		dialog = new CustomProgressDialog(GuestShowDetailsStadium.this);
		
		btnEdit.setVisibility(View.GONE);
		txtTitle.setText("Stadium Detail");
	}

	public void initListener() {

		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(R.anim.slide_right, R.anim.slide_left_leave);
			}
		});
		btnServiceMore.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(GuestShowDetailsStadium.this, "Coming Soon!", Toast.LENGTH_SHORT).show();
			}
		});
		btnEdit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
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
		txtNumberStadium5.setText(getData.getField().getFive_people() + " Sân (Loại Sân 5 Người)");
		txtNumberStadium7.setText(getData.getField().getSeven_people() + " Sân (Loại Sân 7 Người)");
		txtDescription.setText(getData.getDescription());
		txtPriceMorning.setText(getData.getPrice().getPriceMorning()+ " Sáng (6h00 - 14h00)");
		txtPriceAfternoon.setText(getData.getPrice().getPriceAfternoon()+ " Chiều (14h00 - 18h00)");
		txtPriceEvening.setText(getData.getPrice().getPriceEvening()+ " Tối (18h00 - 23h00)");
		if (getData.getOwnerId().equals(app.getToken_api())){
			btnEdit.setVisibility(View.VISIBLE);
		}
	}
	
}
