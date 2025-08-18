// 📁 src/router/layoutRoutes.js
import React from "react";
import { Route } from "react-router-dom";
import routes from "./router";

// 📌 각 페이지 컴포넌트 import
import LoginPage from "../../program/login/pages/LoginPage";
import LogoutLink from "../../program/login/pages/LogoutLink";
import AdminLogin from "../../program/admin/pages/AdminLoginPage";
import Signup from "../../program/signup/pages/SignupPage";


// FundPage.js에서 기본 내보내기가 없으므로, FundMainPage를 이름으로 불러옵니다.
// 이제 FundPage.js에서 내보내는 모든 컴포넌트를 불러옵니다.
import {
  FundApplicationDetails,
  FundApplicationForm,
  FundMainPage,
  GoodsApplicationDetails,
  GoodsApplicationForm,
  RecurringApplicationDetails,
  RecurringApplicationForm
} from "../../program/fund/services/FundPage";

// 1:1 채팅 컴포넌트 import
import ChatList from "../../program/chat/services/ChatList";
import ChatRoom from "../../program/chat/services/ChatRoom";

// 입양 신청서 컴포넌트 import
import AdoptApplicationForm from "../../program/adopt/services/AdoptApplicationForm";

// AnimalForm 컴포넌트 import
import AnimalForm from "../../program/animal/services/AnimalForm";

// mapdata 컴포넌트
import MapForm from "../../program/mapdata/services/MapForm";
import MapPage from "../../program/mapdata/services/MapPage";

// 게시판 관련
import Normal from "../../bbs/normalbbs/Normal";
import NormalBbsView from "../../bbs/normalbbs/NormalBbsview";
import NormalBbsWrite from "../../bbs/normalbbs/NormalBbsWrite";
import NormalBbsEdit from "../../bbs/normalbbs/NormalBbsEdit";
import MemberNormalBbs from "../../bbs/normalbbs/MemberNormalBbs";
import MemberNormalBbsView from "../../bbs/normalbbs/MemberNormalBbsView";
import MemberNormalBbsDummy from "../../bbs/normalbbs/MemberNormalBbsdummy";
import AdminNormalBbsdummy from "../../bbs/normalbbs/AdminNormalBbsdummy";

import QnaBbs from "../../bbs/questionbbs/QnaBbs";
import QnaBbsWrite from "../../bbs/questionbbs/QnaBbsWrite";
import QnaBbsView from "../../bbs/questionbbs/QnaBbsView";
import QnaBbsEdit from "../../bbs/questionbbs/QnaBbsEdit";
import AdminQnaBbs from "../../bbs/questionbbs/AdminQnaBbs";
import AdminQnaBbsView from "../../bbs/questionbbs/AdminQnaBbsView";
import AdminQnaBbsDummy from "../../bbs/questionbbs/AdminQnaBbsDummy";


import ImgList from "../../bbs/imagebbs/imgList";
import ImgDetail from "../../bbs/imagebbs/imgdetail";
import ImgEdit from "../../bbs/imagebbs/imgedit";
import ImgWrite from "../../bbs/imagebbs/imgwrite";
import AdminImgBoard from "../../bbs/imagebbs/AdminImgBoard";
import AdminImgDetail from "../../bbs/imagebbs/AdminImgDetail";
import ImgBoardDummy from "../../bbs/imagebbs/ImgBoardDummy";

