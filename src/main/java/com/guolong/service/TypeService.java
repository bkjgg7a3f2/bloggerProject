package com.guolong.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.guolong.po.Type;

public interface TypeService {

	public Type save(Type type);
	
	public Type getType(Long id);
	
	public Page<Type> listType(Pageable pageable);
	
	public Type updateType(Long id,Type type);
	
	public void deleteType(Long id);
	
	public Type getTypeByName(String name);
	
	public List<Type> listType();
	
	public List<Type> listTypeTop(Integer size);
}
