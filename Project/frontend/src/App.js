import { BrowserRouter, Routes, Route } from "react-router-dom";
<<<<<<< HEAD
import { AuthProvider } from './common/context/AuthContext'; //로그인 상태 Provider import
import LoginPage from "./program/login/pages/LoginPage";
//import MyPage from "./program/login/pages/MyPage";
//import QnaBbs from './bbs/questionbbs/qnabbs';
import NormalBbsWrite from './bbs/normalbbs/NormalBbsWrite';
import NormalBbs from './bbs/normalbbs/NormalBbs';
import Normal from './bbs/normalbbs/Normal';
=======

>>>>>>> refs/heads/ahj0808
import Layout from './layout/Layout';
<<<<<<< HEAD
import QnaBbs from "./bbs/questionbbs/QnaBbs";
import QnaBbsWrite from './bbs/questionbbs/QnaBbsWrite';
import QnaBbsView from './bbs/questionbbs/QnaBbsView';
import ImgDetail from './bbs/imagebbs/imgdetail';
import ImgList from "./bbs/imagebbs/imgList";
import ImgEdit from "./bbs/imagebbs/imgedit";
import ImgWrite from "./bbs/imagebbs/imgwrite";

=======
>>>>>>> refs/heads/ahj0808
import React from 'react';

<<<<<<< HEAD
=======
import layoutRoutes from './common/routes/layoutRoutes';
>>>>>>> refs/heads/ahj0808
function App() {
  return (
<<<<<<< HEAD
    <AuthProvider>
      <BrowserRouter>
        <Layout>
          <Routes>
            <Route path="/login" element={<LoginPage />} />
        
            <Route path="/admin/notice" element={<Normal/>} />
            <Route path="/admin/notice/write" element={<NormalBbsWrite />} />
            <Route path="/admin/notice/view/:id" element={<NormalBbs />} />

            <Route path="/qnabbs" element={<QnaBbs />} />
            <Route path="/qnabbs/write" element={<QnaBbsWrite />} />
            <Route path="/qnabbs/view/:id" element={<QnaBbsView />} />
          
            <Route path="/image" element={<ImgList />} />
            <Route path="/image/detail/:id" element={<ImgDetail />} />
            <Route path="/image/write" element={<ImgWrite />} />
            <Route path="/image/edit/:id" element={<ImgEdit />} />

          </Routes>
        </Layout>
      </BrowserRouter>
    </AuthProvider>
=======
    <BrowserRouter>
      <Layout>
        <Routes>{layoutRoutes}</Routes>
      </Layout>
    </BrowserRouter>
>>>>>>> refs/heads/ahj0808
  );
}
export default App;
