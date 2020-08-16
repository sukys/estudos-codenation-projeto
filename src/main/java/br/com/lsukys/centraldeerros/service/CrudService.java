package br.com.lsukys.centraldeerros.service;

import java.util.List;

/**
 * Interface para definição dos métodos CRUD padrão que são utilizados por todos
 * os services.
 * 
 * @author luis sukys
 *
 * @param <T> Entidade do CRUD
 * @param <U> Tipo do ID da entidade
 */
public interface CrudService<T, U> {

	/**
	 * Busca de todos os registros
	 * 
	 * @return Lista de registros encontrados
	 */
	List<T> findAll();

	/**
	 * Buscar registro por ID.
	 * 
	 * @param id da entidade a ser pesquisada
	 * @return Registro encontrado
	 */
	T findById(U id);

	/**
	 * Salvar/Atualizar registro.
	 * 
	 * @param entity   Registro a ser salvo
	 * @return Registro salvo
	 */
	T save(T entity);

	
	/**
	 * Excluir registro.
	 * 
	 * @param id
	 */
	void delete(U id);

	
}
