package de.tobiasfiebiger.mobile.teachapp.widget;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

import de.tobiasfiebiger.mobile.teachapp.R;
import de.tobiasfiebiger.mobile.teachapp.TeachingApp;
import de.tobiasfiebiger.mobile.teachapp.model.Material;
import de.tobiasfiebiger.mobile.teachapp.util.ImageCache;
import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MaterialAdapter extends MasterAdapter<Material> {
	
	private static final int IO_BUFFER_SIZE = 4 * 1024;
	private static final String TAG = "MaterialAdapter";

	public MaterialAdapter(Activity context) {
		super(context);
	}

	public int getCount() {
        return mThumbIds.length;
    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        final ImageView imageView;
        if (convertView == null) {
        	convertView = inflater.inflate(R.layout.material_gridview_item, null);
            imageView = (ImageView) convertView.findViewById(R.id.material_thumbnail);
        } else {
            imageView = (ImageView) convertView;
        }
        String url = "http://uwekamper.de/media/img/avatar_500.jpg";
        
        TeachingApp.getImageCache().requestImage(url, new ImageCache.BitmapCallback() {
			
			@Override
			public void onCacheError(Exception error) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onBitmapLoaded(Bitmap image) {
				// TODO Auto-generated method stub
				imageView.setImageBitmap(image);
			}
		});

        return imageView;
    }
	
	private void closeStream(BufferedInputStream str) {
		if (str == null)
			return;
		
		try {
			str.close();
		} 
		catch (Exception e) {
			Log.e(TAG, "Error closing stream.");
		}
	}
	
	private void closeStream(BufferedOutputStream str) {
		
		if (str == null)
			return;
		
		try {
			str.close();
		} 
		catch (Exception e) {
			Log.e(TAG, "Error closing stream.");
		}
	}

	// references to our images
    private Integer[] mThumbIds = {
    		R.drawable.ic_launcher
    };

}
