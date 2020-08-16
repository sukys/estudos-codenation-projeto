package br.com.lsukys.centraldeerros.dto;

import lombok.Data;

@Data
public class ErrorDTO {
	private final String errorCode;
	private final String url;
	private final String friendlyMessage;
	private final String exceptionMessage;
}
