import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function QnaBbsWrite() {
  const [bbstitle, setBbstitle] = useState("");
  const [bbscontent, setBbscontent] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    const memberNum = localStorage.getItem("memberNum"); // 로그인된 회원 번호
    if (!memberNum) {
      alert("로그인이 필요합니다.");
      return;
    }

    const dto = { bbstitle, bbscontent };

    const formData = new FormData();
    formData.append("bbsDto", new Blob([JSON.stringify(dto)], { type: "application/json" }));
    formData.append("type", "FAQ");
    formData.append("memberNum", memberNum);

    try {
      await axios.post("/bbs/bbslist/bbsadd", formData, {
        headers: { "Content-Type": "multipart/form-data" },
      });
      alert("질문이 등록되었습니다.");
      navigate("/qnabbs");
    } catch (error) {
      console.error("질문 등록 오류:", error);
      alert("등록 실패");
    }
  };

  return (
    <div className="bbs-container">
      <h2>❓ 질문 작성</h2>
      <form onSubmit={handleSubmit} className="bbs-form">
        <div>
          <label>제목</label>
          <input value={bbstitle} onChange={(e) => setBbstitle(e.target.value)} required />
        </div>
        <div>
          <label>내용</label>
          <textarea value={bbscontent} onChange={(e) => setBbscontent(e.target.value)} required />
        </div>
        <button type="submit">등록</button>
      </form>
    </div>
  );
}

export default QnaBbsWrite;