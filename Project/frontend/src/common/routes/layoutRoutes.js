import { Route } from "react-router-dom";
import routes from "./router";
import { RequireUserOnMember } from "./RouteGuards";
import { LogoutLink } from "../../program/login/pages/LogoutLink";
import RequireAdmin from "../../common/components/RequireAdmin";

// ==============================
// 회원 관련 페이지
// ==============================
import LoginPage from "../../program/login/pages/LoginPage";
import Join from "../../program/signup/pages/JoinPage";
import PhoneVerifyPage from "../../program/signup/pages/PhoneVerifyPage";
import Signup from "../../program/signup/pages/SignupPage";
import FindId from "../../program/member/pages/FindIdPage";
import FindPw from "../../program/member/pages/FindPasswordPage";
import ChangePw from "../../program/member/pages/UpdatePasswordPage";
import MyPage from "../../program/member/pages/Mypage";
import MemberPage from "../../program/member/pages/MemberData";
import MyReserveListPage from "../../program/member/pages/MyReserveListPage";
import LandReserveDetailPage from "../../program/member/pages/LandReserveDetailPage";
import VolunteerReserveDetailPage from "../../program/member/pages/VolunteerReserveDetailPage";

// ==============================
// 후원(Fund) 관련 페이지
// ==============================
import {
  FundApplicationDetails,
  FundApplicationForm,
  FundMainPage,
  GoodsApplicationDetails,
  GoodsApplicationForm,
  RegularApplicationDetails,
  RegularApplicationForm,
  MemberFundList,
  MemberFundDetail
} from "../../program/fund/services/FundPage";

// ==============================
// 1:1 채팅 관련 페이지
// ==============================
import ChatList from "../../program/chat/services/ChatList";
import ChatRoom from "../../program/chat/services/ChatRoom";

// ==============================
// 입양 신청서 관련 페이지
// ==============================
import AdoptApplicationForm from "../../program/adopt/services/AdoptApplicationForm";

// ==============================
// 동물 정보 페이지 (Animal)
// ==============================
import AnimalForm from "../../program/animal/services/AnimalForm";

// ==============================
// 지도 관련 페이지
// ==============================
import MapForm from '../../program/mapdata/services/MapForm.jsx';

// ==============================
// 관리자 페이지
// ==============================
import Admin from "../../program/admin/pages/AdminPage";
import AdminPw from "../../program/admin/pages/AdminPasswordUpdatePage";
import MemberList from "../../program/admin/pages/MemberListPage";
import MemberDetail from "../../program/admin/pages/MemberDetailPage";
import TimeSlotManagePage from "../../program/admin/pages/TimeSlotManagePage";
import ClosedDayManagePage from "../../program/admin/pages/ClosedDayManagePage";
import AdminLandReservePage from "../../program/admin/pages/AdminLandReservePage";
import AdminVolunteerReservePage from "../../program/admin/pages/AdminVolunteerReservePage";
import AdminLandReserveDetailPage from "../../program/admin/pages/AdminLandReserveDetailPage";
import AdminVolunteerReserveDetailPage from "../../program/admin/pages/AdminVolunteerReserveDetailPage";

// ==============================
// 예약 페이지
// ==============================
import LandReserveDatePage from "../../program/reserve/land/pages/LandReserveDatePage";
import LandReserveFormPage from "../../program/reserve/land/pages/LandReserveFormPage";
import LandReserveConfirmPage from "../../program/reserve/land/pages/LandReserveConfirmPage";
import LandReserveSuccessPage from "../../program/reserve/land/pages/LandReserveSuccessPage";
import VolunteerReserveDatePage from "../../program/reserve/volunteer/pages/VolunteerReserveDatePage";
import VolunteerReserveFormPage from "../../program/reserve/volunteer/pages/VolunteerReserveFormPage";
import VolunteerReserveConfirmPage from "../../program/reserve/volunteer/pages/VolunteerReserveConfirmPage";
import VolunteerReserveSuccessPage from "../../program/reserve/volunteer/pages/VolunteerReserveSuccessPage";


