package com.ModelViewer.Model.Support;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class JacksonDepthLimit {
	@JsonIgnore
	@Transient
	int maxDepthLimit = 1;
	@JsonIgnore
	@Transient
	int currentDepthLimit = 0;
	
	
	public int getMaxDepthLimit() {
		return maxDepthLimit;
	}
	public void setMaxDepthLimit(int maxDepthLimit) {
		this.maxDepthLimit = maxDepthLimit;
	}
	public int getCurrentDepthLimit() {
		return currentDepthLimit;
	}
	public void setCurrentDepthLimit(int currentDepthLimit) {
		this.currentDepthLimit = currentDepthLimit;
	}

}
