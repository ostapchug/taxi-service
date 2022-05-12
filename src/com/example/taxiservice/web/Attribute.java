package com.example.taxiservice.web;

/**
 * Attribute holder
 */
public class Attribute {

	private Attribute() {
	}

	// common
	public static final String ERROR__MESSAGE = "errorMessage";
	public static final String ERROR__CAR_CAPACITY = "errorCapacity";
	public static final String ERROR__PHONE = "errorPhone";
	public static final String ERROR__PASSWORD = "errorPassword";
	public static final String ERROR__PASSWORD_CONFIRM = "errorPasswordConfirm";
	public static final String ERROR__NAME = "errorName";
	public static final String ERROR__SURNAME = "errorSurname";
	public static final String LOCALE = "locale";
	public static final String LOCALE__DEFAULT = "defaultLocale";
	public static final String LOCALES = "locales";
	public static final String COMMANDS = "commands";

	// person
	public static final String PERSON__ID = "personId";
	public static final String PERSON__PHONE = "personPhone";
	public static final String PERSON__NAME = "personName";
	public static final String PERSON__SURNAME = "personSurname";
	public static final String PERSON__ROLE = "personRole";

	// trip
	public static final String CAR__CATEGORIES = "categories";
	public static final String CAR__CATEGORY = "category";
	public static final String FILTER__DATE = "dateRange";
	public static final String FILTER__PHONE = "phone";
	public static final String LOCATIONS = "locations";
	public static final String TRIP = "trip";
	public static final String TRIPS = "tripList";
	public static final String TRIP__CONFIRM = "tripConfirm";
	public static final String TRIP__OFFER = "tripOffer";
	public static final String TRIP__ORIGIN = "origin";
	public static final String TRIP__DEST = "destination";
	public static final String SORTING = "sorting";
	public static final String PAGE__TOTAL = "totalPages";
	public static final String PAGE__CURRENT = "currentPage";

}
