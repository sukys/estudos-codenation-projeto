package br.com.lsukys.centraldeerros.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lsukys.centraldeerros.entity.LogEvent;

@Repository
public interface LogEventRepository extends JpaRepository<LogEvent, Long> {

	Integer countByApplicationIdAndLevel(Long applicationId, String level);

}
