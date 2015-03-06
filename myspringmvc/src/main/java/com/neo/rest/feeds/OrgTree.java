package com.neo.rest.feeds;

import java.util.List;
import java.util.ArrayList;

public class OrgTree {
	private String id;  
    
    private String text;  
      
    private List<OrgTree> children = new ArrayList<OrgTree>();  
      
    private String state;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<OrgTree> getChildren() {
		return children;
	}

	public void setChildren(List<OrgTree> children) {
		this.children = children;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
    
    
}
