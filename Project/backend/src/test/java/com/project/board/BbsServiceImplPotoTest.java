package com.project.board;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.multipart.MultipartFile;

import com.project.board.dto.BbsDto;
import com.project.board.dto.ImageBbsDto;
import com.project.board.entity.BbsEntity;
import com.project.board.entity.FileUpLoadEntity;
import com.project.board.entity.ImageBbsEntity;
import com.project.board.exception.BbsException;
import com.project.board.repository.BbsRepository;
import com.project.board.repository.FileUpLoadRepository;
import com.project.board.repository.ImageBbsRepository;
import com.project.board.service.BbsService;
import com.project.member.entity.MemberEntity;
import com.project.member.entity.MemberSex;
import com.project.member.entity.MemberState;
import com.project.member.repository.MemberRepository;
import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 실제 MariaDB 사용
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
@Rollback(false)
class BbsServiceImplPotoTest {

    @Autowired
    private BbsService bbsService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BbsRepository bbsRepository;

    @Autowired
    private ImageBbsRepository imageBbsRepository;

    @Autowired
    private FileUpLoadRepository fileUpLoadRepository;

    @Test
    @DisplayName("POTO 게시판 게시글 작성 + 대표 이미지 + 첨부파일 저장 테스트")
    void testCreatePotoBoardWithImage() throws Exception {
        // 1. 테스트용 회원 저장
        MemberEntity member = MemberEntity.builder()
                .memberId("testuser6@example.com")
                .memberPw("password12345678")
                .memberName("테스트유저11")
                .memberBirth(LocalDate.of(1992, 3, 3))
                .memberAddress("서울 강북구")
                .memberDay(LocalDate.now())
                .memberLock(false)
                .memberPhone("010-3333-4444")
                .memberSex(MemberSex.WOMAN)
                .memberState(MemberState.ACTIVE)
                .build();
        member = memberRepository.save(member);

        // 2. DTO 준비
        BbsDto dto = BbsDto.builder()
                .bbsTitle("사진 게시판 테스트 제목1")
                .bbsContent("예쁜 강아지1.")
                .registDate(LocalDateTime.now())
                .revisionDate(LocalDateTime.now())
                .viewers(0)
                .delDate(null)
                .memberNum(member.getMemberNum())
                .bulletinType(BoardType.POTO)
                .build();

        // 3. 실제 이미지 파일 준비
        File imageFile1 = new File("D:/temp/test1.jpg");  // 👉 대표 이미지로 사용
        File imageFile2 = new File("D:/temp/test2.jpg");
        File imageFile3 = new File("D:/temp/test3.jpg");

        try (FileInputStream fis1 = new FileInputStream(imageFile1);
             FileInputStream fis2 = new FileInputStream(imageFile2);
             FileInputStream fis3 = new FileInputStream(imageFile3)) {

            MockMultipartFile image1 = new MockMultipartFile(
                    "files", imageFile1.getName(), "image/jpeg", fis1);  // ✅ 대표 이미지
            MockMultipartFile image2 = new MockMultipartFile(
                    "files", imageFile2.getName(), "image/jpeg", fis2);
            MockMultipartFile image3 = new MockMultipartFile(
                    "files", imageFile3.getName(), "image/jpeg", fis3);

            // 대표 이미지를 제일 앞에 둠
            List<MultipartFile> files = List.of(image1, image2, image3);

            // 4. 서비스 메서드 호출
            BbsDto result = bbsService.createBbs(dto, member.getMemberNum(), null, files);

            // 5. 결과 검증
            Assertions.assertNotNull(result.getBulletinNum(), "게시글 번호가 생성되어야 합니다.");
            Assertions.assertEquals(BoardType.POTO, result.getBulletinType(), "게시판 타입은 POTO여야 합니다.");

            // ✅ 대표 이미지 1개 저장 검증
         // ✅ 대표 이미지 1개 저장 검증
            List<ImageBbsEntity> savedImages = imageBbsRepository.findByBbsBulletinNum(result.getBulletinNum());
            Assertions.assertEquals(1, savedImages.size(), "대표 이미지는 1개만 저장되어야 합니다.");
            Assertions.assertNotNull(savedImages.get(0).getImagePath(), "대표 이미지 경로가 존재해야 합니다.");


            // ✅ 첨부파일 3개 저장 검증
            List<FileUpLoadEntity> attachments = fileUpLoadRepository.findByBbsBulletinNum(result.getBulletinNum());
            Assertions.assertEquals(3, attachments.size(), "첨부파일은 총 3개 저장되어야 합니다.");
        }
    }




