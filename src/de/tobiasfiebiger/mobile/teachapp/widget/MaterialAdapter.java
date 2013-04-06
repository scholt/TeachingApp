package de.tobiasfiebiger.mobile.teachapp.widget;

import de.tobiasfiebiger.mobile.teachapp.R;
import de.tobiasfiebiger.mobile.teachapp.model.Material;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MaterialAdapter extends MasterAdapter<Material> {

	public int getCount() {
        return mThumbIds.length;
    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }
	
	// references to our images
    private Integer[] mThumbIds = {
    		R.drawable.ic_launcher
    };

}
