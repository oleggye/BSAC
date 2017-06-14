/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.bsac.timetable.dao.Impl;

import by.bsac.timetable.dao.ICancellationDAO;
import by.bsac.timetable.hibernateFiles.entity.Cancellation;

public class CancellationDAOImpl extends AbstractHibernateDAO<Cancellation> implements ICancellationDAO {
	public CancellationDAOImpl() {
		super(Cancellation.class);
	}
}
