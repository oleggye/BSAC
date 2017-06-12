package service;

import hibernateFiles.entity.Chair;
import java.util.List;
import service.exception.ServiceException;

public interface IChairService {

	public void addChair(Chair chair) throws ServiceException;

	public void updateChair(Chair chair) throws ServiceException;

	public Chair getChairById(int chair_id) throws ServiceException;

	public List<Chair> getAllChairs() throws ServiceException;

	public void deleteChair(Chair chair) throws ServiceException;
}