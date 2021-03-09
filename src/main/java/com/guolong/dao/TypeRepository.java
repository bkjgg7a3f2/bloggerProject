package com.guolong.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.guolong.po.Type;

public interface TypeRepository extends JpaRepository<Type, Long> {

	public Type findByName(String name);
	
	@Query("select t from Type t")
	public List<Type> findTop(Pageable pageable);
}
