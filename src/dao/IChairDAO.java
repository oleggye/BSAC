package dao;

import hibernateFiles.entity.Chair;
import java.util.List;

import dao.exception.DAOException;

public interface IChairDAO {

	public void add(Chair chair) throws DAOException;

	public void update(Chair chair) throws DAOException;

	public Chair getById(Number chair_id) throws DAOException;

	public List<Chair> getAll() throws DAOException;

	public void delete(Chair chair) throws DAOException;
}