package com.example.demo.service;

import com.example.demo.domain.BoardDTO;
import com.example.demo.paging.Criteria;

import java.util.List;

public interface BoardService {

    public boolean registerBoard(BoardDTO params);

    public BoardDTO getBoardDetail(Long idx);

    public boolean deleteBoard(Long idx);

    public List<BoardDTO> getBoardList(BoardDTO params);
}
