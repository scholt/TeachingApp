package de.tobiasfiebiger.mobile.teachapp.widget;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import de.tobiasfiebiger.mobile.teachapp.TeachingApp;

public abstract class MasterAdapter<T> extends BaseAdapter {

  protected ArrayList<T>   dataObjects;
  protected LayoutInflater inflater;

  public MasterAdapter(Activity context) {
	if (context != null) {
	  inflater = context.getLayoutInflater();
	} else {
	  inflater = LayoutInflater.from(TeachingApp.getApp());
	}
  }

  public void setData(ArrayList<T> newData) {
	this.dataObjects = null;
	this.dataObjects = newData;
	notifyDataSetChanged();
  }

  @Override
  public int getCount() {
	int count = 0;
	if (dataObjects != null) {
	  count = dataObjects.size();
	}
	return count;
  }

  @Override
  public Object getItem(int position) {
	Object obj = null;
	if (dataObjects != null && dataObjects.size() > position) {
	  obj = dataObjects.get(position);
	}
	return obj;
  }

  @Override
  public long getItemId(int position) {
	return position;
  }

  @Override
  abstract public View getView(int position, View convertView, ViewGroup parent);

}
