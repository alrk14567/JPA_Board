package com.nc13.JPA_Board.repository;

import com.nc13.JPA_Board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> { // 엔티티 클래스와 PK역할 하는 친구의 데이터 타입을 써준다
    // update board_table set board_hits=board_hits+1 where id=? -> 이거는 간단한 커리문

    @Modifying//업데이트나 딜리트 쿼리를 사용할때는 꼭 써주어야 한다.
    // 간단한 쿼리문 + 엔티티를 합치면 아래와 같다. (엔티티를 기준으로 쿼리 작성)
    // @Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id" 마지막 :id는 주소에 붙는 친구?? 여기서는 주소에 붙어서 받은 친구 여기서는 param id 랑 매칭된다.
    @Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id")
    void updateHits(@Param("id") Long id);
}
