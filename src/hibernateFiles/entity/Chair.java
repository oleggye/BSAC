package hibernateFiles.entity;
// Generated May 15, 2017 12:08:05 PM by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;

import components.IName;

/**
 * Chair generated by hbm2java
 */
public class Chair implements java.io.Serializable, Cloneable, IName {

	private static final long serialVersionUID = 1L;

	private Byte idChair;
	private String nameChair;
	private Set<?> lecturers = new HashSet<Object>(0);
	private Set<?> subjects = new HashSet<Object>(0);

	public Chair() {
	}

	public Chair(String nameChair) {
		this.nameChair = nameChair;
	}

	public Chair(String nameChair, Set<?> lecturers, Set<?> subjects) {
		this.nameChair = nameChair;
		this.lecturers = lecturers;
		this.subjects = subjects;
	}

	public Byte getIdChair() {
		return this.idChair;
	}

	public void setIdChair(Byte idChair) {
		this.idChair = idChair;
	}

	public String getNameChair() {
		return this.nameChair;
	}

	public void setNameChair(String nameChair) {
		this.nameChair = nameChair;
	}

	public Set<?> getLecturers() {
		return this.lecturers;
	}

	public void setLecturers(Set<?> lecturers) {
		this.lecturers = lecturers;
	}

	public Set<?> getSubjects() {
		return this.subjects;
	}

	public void setSubjects(Set<?> subjects) {
		this.subjects = subjects;
	}

	@Override
	public String getName() {
		return this.getNameChair();
	}

	@Override
	public String toString() {
		return "Chair [idChair=" + idChair + ", nameChair=" + nameChair + "]";
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chair other = (Chair) obj;
		if (idChair == null) {
			if (other.idChair != null)
				return false;
		} else if (!idChair.equals(other.idChair))
			return false;
		// if (lecturers == null) {
		// if (other.lecturers != null)
		// return false;
		// } else if (!lecturers.equals(other.lecturers))
		// return false;
		// if (nameChair == null) {
		// if (other.nameChair != null)
		// return false;
		// } else if (!nameChair.equals(other.nameChair))
		// return false;
		return true;
	}
}