package com.nc13.JPA_Board.entity;

import com.nc13.JPA_Board.model.BoardDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="board_table")
public class BoardEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length= 20,nullable=false) //크기 20, not null
    private String nickname;

    @Column //크기 255, null가능
    private String boardPass;

    @Column()
    private String title;

    @Column(length = 500)
    private String content;

    @Column
    private int boardHits;

    public static BoardEntity toInsertEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setNickname(boardDTO.getNickname());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardHits(0);
        boardEntity.setTitle(boardDTO.getTitle());
        boardEntity.setContent(boardDTO.getContent());
        return boardEntity;
    }

    public static BoardEntity toUpdateEntity (BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setId(boardDTO.getId());
        boardEntity.setNickname(boardDTO.getNickname());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardHits(boardDTO.getBoardHits());
        boardEntity.setTitle(boardDTO.getTitle());
        boardEntity.setContent(boardDTO.getContent());
        return boardEntity;
    }

}
