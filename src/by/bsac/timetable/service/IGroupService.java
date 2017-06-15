/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.bsac.timetable.service;

import java.util.List;

import by.bsac.timetable.hibernateFiles.entity.Faculty;
import by.bsac.timetable.hibernateFiles.entity.Flow;
import by.bsac.timetable.hibernateFiles.entity.Group;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;

public interface IGroupService {

	public void addGroup(Group group) throws ServiceException, ServiceValidationException;

	public void updateGroup(Group group) throws ServiceException, ServiceValidationException;

	public Group getGroupById(int group_id) throws ServiceException;

	public List<Group> getAllGroups() throws ServiceException;

	public void deleteGroup(Group group) throws ServiceException;

	public List<Group> getGroupsRecordsByFacultyId(Faculty faculty) throws ServiceException;

	public List<Group> getGroupsRecordsByFacultyIdAndEduLevel(Faculty faculty, byte eduLevel) throws ServiceException;

	public List<Group> getGroupListByFlow(Flow flow) throws ServiceException, ServiceValidationException;
}