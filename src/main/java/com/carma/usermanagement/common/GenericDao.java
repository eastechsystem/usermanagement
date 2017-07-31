package com.carma.usermanagement.common;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, ID extends Serializable> {

	Integer saveRecord(T clazz);

	Boolean deleteRecord(T clazz);

	List<T> getAllRecord(Class<? extends Object> class1);

	Boolean deleteRecord(Class<T> clazz, ID id);

	Boolean updateRecord(T clazz);

	T findById(Class<T> clazz, ID id);

	T merge(T instance);
}