package com.nc13.JPA_Board.model;


import com.nc13.JPA_Board.entity.BoardEntity;
import com.nc13.JPA_Board.entity.BoardFileEntity;
import com.nc13.JPA_Board.service.BoardService;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
// ToString 필드값을 확인 할때 사용
@ToString
@NoArgsConstructor//기본 생성자
@AllArgsConstructor//모든 필드를 매개변수로 하는 생성자
public class BoardDTO {
    private Long id;
    private String nickname;
    private String boardPass;
    private String title;
    private String content;
    private int boardHits;
    private LocalDateTime entryDate;
    private LocalDateTime modifyDate;

    private List<MultipartFile> File; // save.html -> Controller 파일 담는 용도
    private List<String> originalFileName; // 원본 파일 이름
    private List<String> storedFileName; // 서버 저장용 파일 이름
    private int fileAttached; // 파일 첨부 여부(첨부 1, 미첨부 0)


    public BoardDTO(Long id, String nickname, String title, int boardHits, LocalDateTime entryDate) {
        this.id = id;
        this.nickname = nickname;
        this.title = title;
        this.boardHits = boardHits;
        this.entryDate = entryDate;
    }

    // 엔티티를 DTO로 변환
    public static BoardDTO toBoardDTO(BoardEntity boardEntity) {
        BoardDTO boardDTO=new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setNickname(boardEntity.getNickname());
        boardDTO.setBoardPass(boardEntity.getBoardPass());
        boardDTO.setTitle(boardEntity.getTitle());
        boardDTO.setContent(boardEntity.getContent());
        boardDTO.setBoardHits(boardEntity.getBoardHits());
        boardDTO.setEntryDate(boardEntity.getEntryDate());
        boardDTO.setModifyDate(boardEntity.getModifyDate());
        if (boardEntity.getFileAttached() ==0) {
            boardDTO.setFileAttached(boardEntity.getFileAttached());
        } else {
            List<String> originalFileNameList = new ArrayList<>();
            List <String> storedFileNameList = new ArrayList<>();
            boardDTO.setFileAttached(boardEntity.getFileAttached());
            //파일 이름도 가져가야함
            // originalFileName, storedFileName : board_file_table(boardFileEntity)]
            for (BoardFileEntity boardFileEntity: boardEntity.getBoardFileEntityList()) {
                originalFileNameList.add(boardFileEntity.getOriginalFileName());
                storedFileNameList.add(boardFileEntity.getStoredFileName());
            }
            /*boardDTO.setOriginalFileName(boardEntity.getBoardFileEntityList().get(0).getOriginalFileName());
            boardDTO.setStoredFileName(boardEntity.getBoardFileEntityList().get(0).getStoredFileName());*/
            boardDTO.setOriginalFileName(originalFileNameList);
            boardDTO.setStoredFileName(storedFileNameList);
        }
        return boardDTO;
    }

}
