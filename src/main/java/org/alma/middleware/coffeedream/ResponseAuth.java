package org.alma.middleware.coffeedream;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ResponseAuth implements Serializable {

	private String call;
	private HashMap<String, Object> answer;

	public ResponseAuth(){
		this("", null);
	}

	public ResponseAuth(String call, HashMap<String, Object> answer){
		this.call = call;
		this.answer = answer;
	}

	public String getCall() {
		return call;
	}

	public void setCall(String call) {
		this.call = call;
	}

	public Map<String, Object> getAnswer() {
		return answer;
	}

	public void setAnswer(HashMap<String, Object> answer) {
		this.answer = answer;
	}
}