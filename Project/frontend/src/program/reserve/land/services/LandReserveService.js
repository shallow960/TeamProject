import api from "../../../../common/api/axios";

/**
 * 백엔드 매핑
 * - GET /land/timeslots?date=yyyy-MM-dd&memberNum=1&landType=SMALL
 *     -> List<LandCountDto> (timeSlotId, label, landType, reservedCount, capacity)
 * - GET /timeslots/land
 *     -> List<TimeSlotDto> (id, label, startTime, endTime, capacity, enabled)
 * - POST /reserve
 *     -> 예약 생성 (사용 중이던 엔드포인트 유지)
 */
const LandReserveService = {
  // 날짜+유형별 시간대 현황(우선 사용)
fetchReservationStatus(landDate, memberNum, landType) {
  return api.get("/land/timeslots", {
    params: { date: landDate, memberNum, landType }
  });
},

  // 전체 LAND 타임슬롯
fetchTimeSlots() {
  return api.get("/timeslots/LAND");
},

  // 예약 생성 (기존 유지)
  createReserve(fullReserveRequestDto) {
    return api.post("/reserve", fullReserveRequestDto);
  },
};

export default LandReserveService;