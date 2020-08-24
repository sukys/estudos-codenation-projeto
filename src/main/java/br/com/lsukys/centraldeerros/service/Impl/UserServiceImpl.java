package br.com.lsukys.centraldeerros.service.Impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.lsukys.centraldeerros.entity.User;
import br.com.lsukys.centraldeerros.repository.UserRepository;
import br.com.lsukys.centraldeerros.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	
	@Autowired
	UserRepository repository;
	
	public List<User> findAll() {
		return repository.findAll();
	}

	@Override
	public User findById(Integer id) {
		return repository.getOne(id);
	}

	@Override
	public User save(User entity) {
		entity.setDataCadastro(LocalDateTime.now());
		return repository.save(entity);
	}

	@Override
	public void delete(Integer id) {
		repository.deleteById(id);
	}

	@Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository.findByEmail(username).get();
	}
	
	
}
