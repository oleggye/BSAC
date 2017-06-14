package by.bsac.timetable.hibernateFiles.entity;
// Generated May 15, 2017 12:08:05 PM by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;

import components.IName;

/**
 * Faculty generated by hbm2java
 */
public class Faculty implements java.io.Serializable, Cloneable, IName {

	private static final long serialVersionUID = 1L;

	private Byte idFaculty;
	private String nameFaculty;
	private Set<?> groups = new HashSet<Object>(0);

	public Faculty() {
	}

	public Faculty(String nameFaculty) {
		this.nameFaculty = nameFaculty;
	}

	public Faculty(String nameFaculty, Set<?> groups) {
		this.nameFaculty = nameFaculty;
		this.groups = groups;
	}

	public Byte getIdFaculty() {
		return this.idFaculty;
	}

	public void setIdFaculty(Byte idFaculty) {
		this.idFaculty = idFaculty;
	}

	public String getNameFaculty() {
		return this.nameFaculty;
	}

	public void setNameFaculty(String nameFaculty) {
		this.nameFaculty = nameFaculty;
	}

	public Set<?> getGroups() {
		return this.groups;
	}

	public void setGroups(Set<?> groups) {
		this.groups = groups;
	}

	@Override
	public String getName() {
		return this.getNameFaculty();
	}

	@Override
	public String toString() {
		return "Faculty [idFaculty=" + idFaculty + ", nameFaculty=" + nameFaculty + "]";
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
}