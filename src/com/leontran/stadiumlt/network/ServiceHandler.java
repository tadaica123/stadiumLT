/**
 * ============================================================================
 *
 * Copyright (C) 2012 Android MySportactivities Systems.  All rights reserved. The content 
 * presented herein may not, under any circumstances, be reproduced, in 
 * whole or in any part or form, without written permission from 
 * MySportactivities Systems.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are NOT permitted. Neither the name of MySportactivities Systems,
 * nor the names of contributors may be used to endorse or promote products 
 * derived from this software without specific prior written permission.
 *
 * ============================================================================
 *
 * Author: tuan
 *  
 *
 * Revision History
 * ----------------------------------------------------------------------------
 * Date                Author              Comment, bug number, fix description
 *
 * Feb 28, 2012      tuan@edge-works.net           version 1.0
 *
 * ----------------------------------------------------------------------------
 */
package com.leontran.stadiumlt.network;

import com.google.gson.Gson;
import com.leontran.stadiumlt.gson.GetAccountGson;
import com.leontran.stadiumlt.gson.GetStadiumDetailGson;


// TODO: Auto-generated Javadoc
/**
 * Created on Feb 28, 2012.
 * 
 * @author tuan
 * @version 1.0
 * @copyright Copyright (c) Android MySportactivities Systems, all rights
 *            reserved
 */
public class ServiceHandler {

	/** The gson. */
	Gson gson = new Gson();

	/**
	 * Gets the account.
	 * 
	 * @param response
	 *            the response
	 * @return the account
	 */
	public GetAccountGson getAccount(String response) {
		GetAccountGson account = gson.fromJson(response, GetAccountGson.class);
		return account;

	}
	
	/**
	 * Gets the data.
	 * 
	 * @param response
	 *            the response
	 * @return the data
	 */
	public GetStadiumDetailGson getStadiumData(String response) {
		GetStadiumDetailGson data = gson.fromJson(response, GetStadiumDetailGson.class);
		return data;

	}
	
	
	
}
