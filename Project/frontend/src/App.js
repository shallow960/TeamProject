import { BrowserRouter, Routes, Route } from "react-router-dom";
import { AuthProvider } from './common/context/AuthContext'; //로그인 상태 Provider import
import LoginPage from "./program/login/pages/LoginPage";
//import MyPage from "./program/login/pages/MyPage";
//import QnaBbs from './bbs/questionbbs/qnabbs';
import NormalBbsWrite from './bbs/normalbbs/NormalBbsWrite';
import NormalBbs from './bbs/normalbbs/NormalBbs';
import Normal from './bbs/normalbbs/Normal';
import Layout from './layout/Layout';
import QnaBbsWrite from './bbs/questionbbs/QnaBbsWrite';
import QnaBbsView from './bbs/questionbbs/QnaBbsView';


import React from 'react';

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Layout>
          <Routes>
            <Route path="/login" element={<LoginPage />} />
        
            <Route path="/admin/notice" element={<Normal/>} />
            <Route path="/admin/notice/write" element={<NormalBbsWrite />} />
            <Route path="/admin/notice/view/:id" element={<NormalBbs />} />

            <Route path="/qnabbs/write" element={<QnaBbsWrite />} />
            <Route path="/qnabbs/view/:id" element={<QnaBbsView />} />
          
          </Routes>
        </Layout>
      </BrowserRouter>
    </AuthProvider>
  );
}




export default App;
