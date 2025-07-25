package com.project.fund;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.project.fund.dto.request.FundSponAppRequestDto;
import com.project.fund.entity.FundCheck;
import com.project.fund.entity.FundEntity;
import com.project.fund.repository.FundRepository;
import com.project.fund.service.FundService;

@SpringBootTest
@Transactional
@Commit // 테스트 데이터가 실제 DB에 남도록
public class FundServiceTests {

    @Autowired
    private FundService fundService;

    @Autowired
    private FundRepository fundRepository;

    @Test
    void 후원금_신청_정상작동() {
        // Given - 후원 신청 DTO 생성
        FundSponAppRequestDto dto = FundSponAppRequestDto.builder()
                .memberNum(null) // 비회원이면 null, 회원이면 적절한 MemberEntity 넣기
                .fundSponsor("테스트 후원자")
                .fundPhone("01012345678")
                .fundBirth("1990-01-01")
                .fundMoney(10000)
                .fundNote("테스트 메모")
                .fundCheck(FundCheck.Y)
                .build();

        // When - 서비스 실행
        fundService.saveSponApp(dto);

        // Then - 저장된 데이터 확인
        List<FundEntity> list = fundRepository.findByFundSponsorAndFundPhone("테스트 후원자", "01012345678");
        assertThat(list).isNotEmpty();
        assertThat(list.get(0).getFundMoney()).isEqualTo(10000);
        assertThat(list.get(0).getFundNote()).isEqualTo("테스트 메모");
    }
}
