package com.jcpdev.board.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.jcpdev.board.dao.FreeboardMapper;
import com.jcpdev.board.model.Board;
import com.jcpdev.board.model.PageDto;



@Service
public class FreeBoardServiceImpl implements FreeboardService{
	private static final Logger logger = LoggerFactory.getLogger(FreeBoardServiceImpl.class);
	
	@Autowired
	FreeboardMapper dao;
	
	@Override
	public int insert(Board dto) {
		logger.info("insert.............");
		return dao.insert(dto);
	}

	@Override
	public int getCount() {
		return dao.getCount();
	}

	@Override
	public List<Board> getPagelist(PageDto dto) {
		return dao.getPagelist(dto);
	}

	@Override
	public int delete(int idx) {
		return dao.delete(idx);
	}

	@Override
	public Board getBoardOne(int idx) {
		return dao.getBoardOne(idx);
	}

	@Override
	public int update(Board dto) {
		return dao.update(dto);
	}

	@Override
	public void updateReadCnt(int idx) {
		dao.updateReadCnt(idx);
		
	}
	@Override
	public int searchCount(Map<String,String> map) {
		//paging에 검색을 위한 필드와 검색어 속성 포함
		return dao.searchCount(map);
	}
	
	@Override
	public List<Board> searchList(PageDto dto) {
		//paging에 검색을 위한 필드와 검색어 속성 포함
		return dao.searchList(dto);
	}
	

}
