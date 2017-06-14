/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.bsac.timetable.service;

import java.util.List;

import by.bsac.timetable.hibernateFiles.entity.Chair;
import by.bsac.timetable.hibernateFiles.entity.Lecturer;
import by.bsac.timetable.service.exception.ServiceException;

public interface ILecturerService {

	public void addLecturer(Lecturer lecturer) throws ServiceException;

	public void updateLecturer(Lecturer lecturer) throws ServiceException;

	public Lecturer getLecturerById(int lecturer_id) throws ServiceException;

	public List<Lecturer> getAllLecturers() throws ServiceException;

	public void deleteLecturer(Lecturer lecturer) throws ServiceException;

	public List<Lecturer> getLecturersRecordsByChairId(Chair chair) throws ServiceException;
}