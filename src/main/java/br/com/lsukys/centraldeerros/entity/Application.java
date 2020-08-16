package br.com.lsukys.centraldeerros.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
public class Application {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message = "An application must have a name.")
	private String name;
	
	private String url;
	
	private String description;
	
	private String version;
	
	private String environment;
	
	private String token;
	
	private LocalDateTime dataCadastro;
	
	@ManyToOne
	private User createdBy;
	
	@OneToMany(mappedBy = "application")
	private List<LogEvent> logs;
	
}
