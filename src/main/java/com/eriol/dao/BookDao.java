package com.eriol.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eriol.entity.Book;
import tk.mybatis.mapper.common.Mapper;

public interface BookDao extends Mapper<Book> {

	/**
	 * 通过ID查询单本图书
	 * 
	 * @param id
	 * @return
	 */
	Book queryById(long id);

	/**
	 * 查询所有图书
	 * 

	 * @return
	 */
//	List<Book> queryAll(@Param("offset") int offset, @Param("limit") int limit);
	List<Book> queryAll();
	/**
	 * 减少馆藏数量
	 * 
	 * @param bookId
	 * @return 如果影响行数等于>1，表示更新的记录行数
	 */
	int reduceNumber(long bookId);

	void updateOne(Book book);

}
