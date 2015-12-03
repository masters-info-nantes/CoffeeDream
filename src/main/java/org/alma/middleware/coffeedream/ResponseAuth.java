package org.alma.middleware.coffeedream;

import java.util.HashMap;

public class ResponseAuth {

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

	public Object getAnswer() {
		return answer;
	}

	public void setAnswer(HashMap<String, Object> answer) {
		this.answer = answer;
	}
}