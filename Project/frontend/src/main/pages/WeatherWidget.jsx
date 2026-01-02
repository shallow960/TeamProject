import React, { useEffect, useState } from "react";

// - 현재 위치를 기반으로 "현재 기온 + 날씨 상태(맑음/흐림/비/눈)"를 표시합니다.
// - Open-Meteo API를 사용해서 API KEY 없이도 호출 가능하게 구성했습니다.

// Open-Meteo는 현재 날씨를 숫자 코드(weather_code)로 내려줍니다.
// 예) 0 = 맑음, 3 = 흐림, 61~65 = 비 ...
// 이 코드 값을 한글 상태 문자열로 변환해주는 함수입니다.

const weatherCode = (code) => {
  if (code === 0) return "맑음";
  if (code === 3) return "흐림";
  if ([61, 63, 65, 66, 67].includes(code)) return "비";
  if ([71, 73, 75, 77].includes(code)) return "눈";
  if ([95, 96, 99].includes(code)) return "천둥번개";

  return "날씨";
};

// Open-Meteo 현재 날씨 호출 함수
// - lat, lon(위도/경도)을 받아서 현재 기온, 현재 날씨코드를 요청합니다.
// - timezone=Asia/Seoul : 시간 계산을 한국 시간 기준으로 맞추기

const CurrentLocation = async (lat, lon) => {
  // current=temperature_2m,weather_code : 현재 온도, 현재 날씨코드 요청
  const url =
    `https://api.open-meteo.com/v1/forecast` +
    `?latitude=${lat}` +
    `&longitude=${lon}` +
    `&current=temperature_2m,weather_code` +
    `&timezone=Asia%2FSeoul`;

  const res = await fetch(url);

  // 네트워크 오류/서버 오류를 확실히 캐치하기 위해 체크
  if (!res.ok) throw new Error(`날씨 API 오류 : ${res.status}`);

  // JSON 형태로 파싱해서 반환
  return res.json();
};

// 브라우저 위치 권한(geolocation)을 Promise로 감싼 함수

const getPosition = () =>
  new Promise((resolve, reject) => {
    // 브라우저가 geolocation을 지원하지 않는 환경이면 실패 처리
    if (!navigator.geolocation) reject(new Error("geolocation not supported"));

    navigator.geolocation.getCurrentPosition(resolve, reject, {
      enableHighAccuracy: false,

      // timeout: 위치를 못 잡으면 몇 ms 후 실패 처리할지
      timeout: 5000,

      // maximumAge: 캐시된 위치를 허용할 시간(ms) [600000ms = 10분]
      maximumAge: 600000,
    });
  });

export default function WeatherWidget() {
  // state 구성
  // - loading: 데이터 불러오는 중인지 여부
  // - temp: 현재 기온(정수로 반올림한 값)
  // - text: 맑음/흐림/비 등 상태 텍스트

  const [state, setState] = useState({
    loading: true,
    temp: null,
    text: "날씨",
  });

  // useEffect: 컴포넌트가 화면에 처음 렌더링될 때(마운트) 1회 실행
  // - 위치 → 날씨 API 호출 → state 업데이트

  useEffect(() => {
    // 비동기 작업 중 컴포넌트가 언마운트될 때 setState하면 경고가 날 수 있어 방지용 플래그
    let mounted = true;

    const run = async () => {
      try {
        //위치 정보 불러오기 실패시 기본값으로 서울 좌표

        let lat = 37.5665; // 서울 위도
        let lon = 126.978; // 서울 경도

        try {
          const pos = await getPosition();
          lat = pos.coords.latitude;
          lon = pos.coords.longitude;
        } catch (e) {
          // 위치권한 실패시 기본값(서울) 사용
        }

        // Open-Meteo API 호출
        const data = await CurrentLocation(lat, lon);
        const t = data?.current?.temperature_2m;
        const code = Number(data?.current?.weather_code);

        // temp는 온도 반올림해서 정수로 표현
        // const tempRounded = typeof t === "number" ? Math.round(t) : null;
        // temp는 소수점 반올림 안한 표현
        const tempValue = typeof t === "number" ? (Math.round(t * 10) / 10) : null;
        // weather_code를 한글 텍스트로 변환(맑음/흐림/비 등)
        const text = weatherCode(code);

        // 언마운트 된 상태면 state 업데이트 하지 않음
        if (!mounted) return;

        setState({
          loading: false,
        // temp: tempRounded,
          temp: tempValue,
          text,
        });
      } catch (e) {
        // API 호출 자체가 실패했을 때
        if (!mounted) return;

        setState({
          loading: false,
          temp: null,
          text: "날씨 정보 없음",
        });
      }
    };

    run();

    // cleanup: 컴포넌트가 사라질 때 mounted=false로 바꿔 비동기 setState 방지
    return () => {
      mounted = false;
    };
  }, []);

  // 렌더링(출력)
  // - 로딩 중이면 "-" / "불러오는 중"
  // - 로딩 완료면 기온과 텍스트 출력
  return (
    <div className="weather_inner">
      <span className="temperature">
        <span>
          {state.loading ? "-" : state.temp ?? "-"}
          <span>℃</span>
        </span>
      </span>

      <span className="weather">
        {state.loading ? "불러오는 중" : state.text}
      </span>
    </div>
  );
}
