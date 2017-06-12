package service;

import hibernateFiles.entity.Record;
import service.exception.ServiceValidationException;

public interface IValidationService {

	public void validateRecord(Record record) throws ServiceValidationException;
}