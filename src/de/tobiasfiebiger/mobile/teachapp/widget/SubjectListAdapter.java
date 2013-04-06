package de.tobiasfiebiger.mobile.teachapp.widget;

import android.app.Activity;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import de.tobiasfiebiger.mobile.teachapp.R;
import de.tobiasfiebiger.mobile.teachapp.TeachingApp;
import de.tobiasfiebiger.mobile.teachapp.model.Subject;

public class SubjectListAdapter extends MasterAdapter<Subject> {

  public SubjectListAdapter(Activity context) {
	super(context);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
	if (inflater != null) {
	  @SuppressWarnings("unused")
	  Subject currentSubject = dataObjects.get(position);
	  SubjectViewHolder holder;
	  if (convertView == null) {
		convertView = inflater.inflate(R.layout.list_item_subject, null);

		holder = new SubjectViewHolder();
		holder.text = (TextView) convertView.findViewById(R.id.list_item_subject_textview);

		convertView.setTag(holder);
	  } else {
		holder = (SubjectViewHolder) convertView.getTag();
	  }

	  if (holder != null) {
		if (currentSubject != null) {
		  String text = TextUtils.concat("<b>", currentSubject.getTitle(), "</b><br/><i>",
			  String.valueOf(currentSubject.getMaterialCount()), " Matrials</i>").toString();
		  holder.text.setText(Html.fromHtml(text));
		  holder.text.setCompoundDrawablesWithIntrinsicBounds(currentSubject.getDrawableId(), 0, 0, 0);
		} else {
		  holder.text.setText("currentSubject ist null");
		}
	  } else {
		Toast.makeText(TeachingApp.getApp(), "view holder is null", Toast.LENGTH_LONG).show();

	  }
	}
	return convertView;
  }
}

class SubjectViewHolder {
  public TextView text;
}
