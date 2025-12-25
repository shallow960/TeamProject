import React, { useEffect } from "react";

export default function RoughMap() {
  useEffect(() => {
    // daum 객체 준비될 때까지 대기
    const timer = setInterval(() => {
      if (window.daum?.roughmap?.Lander) {
        new window.daum.roughmap.Lander({
          timestamp: "1765439763900",
          key: "dp2txyfd5tv",
          mapWidth: "100%",   // 반응형
          mapHeight: "100%", // 원하는 높이
        }).render();
        clearInterval(timer); // 한 번만 실행
      }
    }, 100);
    return () => clearInterval(timer);
  }, []);

  return (
    <div className="map_box">
      <div className="con_map_wrap">
        <div className="map_inner main_map_size">
          <div
            id="daumRoughmapContainer1765439763900"
            className="root_daum_roughmap root_daum_roughmap_landing"
          ></div>
        </div>
      </div>
    </div>
  );
}
