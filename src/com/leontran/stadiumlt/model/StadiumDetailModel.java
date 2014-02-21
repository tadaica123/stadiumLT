package com.leontran.stadiumlt.model;

import com.leontran.stadiumlt.model.DistrictModel;

public class StadiumDetailModel {

	String idToken;
	String ownerId;
	String name;
	String address;
	String email;
	DistrictModel district;
	StadiumNumberModel field;
	PriceModel price;
	String phone;
	String linkLogo;
	String description;

	public StadiumDetailModel(String idToken, String name, String address,
			DistrictModel district, String phone, String linkLogo) {
		super();
		this.idToken = idToken;
		this.name = name;
		this.address = address;
		this.district = district;
		this.phone = phone;
		this.linkLogo = linkLogo;
	}
	
	

	public StadiumDetailModel(String idToken, String name, String address,
			DistrictModel district, StadiumNumberModel field, String phone,
			String linkLogo) {
		super();
		this.idToken = idToken;
		this.name = name;
		this.address = address;
		this.district = district;
		this.field = field;
		this.phone = phone;
		this.linkLogo = linkLogo;
	}



	public StadiumDetailModel() {
		super();

	}

	/**
	 * @return the idToken
	 */
	public String getIdToken() {
		return idToken;
	}

	/**
	 * @param idToken
	 *            the idToken to set
	 */
	public void setIdToken(String idToken) {
		this.idToken = idToken;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the district
	 */
	public DistrictModel getDistrict() {
		return district;
	}

	/**
	 * @param district
	 *            the district to set
	 */
	public void setDistrict(DistrictModel district) {
		this.district = district;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the linkLogo
	 */
	public String getLinkLogo() {
		return linkLogo;
	}

	/**
	 * @param linkLogo
	 *            the linkLogo to set
	 */
	public void setLinkLogo(String linkLogo) {
		this.linkLogo = linkLogo;
	}

	/**
	 * @return the field
	 */
	public StadiumNumberModel getField() {
		return field;
	}

	/**
	 * @param field the field to set
	 */
	public void setField(StadiumNumberModel field) {
		this.field = field;
	}



	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}



	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}



	/**
	 * @return the ownerId
	 */
	public String getOwnerId() {
		return ownerId;
	}



	/**
	 * @param ownerId the ownerId to set
	 */
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}



	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}



	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}



	/**
	 * @return the price
	 */
	public PriceModel getPrice() {
		return price;
	}



	/**
	 * @param price the price to set
	 */
	public void setPrice(PriceModel price) {
		this.price = price;
	}



	public StadiumDetailModel(String idToken, String ownerId, String name,
			String address, String email, DistrictModel district,
			StadiumNumberModel field, PriceModel price, String phone,
			String linkLogo, String description) {
		super();
		this.idToken = idToken;
		this.ownerId = ownerId;
		this.name = name;
		this.address = address;
		this.email = email;
		this.district = district;
		this.field = field;
		this.price = price;
		this.phone = phone;
		this.linkLogo = linkLogo;
		this.description = description;
	}
	
}
