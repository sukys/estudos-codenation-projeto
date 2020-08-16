package br.com.lsukys.centraldeerros.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lsukys.centraldeerros.dto.ErrorDTO;
import br.com.lsukys.centraldeerros.dto.UserDTO;
import br.com.lsukys.centraldeerros.dto.mapper.UserMapper;
import br.com.lsukys.centraldeerros.entity.User;
import br.com.lsukys.centraldeerros.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping("v1/user")
@Api(value = "User", tags = {"Usuário"})
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
	
}
