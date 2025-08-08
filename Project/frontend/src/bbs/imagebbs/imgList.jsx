import React, { useState, useEffect } from "react";
import axios from "axios";
import "./Gallery.css";

const ITEMS_PER_PAGE = 12;
const GROUP_SIZE = 10;

export default function ImgList() {  // 🔹 대문자로 변경
  const [posts, setPosts] = useState([]);
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(0);

  useEffect(() => {
    axios
      .get("/bbs/bbslist", {
        params: {
          type: "POTO",
          page: page - 1,
          size: ITEMS_PER_PAGE
        }
      })
      .then((res) => {
        setPosts(res.data.content);
        setTotalPages(res.data.totalPages);
      })
      .catch(console.error);
  }, [page]);

  const group = Math.ceil(page / GROUP_SIZE);
  const groupStart = (group - 1) * GROUP_SIZE + 1;
  const groupEnd = Math.min(groupStart + GROUP_SIZE - 1, totalPages);

  return (
    <div className="gallery-container">
      <h2>이미지 게시판</h2>
      <div className="gallery-grid">
        {posts.map((post) => (
          <div
            key={post.bulletinNum}
            className="gallery-card"
            onClick={() => (window.location.href = `/bbs/${post.bulletinNum}`)}
          >
            <img src={post.representativeImageUrl} alt={post.bbstitle} />
            <div className="gallery-info">
              <strong>{post.bbstitle}</strong>
              <p>{post.bbscontent}</p>
            </div>
          </div>
        ))}
      </div>

      <div className="pagination">
        {groupStart > 1 && (
          <button onClick={() => setPage(groupStart - 1)}>&lt;</button>
        )}
        {Array.from(
          { length: groupEnd - groupStart + 1 },
          (_, i) => groupStart + i
        ).map((n) => (
          <button
            key={n}
            className={n === page ? "active" : ""}
            onClick={() => setPage(n)}
          >
            {n}
          </button>
        ))}
        {groupEnd < totalPages && (
          <button onClick={() => setPage(groupEnd + 1)}>&gt;</button>
        )}
      </div>
    </div>
  );
}
