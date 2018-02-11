package org.drecic.email.sparkmail.controllers;

public class UserController extends ResourceController {
	
	public static class CreateRequest {
		public String username;
		public String password;
		public String email;
		
		public CreateRequest(String username, String password, String email) {
			super();
			this.username = username;
			this.password = password;
			this.email = email;
		}

		public boolean isValid() {
			if (this.username == null || this.password == null || this.email == null) {
				return false;
			}
			
			this.username.trim();
			this.password.trim();
			this.email.trim();
			
			return true;
		}
	}
	
	public static class UpdateRequest {
		public String username;
		public String oldPassword;
		public String newPassword;
		public String email;
		
		public UpdateRequest(String username, String oldPassword, String newPassword, String email) {
			super();
			this.username = username;
			this.oldPassword = oldPassword;
			this.newPassword = newPassword;
			this.email = email;
		}

		public boolean isValid() {
			if (
					this.oldPassword == null || 
					this.newPassword == null || 
					this.email == null ||
					this.username == null
				) {
				return false;
			}

			this.oldPassword.trim();
			this.newPassword.trim();
			this.email.trim();
			
			return true;
		}
	}


	public UserController() {
		
	}

}
