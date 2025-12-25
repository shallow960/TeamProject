import { useEffect, useState } from "react";
import "../style/Map.css";

const MapForm = () => {
  const [map, setMap] = useState(null);
  const [places, setPlaces] = useState([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [activeTab, setActiveTab] = useState("hospital");
  const [currentLocation, setCurrentLocation] = useState(null);
  const [markers, setMarkers] = useState([]);
  const kakaoMapKey = "445e20007391454dbf9c6cc185f4ce6e";

  // ✅ 첫 번째 useEffect: 카카오맵 SDK 로드 및 맵 객체 생성
  useEffect(() => {
    const script = document.createElement("script");
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${kakaoMapKey}&libraries=services,clusterer&autoload=false`;
    script.async = true;
    document.head.appendChild(script);

    script.onload = () => {
      window.kakao.maps.load(() => {
        const container = document.getElementById("map");
        const options = {
          center: new window.kakao.maps.LatLng(33.450701, 126.570667),
          level: 3,
        };
        const newMap = new window.kakao.maps.Map(container, options);
        setMap(newMap);
      });
    };
  }, []);

  // ✅ 두 번째 useEffect: map이 준비된 후 현재 위치 및 장소 검색 실행
  useEffect(() => {
    if (!map) return;

    window.kakao.maps.load(() => {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
          (position) => {
            const lat = position.coords.latitude;
            const lng = position.coords.longitude;
            const center = new window.kakao.maps.LatLng(lat, lng);
            setCurrentLocation(center);
            map.setCenter(center);

            // 현재 위치 마커
            const currentMarker = new window.kakao.maps.Marker({
              position: center,
              map: map,
            });
            setMarkers([currentMarker]);

            // 현재 위치 기준 동물병원 검색
            fetchPlacesByRadius(map, center, "동물병원", 3000);
          },
          (error) => {
            console.error("위치 정보를 가져오는 데 실패했습니다.", error);

            // ✅ 여기부터 수정: 실패 시 기본 위치(서울 시청)로 동작
            // HTTP + IP 환경에서는 브라우저 정책으로 geolocation이 막히기 때문에
            // 기본 좌표를 하나 잡아서 그 기준으로 검색/탭 등을 동작하게 만든다.
            const defaultCenter = new window.kakao.maps.LatLng(
              37.5665, // 위도 (서울 시청)
              126.9780 // 경도
            );

            // 기본 위치를 currentLocation 으로 세팅
            setCurrentLocation(defaultCenter);
            map.setCenter(defaultCenter);

            // 기본 위치 마커
            const defaultMarker = new window.kakao.maps.Marker({
              position: defaultCenter,
              map: map,
            });
            setMarkers([defaultMarker]);

            alert(
              "현재 위치를 가져오지 못했습니다.\n기본 위치(서울 시청) 기준으로 주변 정보를 표시합니다."
            );

            // 기본 위치 기준으로 주변 동물병원 검색
            fetchPlacesByRadius(map, defaultCenter, "동물병원", 3000);
          }
        );
      } else {
        console.error("이 브라우저에서는 Geolocation이 지원되지 않습니다.");

        // Geolocation 자체가 지원되지 않는 경우에도 기본 위치로 처리
        const defaultCenter = new window.kakao.maps.LatLng(37.5665, 126.9780);
        setCurrentLocation(defaultCenter);
        map.setCenter(defaultCenter);

        const defaultMarker = new window.kakao.maps.Marker({
          position: defaultCenter,
          map: map,
        });
        setMarkers([defaultMarker]);

        fetchPlacesByRadius(map, defaultCenter, "동물병원", 3000);
      }
    });
  }, [map]);

  // ✅ Places 검색 함수
  const fetchPlacesByRadius = (targetMap, center, keyword, radius) => {
    if (!targetMap || !window.kakao?.maps?.services) {
      console.error("카카오 services가 아직 로드되지 않았습니다.");
      return;
    }

    // 기존 마커 제거
    markers.forEach((marker) => marker.setMap(null));
    const newMarkers = [];

    // 현재 위치 마커 유지
    if (currentLocation) {
      const currentMarker = new window.kakao.maps.Marker({
        position: currentLocation,
        map: targetMap,
      });
      newMarkers.push(currentMarker);
    }

    const ps = new window.kakao.maps.services.Places();
    const searchOptions = {
      location: center,
      radius: radius,
      sort: window.kakao.maps.services.SortBy.DISTANCE,
    };

    ps.keywordSearch(
      keyword,
      (data, status) => {
        if (status === window.kakao.maps.services.Status.OK) {
          const searchResults = data.map((item) => ({
            mapdataNum: item.id,
            placeName: item.place_name,
            address: item.address_name,
            latitude: item.y,
            longitude: item.x,
          }));
          setPlaces(searchResults);

          const bounds = new window.kakao.maps.LatLngBounds();

          if (currentLocation) {
            bounds.extend(currentLocation);
          }

          searchResults.forEach((place) => {
            const markerPosition = new window.kakao.maps.LatLng(
              place.latitude,
              place.longitude
            );
            const marker = new window.kakao.maps.Marker({
              position: markerPosition,
            });
            marker.setMap(targetMap);
            newMarkers.push(marker);
            bounds.extend(markerPosition);
          });

          setMarkers(newMarkers);

          if (searchResults.length > 0) {
            targetMap.setBounds(bounds);
          }
        } else {
          console.log("반경 내 검색 결과가 없습니다.");
          setPlaces([]);
        }
      },
      searchOptions
    );
  };

  // ✅ 탭 클릭 시 장소 검색
  const handleTabClick = (tabName) => {
    setActiveTab(tabName);
    setPlaces([]);

    if (!map || !currentLocation) return;

    if (tabName === "hospital") {
      fetchPlacesByRadius(map, currentLocation, "동물병원", 3000);
    } else if (tabName === "playground") {
      fetchPlacesByRadius(map, currentLocation, "애견놀이터", 3000);
    }
  };

  // ✅ 검색 기능
  const handleSearch = () => {
    if (!searchQuery.trim()) {
      alert("검색어를 입력해주세요.");
      return;
    }

    if (!map) {
      alert("지도가 로드되지 않았습니다.");
      return;
    }

    markers.forEach((marker) => marker.setMap(null));
    const newMarkers = [];

    if (currentLocation) {
      const currentMarker = new window.kakao.maps.Marker({
        position: currentLocation,
        map: map,
      });
      newMarkers.push(currentMarker);
    }

    const ps = new window.kakao.maps.services.Places();
    ps.keywordSearch(searchQuery, (data, status) => {
      if (status === window.kakao.maps.services.Status.OK) {
        const searchResults = data.map((item) => ({
          mapdataNum: item.id,
          placeName: item.place_name,
          address: item.address_name,
          latitude: item.y,
          longitude: item.x,
        }));
        setPlaces(searchResults);

        const bounds = new window.kakao.maps.LatLngBounds();
        searchResults.forEach((place) => {
          const markerPosition = new window.kakao.maps.LatLng(
            place.latitude,
            place.longitude
          );
          const marker = new window.kakao.maps.Marker({
            position: markerPosition,
          });
          marker.setMap(map);
          newMarkers.push(marker);
          bounds.extend(markerPosition);
        });

        setMarkers(newMarkers);

        if (searchResults.length > 0) {
          map.setBounds(bounds);
        }
      } else if (status === window.kakao.maps.services.Status.ZERO_RESULT) {
        alert("검색 결과가 없습니다.");
        setPlaces([]);
      } else {
        alert("검색 중 오류가 발생했습니다.");
      }
    });
  };

  // ✅ 리스트 클릭 시 지도 이동
  const handlePlaceClick = (lat, lng) => {
    if (map) {
      const moveLatLon = new window.kakao.maps.LatLng(lat, lng);
      map.setCenter(moveLatLon);
      map.setLevel(2);
    }
  };

  // ✅ 길찾기 버튼
  const handleGetDirections = (destination) => {
    if (!currentLocation) {
      alert("현재 위치 정보를 가져올 수 없습니다.");
      return;
    }
    const originUrl = `https://map.kakao.com/?sName=내 위치&eName=${destination}`;
    window.open(originUrl, "_blank");
  };

  return (
    <div className="map_wrap">
      <div className="info_msg" style={{fontSize:"20px", lineHeight:"24px", color:"red",textAlign:"center",padding:"10px 0 30px"}}>현재 도메인 문제로 사용자의 위치정보를 받아올 수 없습니다.</div>
      <div className="map-container">
        <div className="map-wrapper">
          <div id="map"></div>
        </div>
        <div className="list-wrapper">
          <div className="list-header">
            <div className="temp_form md w60p">
              <input
                className="temp_input"
                type="text"
                placeholder="장소명 검색"
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
              />
            </div>
            <button className="form-button-primary" onClick={handleSearch}>
              검색
            </button>
          </div>
          <div className="list-header">
            <div className="tab-form_btn_box">
              <button
                className={`tab-item ${
                  activeTab === "hospital" ? "active" : ""
                }`}
                onClick={() => handleTabClick("hospital")}
              >
                동물병원
              </button>
            </div>
            <div className="tab-form_btn_box">
              <button
                className={`tab-item ${
                  activeTab === "playground" ? "active" : ""
                }`}
                onClick={() => handleTabClick("playground")}
              >
                애견놀이터
              </button>
            </div>
          </div>
          <ul className="place-list">
            {places.length > 0 ? (
              places.map((place) => (
                <li
                  key={place.mapdataNum}
                  onClick={() =>
                    handlePlaceClick(place.latitude, place.longitude)
                  }
                >
                  <h4>{place.placeName}</h4>
                  <p>{place.address}</p>
                  <button
                    className="form-button-primary"
                    onClick={(e) => {
                      e.stopPropagation();
                      handleGetDirections(place.placeName);
                    }}
                  >
                    길찾기
                  </button>
                </li>
              ))
            ) : (
              <li>검색 결과가 없습니다.</li>
            )}
          </ul>
        </div>
      </div>
    </div>
  );
};

export default MapForm;
