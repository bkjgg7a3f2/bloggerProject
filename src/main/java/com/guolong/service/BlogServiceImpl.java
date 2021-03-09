package com.guolong.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.guolong.NotFoundException;
import com.guolong.dao.BlogRepository;
import com.guolong.po.Blog;
import com.guolong.po.Type;
import com.guolong.util.MarkdownUtils;
import com.guolong.vo.BlogQuery;

@Service
public class BlogServiceImpl implements BlogService {

	@Autowired
	private BlogRepository blogRepository;
	
	
	@Override
	public Blog getBlog(Long id) {
		return blogRepository.getOne(id);
	}

	@Override
	public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
		return blogRepository.findAll(new Specification<Blog>() {
			
			@Override
			public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if(! "".equals(blog.getTitle()) && blog.getTitle() != null) {
					predicates.add(cb.like(root.<String>get("title"),"%"+blog.getTitle()+"%"));
				}
				if(blog.getTypeId() != null) {
					predicates.add(cb.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
				}
				if(blog.isRecommend()) {
					predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
				}
				query.where(predicates.toArray(new Predicate[predicates.size()]));
				return null;
			}
		}, pageable);
	}
	
	

	@Transactional
	@Override
	public Blog saveBlog(Blog blog) {
		if(blog.getId() == null) {
			blog.setCreateTime(new Date());
			blog.setUpdateTime(new Date());
			blog.setViews(0);
		}else {
			blog.setUpdateTime(new Date());
		}
		
		return blogRepository.save(blog);
	}

	@Transactional
	@Override
	public Blog updateBlog(Long id, Blog blog) {
		Blog b = blogRepository.getOne(id);
		if(b == null) {
			throw new NotFoundException("該博客不存在");
		}
		BeanUtils.copyProperties(blog,b);
		return blogRepository.save(b);
	}

	@Transactional
	@Override
	public void deleteBlog(Long id) {
		blogRepository.deleteById(id);
	}

	@Override
	public Page<Blog> listBlog(Pageable pageable) {
		Page<Blog> blog = blogRepository.findAll(pageable);
		
		for(Blog blog1 : blog) {
			String cutContent = blog1.getContent();
			String newContent = cutContent.substring(0, 50)+"...";
			blog1.setContent(newContent);
		}
		return blog;
	}

	@Override
	public List<Blog> listRecommendBlogTop(Integer size) {
		Sort sort = Sort.by(Sort.Direction.ASC,"updateTime");
		Pageable pageable = PageRequest.of(0,size,sort);
		return blogRepository.findTop(pageable);
	}

	@Override
	public Page<Blog> listBlog(String query, Pageable pageable) {
		
		return blogRepository.findByQuery(query, pageable);
	}

	@Override
	public Blog getAndConvert(Long id) {
		Blog blog = blogRepository.getOne(id);
		if(blog == null) {
			throw new NotFoundException("該博客不存在");
		}
		Blog b = new Blog();
		BeanUtils.copyProperties(blog, b);
		String content = b.getContent();
		b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
		
		blogRepository.updateViews(id);
		
		return b;
	}

	@Override
	public Page<Blog> listBlog(Long tagId, Pageable pageable) {
		return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
	}
	
	@Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogRepository.findGroupYear();
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : years) {
            map.put(year, blogRepository.findByYear(year));
        }
        return map;
    }
	
	@Override
    public Long countBlog() {
        return blogRepository.count();
    }

}