  /*  @Test
    @DisplayName("getImageBbsList 정상 동작 테스트 - member_num=6인 회원 게시글 기준")
    void testGetImageBbsList() {
        // 1. member_num=6 회원 조회
        MemberEntity member = memberRepository.findById(6L)
                .orElseThrow(() -> new RuntimeException("member_num 6인 회원이 존재하지 않습니다."));

        // 2. BbsEntity 저장 (member 연결)
        BbsEntity bbs = BbsEntity.builder()
                .bbstitle("테스트 게시글")
                .bbscontent("테스트 내용")
                .registdate(LocalDateTime.now())
                .memberNum(member)  // 회원 연관관계 추가
                .bulletinType(BoardType.POTO) // 필요 시 게시판 타입 지정
                .viewers(0)  // null이 아닌 기본값 지정
                .build();
        bbs = bbsRepository.save(bbs);

        // 3. ImageBbsEntity 저장 (bbs 연관관계 포함)
        ImageBbsEntity img1 = ImageBbsEntity.builder()
                .bbs(bbs)
                .thumbnailPath("/thumbnails/test1.jpg")
                .imagePath("/Images/test1.jpg")
                .build();

        imageBbsRepository.save(img1);
        // 4. 메서드 호출
        List<ImageBbsDto> result = bbsService.getImageBbsList(bbs.getBulletinNum());
        
        // 5. 검증
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(bbs.getBulletinNum(), result.get(0).getBulletinNum());
        assertEquals("/thumbnails/test1.jpg", result.get(0).getThumbnailPath());
        assertEquals("/Images/test1.jpg", result.get(0).getImagePath());

     // === 콘솔 출력 확인 ===
        System.out.println("조회된 이미지 게시글 정보:");
        for (ImageBbsDto dto : result) {
            System.out.println("bulletinNum = " + dto.getBulletinNum());
            System.out.println("thumbnailPath = " + dto.getThumbnailPath());
            System.out.println("imagePath = " + dto.getImagePath());
        }
    } */
    
  /*  @Test
    @DisplayName("존재하는 bulletinNum 기준 이미지 삭제 성공 테스트")
    void testDeleteImage_Success() {
        Long existingBulletinNum = 56L;  // 실제 존재하는 게시글 번호

        // 삭제 전 존재 여부 체크
        assertTrue(imageBbsRepository.existsById(existingBulletinNum));

        // 삭제 메서드 호출 (예외 없이)
        assertDoesNotThrow(() -> bbsService.deleteImage(existingBulletinNum));

        // 삭제 후 존재하지 않아야 함
        assertFalse(imageBbsRepository.existsById(existingBulletinNum));
    } */

