package com.example.taxiservice.web;

/**
 * Path holder (jsp pages and controller commands)
*/

public final class Path {
	
	private Path() {
	}
	
	// pages
	public static final String PAGE__HOME_PAGE = "/WEB-INF/jsp/home.jsp";
	public static final String PAGE__SIGN_IN = "/WEB-INF/jsp/sign_in.jsp";
	public static final String PAGE__SIGN_UP = "/WEB-INF/jsp/sign_up.jsp";
	public static final String PAGE__PROFILE = "/WEB-INF/jsp/profile.jsp";
	public static final String PAGE__PROFILE_UPDATE = "/WEB-INF/jsp/profile_update.jsp";
	public static final String PAGE__TRIPS = "/WEB-INF/jsp/trips.jsp";
	public static final String PAGE__TRIP = "/WEB-INF/jsp/trip.jsp";
	public static final String PAGE__ERROR = "/WEB-INF/jsp/error.jsp";
	
	// commands
	public static final String COMMAND__HOME_PAGE = "?command=home_page";
	public static final String COMMAND__SIGN_UP_PAGE = "?command=sign_up_page";
	public static final String COMMAND__SIGN_IN_PAGE = "?command=sign_in_page";
	public static final String COMMAND__PROFILE_PAGE = "?command=profile_page";
	public static final String COMMAND__PROFILE_UPDATE = "?command=profile_update";
	public static final String COMMAND__SIGN_OUT = "?command=sign_out";

}
