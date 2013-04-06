package de.tobiasfiebiger.mobile.teachapp.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

public class ImageCache {

  private static final String               EMPTY_STRING              = "";
  private static final Object               ZERO                      = "0";
  private static final String               TAG                       = "ImageCache";
  private static final String               GET_METHOD                = "GET";
  private static final String               SLASH                     = "/";

  private static final String               KEY_URL                   = "de.tobiasfiebiger.mobile.teachapp.util.ImageUtil.url.key";
  private static final String               KEY_ERROR_MSG             = "de.tobiasfiebiger.mobile.teachapp.util.ImageUtil.error.message";
  private static final int                  MESSAGE_ID_PREFETCH_IMAGE = 10115;
  private static final int                  MESSAGE_ID_REQUEST_IMAGE  = 97450;
  private static final int                  MESSAGE_ID_POST_IMAGE     = 10247;

  // md5 hash assets
  private MessageDigest                     digest;
  private StringBuffer                      hexString;
  private HandlerThread                     lowPriorityThread;
  private String                            basePath;
  private Hashtable<String, BitmapCallback> delegates;

  private BackgroundHandler                 worker                    = new BackgroundHandler();
  private ForegroundHandler                 notifier                  = new ForegroundHandler();

  public ImageCache(Context context) {
	try {

	  delegates = new Hashtable<String, ImageCache.BitmapCallback>();

	  basePath = TextUtils.concat(Environment.getExternalStorageDirectory().getAbsolutePath(), "/Android/data/", context.getPackageName(),
		  "/cache/").toString();

	  File baseDir = new File(basePath);
	  // Log.d(TAG, "checking basePath: " + basePath);
	  if (!baseDir.exists()) {
		baseDir.mkdirs();
	  }

	  if (baseDir.canWrite()) {
		File nomedia = new File(basePath, ".nomedia");
		if (!nomedia.exists()) {
		  nomedia.createNewFile();
		}
	  } else {
		basePath = null;
	  }

	} catch (Exception e) {
	  Log.e(TAG, e.getLocalizedMessage(), e);
	}
  }

  public void requestImage(String url, BitmapCallback delegate) {
	delegates.put(url, delegate);
	Message msg = worker.obtainMessage(MESSAGE_ID_REQUEST_IMAGE, url);
	worker.sendMessageAtFrontOfQueue(msg);
  }

  public void requestPrefetchImage(String url) {
	Message msg = worker.obtainMessage(MESSAGE_ID_PREFETCH_IMAGE, url);
	msg.sendToTarget();
  }

  public void cancelImageRequest(String url) {
	delegates.remove(url);
  }

  private void downloadImage(String source) throws IOException {
	InputStream is = null;

	try {
	  // Log.d(TAG, "downloadImage: " + source);

	  URL url = new URL(source);
	  HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	  conn.setReadTimeout(10000);
	  conn.setConnectTimeout(15000);
	  conn.setRequestMethod(GET_METHOD);
	  conn.connect();
	  is = conn.getInputStream();

	  File f = new File(basePath, md5(source));

	  // Log.d(TAG, "storing image to : " + f.getAbsolutePath() +
	  // " \n with basepath: " + basePath);

	  FileOutputStream fos = new FileOutputStream(f);
	  IOUtil.copy(is, fos);
	  fos.close();

	} finally {
	  if (is != null) {
		is.close();
	  }
	}
  }

  private Bitmap downloadBitmap(String targetUrl) throws IOException {
	InputStream is = null;

	try {
	  // Log.d(TAG, "downloadBitmap: " + targetUrl);

	  URL url = new URL(targetUrl);
	  HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	  conn.setReadTimeout(10000);
	  conn.setConnectTimeout(15000);
	  conn.setRequestMethod(GET_METHOD);
	  conn.connect();
	  is = conn.getInputStream();

	  return BitmapFactory.decodeStream(is);
	} finally {
	  if (is != null) {
		is.close();
	  }
	}
  }

  private Looper getLowPriorityLooper() {
	if (lowPriorityThread == null) {
	  lowPriorityThread = new HandlerThread("Low Priority Looper", Process.THREAD_PRIORITY_LOWEST);
	  lowPriorityThread.setDaemon(true);
	  lowPriorityThread.start();
	}
	return lowPriorityThread.getLooper();
  }

