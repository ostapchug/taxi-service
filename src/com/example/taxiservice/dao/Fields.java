package com.example.taxiservice.dao;

/**
 * Holder for fields names of DB tables and beans.
 */

public class Fields {
	// Person
	public static final String PERSON__ID = "p_id";
	public static final String PERSON__PHONE = "p_phone";
	public static final String PERSON__PASSWORD = "p_password";
	public static final String PERSON__NAME = "p_name";
	public static final String PERSON__SURNAME = "p_surname";
	public static final String PERSON__ROLE_ID = "p_role";
	
	// Category
	public static final String CATEGORY__ID = "cc_id";
	public static final String CATEGORY__NAME = "cc_name";
	public static final String CATEGORY__PRICE = "cc_price";
	
	// Location
	public static final String LOCATION__ID = "l_id";
	public static final String LOCATION__STREET_NAME = "l_street_name";
	public static final String LOCATION__STREET_NUMBER = "l_street_number";
	public static final String LOCATION__LATITUDE = "l_latitude";
	public static final String LOCATION__LONGITUDE = "l_longitude";
	
	// CarModel
	public static final String CAR_MODEL__ID = "cm_id";
	public static final String CAR_MODEL__BRAND = "cm_brand";
	public static final String CAR_MODEL__NAME = "cm_name";
	public static final String CAR_MODEL__COLOR = "cm_color";
	public static final String CAR_MODEL__YEAR = "cm_year";
	public static final String CAR_MODEL__SEAT_COUNT = "cm_seat_count";
	
	// Car
	public static final String CAR__ID = "c_id";
	public static final String CAR__REG_NUMBER = "c_reg_number";
	public static final String CAR__MODEL = "c_model";
	public static final String CAR__CATEGORY = "c_category";
	public static final String CAR__LOCATION = "c_location";
	public static final String CAR__STATUS = "c_status";
	
	// Trip
	public static final String TRIP__ID = "t_id";
	public static final String TRIP__PERSON_ID = "t_person";
	public static final String TRIP__ORIGIN_ID = "t_origin";
	public static final String TRIP__DESTINATION_ID = "t_destination";
	public static final String TRIP__DISTANCE = "t_distance";
	public static final String TRIP__DATE = "t_date";
	public static final String TRIP__BILL = "t_bill";
	public static final String TRIP__STATUS_ID = "t_status";
	


}
