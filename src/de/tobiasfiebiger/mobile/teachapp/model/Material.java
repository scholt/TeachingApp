package de.tobiasfiebiger.mobile.teachapp.model;

import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

import com.evernote.edam.type.Note;

public class Material {
	
  public URL thumbnailUrl = null;

  public Material(Note note) {
	this.note = note;
  }

  private final static String TAG = "Material";

  private Note                note;

  public String getId() {
	return note.getGuid();
  }

  public String getTitle() {
	return note.getTitle();
  }
  
  public void setThumbnailURL(String myUrl) {
	  try {
		  thumbnailUrl = new URL(myUrl);
	  } catch (MalformedURLException e) {
		  Log.e(TAG, "could not generate thumbnail URL", e);
	  }
  }

  public URL getThumbnailURL() {	
	return thumbnailUrl;
  }
  
  public String toString() {
	  return "<Material:"+ getTitle() + ">";
  }

}
