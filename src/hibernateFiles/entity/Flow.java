package hibernateFiles.entity;
// Generated May 15, 2017 12:08:05 PM by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;

import components.IName;

/**
 * Flow generated by hbm2java
 */
public class Flow implements java.io.Serializable, Cloneable, IName {

	private static final long serialVersionUID = 1L;

	private short idFlow;
	private String name;
	private Set<?> groups = new HashSet<Object>(0);

	public Flow() {
	}

	public Flow(short idFlow, String name) {
		this.idFlow = idFlow;
		this.name = name;
	}

	public Flow(short idFlow, String name, Set<?> groups) {
		this.idFlow = idFlow;
		this.name = name;
		this.groups = groups;
	}

	public short getIdFlow() {
		return this.idFlow;
	}

	public void setIdFlow(short idFlow) {
		this.idFlow = idFlow;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<?> getGroups() {
		return this.groups;
	}

	public void setGroups(Set<?> groups) {
		this.groups = groups;
	}

	@Override
	public String toString() {
		return "Flow [idFlow=" + idFlow + ", name=" + name + "]";
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
}