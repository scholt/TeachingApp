package de.tobiasfiebiger.mobile.teachapp;

import java.util.List;

import com.evernote.client.android.EvernoteSession;
import com.evernote.client.android.OnClientCallback;
import com.evernote.edam.type.Notebook;
import com.evernote.thrift.transport.TTransportException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * An activity representing a list of Materials. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link MaterialListActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link SubjectListFragment} and the item details (if present) is a
 * {@link MaterialGridFragment}.
 * <p>
 * This activity also implements the required
 * {@link SubjectListFragment.Callbacks} interface to listen for item
 * selections.
 */
public class SubjectListActivity extends TeachActivity implements SubjectListFragment.Callbacks {

	public static final String TAG = "SubjectListActivity";
  /**
   * Whether or not the activity is in two-pane mode, i.e. running on a tablet
   * device.
   */
  private boolean mTwoPane;
  
  public void SubjectListActivity() {
	  
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_subject_list);

	if (findViewById(R.id.material_detail_container) != null) {
	  // The detail container view will be present only in the
	  // large-screen layouts (res/values-large and
	  // res/values-sw600dp). If this view is present, then the
	  // activity should be in two-pane mode.
	  mTwoPane = true;

	  // In two-pane mode, list items should be given the
	  // 'activated' state when touched.
	  ((SubjectListFragment) getFragmentManager().findFragmentById(R.id.subject_list)).setActivateOnItemClick(true);

	}

	// gridview.setOnItemClickListener(new OnItemClickListener() {
	// public void onItemClick(AdapterView<?> parent, View v, int position, long
	// id) {
	// Toast.makeText(HelloGridView.this, "" + position,
	// Toast.LENGTH_SHORT).show();
	// }
	// });

	// TODO: If exposing deep links into your app, handle intents here.
	
	mEvernoteSession.authenticate(this);
	
  }
  
  
  public void getNotelist(String subjectMame) {
		try {
			
			mEvernoteSession.getClientFactory().createNoteStoreClient().listNotebooks(new OnClientCallback<List<Notebook>>() {
				int mSelectedPos = -1;
				
				@Override
				public void onSuccess(final List<Notebook> notebooks) {
					for(Notebook notebook : notebooks) {
						Log.i(TAG, notebook.getName());
					}
				}

				@Override
				public void onException(Exception exception) {
					
				}
			});
		}
		catch (TTransportException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
		}
	  }
  
  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
      //Update UI when oauth activity returns result
      case EvernoteSession.REQUEST_CODE_OAUTH:
        if (resultCode == Activity.RESULT_OK) {
          getNotelist("history");
        }
        break;
    }
  }

  /**
   * Callback method from {@link SubjectListFragment.Callbacks} indicating that
   * the item with the given ID was selected.
   */
  @Override
  public void onItemSelected(String id) {
	
	if (mTwoPane) {
	  // In two-pane mode, show the detail view in this activity by
	  // adding or replacing the detail fragment using a
	  // fragment transaction.
	  Bundle arguments = new Bundle();
	  arguments.putString(MaterialGridFragment.ARG_ITEM_ID, id);
	  MaterialGridFragment fragment = new MaterialGridFragment();
	  fragment.setArguments(arguments);
	  getFragmentManager().beginTransaction().replace(R.id.material_detail_container, fragment).commit();
	} else {
	  // In single-pane mode, simply start the detail activity
	  // for the selected item ID.
	  Intent detailIntent = new Intent(this, MaterialListActivity.class);
	  detailIntent.putExtra(MaterialGridFragment.ARG_ITEM_ID, id);
	  startActivity(detailIntent);
	}
  }
}
