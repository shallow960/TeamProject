import React, { useRef, useState, useMemo } from "react";
import { useNavigate } from "react-router-dom";
// 주의: 관리자 폴더에서 common/api까지의 상대경로는 3단계 상위입니다.
import api from "../../common/api/axios";

export default function AdminImgWrite() {
  // 제목/본문
  const [title, setTitle] = useState("");
  const editorRef = useRef(null);

  // 파일 목록 상태
  //  - id: 행 구분용 고유값
  //  - file: 실제 File 객체
  const [files, setFiles] = useState([{ id: Date.now(), file: null }]);

  // 대표 이미지(전역 1개) — 행의 id를 저장
  const [repId, setRepId] = useState(null);

  // 전송 중 중복 제출 방지
  const [submitting, setSubmitting] = useState(false);

  const navigate = useNavigate();

  // ─────────────────────────────────────────────────────────────
  // 유틸: JPEG만 허용 (서비스 정책: jpg/jpeg + image/jpeg + 5MB 이하)
  // ─────────────────────────────────────────────────────────────
  const isAllowedImage = (file) => {
    if (!file) return false;
    const mimeOk = String(file.type || "").toLowerCase() === "image/jpeg";
    const name = String(file.name || "");
    const ext = name.includes(".") ? name.split(".").pop().toLowerCase() : "";
    const extOk = ext === "jpg" || ext === "jpeg";
    const sizeOk = file.size <= 5 * 1024 * 1024;
    return mimeOk && extOk && sizeOk;
  };

  // ─────────────────────────────────────────────────────────────
  // 파일 핸들러
  // ─────────────────────────────────────────────────────────────
  const handleFileChange = (id, file) => {
    if (file && !isAllowedImage(file)) {
      alert("jpg/jpeg 형식, 5MB 이하만 첨부 가능합니다.");
      return;
    }

    setFiles((prev) =>
      prev.map((f) => (f.id === id ? { ...f, file } : f))
    );

    // 대표 미설정 상태에서 첫 파일이 들어오면 자동 대표로 선정(UX 보조)
    if (!repId && file) setRepId(id);
  };

  const addFileInput = () =>
    setFiles((prev) => [...prev, { id: Date.now(), file: null }]);

  const removeFileInput = (id) => {
    setFiles((prev) => prev.filter((f) => f.id !== id));
    // 대표로 선택된 행을 지우면 대표 해제
    if (repId === id) setRepId(null);
  };

  // 현재 유효(파일이 실제 선택된)한 행만 필터링
  const validFiles = useMemo(() => files.filter((f) => !!f.file), [files]);

  // 라디오(전역 1개 그룹) — 해당 행을 대표로 지정
  const chooseRepresentative = (id) => setRepId(id);

  // ─────────────────────────────────────────────────────────────
  // 제출
  // ─────────────────────────────────────────────────────────────
  const handleSubmit = async (e) => {
    e.preventDefault();
    if (submitting) return;

    if (!title.trim()) {
      alert("제목을 입력하세요.");
      return;
    }
    if (validFiles.length === 0) {
      alert("최소 1장 이상의 jpg/jpeg 이미지를 첨부하세요.");
      return;
    }

    // 대표 이미지 보정: 미선택이면 첫 유효 파일을 자동 대표로 지정
    const finalRepId = repId ?? validFiles[0]?.id ?? null;

    // FormData 구성
    const formData = new FormData();
    formData.append("type", "POTO"); // 관례적으로 함께 보냄(서버에서 쓰지 않아도 무해)

    const contentHTML = editorRef.current?.innerHTML || "";
    const bbsDtoPayload = {
      bbsTitle: title,
      bbsContent: contentHTML,
      bulletinType: "POTO",
    };
    formData.append(
      "bbsDto",
      new Blob([JSON.stringify(bbsDtoPayload)], { type: "application/json" })
    );

    // files & isRepresentative (순서/개수 동일하게)
    validFiles.forEach((f) => {
      formData.append("files", f.file);
      formData.append("isRepresentative", f.id === finalRepId ? "Y" : "N");
    });

    const ADMIN_CREATE_URL = "/admin/bbs/imgadd";

    try {
      setSubmitting(true);

      const res = await api.post(ADMIN_CREATE_URL, formData, {
        headers: { "Content-Type": "multipart/form-data" },
      });

      const data = res?.data || {};
      const newId =
        data?.bulletinNum ??
        data?.id ??
        data?.bbs?.bulletinNum ??
        data?.bbs?.id ??
        null;

      console.log("created bulletinNum:", newId);

      alert("게시글이 등록되었습니다.");
      navigate("/admin/bbs/image");
    } catch (error) {
      const msg =
        error?.response?.data?.error ||
        error?.response?.data?.message ||
        "서버 오류";
      alert(`등록 실패: ${msg}`);
      console.error("등록 실패:", error);
    } finally {
      setSubmitting(false);
    }
  };

  // ─────────────────────────────────────────────────────────────
  // 렌더
  // ─────────────────────────────────────────────────────────────
  return (
    <div className="bbs-write-container">
      <form className="bbs-write-form" onSubmit={handleSubmit}>
        <div className="bbs-row">
          <div className="bbs-label">이름</div>
          <input
            type="text"
            className="bbs-title-input"
            placeholder="이름을 입력하세요"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
          />
        </div>

        <div className="bbs-row">
          <div className="bbs-label">소개</div>
          <div
            ref={editorRef}
            contentEditable
            className="bbs-content-input"
            style={{
              minHeight: "200px",
              border: "1px solid #ccc",
              padding: "10px",
              whiteSpace: "pre-wrap",
            }}
          />
        </div>

        <div className="bbs-row">
          <div className="bbs-label">파일 첨부</div>
          <div className="bbs-file-list">
            {files.map((f) => (
              <div className="bbs-file-row" key={f.id}>
                <input
                  type="file"
                  accept=".jpg,.jpeg,image/jpeg"
                  onChange={(e) => handleFileChange(f.id, e.target.files[0])}
                />

                <div className="bbs-file-options">
                  <label>
                    <input
                      type="radio"
                      name="repOption"          // 전역 하나의 그룹
                      disabled={!f.file}        // 파일이 없으면 선택 불가
                      checked={repId === f.id}  // 이 행이 대표인지 여부
                      onChange={() => chooseRepresentative(f.id)}
                    />{" "}
                    대표 이미지로 사용
                  </label>
                </div>

                {files.length > 1 && (
                  <button
                    type="button"
                    className="bbs-file-remove"
                    onClick={() => removeFileInput(f.id)}
                    title="이 행 삭제"
                  >
                    ❌
                  </button>
                )}
              </div>
            ))}
            <p className="em_b_red">
              * jpg/jpeg 형식, 5MB 이하만 업로드 가능. 대표 이미지는 1개만
              선택됩니다.
            </p>
            <button
              type="button"
              className="bbs-file-add"
              onClick={addFileInput}
            >
              ➕ 파일 추가
            </button>
          </div>
        </div>

        <div className="bbs-btn-area">
          <button
            type="button"
            className="bbs-cancel-btn"
            onClick={() => navigate("/admin/bbs/image")}
            disabled={submitting}
          >
            취소
          </button>
          <button type="submit" className="bbs-save-btn" disabled={submitting}>
            {submitting ? "등록 중..." : "등록"}
          </button>
        </div>
      </form>
    </div>
  );
}
