package de.tobiasfiebiger.mobile.teachapp;

import java.util.ArrayList;
import java.util.List;

import com.evernote.client.android.EvernoteSession;
import com.evernote.client.android.OnClientCallback;
import com.evernote.edam.notestore.NoteFilter;
import com.evernote.edam.notestore.NoteList;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.NoteSortOrder;
import com.evernote.edam.type.Notebook;
import com.evernote.thrift.transport.TTransportException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import de.tobiasfiebiger.mobile.teachapp.model.Material;

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
  
  public MaterialGridFragment mFragment;

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
	
	
  }
  
  public void getTagGuid(String subjectName) {
	  
  }
  
  public void getNotelist(String subjectName) {
		try {
			String query = "tag:" + subjectName;
			
			NoteFilter filter = new NoteFilter();
		    filter.setWords(query);
		    filter.setOrder(NoteSortOrder.UPDATED.getValue());
		    filter.setAscending(false);
			
		    //String authToken = EvernoteSession.getAuthToken();
		    
			mEvernoteSession.getClientFactory().createNoteStoreClient().findNotes(filter, 0, 20, new OnClientCallback<NoteList>() {
				int mSelectedPos = -1;
				
				@Override
				public void onSuccess(NoteList notes) {
					ArrayList<Material> ml = new ArrayList<Material>();
					
					for(Note note : notes.getNotes()) {
						
						Material m = new Material(note);
						ml.add(m);
					}
					
					mFragment.setMaterialData(ml);
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
	  mFragment = new MaterialGridFragment();
	  mFragment.setArguments(arguments);
	  getFragmentManager().beginTransaction().replace(R.id.material_detail_container, mFragment).commit();
	  
	  if(mEvernoteSession.isLoggedIn()) {
			getNotelist("english");
	  } else {
			mEvernoteSession.authenticate(this);
      }
	 
	} else {
	  // In single-pane mode, simply start the detail activity
	  // for the selected item ID.
	  Intent detailIntent = new Intent(this, MaterialListActivity.class);
	  detailIntent.putExtra(MaterialGridFragment.ARG_ITEM_ID, id);
	  startActivity(detailIntent);
	}
  }
}
