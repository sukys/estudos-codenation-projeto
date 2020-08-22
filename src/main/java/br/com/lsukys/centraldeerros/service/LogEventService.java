package br.com.lsukys.centraldeerros.service;

import br.com.lsukys.centraldeerros.entity.LogEvent;
import br.com.lsukys.centraldeerros.entity.LogLevelCount;

public interface LogEventService extends CrudService<LogEvent, Long> {

	LogLevelCount countByLevel(Long applicationId, String nivel);
	
}
