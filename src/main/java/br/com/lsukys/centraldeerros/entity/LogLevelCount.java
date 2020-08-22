package br.com.lsukys.centraldeerros.entity;

import lombok.Data;

@Data
public class LogLevelCount {

	private Application application;
	
	private String level;

	private Integer quantidade;

}
