package by.bsac.timetable.hibernateFiles.entity;
// Generated May 15, 2017 12:08:05 PM by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;

import components.IName;

/**
 * Lecturer generated by hbm2java
 */
public class Lecturer implements java.io.Serializable, Cloneable, IName {

	private static final long serialVersionUID = 1L;

	private Short idLecturer;
	private Chair chair;
	private String nameLecturer;
	private Set<?> records = new HashSet<Object>(0);

	public Lecturer() {
	}

	public Lecturer(Chair chair, String nameLecturer) {
		this.chair = chair;
		this.nameLecturer = nameLecturer;
	}

	public Lecturer(Chair chair, String nameLecturer, Set<?> records) {
		this.chair = chair;
		this.nameLecturer = nameLecturer;
		this.records = records;
	}

	public Short getIdLecturer() {
		return this.idLecturer;
	}

	public void setIdLecturer(Short idLecturer) {
		this.idLecturer = idLecturer;
	}

	public Chair getChair() {
		return this.chair;
	}

	public void setChair(Chair chair) {
		this.chair = chair;
	}

	public String getNameLecturer() {
		return this.nameLecturer;
	}

	public void setNameLecturer(String nameLecturer) {
		this.nameLecturer = nameLecturer;
	}

	public Set<?> getRecords() {
		return this.records;
	}

	public void setRecords(Set<?> records) {
		this.records = records;
	}

	@Override
	public String getName() {
		return this.getNameLecturer();
	}

	@Override
	public String toString() {
		return "Lecturer [idLecturer=" + idLecturer + ", chair=" + chair + ", nameLecturer=" + nameLecturer + "]";
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		Lecturer result = (Lecturer) super.clone();
		result.chair = (Chair) chair.clone();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lecturer other = (Lecturer) obj;
		if (idLecturer == null) {
			if (other.idLecturer != null)
				return false;
		} else if (!idLecturer.equals(other.idLecturer))
			return false;
		// if (chair == null) {
		// if (other.chair != null)
		// return false;
		// } else if (!chair.equals(other.chair))
		// return false;

		// if (nameLecturer == null) {
		// if (other.nameLecturer != null)
		// return false;
		// } else if (!nameLecturer.equals(other.nameLecturer))
		// return false;
		return true;
	}
}