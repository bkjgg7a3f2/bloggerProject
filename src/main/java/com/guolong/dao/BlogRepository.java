package com.guolong.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.guolong.po.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long>,JpaSpecificationExecutor<Blog> {

	@Query("select b from Blog b where b.recommend = true")
	List<Blog> findTop(Pageable pageable);
	
	//select * from t_blog where title like %內容%
	@Query("select b from Blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> findByQuery(String query,Pageable pageable);
	
	@Transactional
    @Modifying
    @Query("update Blog b set b.views = b.views+1 where b.id = ?1")
    int updateViews(Long id);
	
	@Query(value="select format(update_time,'yyyy') from t_blog GROUP BY format(update_time,'yyyy')",nativeQuery=true)
	List<String> findGroupYear();
	
	@Query(value="select * from t_blog where format(update_time,'yyyy') = ?1",nativeQuery=true)
    List<Blog> findByYear(String year);
}
