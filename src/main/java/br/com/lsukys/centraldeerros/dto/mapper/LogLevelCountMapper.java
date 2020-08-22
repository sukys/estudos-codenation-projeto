package br.com.lsukys.centraldeerros.dto.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.lsukys.centraldeerros.dto.LogLevelCountDTO;
import br.com.lsukys.centraldeerros.entity.LogLevelCount;

@Mapper(componentModel = "spring", uses = {})
public interface LogLevelCountMapper {

	@Mapping(source = "application.id", target = "applicationId")
	@Mapping(source = "application.name", target = "applicationName")
	LogLevelCountDTO logLevelCountToLogLevelCountDTO(LogLevelCount entity);
	
	@Mapping(source = "applicationId", target = "application.id")
	LogLevelCount logLevelCountDTOToLogLevelCount(LogLevelCountDTO dto);

	List<LogLevelCountDTO> logLevelCountListToLogLevelCountDTOList(List<LogLevelCount> list);

	List<LogLevelCount> logLevelCountDTOListToLogLevelCountList(List<LogLevelCountDTO> list);
}
