package com.lfn.Web.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsuarioCreateDto {
	

	@NotBlank
	@Email(message = "Formato do email invalido")
	private String username;
	
	@NotBlank
	@Size(min = 6, max = 6)
	private String password;
	
	
	
	public UsuarioCreateDto() {
	}
	
	
	public UsuarioCreateDto(String username, String password) {
		this.username = username;
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
