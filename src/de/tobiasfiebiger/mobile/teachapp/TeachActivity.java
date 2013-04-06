package de.tobiasfiebiger.mobile.teachapp;

import android.app.Activity;
import android.os.Bundle;

import com.evernote.client.android.EvernoteSession;

public class TeachActivity extends Activity {
	private static final String CONSUMER_KEY = "uwekamper-0814";
	private static final String CONSUMER_SECRET = "03d370b9ac2816fc";
	
	public static final String TOKEN = "S=s1:U=64b22:E=14537b80239:C=13de006d63c:P=1cd:A=en-devtoken:V=2:H=61473191ddf982a46d1a3184eeb71a40";

	// Initial development is done on Evernote's testing service, the sandbox.
	// Change to HOST_PRODUCTION to use the Evernote production service
	private static final EvernoteSession.EvernoteService EVERNOTE_SERVICE = EvernoteSession.EvernoteService.SANDBOX;

	protected EvernoteSession mEvernoteSession;
	
	protected final int DIALOG_PROGRESS = 101;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Set up the Evernote Singleton Session
		
		mEvernoteSession = EvernoteSession.getInstance(this, CONSUMER_KEY, CONSUMER_SECRET, EVERNOTE_SERVICE);
	}
	
	

	
}
