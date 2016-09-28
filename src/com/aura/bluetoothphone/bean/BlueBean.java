package com.aura.bluetoothphone.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class BlueBean extends BaseBean{
	@Override
	protected void init(JSONObject jSon) throws JSONException {
	}
	
	private String name ;
	private String address ;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
