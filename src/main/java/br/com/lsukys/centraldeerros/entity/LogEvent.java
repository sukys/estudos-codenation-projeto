package br.com.lsukys.centraldeerros.entity;

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
@Entity
@Table(name="log_event", schema = "public")
public class LogEvent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Enumerated(EnumType.STRING)
	private LogLevel level;
	
	@ManyToOne
	@JoinColumn(name = "application_id")
	private Application application;
	
	private String description;
	
	private String log;
	
	private LocalDateTime date;
	
}
