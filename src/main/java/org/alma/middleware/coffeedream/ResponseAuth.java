package org.alma.middleware.coffeedream;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import java.io.Serializable;
import java.util.HashMap;

public class ResponseAuth implements Serializable {

	private String call;

    @JsonDeserialize
	private HashMap<String, String> answer;

	public ResponseAuth(){
		this("",null);
	}

	public ResponseAuth(String call, HashMap<String, String> answer){
		this.call = call;
		this.answer = answer;
	}

	public String getCall() {
		return call;
	}

	public void setCall(String call) {
		this.call = call;
	}

	public HashMap<String, String> getAnswer() {
		return answer;
	}

	public void setAnswer(HashMap<String, String> answer) {
		this.answer = answer;
	}
}