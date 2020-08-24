package br.com.lsukys.centraldeerros.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.lsukys.centraldeerros.entity.User;

public interface UserService extends CrudService<User, Integer> {

	Optional<User> findByEmail(String email);

	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}
