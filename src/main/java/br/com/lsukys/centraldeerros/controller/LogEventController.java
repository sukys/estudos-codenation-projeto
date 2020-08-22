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

import br.com.lsukys.centraldeerros.dto.ErrorDTO;
import br.com.lsukys.centraldeerros.dto.LogEventDTO;
import br.com.lsukys.centraldeerros.dto.LogLevelCountDTO;
import br.com.lsukys.centraldeerros.dto.mapper.LogEventMapper;
import br.com.lsukys.centraldeerros.entity.LogEvent;
import br.com.lsukys.centraldeerros.entity.LogLevelCount;
import br.com.lsukys.centraldeerros.service.LogEventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("v1/logEvent")
@Api(value = "LogEvent", tags = { "Aplicações" })
public class LogEventController {

	@Autowired
	private LogEventService service;

	@Autowired
	private LogEventMapper mapper;

	@ApiOperation(value = "findAll")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = LogEventDTO.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Bad Request", response = ErrorDTO.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ErrorDTO.class),
			@ApiResponse(code = 500, message = "Failure", response = ErrorDTO.class) })
	@GetMapping
	@Secured(value = { "ADMIN" })
	ResponseEntity<List<LogEventDTO>> findAll() {
		List<LogEvent> list = service.findAll();
		List<LogEventDTO> listDto = mapper.logEventListToLogEventDTOList(list);
		return new ResponseEntity<>(listDto, HttpStatus.OK);
	}

	@ApiOperation(value = "findById")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = LogEventDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = ErrorDTO.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ErrorDTO.class),
			@ApiResponse(code = 500, message = "Failure", response = ErrorDTO.class) })
	@GetMapping(value = "/{id}")
	@Secured(value = { "ADMIN" })
	ResponseEntity<LogEventDTO> findById(@ApiParam(value = "id para pesquisa", required = true) @PathVariable Long id) {
		return new ResponseEntity<>(mapper.logEventToLogEventDTO(service.findById(id)), HttpStatus.OK);
	}

	@ApiOperation(value = "create")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created", response = LogEventDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = ErrorDTO.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ErrorDTO.class),
			@ApiResponse(code = 500, message = "Failure", response = ErrorDTO.class) })
	@PostMapping
	@Secured(value = { "ADMIN" })
	ResponseEntity<LogEventDTO> save(@RequestBody LogEventDTO dto) {
		LogEvent entity = service.save(mapper.logEventDTOToLogEvent(dto));
		return new ResponseEntity<>(mapper.logEventToLogEventDTO(entity), HttpStatus.CREATED);
	}

	@ApiOperation(value = "update")
	@ApiResponses(value = { @ApiResponse(code = 20, message = "Success", response = LogEventDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = ErrorDTO.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ErrorDTO.class),
			@ApiResponse(code = 500, message = "Failure", response = ErrorDTO.class) })
	@PutMapping
	@Secured(value = { "ADMIN" })
	ResponseEntity<LogEventDTO> update(@RequestBody LogEventDTO dto) {
		LogEvent entity = service.save(mapper.logEventDTOToLogEvent(dto));
		return new ResponseEntity<>(mapper.logEventToLogEventDTO(entity), HttpStatus.OK);
	}

	@ApiOperation(value = "delete")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad Request", response = ErrorDTO.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ErrorDTO.class),
			@ApiResponse(code = 500, message = "Failure", response = ErrorDTO.class) })
	@DeleteMapping(value = "/{id}")
	@Secured(value = { "ADMIN" })
	public ResponseEntity<String> delete(@PathVariable Long id) {
		service.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	
	
	@ApiOperation(value = "Contar os logs da aplicação filtrando pelo nivel")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad Request", response = ErrorDTO.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ErrorDTO.class),
			@ApiResponse(code = 500, message = "Failure", response = ErrorDTO.class) })
	@DeleteMapping(value = "/{applicationId}/{level}")
	@Secured(value = { "ADMIN" })
	public ResponseEntity<String> count(@PathVariable Long applicationId, @PathVariable String level) {
		try {
			LogLevelCount count = service.countByLevel(applicationId, level);
			 
			LogLevelCountDTO levelCount = new LogLevelCountDTO();
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}


