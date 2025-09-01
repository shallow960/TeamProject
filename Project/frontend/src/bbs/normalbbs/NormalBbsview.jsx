// 📁 src/admin/NormalBbsView.jsx
import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import api from "../../common/api/axios";

function NormalBbsView() {
  const { id } = useParams(); 
  const [post, setPost] = useState(null);
  const [files, setFiles] = useState([]); // 첨부파일 상태
  const navigate = useNavigate();

  const token = localStorage.getItem("accessToken");
  const apiBase = "http://127.0.0.1:8090/admin/bbs/normal";

  useEffect(() => {
    if (!token) {
      alert("관리자 로그인 후 이용해주세요.");
      navigate("/admin/login");
      return;
    }
    fetchPost();
    fetchFiles(); // 첨부파일 별도 호출
  }, [id, token, navigate]);

  // ---------------- 게시글 조회 ----------------
  const fetchPost = async () => {
    try {
      const res = await api.get(`${apiBase}/${id}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setPost(res.data);
    } catch (error) {
      console.error("게시글 조회 오류:", error);
      if (error.response?.status === 401) {
        alert("로그인이 필요합니다.");
        navigate("/admin/login");
      } else if (error.response?.status === 403) {
        alert("권한이 없습니다.");
      } else if (error.response?.status === 404) {
        alert("게시글을 찾을 수 없습니다.");
        navigate("/admin/bbs/normal");
      } else {
        alert("게시글 조회 실패");
      }
    }
  };

  // ---------------- 첨부파일 조회 ----------------
  const fetchFiles = async () => {
    try {
      const res = await api.get(`${apiBase}/${id}/files`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      // fileUrl 세팅
      const filesWithUrl = res.data.map(f => ({
        ...f,
        fileUrl: `http://127.0.0.1:8090/admin/bbs/files/${f.fileNum}/download`
      }));
      setFiles(filesWithUrl);
    } catch (error) {
      console.error("첨부파일 조회 오류:", error);
    }
  };

  // ---------------- 게시글 삭제 ----------------
  const handleDelete = async () => {
    if (!window.confirm("정말 삭제하시겠습니까?")) return;

    try {
      await api.delete(`${apiBase}/${id}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      alert("삭제되었습니다.");
      navigate("/admin/bbs/normal"); 
    } catch (error) {
      console.error("삭제 오류:", error);
      if (error.response?.status === 401) {
        alert("로그인이 필요합니다.");
        navigate("/admin/login");
      } else if (error.response?.status === 403) {
        alert("권한이 없습니다.");
      } else {
        alert("삭제 실패");
      }
    }
  };

  if (!post) return <div>로딩 중...</div>;

  return (
    <div className="bbs-container">
      <h2>{post.bbsTitle}</h2>
      <div className="bbs-detail-meta">
        <span>{post.registDate ? post.registDate.substring(0, 10) : ""}</span>
        <span>조회 {post.readCount ?? 0}</span>
      </div>
      <div
        className="bbs-content"
        dangerouslySetInnerHTML={{ __html: post.bbsContent }}
      />

      {/* 첨부파일 */}
      {files.length > 0 && (
        <div className="bbs-files">
          <h4>첨부파일</h4>
          <ul>
            {files.map((file) => (
              <li key={file.fileNum} style={{ marginBottom: "10px" }}>
                {file.extension?.match(/(jpeg|jpg|gif|png)/i) ? (
                  <img
                    src={file.fileUrl}
                    alt={file.originalName}
                    style={{ maxWidth: "200px" }}
                  />
                ) : (
                  <a href={file.fileUrl} download>
                    {file.originalName}
                  </a>
                )}
              </li>
            ))}
          </ul>
        </div>
      )}

      {/* 삭제 / 수정 버튼 */}
      <div style={{ marginTop: "20px" }}>
        <button onClick={handleDelete}>삭제</button>
        <button
          onClick={() => navigate(`/admin/bbs/normal/edit/${id}`)}
          style={{ marginLeft: "10px" }}
        >
          수정
        </button>
      </div>
    </div>
  );
}

export default NormalBbsView;
