package de.tobiasfiebiger.mobile.teachapp.model;

public class Subject {

  private String id;
  private String title;
  private String tagId;
  private int    materialCount;

  public String getId() {
	return id;
  }

  public void setId(String id) {
	this.id = id;
  }

  public String getTitle() {
	return title;
  }

  public void setTitle(String title) {
	this.title = title;
  }

  public String getTagId() {
	return tagId;
  }

  public void setTagId(String tagId) {
	this.tagId = tagId;
  }

  public int getMaterialCount() {
	return materialCount;
  }

  public void setMaterialCount(int materialCount) {
	this.materialCount = materialCount;
  }

}
