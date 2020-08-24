package br.com.lsukys.centraldeerros.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.naming.directory.InvalidAttributesException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lsukys.centraldeerros.entity.LogEvent;
import br.com.lsukys.centraldeerros.entity.LogLevelCount;
import br.com.lsukys.centraldeerros.enums.LogLevel;
import br.com.lsukys.centraldeerros.repository.ApplicationRepository;
import br.com.lsukys.centraldeerros.repository.LogEventRepository;
import br.com.lsukys.centraldeerros.service.LogEventService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LogEventServiceImpl implements LogEventService {

	@Autowired
	LogEventRepository repository;

	@Autowired
	ApplicationRepository applicationRepository;

	public List<LogEvent> findAll() {
		return repository.findAll();
	}

	@Override
	public LogEvent findById(Integer id) {
		return repository.getOne(id);
	}

	@Override
	public LogEvent save(LogEvent entity) {
		return repository.save(entity);
	}

	@Override
	public void delete(Integer id) {
		repository.deleteById(id);
	}

	@Override
	public LogLevelCount countByLevel(Integer applicationId, String nivel) throws InvalidAttributesException {
		nivel = nivel.toUpperCase();
		List<String> levelNames = Stream.of(LogLevel.values()).map(LogLevel::name).collect(Collectors.toList());
		if (!levelNames.contains(nivel)) {
			String tipos = Stream.of(LogLevel.values()).map(String::valueOf).collect(Collectors.joining(" - "));
			throw new InvalidAttributesException("Nivel deve ser um dos tipos: [ " + tipos + " ].");
		}
		LogLevelCount count = new LogLevelCount();
		count.setApplication(applicationRepository.getOne(applicationId));
		count.setLevel(LogLevel.valueOf(nivel));
		count.setQuantidade(repository.countByApplicationIdAndLevel(applicationId, LogLevel.valueOf(nivel)));
		return count;
	}

	@Override
	public List<LogLevelCount> countAllLevels(Integer applicationId) throws InvalidAttributesException {
		List<LogLevelCount> logs = new ArrayList<>();
		for (LogLevel level : LogLevel.values()) {
			logs.add(countByLevel(applicationId, level.name()));
		}
		return logs;
	}

}
