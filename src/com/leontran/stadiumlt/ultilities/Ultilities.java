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
 * Author: Leon
 *  
 *
 * Revision History
 * ----------------------------------------------------------------------------
 * Date                Author              Comment, bug number, fix description
 *
 * Jun 7, 2012      nguyen.ta@edge-works.net           version 1.9
 *
 * ----------------------------------------------------------------------------
 */
package com.leontran.stadiumlt.ultilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.format.DateFormat;
import android.util.Log;


// TODO: Auto-generated Javadoc
/**
 * Created on Feb 28, 2012.
 * 
 * @author tuan
 * @version 1.0
 * @copyright Copyright (c) Android MySportactivities Systems, all rights
 *            reserved
 */
public class Ultilities {

	public static String getError(String error) {
		String result = "";
		int firstChar = -1;
		firstChar = error.indexOf(":") + 1;
		if (firstChar != -1) {
			String getError = error.substring(firstChar);
			if (getError.contains("\"")) {
				getError = getError.replace("\"", "");
			}
			if (getError.contains("}")) {
				getError = getError.replace("}", "");
			}

			result = getError;
		}
		return result;
	}
	
	public String getHeaderError(String notice) {
		String result = "";
		int firstChar = -1;
		firstChar = notice.indexOf(":") - 1;
		if (firstChar != -1) {
			String getHeader = notice.substring(0, firstChar);
			if (getHeader.contains("\"")) {
				getHeader = getHeader.replace("\"", "");
			}
			if (getHeader.contains("{")) {
				getHeader = getHeader.replace("{", "");
			}

			result = getHeader;
		}
		return result;
	}
	
	public String getFooterError(String notice) {
		String result = "";
		int firstChar = -1;
		firstChar = notice.indexOf(":") + 1;
		if (firstChar != -1) {
			String getFooter = notice.substring(firstChar, notice.length() -1);
			if (getFooter.contains("\"")) {
				getFooter = getFooter.replace("\"", "");
			}
			if (getFooter.contains("}")) {
				getFooter = getFooter.replace("}", "");
			}

			result = getFooter;
		}
		return result;
	}
	
	public String GetCurrentTime() {
		String currenttime = "";
		currenttime = DateFormat.format("yyyy-MM-dd k:mm:ss",
				System.currentTimeMillis()).toString();
		currenttime = currenttime.replace("-", "_");
		currenttime = currenttime.replace(":", "_");
		currenttime = currenttime.replace(" ", "_");

		Log.d("Currnt", currenttime);
		return currenttime;
	}
	
	public Bitmap decodeFile(String filePath) {
		// Decode image size
		Bitmap bitmap;
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, o);

		// The new size we want to scale to
		final int REQUIRED_SIZE = 1024;

		// Find the correct scale value. It should be the power of 2.
		int width_tmp = o.outWidth, height_tmp = o.outHeight;
		int scale = 1;
		while (true) {
			if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
				break;
			width_tmp /= 2;
			height_tmp /= 2;
			scale *= 2;
		}

		// Decode with inSampleSize
		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize = scale;
		bitmap = BitmapFactory.decodeFile(filePath, o2);

		return bitmap;

	}
	
}
