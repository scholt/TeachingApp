package de.tobiasfiebiger.mobile.teachapp.widget;

import de.tobiasfiebiger.mobile.teachapp.R;
import de.tobiasfiebiger.mobile.teachapp.model.Material;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MaterialAdapter extends MasterAdapter<Material> {

	public MaterialAdapter(Activity context) {
		super(context);
	}

	public int getCount() {
        return mThumbIds.length;
    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
        	convertView = inflater.inflate(R.layout.material_gridview_item, null);
            imageView = (ImageView) convertView.findViewById(R.id.material_thumbnail);
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
