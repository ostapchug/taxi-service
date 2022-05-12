package com.example.taxiservice.web;

/**
 * Error messages holder
 */
public class ErrorMsg {

	private ErrorMsg() {
	}

	// common
	public static final String ACCESS_DENIED = "error_jsp.anchor.access_denied";
	public static final String FORMAT = "error.label.anchor.format";
	public static final String NOT_FOUND = "error_jsp.anchor.404";
	public static final String INPUT_PARAMS = "error.label.anchor.input_params";

	// person
	public static final String PHONE__NOT_FOUND = "error.label.anchor.wrong_phone";
	public static final String PHONE__EXIST = "error.label.anchor.already_exist_phone";
	public static final String PASSWORD_WRONG = "error.label.anchor.wrong_password";
	public static final String PASSWORD__CONFIRM_WRONG = "error.label.anchor.wrong_password_confirm";
	public static final String PROFILE__CREATE = "error.label.anchor.profile_create";
	public static final String PROFILE__UPDATE = "error.label.anchor.profile_update";

	// trip
	public static final String CAR__CAPACITY = "error.label.anchor.capacity";
	public static final String CAR__NOT_FOUND = "error.label.anchor.car";
	public static final String TRIP__DISTANCE = "error.label.anchor.distance";
	public static final String TRIP__CREATE = "error.label.anchor.trip_create";
	public static final String TRIP__UPDATE_STATUS = "error.label.anchor.trip_update_status";

}
