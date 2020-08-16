package br.com.lsukys.centraldeerros.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lsukys.centraldeerros.entity.Application;
import br.com.lsukys.centraldeerros.repository.ApplicationRepository;
import br.com.lsukys.centraldeerros.service.ApplicationService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ApplicationServiceImpl implements ApplicationService {

	@Autowired
	ApplicationRepository repository;

	public List<Application> findAll() {
		return repository.findAll();
	}

	@Override
	public Application findById(Long id) {
		return repository.getOne(id);
	}

	@Override
	public Application save(Application entity) {
		return repository.save(entity);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

}
