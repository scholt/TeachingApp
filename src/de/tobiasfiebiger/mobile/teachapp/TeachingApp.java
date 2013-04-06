package de.tobiasfiebiger.mobile.teachapp;

import android.app.Application;
import android.content.Context;
import de.tobiasfiebiger.mobile.teachapp.util.ImageCache;

public class TeachingApp extends Application {

  private static ImageCache imageCache;
  private static Context    instance;

  public TeachingApp() {
	instance = this;
  }

  public static Context getApp() {
	return instance;
  }

  public static ImageCache getImageCache() {
	if (imageCache == null) {
	  imageCache = new ImageCache(TeachingApp.getApp());
	}
	return imageCache;
  }

}
