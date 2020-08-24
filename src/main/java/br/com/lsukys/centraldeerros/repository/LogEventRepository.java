package br.com.lsukys.centraldeerros.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lsukys.centraldeerros.entity.LogEvent;
import br.com.lsukys.centraldeerros.enums.LogLevel;

@Repository
public interface LogEventRepository extends JpaRepository<LogEvent, Integer> {

	Integer countByApplicationIdAndLevel(Integer applicationId, LogLevel level);

}
