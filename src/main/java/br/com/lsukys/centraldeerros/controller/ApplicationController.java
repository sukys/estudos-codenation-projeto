package br.com.lsukys.centraldeerros.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lsukys.centraldeerros.dto.ApplicationDTO;
import br.com.lsukys.centraldeerros.dto.ErrorDTO;
import br.com.lsukys.centraldeerros.dto.mapper.ApplicationMapper;
import br.com.lsukys.centraldeerros.entity.Application;
import br.com.lsukys.centraldeerros.service.ApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("v1/application")
@Api(value = "Application", tags = { "Aplicações" })
public class ApplicationController {

	@Autowired
	private ApplicationService service;

	@Autowired
	private ApplicationMapper mapper;

	@ApiOperation(value = "findAll")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = ApplicationDTO.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Bad Request", response = ErrorDTO.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ErrorDTO.class),
			@ApiResponse(code = 500, message = "Failure", response = ErrorDTO.class) })
	@GetMapping
	@Secured(value = { "ADMIN" })
	ResponseEntity<List<ApplicationDTO>> findAll() {
		List<Application> list = service.findAll();
		List<ApplicationDTO> listDto = mapper.applicationListToApplicationDTOList(list);
		return new ResponseEntity<>(listDto, HttpStatus.OK);
	}

	@ApiOperation(value = "findById")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = ApplicationDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = ErrorDTO.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ErrorDTO.class),
			@ApiResponse(code = 500, message = "Failure", response = ErrorDTO.class) })
	@GetMapping(value = "/{id}")
	@Secured(value = { "ADMIN" })
	ResponseEntity<ApplicationDTO> findById(@ApiParam(value = "id para pesquisa", required = true) @PathVariable Integer id) {
		return new ResponseEntity<>(mapper.applicationToApplicationDTO(service.findById(id)), HttpStatus.OK);
	}

	@ApiOperation(value = "create")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created", response = ApplicationDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = ErrorDTO.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ErrorDTO.class),
			@ApiResponse(code = 500, message = "Failure", response = ErrorDTO.class) })
	@PostMapping
	@Secured(value = { "ADMIN" })
	ResponseEntity<ApplicationDTO> save(@RequestBody ApplicationDTO dto) {
		Application entity = service.save(mapper.applicationDTOToApplication(dto));
		return new ResponseEntity<>(mapper.applicationToApplicationDTO(entity), HttpStatus.CREATED);
	}

	@ApiOperation(value = "update")
	@ApiResponses(value = { @ApiResponse(code = 20, message = "Success", response = ApplicationDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = ErrorDTO.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ErrorDTO.class),
			@ApiResponse(code = 500, message = "Failure", response = ErrorDTO.class) })
	@PutMapping
	@Secured(value = { "ADMIN" })
	ResponseEntity<ApplicationDTO> update(@RequestBody ApplicationDTO dto) {
		Application entity = service.save(mapper.applicationDTOToApplication(dto));
		return new ResponseEntity<>(mapper.applicationToApplicationDTO(entity), HttpStatus.OK);
	}

	@ApiOperation(value = "delete")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad Request", response = ErrorDTO.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ErrorDTO.class),
			@ApiResponse(code = 500, message = "Failure", response = ErrorDTO.class) })
	@DeleteMapping(value = "/{id}")
	@Secured(value = { "ADMIN" })
	public ResponseEntity<String> delete(@PathVariable Integer id) {
		service.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	
}
