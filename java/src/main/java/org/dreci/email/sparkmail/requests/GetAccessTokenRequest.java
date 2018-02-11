package org.bgalusha.email.sparkmail.requests;

public class GetAccessTokenRequest {
	public String refreshToken;

	public boolean isValid() {
		if (this.refreshToken == null) {
			return false;
		}
		
		this.refreshToken.trim();
		
		return true;
	}
}
