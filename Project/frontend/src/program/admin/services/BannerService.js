import api from "../../../common/api/axios";

const BannerService = {
  // 배너 전체 조회
  getAll() {
    return api.get("/banner");
  },

  // 배너 등록 (FormData 필요)
  create(formData) {
    return api.post("/banner", formData, {
      headers: { "Content-Type": "multipart/form-data" },
    });
  },

  // 배너 상세 조회
  getById(id) {
    return api.get(`/banner/${id}`);
  },

  // 배너 수정 (FormData 필요)
  update(id, formData) {
    return api.put(`/banner/${id}`, formData, {
      headers: { "Content-Type": "multipart/form-data" },
    });
  },

  // 배너 삭제
  delete(id) {
    return api.delete(`/banner/${id}`);
  },
};

export default BannerService;