// ==============================
// 📌 routes 객체 기반 Route 구성
// ==============================
const layoutRoutes = [

  // ------------------------------
  // 후원(Fund) 관련 Route
  // ------------------------------
  <Route key="fundMainPage" path={routes.fund.root.path} element={<FundMainPage />} />,
  <Route key="fundForm" path={routes.fund.fundForm.path} element={<FundApplicationForm />} />,
  <Route key="goodsForm" path={routes.fund.goodsForm.path} element={<GoodsApplicationForm />} />,
  <Route key="regularForm" path={routes.fund.regularForm.path} element={<RegularApplicationForm />} />,
  <Route key="fundDetails" path={routes.fund.fundDetails.path} element={<FundApplicationDetails />} />,
  <Route key="goodsDetails" path={routes.fund.goodsDetails.path} element={<GoodsApplicationDetails />} />,
  <Route key="regularDetails" path={routes.fund.regularDetails.path} element={<RegularApplicationDetails />} />,
  <Route key="memberFundList" path={routes.member.fund.list.path} element={<MemberFundList />} />,
  <Route key="memberFundDetail" path={routes.member.fund.detail(`:id`)} element={<MemberFundDetail/>} />,

  // ------------------------------
  // 1:1 채팅 Route
  // ------------------------------
  <Route key="chatList" path={routes.admin.chat.list.path} element={<ChatList />} />,
  <Route key="chatRoom" path={routes.admin.chat.room(':roomId')} element={<ChatRoom />} />,

  // ------------------------------
  // 입양 신청서 Route
  // ------------------------------
  <Route key="memberAdoptList" path={routes.member.adopt.list.path} element={<AdoptApplicationForm />} />,
  <Route key="memberAdoptDetail" path={routes.member.adopt.detail(':id')} element={<AdoptApplicationForm />} />,
  <Route key="adminAdoptList" path={routes.admin.adopt.list.path} element={<AdoptApplicationForm />} />,
  <Route key="adminAdoptDetail" path={routes.admin.adopt.detail(':id')} element={<AdoptApplicationForm />} />,
  <Route key="adminAdoptRegist" path={routes.admin.adopt.regist.path} element={<AdoptApplicationForm />} />,
  <Route key="adminAdoptUpdate" path={routes.admin.adopt.update(':id')} element={<AdoptApplicationForm />} />,

  // ------------------------------
  // 동물 정보 Route (관리자)
  // ------------------------------
  <Route key="adminAnimalList" path={routes.admin.animal.list.path} element={<AnimalForm />} />,
  <Route key="adminAnimalDetail" path={routes.admin.animal.detail(':id')} element={<AnimalForm />} />,
  <Route key="adminAnimalRegist" path={routes.admin.animal.regist.path} element={<AnimalForm />} />,
  <Route key="adminAnimalUpdate" path={routes.admin.animal.update(':id')} element={<AnimalForm />} />,

  // ------------------------------
  // 지도 관련 Route
  // ------------------------------
  <Route key="map" path={routes.map.root.path} element={<MapForm />} />,

  // ------------------------------
  // 예약 플로우 Route
  // ------------------------------
  <Route key="reserve-land-date" path={routes.reserve.land.date.path} element={<LandReserveDatePage />} />,
  <Route key="reserve-land-form" path={routes.reserve.land.form.path} element={<LandReserveFormPage />} />,
  <Route key="reserve-land-confirm" path={routes.reserve.land.confirm.path} element={<LandReserveConfirmPage />} />,
  <Route key="reserve-land-success" path={routes.reserve.land.success.path} element={<LandReserveSuccessPage />} />,
  <Route key="reserve-volunteer-date" path={routes.reserve.volunteer.date.path} element={<VolunteerReserveDatePage />} />,
  <Route key="reserve-volunteer-form" path={routes.reserve.volunteer.form.path} element={<VolunteerReserveFormPage />} />,
  <Route key="reserve-volunteer-confirm" path={routes.reserve.volunteer.confirm.path} element={<VolunteerReserveConfirmPage />} />,
  <Route key="reserve-volunteer-success" path={routes.reserve.volunteer.success.path} element={<VolunteerReserveSuccessPage />} />,

  // ------------------------------
  // 사용자 관련 Route
  // ------------------------------
  <Route key="login" path={routes.member.login.path} element={<LoginPage />} />,
  <Route key="logout" path={routes.member.logout.path} element={<LogoutLink />} />,
  <Route key="join" path={routes.member.join.path} element={<Join />} />,
  <Route key="phonetest" path={routes.member.phone.path} element={<PhoneVerifyPage />} />,
  <Route key="signup" path={routes.member.signup.path} element={<Signup />} />,
  <Route key="find-id" path={routes.member.findid.path} element={<FindId />} />,
  <Route key="find-pw" path={routes.member.findpw.path} element={<FindPw />} />,
  <Route key="update-password" path={routes.member.changepw.path} element={<ChangePw />} />,

  // 마이페이지 관련 라우트 (로그인 필요)
  <Route key="mypage" path={routes.member.mypage.path} element={<RequireUserOnMember><MyPage /></RequireUserOnMember>} />,
  <Route key="memberdata" path={routes.member.memberdata.path} element={<RequireUserOnMember><MemberPage /></RequireUserOnMember>} />,
  <Route key="mypage-reserves" path={routes.member.reserves.path} element={<RequireUserOnMember><MyReserveListPage /></RequireUserOnMember>} />,
  <Route key="mypage-reserve-land-detail" path={routes.member.landReserveDetail.path} element={<RequireUserOnMember><LandReserveDetailPage /></RequireUserOnMember>} />,
  <Route key="mypage-reserve-volunteer-detail" path={routes.member.volunteerReserveDetail.path} element={<RequireUserOnMember><VolunteerReserveDetailPage /></RequireUserOnMember>} />,
  
  // ------------------------------
  // 관리자 관련 Route (관리자 권한 필요)
  // ------------------------------
  <Route key="admin" path={routes.admin.admin.path} element={<RequireAdmin><Admin /></RequireAdmin>} />,
  <Route key="updatePw" path={routes.admin.password.path} element={<RequireAdmin><AdminPw /></RequireAdmin>} />,
  <Route key="memberList" path={routes.admin.members.path} element={<RequireAdmin><MemberList /></RequireAdmin>} />,
  <Route key="memberDetail" path={routes.admin.memberDetail.path} element={<RequireAdmin><MemberDetail /></RequireAdmin>} />,
  <Route key="admin-timeslot-manage" path={routes.admin.timeSlotManage.path} element={<RequireAdmin><TimeSlotManagePage /></RequireAdmin>} />,
  <Route key="admin-closedday-manage" path={routes.admin.closedDayManage.path} element={<RequireAdmin><ClosedDayManagePage /></RequireAdmin>} />,
  <Route key="admin-land-manage" path={routes.admin.landReserveManage.path} element={<RequireAdmin><AdminLandReservePage /></RequireAdmin>} />,
  <Route key="admin-volunteer-manage" path={routes.admin.volunteerReserveManage.path} element={<RequireAdmin><AdminVolunteerReservePage /></RequireAdmin>} />,
  <Route key="admin-land-detail" path={routes.admin.landReserveDetail.path} element={<RequireAdmin><AdminLandReserveDetailPage /></RequireAdmin>} />,
  <Route key="admin-volunteer-detail" path={routes.admin.volunteerReserveDetail.path} element={<RequireAdmin><AdminVolunteerReserveDetailPage /></RequireAdmin>} />,
];

export default layoutRoutes;