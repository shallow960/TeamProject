import api from "../../../common/api/axios";

const TimeSlotService = {
  // 관리(ADMIN)
  create(dto) {
    return api.post("/admin/timeslots", dto);
  },

  update(id, dto) {
    return api.put(`/admin/timeslots/${id}`, dto);
  },

  delete(id) {
    return api.delete(`/admin/timeslots/${id}`);
  },

  // 조회(공용)
  fetchByType(timeType) {
    return api.get(`/admin/timeslots/${timeType}`);
  },
};

export default TimeSlotService;
