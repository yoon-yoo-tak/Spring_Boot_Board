package com.example.demo;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import com.example.demo.domain.BoardDTO;
import com.example.demo.mapper.BoardMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
class MapperTest {
	 
	@Autowired
	private BoardMapper boardMapper;
	
	@Test
	public void insertTest() {
		for (int i = 2; i <= 30; i++) {
			BoardDTO val = new BoardDTO();
			val.setTitle(i + "번째 제목");
			val.setContent(i + "번째 내용");
			val.setWriter(i + "번째 작성자");
			boardMapper.insertBoard(val);
		}
	}
	@Test
	public void selectTest() {
		BoardDTO board = boardMapper.selectBoardDetail((long) 1);
		try {
            String boardJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(board);

			System.out.println("=========================");
			System.out.println(boardJson);
			System.out.println("=========================");

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void upDateTest() {
		BoardDTO board = new BoardDTO();
		board.setTitle("첫번째 제목 수정");
		board.setContent("첫번째 내용 수정");
		board.setWriter("또다른 나");
		board.setIdx((long) 1);
		
		int result = boardMapper.updateBoard(board);
		if(result == 1) {
			BoardDTO val = boardMapper.selectBoardDetail((long) 1);
			try {
				String boardJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(val);
				
				System.out.println("-----");
				System.out.println(boardJson);
				System.out.println("-----");
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void deleteTest() {
		int result = boardMapper.deleteBoard((long) 1);
		if(result == 1) {
			BoardDTO board = boardMapper.selectBoardDetail((long) 1);
			try {
                String boardJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(board);

				System.out.println("-----");
				System.out.println(boardJson);
				System.out.println("-----");

			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
	}
	@Test
	public void listTest() {
		int boardTotalCnt = boardMapper.selectBoardTotalCount();
		if(boardTotalCnt > 0) {
			List<BoardDTO> boardList = boardMapper.selectBoardList();
			if (CollectionUtils.isEmpty(boardList) == false) {
				for (BoardDTO board : boardList) {
					System.out.println("=========================");
					System.out.println(board.getTitle());
					System.out.println(board.getContent());
					System.out.println(board.getWriter());
				}
			}

		}
	}
}
