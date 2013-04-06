package de.tobiasfiebiger.mobile.teachapp.util;

import java.util.ArrayList;

import de.tobiasfiebiger.mobile.teachapp.R;
import de.tobiasfiebiger.mobile.teachapp.model.Subject;

public class MockUtil {

  public static ArrayList<Subject> createMockSubjectList() {
	ArrayList<Subject> mockSubjects = new ArrayList<Subject>();
	mockSubjects.add(createSubject("id:1", "English", 23, R.drawable.subject_english));
	mockSubjects.add(createSubject("id:2", "History", 47, R.drawable.subject_history));
	mockSubjects.add(createSubject("id:3", "Math", 13, R.drawable.subject_math));
	mockSubjects.add(createSubject("id:4", "Physics", 666, R.drawable.subject_physics));
	mockSubjects.add(createSubject("id:5", "Sport", 123, R.drawable.subject_sport));
	return mockSubjects;
  }

  private static Subject createSubject(String id, String title, int materialCount, int drawableId) {
	Subject object = new Subject();
	object.setId(id);
	object.setTitle(title);
	object.setMaterialCount(materialCount);
	object.setDrawableId(drawableId);
	return object;
  }

}
