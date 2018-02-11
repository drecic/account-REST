package org.drecic.email.sparkmail.controllers;

import spark.Route;

public abstract class ResourceController {
	public static Route create;
	public static Route read;
	public static Route update;
	public static Route delete;
}
