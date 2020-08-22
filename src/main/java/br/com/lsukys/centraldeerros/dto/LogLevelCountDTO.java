package br.com.lsukys.centraldeerros.dto;

import lombok.Data;

@Data
public class LogLevelCountDTO {

	private String applicationName;
	
	private Long applicationId;
	
	private String level;

	private Integer quantidade;

}
