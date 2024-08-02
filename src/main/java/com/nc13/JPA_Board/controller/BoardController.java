package com.nc13.JPA_Board.controller;

import com.nc13.JPA_Board.model.BoardDTO;
import com.nc13.JPA_Board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board/")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/write")
    public String write() {

        return "write";
    }

    @PostMapping("/write")
    public String write(@ModelAttribute BoardDTO boardDTO) {
        System.out.println("boardDTO = " + boardDTO);
        boardService.insert(boardDTO);
        return "index";
    }

}
