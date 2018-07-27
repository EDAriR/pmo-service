package com.syntrontech.pmo.redis;

public class ObjectMessage {
	
	private String id;
	
	private ObjectMessageAction action;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ObjectMessageAction getAction() {
		return action;
	}

	public void setAction(ObjectMessageAction action) {
		this.action = action;
	}
	
	@Override
	public String toString(){
		return "ObjectMessage[id:"+id+", action:"+action+"]";
	}
}
