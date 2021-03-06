package by.bsac.timetable.hibernateFiles.entity;
// Generated May 15, 2017 12:08:05 PM by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;

/**
 * SubjectFor generated by hbm2java
 */
public class SubjectFor implements java.io.Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private byte id;
	private String name;
	private Set<?> records = new HashSet<Object>(0);

	public SubjectFor() {
	}

	public SubjectFor(byte id, String name) {
		this.id = id;
		this.name = name;
	}

	public SubjectFor(byte id, String name, Set<?> records) {
		this.id = id;
		this.name = name;
		this.records = records;
	}

	public byte getId() {
		return this.id;
	}

	public void setId(byte id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<?> getRecords() {
		return this.records;
	}

	public void setRecords(Set<?> records) {
		this.records = records;
	}

	@Override
	public String toString() {
		return "SubjectFor [id=" + id + ", name=" + name + "]";
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
