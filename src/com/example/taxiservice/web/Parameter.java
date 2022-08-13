package com.example.taxiservice.web;

/**
 * Parameter holder
 */
public class Parameter {

    // common
    public static final String ACCESS_GROUP__COMMON = "common";
    public static final String ACCESS_GROUP__GUEST = "guest";
    public static final String ACCESS_GROUP__USER = "user";
    public static final String COMMAND = "command";
    public static final String COMMANDS = "commands";
    public static final String ENCODING = "encoding";
    public static final String ERROR = "error";
    public static final String ERROR__QUERY = "&error=";
    public static final String LOCALES = "locales";
    public static final String PAGE = "page";
    public static final String SORT = "sort";

    // person
    public static final String PERSON__PHONE = "phone";
    public static final String PERSON__PASSWORD = "password";
    public static final String PERSON__PASSWORD_CONFIRM = "passwordConfirm";
    public static final String PERSON__NAME = "name";
    public static final String PERSON__SURNAME = "surname";

    // trip
    public static final String CAR_CATEGORY = "category";
    public static final String CAR_CAPACITY = "capacity";
    public static final String FILTER__DATE = "dateFilter";
    public static final String FILTER__PHONE = "phoneFilter";
    public static final String TRIP_CONFIRM = "confirm";
    public static final String TRIP_ID = "trip";
    public static final String TRIP_OFFER = "offer";
    public static final String TRIP_DEST = "destination";
    public static final String TRIP_ORIGIN = "origin";
    public static final String TRIP_STATUS = "tripStatus";
    
    private Parameter() {
    }
}
