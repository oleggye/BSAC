/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.Impl;

import dao.ICancellationDAO;
import hibernateFiles.entity.Cancellation;

public class CancellationDAOImpl extends AbstractHibernateDAO<Cancellation> implements ICancellationDAO {
	public CancellationDAOImpl() {
		super(Cancellation.class);
	}
}
