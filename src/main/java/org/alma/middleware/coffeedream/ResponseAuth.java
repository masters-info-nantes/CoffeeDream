package org.alma.middleware.coffeedream;

public class ResponseAuth {

	private String call;
	private String answer;

	public ResponseAuth(String call, String answer){
		this.call = call;
		this.answer = answer;
	}
}