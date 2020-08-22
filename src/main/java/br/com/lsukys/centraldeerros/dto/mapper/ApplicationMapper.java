package br.com.lsukys.centraldeerros.dto.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import br.com.lsukys.centraldeerros.dto.ApplicationDTO;
import br.com.lsukys.centraldeerros.entity.Application;


@Mapper(componentModel = "spring", uses = {})
public interface ApplicationMapper {

	ApplicationDTO applicationToApplicationDTO(Application entity);
	
	Application applicationDTOToApplication(ApplicationDTO dto);

	List<ApplicationDTO> applicationListToApplicationDTOList(List<Application> list);

	List<Application> applicationDTOListToApplicationList(List<ApplicationDTO> list);
}
