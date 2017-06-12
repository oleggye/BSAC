package dao;

import java.util.List;

import dao.exception.DAOException;

public interface IGenericDAO<E> {

	public void add(E object) throws DAOException;

	public void update(E object) throws DAOException;

	public void delete(E object) throws DAOException;

	public E getById(Number id) throws DAOException;

	public List<E> getAll() throws DAOException;
}