// 📌 routes 객체 기반으로 Route 구성
const layoutRoutes = [
  <Route key="login" path={routes.member.login.path} element={<LoginPage />} />,
  <Route key="login" path={routes.admin.login.path} element={<AdminLogin />} />,
  <Route key="logout" path={routes.member.logout.path} element={<LogoutLink />} />,
  <Route key="signup" path={routes.member.signup.path} element={<Signup />} />,
  


  // 후원 route
  // 각 후원 페이지를 별도의 경로로 라우팅합니다.
  <Route key="fundMainPage" path={routes.fund.root.path} element={<FundMainPage />} />,
  <Route key="fundForm" path={routes.fund.fundForm.path} element={<FundApplicationForm />} />,
  <Route key="goodsForm" path={routes.fund.goodsForm.path} element={<GoodsApplicationForm />} />,
  <Route key="recurringForm" path={routes.fund.recurringForm.path} element={<RecurringApplicationForm />} />,
  <Route key="fundDetails" path={routes.fund.fundDetails.path} element={<FundApplicationDetails />} />,
  <Route key="goodsDetails" path={routes.fund.goodsDetails.path} element={<GoodsApplicationDetails />} />,
  <Route key="recurringDetails" path={routes.fund.recurringDetails.path} element={<RecurringApplicationDetails />} />,

  // 1:1 채팅 route 추가
  <Route key="chatList" path={routes.admin.chat.list.path} element={<ChatList />} />,
  <Route key="chatRoom" path={routes.admin.chat.room(':roomId')} element={<ChatRoom />} />,

  // 입양 신청서 route 추가
    <Route key="adoptForm" path={routes.admin.adopt.form(':memberNum')} element={<AdoptApplicationForm />} />,
  // 동물 정보 기입 route 추가
    <Route key="animalRegister" path={routes.admin.animal.register.path} element={<AnimalForm />} />,

  // 맵 관련 route 추가
    <Route key="mapPage" path={routes.mapdata.map.path} element={<MapPage />} />,
    <Route key="mapRegister" path={routes.mapdata.register.path} element={<MapForm />} />,

    // 일반 게시판 추가
  <Route key="normal" path="/bbs/normal" element={<Normal />} />,
  <Route key="normal-bbs-view" path="/bbs/normal/:id" element={<NormalBbsView />} />,
  <Route key="normal-bbs-write" path="/bbs/normal/write" element={<NormalBbsWrite />} />,
  <Route key="normal-bbs-edit" path="/bbs/normal/edit" element={<NormalBbsEdit />} />,
  <Route key="Membernormal" path="/bbs/member/normal" element={<MemberNormalBbs />} />,
  <Route key="Membernormal-view" path="/bbs/member/normal/view" element={<MemberNormalBbsView />} />,
  <Route key="Membernormal-dummy" path="/bbs/member/normal/dummy" element={<MemberNormalBbsDummy/>} />,
  <Route key="Adminnormal-dummy" path="/bbs/Admin/normal/dummy" element={<AdminNormalBbsdummy/>} />,

  // QnA 게시판 추가
  <Route key="qna-bbs" path="/bbs/qna" element={<QnaBbs />} />,
  <Route key="qna-bbs-write" path="/bbs/qna/write" element={<QnaBbsWrite />} />,
  <Route key="qna-bbs-view" path="/bbs/qna/:id" element={<QnaBbsView />} />,
  <Route key="qna-bbs-edit" path="/bbs/qna/edit" element={<QnaBbsEdit />} />,
  <Route key="qna-bbs-admin" path="/bbs/admin/qna" element={<AdminQnaBbs />} />,
  <Route key="qna-bbs-admin-view" path="/bbs/admin/qna/view" element={<AdminQnaBbsView />} />,
  <Route key="qna-bbs-admin-dummy" path="/bbs/admin/qna/dummy" element={<AdminQnaBbsDummy />} />,


  // 이미지 게시판 추가
  <Route key="img-list" path="/bbs/image" element={<ImgList />} />,
  <Route key="img-detail" path="/bbs/image/:id" element={<ImgDetail />} />,
  <Route key="img-edit" path="/bbs/image/:id/edit" element={<ImgEdit />} />,
  <Route key="img-write" path="/bbs/image/write" element={<ImgWrite />} />,
  <Route key="img-Admin-ImgBoard" path="/bbs/admin/image" element={<AdminImgBoard />} />,
  <Route key="img-Admin-ImgDetail" path="/bbs/admin/image/Detail" element={<AdminImgDetail />} />,
  <Route key="img-Admin-ImgDummy" path="/bbs/admin/image/Dummy" element={<ImgBoardDummy />} />,
];

export default layoutRoutes;
