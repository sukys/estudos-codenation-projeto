package br.com.lsukys.centraldeerros.dto;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.lsukys.centraldeerros.enums.LogLevel;
import lombok.Data;

@Data
public class LogEventDTO {
	
	private Integer id;
	
	private LogLevel level;
	
	private Integer applicationId;
	
	private String description;
	
	private String log;
	
	private LocalDateTime date;
	
}
