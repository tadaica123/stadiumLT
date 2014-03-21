package com.leontran.stadiumlt;

import com.leontran.stadiumlt.gson.GetAccountGson;
import com.leontran.stadiumlt.home.ScreenMainActivity;
import com.leontran.stadiumlt.network.ServiceHandler;
import com.leontran.stadiumlt.network.Services;
import com.leontran.stadiumlt.ultilities.Ultilities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;

public class LoginActivity extends Activity implements OnClickListener {

	private static final int SPLASH_TIME = 1 * 3000;// 2 seconds

	private CustomApplication app;

	private LinearLayout lo_login;

	private TextView txtTitle;

	private RadioButton radGuest;
	private RadioButton radOwner;

	private Button btnLogin;

	private EditText edtUserName;
	private EditText edtPassword;

	private CustomProgressDialog dialog;
	
	private String getErrorWhenSignin = "";
	private String token_api = "";

	public void initComponent() {
		lo_login = (LinearLayout) findViewById(R.id.layout_login);

		txtTitle = (TextView) findViewById(R.id.txt_title);
		edtUserName = (EditText) findViewById(R.id.edt_username);
		edtPassword = (EditText) findViewById(R.id.edt_password);

		radGuest = (RadioButton) findViewById(R.id.rd_guest);
		radOwner = (RadioButton) findViewById(R.id.rd_owner);

		btnLogin = (Button) findViewById(R.id.btn_login);

		dialog = new CustomProgressDialog(LoginActivity.this);
		
		edtUserName.setText("administrator");
		edtPassword.setText("123");
	}

	public void initListener() {

		radGuest.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					edtUserName.setEnabled(false);
					edtPassword.setEnabled(false);
					edtUserName.setFocusable(false);
					edtPassword.setFocusable(false);
					edtUserName.setAlpha((float) 0.5);
					edtPassword.setAlpha((float) 0.5);
				}
			}
		});

		radOwner.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					edtUserName.setEnabled(true);
					edtPassword.setEnabled(true);
					edtUserName.setFocusableInTouchMode(true);
					edtPassword.setFocusableInTouchMode(true);
					edtUserName.setAlpha((float) 1);
					edtPassword.setAlpha((float) 1);
				}
			}
		});

		btnLogin.setOnClickListener(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		app = (CustomApplication) getApplication();
		initComponent();
		initListener();
		initAnimation();
	}

	public void initAnimation() {

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				lo_login.setVisibility(View.VISIBLE);
				final Animation animation = AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.grow_from_bottom);
				txtTitle.startAnimation(animation);
				Handler handle = new Handler();
				handle.postDelayed(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							txtTitle.setVisibility(View.VISIBLE);
						} catch (Exception ex) {
						}
						animation.cancel();
					}
				}, 1020);

				final Animation animation2 = AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.slide_right_dialog);
				lo_login.startAnimation(animation2);
				Handler handle2 = new Handler();
				handle2.postDelayed(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							lo_login.setVisibility(View.VISIBLE);
						} catch (Exception ex) {
						}
						animation2.cancel();
					}
				}, 1020);

			}
		}, SPLASH_TIME);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_login:
			setDomainServer();
			if (radGuest.isChecked()) {
				dialog.show();
				app.setTyleLogin(0);
				Handler handle = new Handler();
				handle.postDelayed(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							Intent intent = new Intent(LoginActivity.this,
									ScreenMainActivity.class);
							startActivity(intent);
							overridePendingTransition(R.anim.slide_left,
									R.anim.slide_right_leave);
						} catch (Exception ex) {
						}
						dialog.dismiss();
					}
				}, 2020);

			} else if (radOwner.isChecked()) {
				app.setTyleLogin(1);
				SignInTheServer signIn = new SignInTheServer();
				signIn.execute("");
			}
//			app.setTyleLogin(1);
//			Intent intent = new Intent(LoginActivity.this,
//					ScreenMainActivity.class);
//			startActivity(intent);
//			overridePendingTransition(R.anim.slide_left,
//					R.anim.slide_right_leave);
			break;
		}

	}

	public void setDomainServer() {
//		app.setLoginServer("http://192.168.1.171:3000");
		app.setLoginServer("http://192.168.0.107:3000");
	}

	private class SignInTheServer extends AsyncTask<String, Void, String> {
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
						+ getResources().getString(R.string.Server_Login);
				Log.d("Url login", url);
				Services serviceLoad = new Services();
				String json = serviceLoad.Login(LoginActivity.this, url,
						edtUserName.getText().toString(), edtPassword.getText()
								.toString());
				Log.d("json", json);
				if (json.length() > 0) {

					if (json.contains("errorName")) {
						getErrorWhenSignin = json;
					}
					if (!json.contains("errorName")) {
						ServiceHandler service = new ServiceHandler();
						GetAccountGson accounts = service.getAccount(json);
						// acc = accounts.getUser();

						token_api = accounts.token;
						if (token_api != null) {
							if (!token_api.equals("")) {
								Log.d("token", "" + token_api);
								app.setToken_api(token_api);
							}
						}
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

			if (!token_api.equals("")) {
				Intent intent = new Intent(LoginActivity.this,
						ScreenMainActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_left,
						R.anim.slide_right_leave);
			} else {
				String showError = "Invalid username or password";
				if (getErrorWhenSignin.contains(":")) {
					showError = Ultilities.getError(getErrorWhenSignin);
				}
				Toast.makeText(LoginActivity.this, showError, Toast.LENGTH_SHORT).show();
			}
			dialog.dismiss();
		}
	}
}
