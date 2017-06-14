/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.bsac.timetable.service;

import java.util.List;

import by.bsac.timetable.hibernateFiles.entity.Chair;
import by.bsac.timetable.hibernateFiles.entity.Subject;
import by.bsac.timetable.service.exception.ServiceException;

public interface ISubjectService {

	public void addSubject(Subject subject) throws ServiceException;

	public void updateSubject(Subject subject) throws ServiceException;

	public Subject getSubjectById(int subject_id) throws ServiceException;

	public List<Subject> getAllSubjects() throws ServiceException;

	public void deleteSubject(Subject subject) throws ServiceException;

	public List<Subject> getSubjectsRecordsByChairId(Chair chair) throws ServiceException;

	public List<Subject> getSubjectsRecordsByChairIdAndEduLevel(Chair chair, byte eduLevel) throws ServiceException;
}