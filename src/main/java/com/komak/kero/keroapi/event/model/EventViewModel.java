package com.komak.kero.keroapi.event.model;

import java.util.Set;

public class EventViewModel {

  private String id;
  private String title;
  private long date;
  private String description;
  private String authorId;
  private Set<String> previews;

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

  public long getDate() {
    return date;
  }

  public void setDate(long date) {
    this.date = date;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getAuthorId() {
    return authorId;
  }

  public void setAuthorId(String authorId) {
    this.authorId = authorId;
  }

  public Set<String> getPreviews() {
    return previews;
  }

  public void setPreviews(Set<String> previews) {
    this.previews = previews;
  }
}
