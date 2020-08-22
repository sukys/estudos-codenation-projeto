package br.com.lsukys.centraldeerros.config;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import br.com.lsukys.centraldeerros.dto.UserDTO;
import br.com.lsukys.centraldeerros.dto.mapper.UserMapper;
import br.com.lsukys.centraldeerros.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Geração e validação de tokens JWT.
 * 
 * @author schmitt
 *
 */
@Component
public class TokenProvider implements Serializable {

	private static final long serialVersionUID = 4745560122673316499L;

	public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 1 * 60 * 60; // 1h
	public static final String SIGNING_KEY = "c3ntra1d33rro5";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String AUTHORITIES_KEY = "scopes";

	@Autowired
	private UserMapper userMapper;

	/**
	 * Método para geração do token, incluindo todos os dados adicionais de usuário
	 * 
	 * @param authentication
	 * @return
	 */
	public String generateToken(Authentication authentication) {
		final List<String> authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
		User user = (User) authentication.getPrincipal();

		return TOKEN_PREFIX + Jwts.builder().setSubject(authentication.getName())
				// adiciona os campos adicionais de usuário no token
				.claim("email", user.getEmail()).claim("idUser", user.getId()).claim("firstName", user.getFirstName())
				.claim("lastName", user.getLastName()).claim(AUTHORITIES_KEY, authorities)
				.signWith(SignatureAlgorithm.HS256, SIGNING_KEY).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000)).compact();
	}

	/**
	 * Método para validação do token e extração das informações necessárias na
	 * aplicação
	 * 
	 * @param token
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Authentication validateTokenAndGetAuthentication(String token) throws Exception {
		// Valida o token
		Jws<Claims> jwsClaims = Jwts.parser().setSigningKey(SIGNING_KEY).requireExpiration(null).parseClaimsJws(token);

		// Obtém as claims do body do JWS e transforma o campo "roles" em uma collection
		// de GrantedAuthority
		Claims claims = jwsClaims.getBody();
		List<SimpleGrantedAuthority> authorities = ((List<String>) claims.get(AUTHORITIES_KEY)).stream()
				.map(SimpleGrantedAuthority::new).collect(Collectors.toList());

		User userEntity = recuperarDadosAdicionaisUsuarioToken(claims);
		Long iat = Long.parseLong(claims.get("iat").toString());

		isTokenInvalid(iat);

		User user = userEntity;
		// new User(claims.getSubject(), userEntity, authorities, iat);
		return new PreAuthenticatedAuthenticationToken(user, token, authorities);
	}

	/**
	 * Valida se o toke e inválido por um dos seguintes motivos: - Usuário invalidou
	 * o token através de chamada de função específica pra isso; - Token contém
	 * algum domain que foi excluído - <<adicionar>>
	 * 
	 * @param userEntity
	 * @param iat
	 * @throws Exception
	 */
	private void isTokenInvalid(Long iat) throws Exception {

		LocalDateTime ldtIatToken = Instant.ofEpochSecond(iat).atZone(ZoneId.systemDefault()).toLocalDateTime();

		if (ldtIatToken.isAfter(LocalDateTime.now())) {
			throw new Exception("Token expired");
		}
	}

	/**
	 * Método para recuperar os dados customizados de usuário que estao dentro do
	 * token JWT
	 * 
	 * @param claims
	 * @return
	 */
	private User recuperarDadosAdicionaisUsuarioToken(Claims claims) {
		UserDTO userDto = new UserDTO();
		userDto.setId(claims.get("idUser") != null ? Integer.parseInt(claims.get("idUser").toString()) : null);
		userDto.setFirstName(claims.get("firstName") != null ? claims.get("firstName").toString() : null);
		userDto.setLastName(claims.get("lastName") != null ? claims.get("lastName").toString() : null);
		userDto.setEmail(claims.get("email") != null ? claims.get("email").toString() : null);
		userDto.setPassword("");
		return userMapper.userDTOToUser(userDto);
	}

}
