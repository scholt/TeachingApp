package de.tobiasfiebiger.mobile.teachapp.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import de.tobiasfiebiger.mobile.teachapp.TeachingApp;
import de.tobiasfiebiger.mobile.teachapp.util.ImageCache;
import de.tobiasfiebiger.mobile.teachapp.util.ImageCache.BitmapCallback;

public class AsyncImageView extends ImageView implements BitmapCallback {

  private final static String TAG = "AsyncImageView";
  private ImageCache          cache;

  public AsyncImageView(Context context) {
	super(context);
  }

  public AsyncImageView(Context context, AttributeSet attrs) {
	this(context, attrs, 0);
  }

  public AsyncImageView(Context context, AttributeSet attrs, int defStyle) {
	super(context, attrs, defStyle);
	setScaleType(ScaleType.FIT_CENTER);
  }

  public void loadImage(final String imageUrl) {
	Log.i(TAG, "*** loading image from url: " + imageUrl);
	setImageDrawable(null);
	if (cache == null) {
	  cache = TeachingApp.getImageCache();
	}
	cache.requestImage(imageUrl, this);
  }

  @Override
  public void onCacheError(Exception error) {
	Log.e(TAG, "Error while loading image", error);
  }

  @Override
  public void onBitmapLoaded(Bitmap image) {
	Log.i(TAG, "*** received bitmap from image cache");
	try {
	  setImageBitmap(image);
	  requestLayout();
	} catch (Exception e) {
	  Log.e(TAG, "Error while displaying image", e);
	}
  }

}
