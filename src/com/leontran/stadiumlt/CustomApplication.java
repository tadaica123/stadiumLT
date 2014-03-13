package com.leontran.stadiumlt;

import com.leontran.stadiumlt.model.DistrictModel;
import com.leontran.stadiumlt.model.Map;
import com.leontran.stadiumlt.model.StadiumDetailModel;

import android.app.Application;

public class CustomApplication extends Application {

	public String loginServer;

	public String token_api;

	private String venueName = null;

	private String venueAddress = null;

	public int tyleLogin;

	public DistrictModel returnDistrict;

	StadiumDetailModel stadiumDetails;

	Map map;

	/**
	 * @return the tyleLogin
	 */
	public int getTyleLogin() {
		return tyleLogin;
	}

	/**
	 * @param tyleLogin
	 *            the tyleLogin to set
	 */
	public void setTyleLogin(int tyleLogin) {
		this.tyleLogin = tyleLogin;
	}

	/**
	 * @return the stadiumDetails
	 */
	public StadiumDetailModel getStadiumDetails() {
		return stadiumDetails;
	}

	/**
	 * @param stadiumDetails
	 *            the stadiumDetails to set
	 */
	public void setStadiumDetails(StadiumDetailModel stadiumDetails) {
		this.stadiumDetails = stadiumDetails;
	}

	/**
	 * @return the loginServer
	 */
	public String getLoginServer() {
		return loginServer;
	}

	/**
	 * @param loginServer
	 *            the loginServer to set
	 */
	public void setLoginServer(String loginServer) {
		this.loginServer = loginServer;
	}

	/**
	 * @return the token_api
	 */
	public String getToken_api() {
		return token_api;
	}

	/**
	 * @param token_api
	 *            the token_api to set
	 */
	public void setToken_api(String token_api) {
		this.token_api = token_api;
	}

	/**
	 * @return the returnDistrict
	 */
	public DistrictModel getReturnDistrict() {
		return returnDistrict;
	}

	/**
	 * @param returnDistrict
	 *            the returnDistrict to set
	 */
	public void setReturnDistrict(DistrictModel returnDistrict) {
		this.returnDistrict = returnDistrict;
	}

	/**
	 * @return the map
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * @param map
	 *            the map to set
	 */
	public void setMap(Map map) {
		this.map = map;
	}

	/**
	 * @return the venueName
	 */
	public String getVenueName() {
		return venueName;
	}

	/**
	 * @param venueName
	 *            the venueName to set
	 */
	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}

	/**
	 * @return the venueAddress
	 */
	public String getVenueAddress() {
		return venueAddress;
	}

	/**
	 * @param venueAddress
	 *            the venueAddress to set
	 */
	public void setVenueAddress(String venueAddress) {
		this.venueAddress = venueAddress;
	}

	public void resetLogin() {

	}

}
