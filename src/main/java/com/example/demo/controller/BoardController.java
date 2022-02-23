package com.example.demo.controller;

import com.example.demo.constant.Method;
import com.example.demo.domain.BoardDTO;
import com.example.demo.paging.Criteria;
import com.example.demo.service.BoardService;
import com.example.demo.util.UiUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Controller
public class BoardController extends UiUtils{

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
    public String registerBoard(final BoardDTO board, Model model){
        try{
            boolean isRegister = boardService.registerBoard(board);
            if(isRegister == false){
            	return redirectMessage("게시글 등록에 실패하였습니다.", "/board/list.do", Method.GET, null, model);
            }
        }catch (DataAccessException e){
        	return redirectMessage("DB처리에 문제가 발생하였습니다.", "/board/list.do", Method.GET, null, model);
        }catch (Exception e){
        	return redirectMessage("시스템에 문제가 발생하였습니다.", "/board/list.do", Method.GET, null, model);
        }
        return redirectMessage("게시글 등록이 완료되었습니다.", "/board/list.do", Method.GET, null, model);
    }
    
    @GetMapping(value = "/board/list.do")
    public String boardList(Model model, @ModelAttribute("params") BoardDTO params) {
    	List<BoardDTO> boardLst = boardService.getBoardList(params);
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
    public String deleteBoard(@RequestParam(value="idx", required = false) Long idx, Model model) {
    	if(idx == null) {
    		return redirectMessage("올바르지 않은 접근입니다.", "/board/list.do", Method.GET, null, model);
    	}
    	try {
    		boolean isDeleted = boardService.deleteBoard(idx);
    		if(isDeleted == false) {
    			return redirectMessage("게시글 삭제에 실패하였습니다.", "/board/list.do", Method.GET, null, model);
    		}
    	}catch(DataAccessException e){
    		return redirectMessage("DB처리에 문제가 발생하였습니다.", "/board/list.do", Method.GET, null, model);
    	}catch(Exception e) {
    		return redirectMessage("시스템에 문제가 발생하였습니다.", "/board/list.do", Method.GET, null, model);
    	}
    	return redirectMessage("게시글 삭제가 완료되었습니다.", "/board/list.do", Method.GET, null, model);
    }
}

