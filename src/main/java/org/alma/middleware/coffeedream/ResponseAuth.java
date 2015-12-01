package org.alma.middleware.coffeedream;

public class ResponseAuth {

	private String call;
	private Object answer;

	public ResponseAuth(){
		this("", null);
	}

	public ResponseAuth(String call, Object answer){
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

	public void setAnswer(Object answer) {
		this.answer = answer;
	}
}