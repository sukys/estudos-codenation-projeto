package br.com.lsukys.centraldeerros.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ApplicationDTO {

	private Integer id;

	private String name;

	private String url;

	private String description;

	private String version;

	private String environment;

	private String token;

	private LocalDateTime dataCadastro;

	private Integer createdByUserId;

}
