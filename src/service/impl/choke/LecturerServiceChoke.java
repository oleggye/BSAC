package service.impl.choke;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import hibernateFiles.entity.Chair;
import hibernateFiles.entity.Lecturer;
import hibernateFiles.entity.Record;
import hibernateFiles.entity.builder.LecturerBuilder;
import service.ILecturerService;
import service.exception.ServiceException;

public class LecturerServiceChoke implements ILecturerService {

	private static List<Lecturer> lecturerList = new ArrayList<>();

	static {
		ChairServiceChoke chairChoke = new ChairServiceChoke();

		try {
			Chair chair1 = chairChoke.getChairById(1);
			Chair chair2 = chairChoke.getChairById(2);
			Chair chair3 = chairChoke.getChairById(3);

			Lecturer lect1 = new LecturerBuilder().buildChair(chair1).buildId((short) 1).buildNameLecturer("Kate").build();
			Lecturer lect2 = new LecturerBuilder().buildChair(chair2).buildId((short) 2).buildNameLecturer("John").build();
			Lecturer lect3 = new LecturerBuilder().buildChair(chair3).buildId((short) 3).buildNameLecturer("Victor").build();

			lecturerList = Arrays.asList(lect1, lect2, lect3);

		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addLecturer(Lecturer lecturer) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateLecturer(Lecturer lecturer) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public Lecturer getLecturerById(int lecturer_id) throws ServiceException {
		// TODO Auto-generated method stub
		return lecturerList.get(lecturer_id - 1);
	}

	@Override
	public List<Lecturer> getAllLecturers() throws ServiceException {
		// TODO Auto-generated method stub
		return lecturerList;
	}

	@Override
	public void deleteLecturer(Lecturer lecturer) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Lecturer> getLecturersRecordsByChairId(Chair chair) throws ServiceException {
		ListIterator<Lecturer> iterator = lecturerList.listIterator();
		while (iterator.hasNext()) {
			Lecturer record = iterator.next();
			record.setChair(chair);
		}
		return lecturerList;
	}
}