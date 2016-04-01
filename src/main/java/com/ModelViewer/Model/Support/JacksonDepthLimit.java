package com.ModelViewer.Model.Support;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class JacksonDepthLimit {
	@JsonIgnore
	int maxDepthLimit = 0;
	@JsonIgnore
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