   /* @Test
    @DisplayName("존재하지 않는 bulletinNum 기준 이미지 삭제 시 예외 발생 테스트")
    void testDeleteImage_NotFound() {
        Long nonExistingBulletinNum = 999999L;

        BbsException exception = assertThrows(BbsException.class, () -> {
            bbsService.deleteImage(nonExistingBulletinNum);
        });

        assertEquals("이미지 없음", exception.getMessage());
    } */
   /* 
    @Test
    void testDeleteImage_NotFound() {
        Long nonExistingBulletinNum = 999999L;
        System.out.println("테스트 시작");

        BbsException ex = assertThrows(BbsException.class, () -> {
            bbsService.deleteImage(nonExistingBulletinNum);
        });

        System.out.println("예외 메시지: " + ex.getMessage());
        assertEquals("이미지 없음", ex.getMessage());
    } */
  /*  @Test
    @DisplayName("여러 이미지 삭제 테스트 - DB 및 실제 파일 삭제 확인")
    @Transactional
    @Rollback(false)
    void testDeleteImagesIncludingFiles() throws Exception {
        // given
        List<Long> imageIdsToDelete = List.of(101L, 102L); // 실제 존재하는 이미지 ID로 교체하세요

        List<ImageBbsEntity> imagesBefore = imageBbsRepository.findAllById(imageIdsToDelete);
        assertFalse(imagesBefore.isEmpty(), "삭제할 이미지들이 DB에 존재해야 합니다.");

        for (ImageBbsEntity image : imagesBefore) {
            String imagePath = image.getImagePath();
            String thumbnailPath = image.getThumbnailPath();
            assertTrue(imagePath != null && thumbnailPath != null, "파일 경로가 null이면 안 됨");

            Path actualImage = Path.of("C:/photo", Paths.get(imagePath).getFileName().toString());
            Path actualThumbnail = Path.of("C:/photo", Paths.get(thumbnailPath).getFileName().toString());

            assertTrue(Files.exists(actualImage), "실제 이미지 파일이 존재해야 합니다.");
            assertTrue(Files.exists(actualThumbnail), "실제 썸네일 파일이 존재해야 합니다.");
        }

        // when - 이미지 삭제 실행
        bbsService.deleteImages(imageIdsToDelete);

        // then - DB에서 삭제됐는지 확인
        List<ImageBbsEntity> imagesAfter = imageBbsRepository.findAllById(imageIdsToDelete);
        assertTrue(imagesAfter.isEmpty(), "이미지가 DB에서 삭제되어야 합니다.");

        // then - 실제 파일 삭제 확인
        for (ImageBbsEntity image : imagesBefore) {
            Path actualImage = Path.of("C:/photo", Paths.get(image.getImagePath()).getFileName().toString());
            Path actualThumbnail = Path.of("C:/photo", Paths.get(image.getThumbnailPath()).getFileName().toString());

            assertFalse(Files.exists(actualImage), "이미지 파일이 실제로 삭제되어야 합니다.");
            assertFalse(Files.exists(actualThumbnail), "썸네일 파일이 실제로 삭제되어야 합니다.");
        }
    } */

    
 /*   @Test
    @DisplayName("POTO 게시판 게시글 작성 + 대표 이미지 + 첨부파일 저장 테스트")
    void testCreatePotoBoardWithImage() throws Exception {
        // 1. 존재하는 회원 번호 사용
        Long existingMemberNum = 3L; // DB에 실제 존재하는 회원 번호

        // 2. DTO 준비
        BbsDto dto = BbsDto.builder()
                .bbsTitle("사진 게시판 테스트 제목1")
                .bbsContent("예쁜 강아지1.")
                .registDate(LocalDateTime.now())
                .revisionDate(LocalDateTime.now())
                .viewers(0)
                .delDate(null)
                .memberNum(existingMemberNum)
                .bulletinType(BoardType.POTO)
                .build();

        // 3. 실제 이미지 파일 준비
        File imageFile1 = new File("D:/temp/meat1.jpg");  // ✅ 대표 이미지
        File imageFile2 = new File("D:/temp/cloth1.jpg");
        File imageFile3 = new File("D:/temp/test3.jpg");

        try (FileInputStream fis1 = new FileInputStream(imageFile1);
             FileInputStream fis2 = new FileInputStream(imageFile2);
             FileInputStream fis3 = new FileInputStream(imageFile3)) {

            MockMultipartFile image1 = new MockMultipartFile(
                    "files", imageFile1.getName(), "image/jpeg", fis1);  // ✅ 대표 이미지
            MockMultipartFile image2 = new MockMultipartFile(
                    "files", imageFile2.getName(), "image/jpeg", fis2);
            MockMultipartFile image3 = new MockMultipartFile(
                    "files", imageFile3.getName(), "image/jpeg", fis3);

            // 대표 이미지를 제일 앞에 둠
            List<MultipartFile> files = List.of(image1, image2, image3);

            // 4. 서비스 메서드 호출
            BbsDto result = bbsService.createBbs(dto, existingMemberNum, null, files);

            // 5. 결과 검증
            Assertions.assertNotNull(result.getBulletinNum(), "게시글 번호가 생성되어야 합니다.");
            Assertions.assertEquals(BoardType.POTO, result.getBulletinType(), "게시판 타입은 POTO여야 합니다.");

            // ✅ 대표 이미지 1개 저장 검증
            List<ImageBbsEntity> savedImages = imageBbsRepository.findByBbsBulletinNum(result.getBulletinNum());
            Assertions.assertEquals(1, savedImages.size(), "대표 이미지는 1개만 저장되어야 합니다.");
            Assertions.assertNotNull(savedImages.get(0).getImagePath(), "대표 이미지 경로가 존재해야 합니다.");

            // ✅ 첨부파일 3개 저장 검증
            List<FileUpLoadEntity> attachments = fileUpLoadRepository.findByBbsBulletinNum(result.getBulletinNum());
            Assertions.assertEquals(3, attachments.size(), "첨부파일은 총 3개 저장되어야 합니다.");
        }
    }
*/

}