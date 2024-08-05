package com.nc13.JPA_Board.model;

import com.nc13.JPA_Board.entity.CommentEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CommentDTO {
    private Long id;
    private String commentWriter;
    private String commentContent;
    private Long boardId;
    private LocalDateTime commentEntryDate;

    public static CommentDTO toCommentDTO(CommentEntity commentEntity, Long boardId) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(commentEntity.getId());
        commentDTO.setCommentWriter(commentEntity.getCommentWriter());
        commentDTO.setCommentContent(commentEntity.getCommentContent());
        commentDTO.setCommentEntryDate(commentEntity.getEntryDate());
       // commentDTO.setBoardId(commentEntity.getBoardEntity().getId());  // Service 메서드에 @Transactional 어노테이션을 해야함
        commentDTO.setBoardId(boardId);
        return commentDTO;

    }
}
