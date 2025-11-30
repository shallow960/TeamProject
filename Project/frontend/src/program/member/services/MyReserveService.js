import api from "../../../common/api/axios";


const MyReserveService = {
  // 전체 예약 목록 조회
  getMyReserves: async (memberNum) => {
    const res = await api.get(`/reserve/my`, {
      params: { memberNum },
    });
    return res.data;
  },

  // 유형별 예약 목록 조회 (type: 1 = LAND, 2 = VOLUNTEER)
  getMyReservesByType(memberNum, type) {
  return api.get(`/reserve/my/type`, { 
    params: { memberNum, type }
    }).then(res => res.data);
  },

  // ✅ 예약 상세 조회 (놀이터)
 getLandReserveDetail: async (reserveCode, token) => {
  const res = await api.get(`/reserve/land/${reserveCode}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return res.data;
},

  // ✅ 예약 상세 조회 (봉사)
  getVolunteerReserveDetail: async (reserveCode, token) => {
    const res = await api.get(`/reserve/volunteer/${reserveCode}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return res.data;
},

  // ✅ 예약 취소
  cancelReserve: async (reserveCode, token) => {
    const res = await api.delete(`/reserve/${reserveCode}/cancel`, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    return res.data;
  },
};

export default MyReserveService;