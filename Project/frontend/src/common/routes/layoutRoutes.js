// 📁 src/router/layoutRoutes.js

import { Route } from "react-router-dom";
import routes from "./router";

// 📌 각 페이지 컴포넌트 import
import LoginPage from "../../program/login/pages/LoginPage";
import LogoutLink from "../../program/login/pages/LogoutLink";

// ✅ Land 예약 플로우 페이지
import LandReserveDatePage from "../../program/reserve/land/pages/LandReserveDatePage";
import LandReserveFormPage from "../../program/reserve/land/pages/LandReserveFormPage";
import LandReserveConfirmPage from "../../program/reserve/land/pages/LandReserveConfirmPage";
import LandReserveSuccessPage from "../../program/reserve/land/pages/LandReserveSuccessPage";

// ✅ Volunteer 예약 플로우
import VolunteerReserveDatePage from "../../program/reserve/volunteer/pages/VolunteerReserveDatePage";
import VolunteerReserveFormPage from "../../program/reserve/volunteer/pages/VolunteerReserveFormPage";
import VolunteerReserveConfirmPage from "../../program/reserve/volunteer/pages/VolunteerReserveConfirmPage";
import VolunteerReserveSuccessPage from "../../program/reserve/volunteer/pages/VolunteerReserveSuccessPage";


// 📌 routes 객체 기반으로 Route 구성
const layoutRoutes = [
  <Route key="login" path={routes.member.login.path} element={<LoginPage />} />,
  <Route key="logout" path={routes.member.logout.path} element={<LogoutLink />} />,
  // 🔹 Land 예약: 날짜 → 폼 → 확인 → 완료
  <Route key="reserve-land-date" path={routes.reserve.land.date.path} element={<LandReserveDatePage />} />,
  <Route key="reserve-land-form" path={routes.reserve.land.form.path} element={<LandReserveFormPage />} />,
  <Route key="reserve-land-confirm" path={routes.reserve.land.confirm.path} element={<LandReserveConfirmPage />} />,
  <Route key="reserve-land-success" path={routes.reserve.land.success.path} element={<LandReserveSuccessPage />} />,

  // 🔹 Volunteer 예약: 날짜 → 폼 → 확인 → 완료
  <Route key="reserve-volunteer-date" path={routes.reserve.volunteer.date.path} element={<VolunteerReserveDatePage />} />,
  <Route key="reserve-volunteer-form" path={routes.reserve.volunteer.form.path} element={<VolunteerReserveFormPage />} />,
  <Route key="reserve-volunteer-confirm" path={routes.reserve.volunteer.confirm.path} element={<VolunteerReserveConfirmPage />} />,
  <Route key="reserve-volunteer-success" path={routes.reserve.volunteer.success.path} element={<VolunteerReserveSuccessPage />} />,
];

export default layoutRoutes;
