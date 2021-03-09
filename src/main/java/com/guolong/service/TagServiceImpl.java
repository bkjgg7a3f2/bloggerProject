package com.guolong.service;

import java.util.ArrayList;
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
import com.guolong.dao.TagRepository;
import com.guolong.dao.TypeRepository;
import com.guolong.po.Tag;
import com.guolong.po.Type;

@Service
public class TagServiceImpl implements TagService {
	
	@Autowired
	private TagRepository tagRepository;

	@Override
	@Transactional
	public Tag save(Tag type) {
		return tagRepository.save(type);
	}

	@Override
	@Transactional
	public Tag getTag(Long id) {
		return tagRepository.getOne(id);
	}

	@Override
	@Transactional
	public Page<Tag> listTag(Pageable pageable) {
		return tagRepository.findAll(pageable);
	}

	@Override
	@Transactional
	public Tag updateTag(Long id, Tag type) {
		Tag t = tagRepository.getOne(id);
		if(t == null) {
			throw new NotFoundException("id不存在");
		}
		BeanUtils.copyProperties(type, t);
		
		return tagRepository.save(t);
	}

	@Override
	@Transactional
	public void deleteTag(Long id) {
		tagRepository.deleteById(id);
	}

	@Override
	public Tag getTagByName(String name) {
		return tagRepository.findByName(name);
	}

	@Override
	public List<Tag> listTag() {
		return tagRepository.findAll();
	}

	@Override
	public List<Tag> listTag(String ids) {
		return tagRepository.findAllById(convertToList(ids));
	}
	
	private List<Long> convertToList(String ids){
		List<Long> list = new ArrayList<>();
		if(!"".equals(ids) && ids != null) {
			String[] idarray = ids.split(",");
			for(int i = 0;i < idarray.length;i++) {
				list.add(new Long(idarray[i]));
			}
		}
		return list;
	}

	@Override
	public List<Tag> listTagTop(Integer size) {
		Sort sort = Sort.by(Sort.Direction.DESC,"blogs.size");
		Pageable pageable = PageRequest.of(0,size,sort);
		return tagRepository.findTop(pageable);
	}

}
