package br.com.lsukys.centraldeerros.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserDTO {

	private Integer id;
	
	private String firstName;
	
	private String lasttName;

	private String email;
	
	private String password;
	
	private LocalDateTime dataCadastro;
	
}
