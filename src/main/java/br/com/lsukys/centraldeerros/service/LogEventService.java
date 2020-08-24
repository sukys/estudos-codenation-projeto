package br.com.lsukys.centraldeerros.service;

import java.util.List;

import javax.naming.directory.InvalidAttributesException;

import br.com.lsukys.centraldeerros.entity.LogEvent;
import br.com.lsukys.centraldeerros.entity.LogLevelCount;

public interface LogEventService extends CrudService<LogEvent, Integer> {

	LogLevelCount countByLevel(Integer applicationId, String nivel) throws InvalidAttributesException;

	List<LogLevelCount> countAllLevels(Integer applicationId) throws InvalidAttributesException;
	
}
