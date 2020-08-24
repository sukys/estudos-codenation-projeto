package br.com.lsukys.centraldeerros.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lsukys.centraldeerros.entity.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer>{

}
