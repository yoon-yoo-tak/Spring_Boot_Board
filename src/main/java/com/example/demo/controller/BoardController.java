package com.example.demo.controller;

import com.example.demo.domain.BoardDTO;
import com.example.demo.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Controller
public class BoardController {

    @Autowired
    BoardService boardService;

    @GetMapping(value = "/board/write.do")
    public String boardWrite(Model model, @RequestParam(value = "idx", required = false) Long idx){
        if(idx == null){
            model.addAttribute("board", new BoardDTO());
        }else{
            BoardDTO board = boardService.getBoardDetail(idx);
            if(board == null){
                return "redirect:/board/list.do";
            }
            model.addAttribute("board", board);
        }
        return "board/write";
    }

    @PostMapping(value = "/board/register.do")
    public String registerBoard(final BoardDTO board){
        try{
            boolean isRegister = boardService.registerBoard(board);
            if(isRegister == false){
                // 게시글 등록에 실패
            }
        }catch (DataAccessException e){
            // DB처리과정에 문제 발생
        }catch (Exception e){
            // 시스템 문제 발생
        }
        return "redirect:/board/list.do";
    }
    
    @GetMapping(value = "/board/list.do")
    public String boardList(Model model) {
    	List<BoardDTO> boardLst = boardService.getBoardList();
    	model.addAttribute("boardLst", boardLst);
    	
    	return "board/list";
    }
    
    @GetMapping(value = "/board/view.do")
    public String boardDetail(@RequestParam(value = "idx", required = false) Long idx, Model model) {
    	if(idx == null) {
    		// 글번호가 없을경우
    		return "redirect:/board/list.do";
    	}
    	BoardDTO board = boardService.getBoardDetail(idx);
    	if(board == null || "Y".equals(board.getDeleteYn())) {
    		// 없거나 삭제되엇을 경우
    		return "redirect:/board/list.do";
    	}
    	model.addAttribute("board", board);
    	return "board/view";
    }
    
    @PostMapping(value="/board/delete.do")
    public String deleteBoard(@RequestParam(value="idx", required = false) Long idx) {
    	if(idx == null) {
    		// 글번호가 없다
    		return "redirect:/board/list.do";
    	}
    	try {
    		boolean isDeleted = boardService.deleteBoard(idx);
    		if(isDeleted == false) {
    			// 삭제 실패
    		}
    	}catch(DataAccessException e){
    		// db문제
    	}catch(Exception e) {
    		// 
    	}
    	return "redirect:/board/list.do";
    }
}

