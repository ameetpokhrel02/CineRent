package model;

public class User {
		private int  id;
		private String username;
		private String email;
		private String password;
		private String first_name;
		private String last_name;
		private int phone;
		private byte[] profile_image;
		private String role;
		
		public User() {}
		
		
		public User(int id, String username, String email, String password, String first_name, String last_name,
				int phone, byte[] profile_image, String role) {
			
			super();
			this.id = id;
			this.username = username;
			this.email = email;
			this.password = password;
			this.first_name = first_name;
			this.last_name = last_name;
			this.phone = phone;
			this.profile_image = profile_image;
			this.role = role;
			
		}
		
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getFirst_name() {
			return first_name;
		}
		public void setFirst_name(String first_name) {
			this.first_name = first_name;
		}
		public String getLast_name() {
			return last_name;
		}
		public void setLast_name(String last_name) {
			this.last_name = last_name;
		}
		public int getPhone() {
			return phone;
		}
		public void setPhone(int phone) {
			this.phone = phone;
		}
		public byte[] getProfile_image() {
			return profile_image;
		}
		public void setProfile_image(byte[] profile_image) {
			this.profile_image = profile_image;
		}
		public String getRole() {
			return role;
		}
		public void setRole(String role) {
			this.role = role;
		}
		
	}