  private synchronized String md5(String s) {
	try {
	  // Create MD5 Hash
	  if (digest == null) {
		digest = java.security.MessageDigest.getInstance("MD5");
	  }
	  digest.update(s.getBytes());
	  byte messageDigest[] = digest.digest();

	  // Create Hex String
	  if (hexString != null) {
		hexString.setLength(0);
	  } else {
		hexString = new StringBuffer();
	  }

	  String h;
	  for (int i = 0; i < messageDigest.length; i++) {
		h = Integer.toHexString(0xFF & messageDigest[i]);
		if (h.length() < 2) {
		  hexString.append(ZERO);
		}
		hexString.append(h);
	  }

	  return hexString.toString();

	} catch (NoSuchAlgorithmException e) {
	  Log.e(TAG, "Could not get MD5 Algorithm", e);
	}
	return EMPTY_STRING;
  }

  public void _handleMessage(Message msg) {
	switch (msg.what) {
	case MESSAGE_ID_REQUEST_IMAGE:
	  requestImage((String) msg.obj);
	  break;
	case MESSAGE_ID_POST_IMAGE:
	  postImageToDelegate(msg);
	  break;
	case MESSAGE_ID_PREFETCH_IMAGE:
	  prefetchImage((String) msg.obj);
	  break;
	}
  }

  private void prefetchImage(String url) {
	try {
	  if (!isImageDownloaded(url)) {
		Log.d(TAG, "prefetchImage: " + url);
		downloadImage(url);
	  }
	} catch (Exception e) {
	  Log.e(TAG, e.getLocalizedMessage(), e);
	}
  }

  private void postImageToDelegate(Message msg) {
	Bundle bundle = msg.getData();
	String imageUrl = bundle.getString(KEY_URL);
	BitmapCallback delegate = delegates.remove(imageUrl);

	if (delegate != null) {
	  if (msg.obj != null && msg.obj instanceof Bitmap) {
		Bitmap image = (Bitmap) msg.obj;
		delegate.onBitmapLoaded(image);
	  } else {
		delegate.onCacheError(null);
	  }
	}
  }

  private void requestImage(String imageUrl) {

	Message postMessage = notifier.obtainMessage(MESSAGE_ID_POST_IMAGE);
	Bundle bundle = postMessage.getData();
	bundle.putString(KEY_URL, imageUrl);
	postMessage.setData(bundle);

	try {
	  if (basePath == null) {
		postMessage.obj = downloadBitmap(imageUrl);
	  } else {
		if (!isImageDownloaded(imageUrl)) {
		  downloadImage(imageUrl);
		}
		postMessage.obj = loadImage(imageUrl);
	  }

	} catch (Exception e) {
	  Log.e(TAG, e.getLocalizedMessage(), e);

	  // Message postMessage = notifier.obtainMessage(MESSAGE_ID_POST_IMAGE);
	  // Bundle bundle = postMessage.getData();
	  // bundle.putString(KEY_ERROR_MSG, e.getLocalizedMessage());
	  // postMessage.setData(bundle);
	  // postMessage.sendToTarget();
	}

	postMessage.sendToTarget();
  }

  private Bitmap loadImage(String imageUrl) {
	File f = new File(basePath, md5(imageUrl));
	// Log.d(TAG, "loadingImage: " + f.getAbsolutePath());
	return BitmapFactory.decodeFile(f.getAbsolutePath());
  }

  private boolean isImageDownloaded(String imageUrl) {
	File f = new File(basePath, md5(imageUrl));
	// Log.d(TAG, f.getAbsolutePath() + ": " + f.exists());
	return f.exists();
  }

  private class BackgroundHandler extends Handler {

	public BackgroundHandler() {
	  super(getLowPriorityLooper());
	}

	@Override
	public void handleMessage(Message msg) {
	  _handleMessage(msg);
	}
  }

  private class ForegroundHandler extends Handler {

	public ForegroundHandler() {
	  super(Looper.getMainLooper());
	}

	@Override
	public void handleMessage(Message msg) {
	  _handleMessage(msg);
	}
  }

  public interface BitmapCallback {
	public void onBitmapLoaded(Bitmap image);

	public void onCacheError(Exception error);
  }

}
