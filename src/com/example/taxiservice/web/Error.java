package com.example.taxiservice.web;

/**
 * Error holder
 */
public class Error {

    // common
    public static final String ACCESS_DENIED = "access_denied";
    public static final String NOT_FOUND = "not_found";
    public static final String INPUT_PARAMS = "input_params";

    // person
    public static final String PHONE__EXIST = "phone_exist";
    public static final String PHONE__FORMAT = "phone_format";
    public static final String PHONE__NOT_FOUND = "phone_not_found";
    public static final String PASSWORD__WRONG = "password_wrong";
    public static final String PASSWORD__FORMAT = "password_format";
    public static final String PASSWORD__CONFIRM_WRONG = "password_confirm_wrong";
    public static final String NAME__FORMAT = "name_format";
    public static final String SURNAME__FORMAT = "surname_format";
    public static final String PROFILE__CREATE = "profile_create";
    public static final String PROFILE__UPDATE = "profile_update";

    // trip
    public static final String FILTER__FORMAT = "filter_format";
    public static final String CAR__CAPACITY = "capacity";
    public static final String CAR__NOT_FOUND = "car_not_found";
    public static final String TRIP__CREATE = "trip_create";
    public static final String TRIP__DISTANCE = "distance";
    public static final String TRIP__NOT_FOUND = "trip_not_found";
    public static final String TRIP__STATUS = "trip_status";
    
    private Error() {
    }
}
