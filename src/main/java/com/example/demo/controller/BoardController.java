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
}
