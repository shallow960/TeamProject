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
// 백엔드 컨트롤러 매핑에 맞춤
// @RequestMapping("/api/timeslots")

// const PREFIX = "/timeslots";

// const TimeSlotService = {
//   // type: "LAND" | "VOL" | "ALL"
//   fetchByType: (type) => {
//     // 최종 호출: GET http://152.67.212.81/api/timeslots/LAND
//     return api.get(`${PREFIX}/${type}`);
//   },

//   create: (dto) => {
//     // POST /api/timeslots
//     return api.post(PREFIX, dto);
//   },

//   update: (id, dto) => {
//     // PUT /api/timeslots/{id}
//     return api.put(`${PREFIX}/${id}`, dto);
//   },

//   delete: (id) => {
//     // DELETE /api/timeslots/{id}
//     return api.delete(`${PREFIX}/${id}`);
//   },
// };
export default TimeSlotService;
