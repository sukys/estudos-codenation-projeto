package br.com.lsukys.centraldeerros.dto.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.lsukys.centraldeerros.dto.LogEventDTO;
import br.com.lsukys.centraldeerros.entity.LogEvent;

@Mapper(componentModel = "spring", uses = {})
public interface LogEventMapper {

	@Mapping(source = "application.id", target = "applicationId")
	LogEventDTO logEventToLogEventDTO(LogEvent entity);
	
	@InheritInverseConfiguration
	LogEvent logEventDTOToLogEvent(LogEventDTO dto);

	List<LogEventDTO> logEventListToLogEventDTOList(List<LogEvent> list);

	List<LogEvent> logEventDTOListToLogEventList(List<LogEventDTO> list);
}
