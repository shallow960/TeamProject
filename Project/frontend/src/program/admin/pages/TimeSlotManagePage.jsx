import React, { useEffect, useState, useMemo } from "react";
import axios from "axios";

const STORAGE_KEY = "landTypeRules"; // { SMALL: number[], LARGE: number[] }

const api = axios.create({
  baseURL: process.env.REACT_APP_API_BASE || "",
});

const TimeSlotManagePage = () => {
  const [slots, setSlots] = useState([]);
  const [rules, setRules] = useState({ SMALL: [], LARGE: [] });
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    let mounted = true;

    const load = async () => {
      try {
        setLoading(true);
        setError("");

        const res = await api.get("/api/time-slots/LAND");
        if (!mounted) return;
        setSlots(res.data || []);

        const saved = localStorage.getItem(STORAGE_KEY);
        if (saved) {
          try {
            const parsed = JSON.parse(saved);
            setRules({
              SMALL: Array.isArray(parsed.SMALL) ? parsed.SMALL : [],
              LARGE: Array.isArray(parsed.LARGE) ? parsed.LARGE : [],
            });
          } catch {
            setRules({ SMALL: [], LARGE: [] });
          }
        }
      } catch (e) {
        if (mounted) setError("시간대 목록을 불러오지 못했습니다.");
      } finally {
        if (mounted) setLoading(false);
      }
    };

    load();
    return () => { mounted = false; };
  }, []);

  const allIds = useMemo(() => slots.map(s => s.id), [slots]);

  const toggleRule = (type, slotId, checked) => {
    setRules(prev => {
      const set = new Set(prev[type] || []);
      checked ? set.add(slotId) : set.delete(slotId);
      return { ...prev, [type]: Array.from(set) };
    });
  };

  const selectAll = (type) => {
    setRules(prev => ({ ...prev, [type]: allIds }));
  };

  const clearAll = (type) => {
    setRules(prev => ({ ...prev, [type]: [] }));
  };

  const save = () => {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(rules));
    alert("저장되었습니다.");
  };

  if (loading) return <div>불러오는 중입니다…</div>;
  if (error) return <div>{error}</div>;

  return (
    <div>
      <h2>시간대 운영 관리 (놀이터)</h2>

      <div>
        <button onClick={() => selectAll("SMALL")}>소형견 전체 허용</button>
        <button onClick={() => clearAll("SMALL")}>소형견 전체 해제</button>
        <button onClick={() => selectAll("LARGE")}>대형견 전체 허용</button>
        <button onClick={() => clearAll("LARGE")}>대형견 전체 해제</button>
        <button onClick={save} disabled={saving}>
          {saving ? "저장 중…" : "저장"}
        </button>
      </div>

      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>라벨</th>
            <th>정원</th>
            <th>활성</th>
            <th>소형견 허용</th>
            <th>대형견 허용</th>
          </tr>
        </thead>
        <tbody>
          {slots.map((s) => (
            <tr key={s.id}>
              <td>{s.id}</td>
              <td>{s.label}</td>
              <td>{s.capacity ?? "-"}</td>
              <td>{s.enabled ? "Y" : "N"}</td>
              <td>
                <input
                  type="checkbox"
                  checked={(rules.SMALL || []).includes(s.id)}
                  onChange={(e) => toggleRule("SMALL", s.id, e.target.checked)}
                />
              </td>
              <td>
                <input
                  type="checkbox"
                  checked={(rules.LARGE || []).includes(s.id)}
                  onChange={(e) => toggleRule("LARGE", s.id, e.target.checked)}
                />
              </td>
            </tr>
          ))}
          {slots.length === 0 && (
            <tr>
              <td colSpan={6}>표시할 시간대가 없습니다.</td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
};

export default TimeSlotManagePage;