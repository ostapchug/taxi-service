package com.example.taxiservice.web;

public class Page {
	private String path;
	private boolean isRedirect;
	
	public Page(String path) {
		this.path = path;
	}

	public Page(String path, boolean isRedirect) {
		this.path = path;
		this.isRedirect = isRedirect;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isRedirect() {
		return isRedirect;
	}

	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}

}
