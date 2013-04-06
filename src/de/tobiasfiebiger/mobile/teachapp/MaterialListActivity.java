package de.tobiasfiebiger.mobile.teachapp;

import java.util.List;

import com.evernote.client.android.OnClientCallback;
import com.evernote.edam.type.Notebook;
import com.evernote.thrift.transport.TTransportException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

/**
 * An activity representing a single Material detail screen. This activity is
 * only used on handset devices. On tablet-size devices, item details are
 * presented side-by-side with a list of items in a {@link SubjectListActivity}
 * .
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link MaterialGridFragment}.
 */
public class MaterialListActivity extends TeachActivity {
	
  public static final String TAG = "TeachActivity";
  

  @Override
  public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_material_detail);

	// Show the Up button in the action bar.
	getActionBar().setDisplayHomeAsUpEnabled(true);

	// savedInstanceState is non-null when there is fragment state
	// saved from previous configurations of this activity
	// (e.g. when rotating the screen from portrait to landscape).
	// In this case, the fragment will automatically be re-added
	// to its container so we don't need to manually add it.
	// For more information, see the Fragments API guide at:
	//
	// http://developer.android.com/guide/components/fragments.html
	//
	if (savedInstanceState == null) {
	  // Create the detail fragment and add it to the activity
	  // using a fragment transaction.
	  Bundle arguments = new Bundle();
	  arguments.putString(MaterialGridFragment.ARG_ITEM_ID, getIntent().getStringExtra(MaterialGridFragment.ARG_ITEM_ID));
	  MaterialGridFragment fragment = new MaterialGridFragment();
	  fragment.setArguments(arguments);
	  getFragmentManager().beginTransaction().add(R.id.material_detail_container, fragment).commit();
	  
	 
	}
	

  }
  

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
	switch (item.getItemId()) {
	case android.R.id.home:
	  navigateUpToFromChild(this, new Intent(this, SubjectListActivity.class));
	  return true;
	}
	return super.onOptionsItemSelected(item);
  }
}
