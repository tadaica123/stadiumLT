package com.leontran.stadiumlt.gson;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;
import com.leontran.stadiumlt.gson.GetStadiumDetailGson.GetTypeStadiumGson;

@SuppressWarnings("serial")
public class GetStadiumDetailGson extends ArrayList<GetTypeStadiumGson> {
	
	public class GetTypeStadiumGson{
		@SerializedName("_id")
		public String id;
		
		@SerializedName("name")
		public String name;

		@SerializedName("phone")
		public String phone;

		@SerializedName("email")
		public String email;

		@SerializedName("contact")
		public String contact;

		@SerializedName("address")
		public String address;

		@SerializedName("description")
		public String description;

		@SerializedName("picture")
		public String picture;

		@SerializedName("ownerId")
		public String ownerId;
		
		@SerializedName("map")
		public GetTypeMap map;

		@SerializedName("district")
		public GetDistrictGson district;
		
		@SerializedName("field_number")
		public GetFieldGson field_number;
		
		@SerializedName("price_five")
		public GetPriceGson price_five;
		
		@SerializedName("price_seven")
		public GetPriceGson price_seven;
	}
	
	public class GetTypeMap {
		
		@SerializedName("lat")
		public String lat;
		
		@SerializedName("long")
		public String lng;
	}

	
	public class GetDistrictGson {
		@SerializedName("id")
		public String id;

		@SerializedName("name")
		public String name;
	}

	public class GetFieldGson {
		@SerializedName("five_people")
		public String five_people;

		@SerializedName("seven_people")
		public String seven_people;
	}

	public class GetPriceGson {
		@SerializedName("morning")
		public String morning;

		@SerializedName("afternoon")
		public String afternoon;
		
		@SerializedName("evening")
		public String evening;
	}
	


}
