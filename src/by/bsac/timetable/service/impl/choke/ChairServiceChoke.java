package by.bsac.timetable.service.impl.choke;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import by.bsac.timetable.hibernateFiles.entity.Chair;
import by.bsac.timetable.service.IChairService;
import by.bsac.timetable.service.exception.ServiceException;

public class ChairServiceChoke implements IChairService {

	private static List<Chair> chairList = new ArrayList<>();

	static {
		Chair chair1 = new Chair();
		chair1.setIdChair((byte) 1);
		chair1.setNameChair("ПОСТ");
		Chair chair2 = new Chair();
		chair2.setIdChair((byte) 2);
		chair2.setNameChair("Еще какая-то");
		Chair chair3 = new Chair();
		chair3.setIdChair((byte) 3);
		chair3.setNameChair("Тоже типо кафедра");

		chairList = Arrays.asList(chair1, chair2, chair3);
	}

	@Override
	public void addChair(Chair chair) throws ServiceException {
		System.out.println("New chair was added! " + chair);
	}

	@Override
	public void updateChair(Chair chair) throws ServiceException {
		System.out.println("New chair with id =" + chair.getIdChair() + " was updated to ! " + chair);
	}

	@Override
	public Chair getChairById(byte idChair) throws ServiceException {
		return chairList.get(idChair - 1);
	}

	@Override
	public List<Chair> getAllChair() throws ServiceException {
		return chairList;
	}

	@Override
	public void deleteChair(Chair chair) throws ServiceException {
		System.out.println(chair + " was deleted! ");
	}
}