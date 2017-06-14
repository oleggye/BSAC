package by.bsac.timetable.service.impl.choke;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import by.bsac.timetable.hibernateFiles.entity.Chair;
import by.bsac.timetable.hibernateFiles.entity.Lecturer;
import by.bsac.timetable.hibernateFiles.entity.Subject;
import by.bsac.timetable.hibernateFiles.entity.builder.SubjectBuilder;
import by.bsac.timetable.service.ISubjectService;
import by.bsac.timetable.service.exception.ServiceException;

public class SubjectServiceChoke implements ISubjectService {

	private static List<Subject> subjectList = new ArrayList<>();

	static {
		ChairServiceChoke chairChoke = new ChairServiceChoke();

		try {
			Chair chair1 = chairChoke.getChairById((byte) 1);
			Chair chair2 = chairChoke.getChairById((byte) 2);
			Chair chair3 = chairChoke.getChairById((byte) 3);

			Subject subject1 = new SubjectBuilder().buildChair(chair1).buildId((byte) 1).buildNameSubject("Physics")
					.buildAbname("PH").build();
			Subject subject2 = new SubjectBuilder().buildChair(chair2).buildId((byte) 2).buildNameSubject("Math")
					.buildAbname("MT").build();
			Subject subject3 = new SubjectBuilder().buildChair(chair3).buildId((byte) 3).buildNameSubject("English")
					.buildAbname("EN").build();

			subjectList = Arrays.asList(subject1, subject2, subject3);

		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addSubject(Subject subject) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateSubject(Subject subject) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public Subject getSubjectById(int subject_id) throws ServiceException {
		// TODO Auto-generated method stub
		return subjectList.get(subject_id - 1);
	}

	@Override
	public List<Subject> getAllSubjects() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteSubject(Subject subject) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Subject> getSubjectsRecordsByChairId(Chair chair) throws ServiceException {
		// ListIterator<Subject> iterator = subjectList.listIterator();
		// while (iterator.hasNext()) {
		// Subject record = iterator.next();
		// record.setChair(chair);
		// }
		return subjectList;
	}

	@Override
	public List<Subject> getSubjectsRecordsByChairIdAndEduLevel(Chair chair, byte eduLevel) throws ServiceException {
		ListIterator<Subject> iterator = subjectList.listIterator();
		while (iterator.hasNext()) {
			Subject record = iterator.next();
			record.setChair(chair);
			record.setEduLevel((byte) eduLevel);
		}
		return subjectList;
	}
}