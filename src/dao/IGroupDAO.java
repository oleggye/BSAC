/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import hibernateFiles.entity.Faculty;
import hibernateFiles.entity.Flow;
import hibernateFiles.entity.Group;
import java.util.List;

import dao.exception.DAOException;

public interface IGroupDAO {

	public void add(Group group) throws DAOException;

	public void update(Group group) throws DAOException;

	public Group getById(Number group_id) throws DAOException;

	public List<Group> getAll() throws DAOException;

	public void delete(Group group) throws DAOException;

	public List<Group> getGroupListByFaculty(Faculty faculty) throws DAOException;

	public List<Group> getGroupListByFacultyAndEduLevel(Faculty faculty, byte eduLevel) throws DAOException;

	public List<Group> getGroupByFlow(Flow flow) throws DAOException;
}