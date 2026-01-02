// src/program/signup/pages/PhoneVerifyPage.jsx
// 목적: 라우트 "/phonetest"에서 동작하는 "휴대폰 인증 단계" 전용 페이지
// 동작:
//  - [휴대폰 인증] 버튼 클릭 → PhoneAuthModal(팝업) 오픈
//  - 팝업에서 인증 성공 시 onVerified 콜백 → sessionStorage에 인증 결과 저장
//  - 즉시 "/join/signup" 으로 navigate 하여 다음 단계(회원가입 폼)로 진행
//
// 주의:
//  - 중복체크는 PhoneAuthModal 내부에서 Firestore members 컬렉션(memberPhone 필드)로 처리
//  - sessionStorage 저장 키
//      - "phoneVerified": "true"
//      - "verifiedPhone": "+821012345678" (E.164형식)
//      - "phoneVerifiedAt": ISO 문자열(디버깅/만료 정책용)

import { useState, useCallback } from "react";
import { useNavigate } from "react-router-dom";
import PhoneAuthModal from "../../../common/components/PhoneAuthModal";

export default function PhoneVerifyPage() {
  const navigate = useNavigate();
  const [open, setOpen] = useState(false);

  // +82(E.164) -> 010xxxxxxxx (서비스 내부 010 통일)
  const normalizeKoreanPhone = (raw) => {
    if (!raw) return "";

    let p = String(raw).trim();

    // 숫자와 +만 남김 (공백/하이픈/괄호 제거)
    p = p.replace(/[^\d+]/g, "");

    // +82 / 82 로 시작하면 0으로 치환
    if (p.startsWith("+82")) p = "0" + p.slice(3);
    else if (p.startsWith("82")) p = "0" + p.slice(2);

    // 최종적으로 숫자만 남김
    p = p.replace(/[^\d]/g, "");

    return p;
  };

  // 팝업에서 인증 성공 시 호출되는 콜백
  const handleVerified = useCallback(
    ({ phone, firebaseUid }) => {
      const normalizedPhone = normalizeKoreanPhone(phone);

      try {
        // 세션에 인증 결과 저장 (회원가입 페이지 가드/자동 채움용)
        sessionStorage.setItem("phoneVerified", "true");

        // A방안: 내부 표준(010)로 저장/전달
        sessionStorage.setItem("verifiedPhone", normalizedPhone);

        // 원본(+82) 보관
        // sessionStorage.setItem("verifiedPhoneRaw", phone);

        sessionStorage.setItem("phoneVerifiedAt", new Date().toISOString());

        if (firebaseUid) sessionStorage.setItem("firebaseUid", firebaseUid);
      } catch (e) {
        console.warn("세션 저장 실패:", e);
      }

      // 회원가입 페이지로 즉시 이동
      navigate("/join", {
        state: { phoneVerified: true, verifiedPhone: normalizedPhone },
        replace: true,
      });
    },
    [navigate]
  );

  return (
    <div>
      <div className="form_top_box">
        <div className="form_top_item">
          <i className="form_icon type5"></i>
          <div className="form_title">휴대폰 인증</div>
          <div className="form_desc">
            <p>휴대폰 인증 후 회원가입이 가능합니다.</p>
          </div>
        </div>
      </div>
      <div className="phone_box">
        <div className="phone_inner">
          <button
            type="button"
            className="phone_btn"
            onClick={() => setOpen(true)}
          >
            <span>휴대폰 인증</span>
          </button>
        </div>
      </div>
      {/* 인증 팝업 (회원가입 플로우이므로 mode='signup') */}
      <PhoneAuthModal
        open={open}
        onClose={() => setOpen(false)}
        onVerified={handleVerified}
        mode="signup"
      />
    </div>
  );
}
