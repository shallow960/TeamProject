import api from "../../../common/api/axios";

const AdminReserveService = {
  // 놀이터 예약 목록
  getLandReservations() {
    return api.get("/admin/reserve/land");
  },

  // 봉사 예약 목록
  getVolunteerReservations() {
    return api.get("/admin/reserve/volunteer");
  },

  // 놀이터 예약 검색
  searchLandReservations(filters) {
    return api.post("/admin/reserve/land/search", filters);
  },

  // 봉사 예약 검색
  searchVolunteerReservations(filters) {
    return api.post("/admin/reserve/volunteer/search", filters);
  },
  // ✅ 놀이터 예약 상세 조회
  getLandReservationDetail(reserveCode) {
    return api.get(`/admin/reserve/land/${reserveCode}`);
  },

  // ✅ 봉사 예약 상세 조회
  getVolunteerReservationDetail(reserveCode) {
    return api.get(`/admin/reserve/volunteer/${reserveCode}`);
  },

  // ✅ 예약 상태 변경 (공통)
  updateReserveState(reserveCode, newState) {
    return api.patch(`/admin/reserve/${reserveCode}/state`, {
      reserveState: newState,
    });
  },
};

export default AdminReserveService;