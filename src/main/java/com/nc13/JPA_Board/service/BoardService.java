package com.nc13.JPA_Board.service;

import com.nc13.JPA_Board.entity.BoardEntity;
import com.nc13.JPA_Board.model.BoardDTO;
import com.nc13.JPA_Board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// DTO 를 엔티티로 변환 하거나 (엔티티 클래스에서 )
// 엔티티를 DTO 형태로 변환 하거나 (DTO 클래스에서)

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public void insert(BoardDTO boardDTO) {
        BoardEntity boardEntity=BoardEntity.toInsertEntity(boardDTO);
        boardRepository.save(boardEntity);
    }
}
