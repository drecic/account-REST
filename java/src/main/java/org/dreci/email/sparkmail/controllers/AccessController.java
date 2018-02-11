package org.drecic.email.sparkmail.controllers;

public interface AccessController {
	public boolean allowed(RequestData request);
}