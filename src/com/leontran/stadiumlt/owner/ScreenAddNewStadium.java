package com.leontran.stadiumlt.owner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.leontran.stadiumlt.CustomApplication;
import com.leontran.stadiumlt.CustomProgressDialog;
import com.leontran.stadiumlt.R;
import com.leontran.stadiumlt.home.ScreenMainActivity;
import com.leontran.stadiumlt.model.DistrictModel;
import com.leontran.stadiumlt.model.StadiumDetailModel;
import com.leontran.stadiumlt.model.StadiumNumberModel;
import com.leontran.stadiumlt.network.Services;
import com.leontran.stadiumlt.other.SelectFileDialog;
import com.leontran.stadiumlt.ultilities.CustomListViewDialog;
import com.leontran.stadiumlt.ultilities.Global;
import com.leontran.stadiumlt.ultilities.Ultilities;

public class ScreenAddNewStadium extends FragmentActivity implements
		OnMapClickListener, OnMapLongClickListener, OnCameraChangeListener {

	private final static int TAKE_IMAGE = 1;
	private final static int CHOOSE_MEDIA = 10;

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

	ImageView imgLogo;

	private LinearLayout btnBack;
	private LinearLayout btnEdit;

	ScrollView mainScrolView;

	private GoogleMap mMap;

	private List<Marker> listmarker;

	private Button btnServiceMore;

	private RelativeLayout layout_district;
	private RelativeLayout layout_map;

	StadiumDetailModel dataPost;

	private CustomProgressDialog dialog;
	private Ultilities utils = new Ultilities();

	private Uri imageUri;
	private String fileName = "";

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

		imgLogo = (ImageView) findViewById(R.id.imageLogo);

		layout_district = (RelativeLayout) findViewById(R.id.layout_district);
		layout_map = (RelativeLayout) findViewById(R.id.ll_mapview);

		btnBack = (LinearLayout) findViewById(R.id.layout_button_left);
		btnEdit = (LinearLayout) findViewById(R.id.layout_button_right);
		mainScrolView = (ScrollView) findViewById(R.id.main_layout);

		btnServiceMore = (Button) findViewById(R.id.btn_other_service);

		listmarker = new ArrayList<Marker>();

		dialog = new CustomProgressDialog(ScreenAddNewStadium.this);
		txtTitle.setText("New Stadium ");
	}

	public void initListener() {

		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mainScrolView.getVisibility() == View.GONE) {
					mainScrolView.setVisibility(View.VISIBLE);
					btnEdit.setVisibility(View.VISIBLE);
					layout_map.setVisibility(View.GONE);
				} else {
					finish();
					overridePendingTransition(R.anim.slide_right,
							R.anim.slide_left_leave);
				}

			}
		});
		btnServiceMore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mainScrolView.setVisibility(View.GONE);
				btnEdit.setVisibility(View.GONE);
				layout_map.setVisibility(View.VISIBLE);
			}
		});
		layout_district.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CustomListViewDialog dialog = new CustomListViewDialog(
						ScreenAddNewStadium.this, txtDistrict, app);
				dialog.show();
			}
		});
		imgLogo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Dialog mDialog = new Dialog(ScreenAddNewStadium.this,
						android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
				mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				mDialog.setContentView(R.layout.dialog_option_select_media);
				mDialog.setCancelable(true);

				final LinearLayout layout_main = (LinearLayout) mDialog
						.findViewById(R.id.layout_main);
				Button btn_take_photo = (Button) mDialog
						.findViewById(R.id.btn_take_photo);
				Button btn_from_gallery = (Button) mDialog
						.findViewById(R.id.btn_from_gallery);

				Button btn_cancel = (Button) mDialog
						.findViewById(R.id.btn_cancel);
				mDialog.show();

				final Animation animation2 = AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.slide_bottom_to_top);
				layout_main.startAnimation(animation2);
				Handler handle2 = new Handler();
				handle2.postDelayed(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {

						} catch (Exception ex) {
						}
						animation2.cancel();
					}
				}, 1020);

				btn_take_photo.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						capturePhoto();
						mDialog.cancel();
					}
				});
				btn_from_gallery.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intentTakePhoto = new Intent(
								ScreenAddNewStadium.this,
								SelectFileDialog.class);

						intentTakePhoto.putExtra("type", "photo");
						startActivityForResult(intentTakePhoto, CHOOSE_MEDIA);
						overridePendingTransition(R.anim.slide_left,
								R.anim.slide_right_leave);
						mDialog.cancel();
					}
				});

				btn_cancel.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						final Animation animation2 = AnimationUtils
								.loadAnimation(getApplicationContext(),
										R.anim.slide_top_to_bottom);
						layout_main.startAnimation(animation2);
						Handler handle2 = new Handler();
						handle2.postDelayed(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								try {

								} catch (Exception ex) {
								}
								animation2.cancel();
							}
						}, 1020);
						mDialog.cancel();
					}
				});
			}
		});
		btnEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (txtName.getText().toString().trim().equals("")) {
					Toast.makeText(ScreenAddNewStadium.this,
							"Xin nhập tên sân của bạn !", Toast.LENGTH_SHORT)
							.show();
				} else if (txtPhone.getText().toString().trim().equals("")
						|| txtPhone.getText().toString().length() < 9) {
					Toast.makeText(ScreenAddNewStadium.this,
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_add_new_stadium);
		app = (CustomApplication) getApplication();
		initComponent();
		initListener();
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
				result = serviceLoad.addNewStadium(ScreenAddNewStadium.this,
						url, dataPost);
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
				Intent intent = new Intent(ScreenAddNewStadium.this,
						ScreenMainActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_left,
						R.anim.slide_right_leave);
			} else {
				String showError = "";
				if (result.contains(":")) {
					showError = Ultilities.getError(result);
				}
				Toast.makeText(ScreenAddNewStadium.this, showError,
						Toast.LENGTH_SHORT).show();
			}
			dialog.dismiss();
		}
	}

	private void setUpMapIfNeeded() {
		if (mMap == null) {
			mMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			if (mMap != null) {
				setUpMap();
			}
		}
	}

	private void setUpMap() {
		mMap.setOnMapClickListener(this);
		mMap.setOnMapLongClickListener(this);
		mMap.setOnCameraChangeListener(this);
	}

	@Override
	public void onMapClick(LatLng p) {
		// mTapTextView.setText("tapped, point=" + point);
		listmarker.clear();
		mMap.clear();
		try {
			Geocoder geoCoder = new Geocoder(this, Locale.getDefault());

			List<Address> addresses = geoCoder.getFromLocation(p.latitude,
					p.longitude, 1);
			Log.d("lat neeeeeee", "" + p.latitude);
			Log.d("lng neeeeeee", "" + p.longitude);
			Global.lat = String.valueOf(p.latitude);
			Global.lng = String.valueOf(p.longitude);

			String add = "";
			if (addresses.size() > 0) {

				for (int i = 0; i < addresses.get(0).getMaxAddressLineIndex(); i++)

					add += addresses.get(0).getAddressLine(i) + " ";
				Toast.makeText(ScreenAddNewStadium.this, add,
						Toast.LENGTH_SHORT).show();
			}
			Marker marker = mMap.addMarker(new MarkerOptions().position(p)
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
			listmarker.add(marker);
			if (add.contains(",")) {
				String[] state = add.split(",");
				String getState = state[1];
				String getAdd = state[0];

				getState = getState.trim();
				if (getState.contains(" ")) {
					String[] getZip = getState.split(" ");
					Global.zipcode = getZip[1];
					Global.state = getZip[0];
				}
				getAdd = getAdd.trim();
				if (getAdd.contains(" ")) {
					String[] getAddress = getAdd.split(" ");
					int lenghtOfArray = getAddress.length;
					String city = getAddress[lenghtOfArray - 1];
					String address = "";
					if (lenghtOfArray > 1) {
						for (int i = 0; i < (lenghtOfArray - 1); i++) {
							address += getAddress[i] + " ";
						}
					}

					Global.city = city;
					Global.street = address;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onMapLongClick(LatLng point) {
		// mTapTextView.setText("long pressed, point=" + point);
	}

	@Override
	public void onCameraChange(final CameraPosition position) {
		// mCameraTextView.setText(position.toString());
	}

	public void capturePhoto() {
		fileName = "msa_img";
		String value = utils.GetCurrentTime();
		fileName += "_" + value + ".jpg";
		File file = new File(Environment.getExternalStorageDirectory()
				.getPath(), fileName);
		imageUri = Uri.fromFile(file);
		Intent intent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(intent, TAKE_IMAGE);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == TAKE_IMAGE) {
			if (resultCode == RESULT_OK) {

				File f = new File(Environment.getExternalStorageDirectory()
						.getPath() + "/" + fileName);
				if (f.exists()) {
					Log.d("file", fileName);
					app.setLinks(Environment.getExternalStorageDirectory()
							.getPath() + "/" + fileName);
					Bitmap bitmap = utils.decodeFile(Environment
							.getExternalStorageDirectory().getPath()
							+ "/"
							+ fileName);
					imgLogo.setImageBitmap(bitmap);
				} else {
					Toast.makeText(getApplicationContext(),
							"Failed to take the photo. Please try again",
							Toast.LENGTH_LONG).show();
				}
			}
		}
		if ((requestCode == CHOOSE_MEDIA) && (resultCode == RESULT_OK)) {

			if (!app.getLinks().equals("")) {
				if (app.getLinks().contains("/")) {
					String[] listTypeImage = getResources().getStringArray(
							R.array.image);
					String[] nameFile = app.getLinks().split("/");
					String filDescription = nameFile[nameFile.length - 1];
					for (int i = 0; i < listTypeImage.length; i++) {
						if (filDescription.contains("." + listTypeImage[i])) {
							Bitmap bitmap = utils.decodeFile(app.getLinks());
							imgLogo.setImageBitmap(bitmap);
							break;
						}
					}
				}
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		setUpMapIfNeeded();
	}

}
