package com.guolong.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.guolong.NotFoundException;
import com.guolong.dao.TypeRepository;
import com.guolong.po.Type;

@Service
public class TypeServiceImpl implements TypeService {
	
	@Autowired
	private TypeRepository typeRepository;

	@Override
	@Transactional
	public Type save(Type type) {
		return typeRepository.save(type);
	}

	@Override
	@Transactional
	public Type getType(Long id) {
		return typeRepository.getOne(id);
	}

	@Override
	@Transactional
	public Page<Type> listType(Pageable pageable) {
		return typeRepository.findAll(pageable);
	}

	@Override
	@Transactional
	public Type updateType(Long id, Type type) {
		Type t = typeRepository.getOne(id);
		if(t == null) {
			throw new NotFoundException("id不存在");
		}
		BeanUtils.copyProperties(type, t);
		
		return typeRepository.save(t);
	}

	@Override
	@Transactional
	public void deleteType(Long id) {
		typeRepository.deleteById(id);
	}

	@Override
	public Type getTypeByName(String name) {
		return typeRepository.findByName(name);
	}

	@Override
	public List<Type> listType() {
		return typeRepository.findAll();
	}

	@Override
	public List<Type> listTypeTop(Integer size) {
		Sort sort = Sort.by(Sort.Direction.DESC,"blogs.size");
		Pageable pageable = PageRequest.of(0,size,sort);
		return typeRepository.findTop(pageable);
	}

}
