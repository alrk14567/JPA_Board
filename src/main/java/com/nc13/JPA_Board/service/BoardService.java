package com.nc13.JPA_Board.service;

import com.nc13.JPA_Board.entity.BoardEntity;
import com.nc13.JPA_Board.entity.BoardFileEntity;
import com.nc13.JPA_Board.model.BoardDTO;
import com.nc13.JPA_Board.repository.BoardFileRepository;
import com.nc13.JPA_Board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// DTO 를 엔티티로 변환 하거나 (엔티티 클래스에서 )
// 엔티티를 DTO 형태로 변환 하거나 (DTO 클래스에서)

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;

    public void insert(BoardDTO boardDTO) throws IOException {
        // 파일 첨부 여부에 따라 로직 분리
        if (boardDTO.getFile().isEmpty()) {
            //파일 없음
            BoardEntity boardEntity = BoardEntity.toInsertEntity(boardDTO);
            boardRepository.save(boardEntity);
        } else {
            //파일 있음
            /*
                1. DTO에 담긴 파일을 꺼냄
                2. 파일의 이름을 가져옴
                3. 서버 저장용 이름을 만듬
                4. 저장 경로 설정
                5. 해당 경로에 파일 저장
                6. board_table에 해당 데이터 save처리
                7. board_file_table에 해당 데이터 save 처리
            */
            BoardEntity boardEntiy = BoardEntity.toSaveFileEntity(boardDTO);
            Long saveId = boardRepository.save(boardEntiy).getId();
            BoardEntity board = boardRepository.findById(saveId).get();
            for (MultipartFile boardFile : boardDTO.getFile()) {
     //           MultipartFile boardFile = boardDTO.getFile();
                String originalFilename = boardFile.getOriginalFilename();
                String storedFileName = System.currentTimeMillis() + "_" + originalFilename; //1970년 1월1일부터 얼마나 지났는지 밀리초 단위로 나타내는 친구
                String savePath = "C:/springboot_img/" + storedFileName;
                boardFile.transferTo(new File(savePath));


                BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(board, originalFilename, storedFileName);
                boardFileRepository.save(boardFileEntity);  //이게 DB에 저장한거임
            }

        }

    }

    @Transactional
    public List<BoardDTO> selectAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        //  이후에는 엔티티 객체를 DTO객체를 옮겨 담아야 한다. 위치는 모델에서
        for (BoardEntity boardEntity : boardEntityList) {
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
        }
        return boardDTOList;
    }

    @Transactional
    // 우리 쿼리가 순수 쿼리가 아닐 경우 jpa에서 관리해주는 트랜잭셔널을 걸어서 영속성 관리를 해줘야한다.
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    @Transactional
    public BoardDTO selectOne(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);
            return boardDTO;
        } else {
            return null;
        }
    }

    @Transactional
    public BoardDTO update(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO);
        boardRepository.save(boardEntity);
        return selectOne(boardDTO.getId());
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    public Page<BoardDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 3; // 한 페이지에 보여줄 글 갯수
        // 한페이지당 3개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순 정렬
        // page 위치에 있는 값은 0부터 시작
        Page<BoardEntity> boardEntities =
                boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); // 요청 페이지에 해당하는 글
        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber()); // DB로 요청한 페이지 번호
        System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한 페이지에 보여지는 글 갯수
        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 첫 페이지 여부
        System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막 페이지 여부

        // 목록: id, 작성자, 제목, 조회수, 생성 시간의 엔티티 데이터를 보드 DTO로 바꿔주는 명령어
        Page<BoardDTO> boardDTOS = boardEntities.map(board -> new BoardDTO(board.getId(), board.getNickname(), board.getTitle(), board.getBoardHits(), board.getEntryDate()));

        return boardDTOS;
    }
}
