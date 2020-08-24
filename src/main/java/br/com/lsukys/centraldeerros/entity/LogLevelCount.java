package br.com.lsukys.centraldeerros.entity;

import br.com.lsukys.centraldeerros.enums.LogLevel;
import lombok.Data;

@Data
public class LogLevelCount {

	private Application application;
	
	private LogLevel level;

	private Integer quantidade;

}
