package com.guolong.service;

import java.util.List;

import com.guolong.po.Comment;

public interface CommentService {

	List<Comment> listCommentByBlogId(Long blogId);
	
	Comment saveComment(Comment comment);
	
}
