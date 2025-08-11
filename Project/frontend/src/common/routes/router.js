// 🛣 모든 경로를 중앙에서 관리하는 라우터 모음

const routes = {
  // 메인/홈
  home: { path: "/", label: "홈" },

  // 센터 소개
  about: {
    root: { path: "/about", label: "센터소개" },
    greeting: { path: "/about/greeting", label: "인사말" },
    facility: { path: "/about/facility", label: "시설 소개" },
    location: { path: "/about/location", label: "오시는 길" },
  },

  // 입양 소식
  adoption: {
    list: { path: "/about/list", label: "센터 아이들" },
    process: { path: "/about/process", label: "입양 절차 안내" },
    review: { path: "/about/review", label: "입양 후기" },
  },

  // 동물 놀이터
  land: {
    root: { path: "/land", label: "동물 놀이터" },
    info: { path: "/land/info", label: "놀이터 소개" },
    gallery: { path: "/land/gallery", label: "놀이터 둘러보기" },
  },

  //예약
reserve: {
  root:   { path: "/reserve", label: "예약하기" },
  land: {
    date:    { path: "/reserve/land/date",    label: "날짜/시간 선택" }, 
    form:    { path: "/reserve/land/form",    label: "놀이터 예약" },
    confirm: { path: "/reserve/land/confirm", label: "예약 확인" },
    success: { path: "/reserve/land/success", label: "예약 완료" },
  },
  volunteer: {
    date:    { path: "/reserve/volunteer/date",    label: "날짜/시간 선택" },
    form:    { path: "/reserve/volunteer/form",    label: "봉사 신청" },
    confirm: { path: "/reserve/volunteer/confirm", label: "신청 확인" },
    success: { path: "/reserve/volunteer/success", label: "신청 완료" },
  },
},

  // 게시판
  board: {
    root: { path: "/board", label: "게시판" },
    notice: { path: "/board/notice", label: "공지사항" },
    qna: { path: "/board/qna", label: "질문 게시판" },
    photo: { path: "/board/photo", label: "포토 게시판" },
  },

  // 회원 관련
  member: {
    signup: { path: "/member/signup", label: "회원가입" },
    login: { path: "/login", label: "로그인" },
    logout: { path: "/logout", label: "로그아웃" },
    mypage: { path: "/mypage", label: "마이페이지" }, // USER 전용
    update: { path: "/mypage/update", label: "회원정보 수정" },
    delete: { path: "/mypage/update/delete", label: "회원 탈퇴" },
  },

  // 관리자
  admin: {
    login: { path: "/admin/login", label: "관리자 로그인" },
    dashboard: { path: "/admin/dashboard", label: "관리자 홈" },
    members: { path: "/admin/members", label: "회원 관리" },
    memberDetail: (id) => `/admin/members/${id}`,
  },

  // 예약
  resere: {
    list: { path: "/reservation/list", label: "예약 목록" },
    create: { path: "/reservation/create", label: "예약 생성" },
    landreserve: { path: "/reserve/land/date", label: "놀이터예약"},
    detail: (id) => `/reservation/${id}`,
  },

  // 봉사활동
  volunteer: {
    list: { path: "/volunteer/list", label: "봉사 목록" },
    detail: (id) => `/volunteer/${id}`,
    apply: { path: "/volunteer/apply", label: "봉사 신청" },
  },

  // 404 등 기타
  common: {
    notFound: { path: "*", label: "페이지를 찾을 수 없습니다" },
  },
  
};

export default routes;
