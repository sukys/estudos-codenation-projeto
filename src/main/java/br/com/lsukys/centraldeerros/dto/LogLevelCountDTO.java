package br.com.lsukys.centraldeerros.dto;

import br.com.lsukys.centraldeerros.enums.LogLevel;
import lombok.Data;

@Data
public class LogLevelCountDTO {

	private String applicationName;
	
	private Long applicationId;
	
	private LogLevel level;

	private Integer quantidade;

}
