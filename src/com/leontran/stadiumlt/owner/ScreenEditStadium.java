package com.leontran.stadiumlt.owner;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leontran.stadiumlt.CustomApplication;
import com.leontran.stadiumlt.CustomProgressDialog;
import com.leontran.stadiumlt.R;
import com.leontran.stadiumlt.home.ScreenMainActivity;
import com.leontran.stadiumlt.model.DistrictModel;
import com.leontran.stadiumlt.model.StadiumDetailModel;
import com.leontran.stadiumlt.model.StadiumNumberModel;
import com.leontran.stadiumlt.network.Services;
import com.leontran.stadiumlt.ultilities.CustomListViewDialog;
import com.leontran.stadiumlt.ultilities.Ultilities;

public class ScreenEditStadium extends Activity {

	private CustomApplication app;

	private EditText txtName;
	private EditText txtAddress;
	private TextView txtDistrict;
	private EditText txtEmail;
	private EditText txtPhone;
	private EditText txtNumberStadium5;
	private EditText txtNumberStadium7;
	private EditText txtDescription;

	private TextView txtTitle;

	private LinearLayout btnBack;
	private LinearLayout btnEdit;
	private Button btnServiceMore;

	private RelativeLayout layout_district;

	StadiumDetailModel dataPost;

	private CustomProgressDialog dialog;

	public void initComponent() {

		txtName = (EditText) findViewById(R.id.txt_name);
		txtAddress = (EditText) findViewById(R.id.txt_address);
		txtEmail = (EditText) findViewById(R.id.txt_email);
		txtPhone = (EditText) findViewById(R.id.txt_phone);
		txtNumberStadium5 = (EditText) findViewById(R.id.txt_stadium_five);
		txtNumberStadium7 = (EditText) findViewById(R.id.txt_stadium_seven);
		txtDescription = (EditText) findViewById(R.id.txt_description);

		txtDistrict = (TextView) findViewById(R.id.txt_district);
		txtTitle = (TextView) findViewById(R.id.txt_title);

		layout_district = (RelativeLayout) findViewById(R.id.layout_district);

		btnBack = (LinearLayout) findViewById(R.id.layout_button_left);
		btnEdit = (LinearLayout) findViewById(R.id.layout_button_right);
		btnServiceMore = (Button) findViewById(R.id.btn_other_service);

		dialog = new CustomProgressDialog(ScreenEditStadium.this);
		txtTitle.setText("New Stadium ");
	}

	public void initListener() {

		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(R.anim.slide_right,
						R.anim.slide_left_leave);
			}
		});
		btnServiceMore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(ScreenEditStadium.this, "Coming Soon!",
						Toast.LENGTH_SHORT).show();
			}
		});
		layout_district.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CustomListViewDialog dialog = new CustomListViewDialog(
						ScreenEditStadium.this, txtDistrict, app);
				dialog.show();
			}
		});
		btnEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (txtName.getText().toString().trim().equals("")) {
					Toast.makeText(ScreenEditStadium.this,
							"Xin nhập tên sân của bạn !", Toast.LENGTH_SHORT)
							.show();
				} else if (txtPhone.getText().toString().trim().equals("")
						|| txtPhone.getText().toString().length() < 9) {
					Toast.makeText(ScreenEditStadium.this,
							"Số điện thoại liên lạc không phù hợp !",
							Toast.LENGTH_SHORT).show();
				} else {
					dataPost = new StadiumDetailModel();
					dataPost.setName(txtName.getText().toString());
					dataPost.setAddress(txtAddress.getText().toString());
					DistrictModel district;
					if (app.getReturnDistrict() != null
							&& !app.getReturnDistrict().getId().equals("")) {
						district = app.getReturnDistrict();
					} else {
						district = new DistrictModel("", "");
					}
					dataPost.setDistrict(district);
					dataPost.setEmail(txtEmail.getText().toString());
					dataPost.setPhone(txtPhone.getText().toString());
					dataPost.setDescription(txtDescription.getText().toString());
					StadiumNumberModel field = new StadiumNumberModel();
					field.setFive_people(txtNumberStadium5.getText().toString());
					field.setSeven_people(txtNumberStadium7.getText()
							.toString());
					dataPost.setField(field);
					dataPost.setOwnerId(app.getToken_api());
					AddDataToServer postToWS = new AddDataToServer();
					postToWS.execute("");
				}

			}
		});

	}

	public void initData() {
		StadiumDetailModel getData = app.getStadiumDetails();
		txtName.setText(getData.getName());
		txtAddress.setText(getData.getAddress());
		txtDistrict.setText(getData.getDistrict().getName());
		txtEmail.setText(getData.getEmail());
		txtPhone.setText(getData.getPhone());
		txtNumberStadium5.setText(getData.getField().getFive_people()
				+ " Sân (Loại Sân 5 Người)");
		txtNumberStadium7.setText(getData.getField().getSeven_people()
				+ " Sân (Loại Sân 7 Người)");
		txtDescription.setText(getData.getDescription());
		if (getData.getOwnerId().equals(app.getToken_api())) {
			btnEdit.setVisibility(View.VISIBLE);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_add_new_stadium);
		app = (CustomApplication) getApplication();
		initComponent();
		initListener();
		initData();
	}

	private class AddDataToServer extends AsyncTask<String, Void, String> {
		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected String doInBackground(String... urls) {
			// bottom_layout.setVisibility(View.GONE);
			String result = "";
			try {
				String url = app.getLoginServer()
						+ getResources()
								.getString(R.string.Server_Get_All_Data);
				Log.d("Url login", url);
				Services serviceLoad = new Services();
				result = serviceLoad.addNewStadium(ScreenEditStadium.this, url,
						dataPost);
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			dialog.show();
		}

		@Override
		protected void onPostExecute(String result) {

			if (result.equals("success")) {
				Intent intent = new Intent(ScreenEditStadium.this,
						ScreenMainActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_left,
						R.anim.slide_right_leave);
			} else {
				String showError = "";
				if (result.contains(":")) {
					showError = Ultilities.getError(result);
				}
				Toast.makeText(ScreenEditStadium.this, showError,
						Toast.LENGTH_SHORT).show();
			}
			dialog.dismiss();
		}
	}

}
