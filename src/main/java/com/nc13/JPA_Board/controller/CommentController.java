package com.nc13.JPA_Board.controller;

import com.nc13.JPA_Board.model.CommentDTO;
import com.nc13.JPA_Board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment/")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("write")
    public ResponseEntity write(@ModelAttribute CommentDTO commentDTO) {
        System.out.println("commentDTO: " + commentDTO);
        Long writeResult = commentService.write(commentDTO);
        if (writeResult != null) {
            // 작정 성공하면 댓글 목록을 가져와서 리턴
            // 댓글 목록: 해당 게시글의 댓글 전체
            List<CommentDTO> commentDTOList = commentService.selectAll(commentDTO.getBoardId());
            return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 게시글이 존재하지 않습니다.");
        }

    }
}
