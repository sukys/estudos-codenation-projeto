package br.com.lsukys.centraldeerros.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lsukys.centraldeerros.dto.ErrorDTO;
import br.com.lsukys.centraldeerros.dto.UserDTO;
import br.com.lsukys.centraldeerros.dto.mapper.UserMapper;
import br.com.lsukys.centraldeerros.entity.User;
import br.com.lsukys.centraldeerros.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("v1/user")
@Api(value = "User", tags = { "Usuário" })
public class UserController {

	@Autowired
	private UserService service;

	@Autowired
	private UserMapper mapper;

	@ApiOperation(value = "findAll")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = UserDTO.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Bad Request", response = ErrorDTO.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ErrorDTO.class),
			@ApiResponse(code = 500, message = "Failure", response = ErrorDTO.class) })
	@GetMapping
	ResponseEntity<List<UserDTO>> findAll() {
		List<User> list = service.findAll();
		List<UserDTO> listDto = mapper.userListToUserDTOList(list);
		return new ResponseEntity<>(listDto, HttpStatus.OK);
	}

	@ApiOperation(value = "findById")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = UserDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = ErrorDTO.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ErrorDTO.class),
			@ApiResponse(code = 500, message = "Failure", response = ErrorDTO.class) })
	@GetMapping(value = "/{id}")
	ResponseEntity<UserDTO> findById(@ApiParam(value = "id para pesquisa", required = true) @PathVariable Long id) {
		return new ResponseEntity<>(mapper.userToUserDTO(service.findById(id)), HttpStatus.OK);
	}

	@ApiOperation(value = "create")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created", response = UserDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = ErrorDTO.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ErrorDTO.class),
			@ApiResponse(code = 500, message = "Failure", response = ErrorDTO.class) })
	@PostMapping
	ResponseEntity<UserDTO> save(@RequestBody UserDTO dto) {
		User entity = service.save(mapper.userDTOToUser(dto));
		return new ResponseEntity<>(mapper.userToUserDTO(entity), HttpStatus.CREATED);
	}

	@ApiOperation(value = "update")
	@ApiResponses(value = { @ApiResponse(code = 20, message = "Success", response = UserDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = ErrorDTO.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ErrorDTO.class),
			@ApiResponse(code = 500, message = "Failure", response = ErrorDTO.class) })
	@PutMapping
	ResponseEntity<UserDTO> update(@RequestBody UserDTO dto) {
		User entity = service.save(mapper.userDTOToUser(dto));
		return new ResponseEntity<>(mapper.userToUserDTO(entity), HttpStatus.OK);
	}

	@ApiOperation(value = "delete")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad Request", response = ErrorDTO.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ErrorDTO.class),
			@ApiResponse(code = 500, message = "Failure", response = ErrorDTO.class) })
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		service.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
