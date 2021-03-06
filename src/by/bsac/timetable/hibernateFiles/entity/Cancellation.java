package by.bsac.timetable.hibernateFiles.entity;
// Generated Jun 7, 2017 5:03:56 PM by Hibernate Tools 4.3.1

import java.util.Date;

/**
 * Cancellation generated by hbm2java
 */
public class Cancellation implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idCancellation;
	private Record record;
	private Date dateTo;
	private Date dateFrom;

	public Cancellation() {
	}

	public Cancellation(Record record, Date dateTo, Date dateFrom) {
		this.record = record;
		this.dateTo = dateTo;
		this.dateFrom = dateFrom;
	}

	public Integer getIdCancellation() {
		return this.idCancellation;
	}

	public void setIdCancellation(Integer idCancellation) {
		this.idCancellation = idCancellation;
	}

	public Record getRecord() {
		return this.record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}

	public Date getDateTo() {
		return this.dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public Date getDateFrom() {
		return this.dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

}
