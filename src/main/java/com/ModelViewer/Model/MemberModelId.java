package com.ModelViewer.Model;

import java.io.Serializable;

public class MemberModelId implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6676621279977420137L;
	
	private String userName;
	private String member;
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof MemberModelId){
			MemberModelId pobj = (MemberModelId) obj;
			if(pobj.userName.equals(this.userName) &&
					pobj.member.equals(this.member))
				return true;
		}
		return false;
	}
	
	@Override 
	public int hashCode(){
		return (this.userName+this.member).hashCode();
	}
}
