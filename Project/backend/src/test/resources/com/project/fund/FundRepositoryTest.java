package com.project.fund;

import com.project.fund.entity.FundEntity;
import com.project.fund.repository.FundRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 실제 DB 사용
@Transactional
@Rollback(false) // 테스트 후에도 데이터 남기고 싶을 경우
public class FundRepositoryTest {

    @Autowired
    FundRepository fundRepository;

    @Test
    @DisplayName("Fund 저장 테스트")
    void testSaveFund() {
        FundEntity fund = FundEntity.builder()
                .fundSponsor("홍길동")
                .fundPhone("010-1111-2222")
                .fundBirth("1990-01-01")
                .fundMoney(10000)
                .fundTime(LocalDate.now())
                .sumMoney("10000")
                .fundItem("사료")
                .fundNote("도움이 되길 바랍니다")
                .fundBank("국민")
                .fundAccountnum("111-222-333")
                .fundDepositor("홍길동")
                .fundDrawaldate("2025-08-01")
                .fundCheck("Y")
                .build();

        FundEntity saved = fundRepository.save(fund);
        assertThat(saved.getId()).isNotNull();
    }
}
