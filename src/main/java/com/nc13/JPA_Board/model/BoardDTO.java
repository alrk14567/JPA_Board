package com.nc13.JPA_Board.model;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;

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

//    private MultipartFile boardFile; // save.html -> Controller 파일 담는 용도
//    private String originalFileName; // 원본 파일 이름
//    private String storedFileName; // 서버 저장용 파일 이름
//    private int fileAttached; // 파일 첨부 여부(첨부 1, 미첨부 0)

}
