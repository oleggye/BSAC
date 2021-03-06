package service.impl.choke;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import hibernateFiles.entity.Faculty;
import hibernateFiles.entity.Group;
import hibernateFiles.entity.Subject;
import hibernateFiles.entity.builder.GroupBuilder;
import service.IGroupService;
import service.exception.ServiceException;

public class GroupServiceChoke implements IGroupService {

	private static List<Group> groupList = new ArrayList<>();

	static {
		Group group1 = new GroupBuilder().buildId((short) 1).buildName("СП441").build();
		Group group2 = new GroupBuilder().buildId((short) 2).buildName("СП442").build();
		Group group3 = new GroupBuilder().buildId((short) 3).buildName("СТ441").build();
		Group group4 = new GroupBuilder().buildId((short) 4).buildName("СТ442").build();

		groupList = Arrays.asList(group1, group2, group3, group4);
	}

	@Override
	public void addGroup(Group group) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateGroup(Group group) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public Group getGroupById(int group_id) throws ServiceException {
		return groupList.get(group_id - 1);
	}

	@Override
	public List<Group> getAllGroups() throws ServiceException {
		return groupList;
	}

	@Override
	public void deleteGroup(Group group) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Group> getGroupsRecordsByFacultyId(Faculty faculty) throws ServiceException {
		ListIterator<Group> iterator = groupList.listIterator();
		while (iterator.hasNext()) {
			Group record = iterator.next();
			record.setFaculty(faculty);
		}
		return groupList;
	}

	@Override
	public List<Group> getGroupsRecordsByFacultyIdAndEduLevel(Faculty faculty, byte eduLevel) throws ServiceException {
		ListIterator<Group> iterator = groupList.listIterator();
		while (iterator.hasNext()) {
			Group record = iterator.next();
			record.setFaculty(faculty);
			record.setEduLevel((byte) eduLevel);
		}
		return groupList;
	}
}
