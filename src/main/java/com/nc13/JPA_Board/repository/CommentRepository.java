package com.nc13.JPA_Board.repository;


import com.nc13.JPA_Board.entity.BaseEntity;
import com.nc13.JPA_Board.entity.BoardEntity;
import com.nc13.JPA_Board.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    // select * from comment_table where board_id=? order by id DESC
    List<CommentEntity> findAllByBoardEntityOrderByIdDesc(BoardEntity boardEntity);
}
