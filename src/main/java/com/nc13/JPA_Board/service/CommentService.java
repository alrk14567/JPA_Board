package com.nc13.JPA_Board.service;

import com.nc13.JPA_Board.entity.BoardEntity;
import com.nc13.JPA_Board.entity.CommentEntity;
import com.nc13.JPA_Board.model.CommentDTO;
import com.nc13.JPA_Board.repository.BoardRepository;
import com.nc13.JPA_Board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;


    public Long write(CommentDTO commentDTO) {
        // 부모 엔티티, 조회
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDTO.getBoardId());
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            CommentEntity commentEntity = CommentEntity.toWriteEntity(commentDTO, boardEntity);
            // builder 쓰는 방법도 있음
            return commentRepository.save(commentEntity).getId();
        } else {
            return null;
        }
    }

    public List<CommentDTO> selectAll(Long boardId) {
        // select * from comment_table where board_id=? order by id DESC
        BoardEntity boardEntity= boardRepository.findById(boardId).get();
        List<CommentEntity> commentEntityList = commentRepository.findAllByBoardEntityOrderByIdDesc(boardEntity);
        // EntityList -> DTOList
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for(CommentEntity commentEntity : commentEntityList) {
            CommentDTO commentDTO = CommentDTO.toCommentDTO(commentEntity,boardId);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }
}
