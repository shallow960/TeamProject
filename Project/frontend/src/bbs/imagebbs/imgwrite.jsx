import React, { useState } from "react";
import axios from "axios";
import "./Gallery.css";

export default function ImgWrite() {  // 🔹 대문자로 변경
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [files, setFiles] = useState([]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const formData = new FormData();
    const bbsDto = { bbstitle: title, bbscontent: content };
    formData.append(
      "bbsDto",
      new Blob([JSON.stringify(bbsDto)], { type: "application/json" })
    );
    formData.append("memberNum", 1);
    formData.append("type", "POTO");
    files.forEach((file) => formData.append("files", file));

    await axios.post("/bbs/bbslist/bbsadd", formData, {
      headers: { "Content-Type": "multipart/form-data" }
    });

    alert("등록 완료");
    window.location.href = "/";
  };

  return (
    <form className="form-container" onSubmit={handleSubmit}>
      <input
        type="text"
        placeholder="제목"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
        required
      />
      <textarea
        placeholder="내용"
        value={content}
        onChange={(e) => setContent(e.target.value)}
        required
      />
      <input
        type="file"
        multiple
        onChange={(e) => setFiles(Array.from(e.target.files))}
      />
      <button type="submit">작성</button>
    </form>
  );
}
