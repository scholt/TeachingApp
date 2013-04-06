package de.tobiasfiebiger.mobile.teachapp;

import java.util.ArrayList;

import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.error.EDAMUserException;
import com.evernote.thrift.TException;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import de.tobiasfiebiger.mobile.teachapp.model.Material;
import de.tobiasfiebiger.mobile.teachapp.widget.MaterialAdapter;

public class MaterialGridFragment extends Fragment {

  public static final String ARG_ITEM_ID = "item_id";
  public static final String TAG = "MaterialGridFragment";

  public MaterialGridFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	if (getArguments().containsKey(ARG_ITEM_ID)) {
	  // TODO load material list for specified subject id
	}
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	View rootView = inflater.inflate(R.layout.fragment_material_detail, container, false);
	MaterialAdapter materialAdapter = null;
	try {
	  // GridView gridview = (GridView)
	  // rootView.findViewById(R.id.materials_gridview);
	  GridView gridview = (GridView) rootView;
	  materialAdapter = new MaterialAdapter(getActivity());
	  gridview.setAdapter(materialAdapter);
	} catch (Exception e) {
	  e.printStackTrace();
	}

	ArrayList<Material> array = new ArrayList<Material>();
	array.add(new Material());
	array.add(new Material());

	if (materialAdapter != null) {
	  materialAdapter.setData(array);
	}
	return rootView;
  }

}
