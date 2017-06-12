package dao;

import java.util.List;

import dao.exception.DAOException;
import hibernateFiles.entity.Cancellation;

public interface ICancellationDAO {

	public void add(Cancellation object) throws DAOException;

	public void update(Cancellation object) throws DAOException;

	public void delete(Cancellation object) throws DAOException;

	public Cancellation getById(Number id) throws DAOException;

	public List<Cancellation> getAll() throws DAOException;
}
