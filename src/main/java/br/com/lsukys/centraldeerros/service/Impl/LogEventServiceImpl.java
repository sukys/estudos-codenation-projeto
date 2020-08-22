package br.com.lsukys.centraldeerros.service.Impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.naming.directory.InvalidAttributesException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lsukys.centraldeerros.entity.LogEvent;
import br.com.lsukys.centraldeerros.entity.LogLevelCount;
import br.com.lsukys.centraldeerros.enums.LogLevel;
import br.com.lsukys.centraldeerros.repository.LogEventRepository;
import br.com.lsukys.centraldeerros.service.LogEventService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LogEventServiceImpl implements LogEventService {

	@Autowired
	LogEventRepository repository;

	public List<LogEvent> findAll() {
		return repository.findAll();
	}

	@Override
	public LogEvent findById(Long id) {
		return repository.getOne(id);
	}

	@Override
	public LogEvent save(LogEvent entity) {
		return repository.save(entity);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	
	@Override
	public LogLevelCount countByLevel(Long applicationId, String nivel) {
		if(!Arrays.asList(LogLevel.values()).contains(nivel)) {
			String tipos = Stream.of(LogLevel.values()) 
                    .map(String::valueOf) 
                    .collect(Collectors.joining(" - ")); 
			throw new InvalidAttributesException("Nivel deve ser um dos tipos: [ " + tipos + " ]."); 
		}
		
		
		LogLevelCount count = new LogLevelCount();
		LogLevel x;
		repository.countByLevelAndApplicationId(applicationId, nivel);
		
		return count;
	}
	
}
