// 파일: frontend/src/program/oauth/components/KakaoLoginButton.jsx
// 목적:
// - "카카오로 로그인" 버튼 컴포넌트
// - 클릭 시 Kakao OAuth 인가 페이지로 이동 (response_type=code)
// - 완료 후 프론트 콜백(/oauth/kakao/callback)으로 돌아와서 우리가 만든 흐름 진행
//
// 환경변수(.env) 권장 값:
//   - REACT_APP_KAKAO_REST_KEY=카카오REST_API_KEY
//   - REACT_APP_KAKAO_REDIRECT_URI=http://127.0.0.1:3000/oauth/kakao/callback
//
// 주의:
// - 카카오 개발자 콘솔에도 Redirect URI를 위 값으로 등록해야 함
// - scope는 account_email, profile_nickname, phone_number, gender, birthday, birthyear 등
//   프로젝트 요구 데이터에 맞춰 사용
//
// 사용 예시(로그인 페이지 어디서든):
//   import KakaoLoginButton from "../../program/oauth/components/KakaoLoginButton";
//   <KakaoLoginButton fullWidth />

import React, { useMemo } from "react";

// 1) env 우선 사용 로직 일단 비활성화(주석 처리)

/*
const ENV_REST_KEY =
  process.env.REACT_APP_KAKAO_REST_API_KEY ||
  import.meta?.env?.VITE_KAKAO_REST_KEY;
const ENV_REDIRECT_URI =
  process.env.REACT_APP_KAKAO_REDIRECT_URI ||
  import.meta?.env?.VITE_KAKAO_REDIRECT_URI ||
  "http://152.67.212.81/api/kakao/callback";
*/

// 2) 카카오 REST API 키와 Redirect URI를 "현재 사용 중인 앱 설정"으로 하드코딩
const REST_API_KEY = "81e534db4230445c24fa35d7ac6594af";
const REDIRECT_URI = "http://152.67.212.81/oauth/kakao/callback";

const KAKAO_AUTHORIZE_URL = "https://kauth.kakao.com/oauth/authorize";
const DEFAULT_SCOPES = [
  "account_email",
  "phone_number",
  "gender",
  "birthday",
  "birthyear",
];

function buildState() {
  const s = Math.random().toString(36).slice(2) + Date.now().toString(36);
  try {
    sessionStorage.setItem("kakao_oauth_state", s);
  } catch {}
  return s;
}

export default function KakaoLoginButton({ scopes = DEFAULT_SCOPES }) {
  const authorizeUrl = useMemo(() => {
    const clientId = REST_API_KEY;
    const redirectUri = REDIRECT_URI;

    if (!clientId || !redirectUri) return null;

    const params = new URLSearchParams({
      response_type: "code",
      client_id: clientId,
      redirect_uri: redirectUri,
      scope: scopes.join(" "),
      prompt: "select_account",
      state: buildState(),
    });
    return `${KAKAO_AUTHORIZE_URL}?${params.toString()}`;
  }, [scopes]);

  const handleClick = () => {
    if (!authorizeUrl) {
      alert("Kakao 설정이 잘못되었습니다.");
      return;
    }
    window.location.href = authorizeUrl;
  };

  return (
    <div className="kakao_login bth_item">
      <button
        type="button"
        className="login_btn kakao_btn"
        onClick={handleClick}
      >
        <path
          fillRule="evenodd"
          clipRule="evenodd"
          d="M9.96052 3C5.83983 3 2.5 5.59377 2.5 8.79351C2.5 10.783 3.79233 12.537 5.75942 13.5807L4.9313 16.6204C4.85835 16.8882 5.1634 17.1029 5.39883 16.9479L9.02712 14.5398C9.33301 14.5704 9.64386 14.587 9.96052 14.587C14.0812 14.587 17.421 11.9932 17.421 8.79351C17.421 5.59377 14.0812 3 9.96052 3Z"
          fill="black"
        ></path>
        <span>카카오 로그인</span>
      </button>
    </div>
  );
}
