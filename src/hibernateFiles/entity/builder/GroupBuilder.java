package hibernateFiles.entity.builder;

import hibernateFiles.entity.Faculty;
import hibernateFiles.entity.Flow;
import hibernateFiles.entity.Group;

public class GroupBuilder {

	private short idGroup;
	private Faculty faculty;
	private Flow flow;
	private String nameGroup;
	private byte eduLevel;

	public GroupBuilder buildId(short idGroup) {
		this.idGroup = idGroup;
		return this;
	}

	public GroupBuilder buildFaculty(Faculty faculty) {
		this.faculty = faculty;
		return this;
	}

	public GroupBuilder buildFlow(Flow flow) {
		this.flow = flow;
		return this;
	}

	public GroupBuilder buildName(String nameGroup) {
		this.nameGroup = nameGroup;
		return this;
	}

	public GroupBuilder buildEduLevel(byte eduLevel) {
		this.eduLevel = eduLevel;
		return this;
	}

	public Group build() {
		Group group = new Group();
		group.setIdGroup(idGroup);
		group.setFaculty(faculty);
		group.setFlow(flow);
		group.setNameGroup(nameGroup);
		group.setEduLevel(eduLevel);
		return group;
	}
}