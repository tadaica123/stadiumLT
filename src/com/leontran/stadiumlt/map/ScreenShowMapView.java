package com.leontran.stadiumlt.map;

import java.util.List;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.Overlay;
import com.leontran.stadiumlt.CustomApplication;
import com.leontran.stadiumlt.R;


public class ScreenShowMapView extends FragmentActivity implements
		OnClickListener, ConnectionCallbacks, OnConnectionFailedListener,
		LocationListener, OnMyLocationButtonClickListener {

	List<Overlay> mapOverlays;
	Drawable drawable;
	CustomItemizedOverlay<CustomOverlayItem> itemizedOverlay;
	private Button btn_standard;
	private Button btn_satellite;
	private Button btn_hybrid;
	private Button btn_navigate;
	private TextView txt_title;
	CustomApplication apps;
	LinearLayout btn_home;
	Double lat = 0.0;
	Double lng = 0.0;
	private LocationClient mLocationClient;

	// These settings are the same as the settings for the map. They will in
	// fact give you updates
	// at the maximal rates currently possible.
	private static final LocationRequest REQUEST = LocationRequest.create()
			.setInterval(5000) // 5 seconds
			.setFastestInterval(16) // 16ms = 60fps
			.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

	/**
	 * Note that this may be null if the Google Play services APK is not
	 * available.
	 */
	private GoogleMap mMap;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.screen_show_map);
		setUpMapIfNeeded();
		apps = (CustomApplication) getApplication();
		btn_home = (LinearLayout) findViewById(R.id.layout_button_left);
		btn_standard = (Button) findViewById(R.id.btn_standard);
		btn_satellite = (Button) findViewById(R.id.btn_satellite);
		btn_hybrid = (Button) findViewById(R.id.btn_hybrid);
		btn_navigate = (Button) findViewById(R.id.layout_button_right);
		txt_title = (TextView) findViewById(R.id.txt_title);
		txt_title.setText(apps.getVenueName());
		btn_standard.setOnClickListener(this);
		btn_satellite.setOnClickListener(this);
		btn_hybrid.setOnClickListener(this);
		btn_navigate.setOnClickListener(this);
		btn_navigate.setText("Navigate");

		btn_home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(R.anim.slide_right,
						R.anim.slide_left_leave);
			}
		});
		drawable = getResources().getDrawable(R.drawable.bubble);
		if (!apps.getMap().getLat().equals("")) {
			lat = (Double.valueOf(apps.getMap().getLat()) * 1);
			lng = (Double.valueOf(apps.getMap().getLng()) * 1);
			Log.d("lat", lat + "");
			Log.d("lng", lng + "");
			CameraPosition newPosition = new CameraPosition.Builder()
					.target(new LatLng(
							(Double.valueOf(apps.getMap().getLat()) * 1),
							(Double.valueOf(apps.getMap().getLng()) * 1)))
					.zoom(15.5f).bearing(0).tilt(25).build();
			mMap.addMarker(new MarkerOptions().position(
					new LatLng((Double.valueOf(apps.getMap().getLat()) * 1),
							(Double.valueOf(apps.getMap().getLng()) * 1)))
					.title(apps.getVenueAddress()));
			changeCamera(CameraUpdateFactory.newCameraPosition(newPosition));
		} else {
			Toast.makeText(getApplicationContext(), "Unknown Location !!",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.btn_standard:
			Resources res1 = getResources();
			Drawable drawableLayout = res1.getDrawable(R.drawable.btn_pending);
			btn_standard.setBackground(drawableLayout);
			
			Resources res2 = getResources();
			drawableLayout = res2.getDrawable(R.drawable.btn_today_off);
			btn_satellite.setBackground(drawableLayout);
			
			Resources res3 = getResources();
			drawableLayout = res3.getDrawable(R.drawable.btn_month_off);
			btn_hybrid.setBackground(drawableLayout);
			
			btn_standard.setTextColor(getResources().getColor(R.color.white));
			btn_satellite.setTextColor(getResources().getColor(R.color.gray_2));
			btn_hybrid.setTextColor(getResources().getColor(R.color.gray_2));
			// mapView.setSatellite(false);
			// mapView.setTraffic(false);
			mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			break;
		case R.id.btn_satellite:
			res1 = getResources();
			drawableLayout = res1.getDrawable(R.drawable.btn_pending_off);
			btn_standard.setBackground(drawableLayout);
			res2 = getResources();
			drawableLayout = res2.getDrawable(R.drawable.btn_today);
			btn_satellite.setBackground(drawableLayout);
			res3 = getResources();
			drawableLayout = res3.getDrawable(R.drawable.btn_month_off);
			btn_hybrid.setBackground(drawableLayout);
			
			btn_standard.setTextColor(getResources().getColor(R.color.gray_2));
			btn_satellite.setTextColor(getResources().getColor(R.color.white));
			btn_hybrid.setTextColor(getResources().getColor(R.color.gray_2));
			
			mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			break;
		case R.id.btn_hybrid:
			res1 = getResources();
			drawableLayout = res1.getDrawable(R.drawable.btn_pending_off);
			btn_standard.setBackground(drawableLayout);
			res2 = getResources();
			drawableLayout = res2.getDrawable(R.drawable.btn_today_off);
			btn_satellite.setBackground(drawableLayout);
			res3 = getResources();
			drawableLayout = res3.getDrawable(R.drawable.btn_month);
			btn_hybrid.setBackground(drawableLayout);
			
			btn_standard.setTextColor(getResources().getColor(R.color.gray_2));
			btn_satellite.setTextColor(getResources().getColor(R.color.gray_2));
			btn_hybrid.setTextColor(getResources().getColor(R.color.white));
			
			mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			break;
		case R.id.layout_button_right:
			String url = "http://maps.google.com/maps?daddr=" + lat + "," + lng
					+ "%20&dirflg=d";
			Log.d("MapURL", url);
			Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
					Uri.parse(url));
			if (isAppInstalled("com.google.android.apps.maps")) {
				intent.setClassName("com.google.android.apps.maps",
						"com.google.android.maps.MapsActivity");
			}
			startActivity(intent);
			overridePendingTransition(R.anim.slide_left,
					R.anim.slide_right_leave);
			break;
		default:
			break;
		}
	}

	// helper function to check if Maps is installed
	private boolean isAppInstalled(String uri) {
		PackageManager pm = getApplicationContext().getPackageManager();
		boolean app_installed = false;
		try {
			pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
			app_installed = true;
		} catch (PackageManager.NameNotFoundException e) {
			app_installed = false;
		}
		return app_installed;
	}

	/**
	 * Sets up the map if it is possible to do so (i.e., the Google Play
	 * services APK is correctly installed) and the map has not already been
	 * instantiated.. This will ensure that we only ever call
	 * {@link #setUpMap()} once when {@link #mMap} is not null.
	 * <p>
	 * If it isn't installed {@link SupportMapFragment} (and
	 * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt
	 * for the user to install/update the Google Play services APK on their
	 * device.
	 * <p>
	 * A user can return to this FragmentActivity after following the prompt and
	 * correctly installing/updating/enabling the Google Play services. Since
	 * the FragmentActivity may not have been completely destroyed during this
	 * process (it is likely that it would only be stopped or paused),
	 * {@link #onCreate(Bundle)} may not be called again so we should call this
	 * method in {@link #onResume()} to guarantee that it will be called.
	 */
	@Override
	protected void onResume() {
		super.onResume();
		setUpMapIfNeeded();
		setUpLocationClientIfNeeded();
		mLocationClient.connect();
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mLocationClient != null) {
			mLocationClient.disconnect();
		}
	}

	private void setUpLocationClientIfNeeded() {
		if (mLocationClient == null) {
			mLocationClient = new LocationClient(getApplicationContext(), this, // ConnectionCallbacks
					this); // OnConnectionFailedListener
		}
	}

	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the
		// map.
		if (mMap == null) {
			// Try to obtain the map from the SupportMapFragment.
			mMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			// Check if we were successful in obtaining the map.
			if (mMap != null) {
				setUpMap();
			}
		}
	}

	/**
	 * This is where we can add markers or lines, add listeners or move the
	 * camera. In this case, we just add a marker near Africa.
	 * <p>
	 * This should only be called once and when we are sure that {@link #mMap}
	 * is not null.
	 */
	private void setUpMap() {

		if (GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(ScreenShowMapView.this) == ConnectionResult.SUCCESS) {
			mMap.setMyLocationEnabled(true);
			mMap.setOnMyLocationButtonClickListener(this);
			mMap.setIndoorEnabled(true);
		} else {
			GooglePlayServicesUtil
					.getErrorDialog(
							GooglePlayServicesUtil
									.isGooglePlayServicesAvailable(ScreenShowMapView.this),
							ScreenShowMapView.this, 0).show();
		}
	}

	/**
	 * Button to get current Location. This demonstrates how to get the current
	 * Location as required without needing to register a LocationListener.
	 */
	public void showMyLocation(View view) {
		if (mLocationClient != null && mLocationClient.isConnected()) {
			String msg = "Location = " + mLocationClient.getLastLocation();
			Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT)
					.show();
		}
	}

	private void changeCamera(CameraUpdate update, CancelableCallback callback) {
		mMap.animateCamera(update, Math.max(4000, 1), callback);
	}

	private void changeCamera(CameraUpdate update) {
		changeCamera(update, null);
	}

	/**
	 * Implementation of {@link LocationListener}.
	 */
	@Override
	public void onLocationChanged(Location location) {
		mLocationClient.requestLocationUpdates(REQUEST, this);
	}

	/**
	 * Callback called when connected to GCore. Implementation of
	 * {@link ConnectionCallbacks}.
	 */
	@Override
	public void onConnected(Bundle connectionHint) {
		mLocationClient.requestLocationUpdates(REQUEST, this); // LocationListener

	}

	/**
	 * Callback called when disconnected from GCore. Implementation of
	 * {@link ConnectionCallbacks}.
	 */
	@Override
	public void onDisconnected() {
		// Do nothing
	}

	/**
	 * Implementation of {@link OnConnectionFailedListener}.
	 */
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// Do nothing
	}

	@Override
	public boolean onMyLocationButtonClick() {
		// Return false so that we don't consume the event and the default
		// behavior still occurs
		// (the camera animates to the user's current position).
		if (mLocationClient != null && mLocationClient.isConnected()) {
			if (mLocationClient.getLastLocation() != null) {

				CameraPosition newPosition = new CameraPosition.Builder()
						.target(new LatLng(mLocationClient.getLastLocation()
								.getLatitude(), mLocationClient
								.getLastLocation().getLongitude())).zoom(15.5f)
						.bearing(0).tilt(25).build();
				changeCamera(CameraUpdateFactory.newCameraPosition(newPosition));
			} else {
				Toast.makeText(getApplicationContext(),
						"Please try again in outdoor", Toast.LENGTH_SHORT)
						.show();
			}
		} else {
			Toast.makeText(getApplicationContext(),
					"Please try again in outdoor", Toast.LENGTH_SHORT).show();
		}
		return false;
	}

}
