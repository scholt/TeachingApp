package de.tobiasfiebiger.mobile.teachapp;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import de.tobiasfiebiger.mobile.teachapp.model.Material;
import de.tobiasfiebiger.mobile.teachapp.widget.MaterialAdapter;

/**
 * A fragment representing a single Material detail screen. This fragment is
 * either contained in a {@link SubjectListActivity} in two-pane mode (on
 * tablets) or a {@link MaterialListActivity} on handsets.
 */
public class MaterialGridFragment extends Fragment {
  /**
   * The fragment argument representing the item ID that this fragment
   * represents.
   */
  public static final String ARG_ITEM_ID = "item_id";

  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public MaterialGridFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	if (getArguments().containsKey(ARG_ITEM_ID)) {

	}
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	View rootView = inflater.inflate(R.layout.fragment_material_detail, container, false);
	MaterialAdapter ma = null;
	try {
	  GridView gridview = (GridView) rootView.findViewById(R.id.materials_gridview);
	  ma = new MaterialAdapter(getActivity());
	  gridview.setAdapter(ma);
	} catch (Exception e) {
	  e.printStackTrace();
	}
	
	ArrayList<Material> array = new ArrayList<Material>();
	array.add(new Material());
	array.add(new Material());
	
	if (ma != null) 
		ma.setData(array);
	return rootView;
  }
  
  
}
