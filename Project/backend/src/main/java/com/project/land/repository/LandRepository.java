package com.project.land.repository;

import com.project.land.entity.Land;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface LandRepository extends JpaRepository<Land, Long> {

    // 예약 코드로 Land 정보 조회
    Optional<Land> findByReserveCode(Long reserveCode);

    // 예약 코드 리스트로 여러 Land 조회
    List<Land> findByReserveCodeIn(List<Long> reserveCodes);

    // 동물 수가 특정 값 이상인 경우 필터링 //통계, 제한조건에 사용가능
    List<Land> findByAnimalNumberGreaterThanEqual(int minAnimalNumber);


}