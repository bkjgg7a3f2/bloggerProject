package com.guolong.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.guolong.po.Tag;
import com.guolong.po.Type;

public interface TagService {

	public Tag save(Tag tag);
	
	public Tag getTag(Long id);
	
	public Page<Tag> listTag(Pageable pageable);
	
	public Tag updateTag(Long id,Tag tag);
	
	public void deleteTag(Long id);
	
	public Tag getTagByName(String name);
	
	public List<Tag> listTag();
	
	public List<Tag> listTag(String ids);
	
	public List<Tag> listTagTop(Integer size);
}
