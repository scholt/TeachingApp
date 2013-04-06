package de.tobiasfiebiger.mobile.teachapp.model;

import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

import com.evernote.edam.type.Note;

public class Material {

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

  public URL getThumbnailURL() {
	URL thumbnailUrl = null;
	try {
	  thumbnailUrl = new URL("http://static.androidnext.de/androidnext-de-Marie-Schweiz.jpg");
	} catch (MalformedURLException e) {
	  Log.e(TAG, "could not generate thumbnail URL", e);
	}
	return thumbnailUrl;
  }

}
