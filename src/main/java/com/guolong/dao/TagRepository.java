package com.guolong.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.guolong.po.Tag;


public interface TagRepository extends JpaRepository<Tag, Long> {

	public Tag findByName(String name);
	
	@Query("select t from Tag t")
	public List<Tag> findTop(Pageable pageable);
}
