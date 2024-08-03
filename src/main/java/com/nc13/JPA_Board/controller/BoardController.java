package com.nc13.JPA_Board.controller;

import com.nc13.JPA_Board.model.BoardDTO;
import com.nc13.JPA_Board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board/")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("write")
    public String write() {

        return "write";
    }

    @PostMapping("write")
    public String write(@ModelAttribute BoardDTO boardDTO) {
        System.out.println("boardDTO = " + boardDTO);
        boardService.insert(boardDTO);
        return "index";
    }

    @GetMapping("showList")
    public String showAll(Model model) {
        List<BoardDTO> boardList = boardService.selectAll();
        model.addAttribute("boardList", boardList);

        return "showList";
    }

    @GetMapping("showOne/{id}")
    public String showOne(@PathVariable Long id, Model model) {
        /*
            해당 게시글의 조회수를 하나 올리고
            게시글 데이터를 가져와서 showOne.html에 출력
        */
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.selectOne(id);
        model.addAttribute("boardDTO", boardDTO);

        return "showOne";
    }

    @GetMapping("update/{id}")
    public String update(@PathVariable Long id, Model model) {
        BoardDTO boardDTO = boardService.selectOne(id);
        model.addAttribute("boardUpdate", boardDTO);
        return "update";
    }

    @PostMapping("update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model) {
        BoardDTO board = boardService.update(boardDTO);
        model.addAttribute("board", board);
        return "showOne";
        //return "redirect:/board/showOne/" + boardDTO.getId(); 도 가능하지만 업데이트로 조회수가 오르기 때문에 일단 안함
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable Long id) {
        boardService.delete(id);
        return "redirect:/board/showList";
    }

    // /board/paging?page=1
    @GetMapping("paging")
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) {
        pageable.getPageNumber();
        Page<BoardDTO> boardList = boardService.paging(pageable);
        int blockLimit = 3;  // 페이지 번호의 갯수
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1,2,3페이지에 있으면 1을 준다. 1 4 7 10 이 나온다.
        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();

        // page 갯수 20개 라면
        // 그리고 현재 사용자가 3페이지를 본다면
        // 1 2 3
        // 현재 사용자가 7페이지 라면
        // 7 8 9
        // 보여지는 페이지 갯수 3개
        // 총 페이지 갯수 8개

        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "paging";


    }


}
