package br.com.lsukys.centraldeerros.dto.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.lsukys.centraldeerros.dto.ApplicationDTO;
import br.com.lsukys.centraldeerros.entity.Application;


@Mapper(componentModel = "spring", uses = {})
public interface ApplicationMapper {

	@Mapping(source = "createdBy.id", target = "createdByUserId")
	ApplicationDTO applicationToApplicationDTO(Application entity);
	
	@InheritInverseConfiguration
	Application applicationDTOToApplication(ApplicationDTO dto);

	List<ApplicationDTO> applicationListToApplicationDTOList(List<Application> list);

	List<Application> applicationDTOListToApplicationList(List<ApplicationDTO> list);
}
