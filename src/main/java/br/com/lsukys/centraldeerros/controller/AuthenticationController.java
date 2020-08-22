package br.com.lsukys.centraldeerros.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import br.com.lsukys.centraldeerros.config.TokenProvider;
import br.com.lsukys.centraldeerros.dto.ErrorDTO;
import br.com.lsukys.centraldeerros.dto.UserDTO;
import br.com.lsukys.centraldeerros.dto.mapper.UserMapper;
import br.com.lsukys.centraldeerros.entity.User;
import br.com.lsukys.centraldeerros.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("v1/")
@Api(value = "Authentication", tags = { "Login" })
public class AuthenticationController {

	@Autowired
	private TokenProvider jwtTokenUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	BuildProperties buildProperties;

	@Autowired
	private UserService service;

	@Autowired
	private UserMapper mapper;

	/**
	 * Efetua o login na aplicação.
	 * 
	 * @param loginUser
	 * @return
	 * @throws AuthenticationException
	 */
	@ApiOperation(value = "Login by username, username@domain or email", notes = "Para logar com ADMIN usar <b>'username'</b> ou <b>'email'</b>. \n"
			+ "Para logar com USER usar <b>'username@domain'</b> ou <b>'email'</b>.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = AuthenticationReponse.class),
			@ApiResponse(code = 400, message = "Bad Request", response = ErrorDTO.class) })
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "Application/Json")
	public ResponseEntity<AuthenticationReponse> register(@RequestBody UserDTO loginUser)
			throws AuthenticationException {
		try {
			final Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			final String token = jwtTokenUtil.generateToken(authentication);
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("token", token);

			return new ResponseEntity<AuthenticationReponse>(new AuthenticationReponse(token), responseHeaders,
					HttpStatus.OK);

		} catch (Exception e) {
			// Tratativa específica para casos de e-mails repetidos na base de dados
			if (e.getMessage() != null && e.getMessage().contains("NonUniqueResultException")) {
				throw new RestClientException("Resultado com mais de um registro.");
			}
			log.warn("Tentativa de login inválida: {}", loginUser.getEmail());
			log.error("Exceção ocorrida: ", e);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	/**
	 * Preenche o response de acordo com o tipo de usuário que está logando. - ADMIN
	 * recebe apenas o token; - USER recebe o token e outras informações para
	 * conexão com o FS.
	 * 
	 * @param token
	 * @param loggedUser
	 * @param scheduleStartTO
	 * @param e
	 * @return
	 */
	private AuthenticationReponse fillResponse(final String token, User loggedUser) {
		AuthenticationReponse response;
		if (loggedUser.getAuthorities().contains("ADMIN")) {
			response = new AuthenticationReponse(token);
		} else {
			response = new AuthenticationReponse(token);
		}
		return response;
	}

	/**
	 * Recupera os dados sobre a versão da aplicação.
	 * 
	 * @return
	 * @throws AuthenticationException
	 */
	@RequestMapping(value = "/version", method = RequestMethod.GET, produces = "Application/Json")
	public ResponseEntity<String> getVersion() throws AuthenticationException {
		try {
			StringBuilder body = new StringBuilder();
			body.append("{");
			body.append("\"id\" : \"");
			body.append(buildProperties.getName());
			body.append("\",\"groupId\" : \"");
			body.append(buildProperties.getGroup());
			body.append("\",\"artifactId\" : \"");
			body.append(buildProperties.getArtifact());
			body.append("\",\"version\" : \"");
			body.append(buildProperties.getVersion());
			body.append("\",\"buildAt\" : \"");
			body.append(buildProperties.getTime());
			body.append("\"}");
			return ResponseEntity.ok(body.toString());
		} catch (BadCredentialsException b) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

}

@Data
@AllArgsConstructor
class AuthenticationReponse {
	private String token;
	private String warning;
	private String user;
	private String password;
	private String domain;

	public AuthenticationReponse(String token) {
		super();
		this.token = token;
	}

}
