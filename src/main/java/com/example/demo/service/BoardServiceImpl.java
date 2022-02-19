package com.example.demo.service;

import com.example.demo.domain.BoardDTO;
import com.example.demo.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService{

    @Autowired
    private BoardMapper boardMapper;

    @Override
    public boolean registerBoard(BoardDTO params) {
        int result = 0;

        if(params.getIdx() == null){
            result = boardMapper.insertBoard(params);
        }else{
            result = boardMapper.updateBoard(params);
        }
        return (result == 1) ? true : false;
    }

    @Override
    public BoardDTO getBoardDetail(Long idx) {
        return boardMapper.selectBoardDetail(idx);
    }

    @Override
    public boolean deleteBoard(Long idx) {

        int result = 0;

        BoardDTO board = boardMapper.selectBoardDetail(idx);
        if(board != null && "N".equals(board.getDeleteYn())){
            result = boardMapper.deleteBoard(idx);
        }
        return (result == 1) ? true : false;
    }

    @Override
    public List<BoardDTO> getBoardList() {
        List<BoardDTO> boardList = Collections.emptyList();

        int totalCnt = boardMapper.selectBoardTotalCount();

        if(totalCnt > 0 ){
            boardList = boardMapper.selectBoardList();
        }
        return boardList;
    }
}
