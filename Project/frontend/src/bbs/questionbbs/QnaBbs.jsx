// src/bbs/questionbbs/QnaBbs.jsx
import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import "./qnabbs.css";

function QnaBbs() {
  const [posts, setPosts] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const navigate = useNavigate();

  useEffect(() => {
    fetchPosts(page);
  }, [page]);

  const fetchPosts = async (pageNum) => {
    try {
      const res = await axios.get(`/bbs/bbslist?type=FAQ&page=${pageNum}&size=10`);
      setPosts(res.data.content);
      setTotalPages(res.data.totalPages);
    } catch (error) {
      console.error("목록 조회 오류:", error);
      alert("목록 조회 실패");
    }
  };

  return (
    <div className="bbs-container">
      <h2>❓ Q&A 게시판</h2>
      <table className="bbs-table">
        <thead>
          <tr>
            <th>번호</th>
            <th>제목</th>
            <th>작성자</th>
            <th>작성일</th>
          </tr>
        </thead>
        <tbody>
          {posts.length > 0 ? (
            posts.map((post) => (
              <tr key={post.bulletinNum}>
                <td>{post.bulletinNum}</td>
                <td>
                  <Link to={`/qnabbs/view/${post.bulletinNum}`}>{post.bbsTitle}</Link>
                </td>
                <td>{post.memberName || "익명"}</td>
                <td>{new Date(post.registDate).toLocaleDateString()}</td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="4">등록된 질문이 없습니다.</td>
            </tr>
          )}
        </tbody>
      </table>

      {/* 페이지네이션 */}
      <div className="pagination">
        <button disabled={page === 0} onClick={() => setPage(page - 1)}>
          «
        </button>
        {Array.from({ length: totalPages }, (_, i) => (
          <button
            key={i}
            className={page === i ? "active" : ""}
            onClick={() => setPage(i)}
          >
            {i + 1}
          </button>
        ))}
        <button disabled={page === totalPages - 1} onClick={() => setPage(page + 1)}>
          »
        </button>
      </div>

      {/* 글쓰기 버튼 */}
      <div className="bbs-actions">
        <button onClick={() => navigate("/qnabbs/write")}>질문 작성</button>
      </div>
    </div>
  );
}

export default QnaBbs;
