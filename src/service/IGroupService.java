/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import hibernateFiles.entity.Faculty;
import hibernateFiles.entity.Group;
import java.util.List;
import service.exception.ServiceException;

public interface IGroupService {

	public void addGroup(Group group) throws ServiceException;

	public void updateGroup(Group group) throws ServiceException;

	public Group getGroupById(int group_id) throws ServiceException;

	public List<Group> getAllGroups() throws ServiceException;

	public void deleteGroup(Group group) throws ServiceException;

	public List<Group> getGroupsRecordsByFacultyId(Faculty faculty) throws ServiceException;

	public List<Group> getGroupsRecordsByFacultyIdAndEduLevel(Faculty faculty, byte eduLevel) throws ServiceException;
}