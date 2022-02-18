package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.BoardDTO;

@Mapper
public interface BoardMapper {
	
	public int insertBoard(BoardDTO Board);
	
	public BoardDTO selectBoardDetail(long idx);
	
	public int updateBoard(BoardDTO param);
	
	public int deleteBoard(Long idx);
	
	public List<BoardDTO> selectBoardList();
	
	public int selectBoardTotalCount();
}
