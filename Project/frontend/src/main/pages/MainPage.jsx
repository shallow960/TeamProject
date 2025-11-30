import React, { useEffect, useRef, useState } from "react";
import { Link } from "react-router-dom";
import api from "../../common/api/axios";

// swiper
import { Swiper, SwiperSlide } from "swiper/react";
import {
  Navigation,
  Pagination,
  Autoplay,
  A11y,
  EffectFade,
} from "swiper/modules";
import "swiper/css";
import "swiper/css/pagination";
import "swiper/css/navigation";
import "swiper/css/effect-fade";

// gsap 스크롤
import gsap from "gsap";
import { useGSAP } from "@gsap/react";

// 메인 맵지도
import RoughMap from "./RoughMap";
// 게시판 데이터
import NormalBbs from "./NormalItem";

import "../style/main.css";

// 이미지 import (현재 사용 안 해도 무방)
import animal1 from "../images/animal1.jpeg";
import animal2 from "../images/animal2.jpeg";
import animal3 from "../images/animal3.jpeg";
import animal4 from "../images/animal4.jpeg";
import animal5 from "../images/animal5.jpeg";
import animal6 from "../images/animal6.jpeg";

// =======================
// 컴포넌트 밖 상수 / 유틸
// =======================

const BBS_LIST_URL = "/bbs/bbslist";
const BACKEND_URL = "/api";
const BANNER_IMG_BASE = `${BACKEND_URL}/files`;

// 백엔드가 내려주는 이미지 경로를 화면용으로 정규화
// - "/DATA" 또는 "http"로 시작하면 그대로 사용
// - 그 외엔 백엔드 호스트 prefix를 붙임
const resolveSrc = (raw) => {
  if (!raw) return null;
  const s = String(raw);
  if (s.startsWith("/DATA") || s.startsWith("http")) return s;
  return `${BACKEND_URL}${s}`;
};

export default function MainPage() {
  // Swiper 인스턴스 레퍼런스
  const swiperRef = useRef(null);

  // 재생/정지 상태
  const [isPlaying, setIsPlaying] = useState(true);

  // 외부 버튼 핸들러
  const handlePrev = () => swiperRef.current?.slidePrev();
  const handleNext = () => swiperRef.current?.slideNext();
  const handleTogglePlay = () => {
    if (!swiperRef.current) return;
    if (isPlaying) {
      swiperRef.current.autoplay?.stop();
      setIsPlaying(false);
    } else {
      swiperRef.current.autoplay?.start();
      setIsPlaying(true);
    }
  };

  // 배너 데이터
  const [banners, setBanners] = useState([]);

  useEffect(() => {
    api
      .get("/banner/active")
      .then((res) => setBanners(res.data))
      .catch((err) => console.error(err));
  }, []);

  const slideMarkup = (
    <Swiper
      modules={[Navigation, Pagination, Autoplay, A11y, EffectFade]}
      effect="fade"
      fadeEffect={{ crossFade: true }}
      loop
      speed={700}
      autoplay={{
        delay: 3000,
        disableOnInteraction: false,
      }}
      pagination={{ clickable: true }}
      onBeforeInit={(swiper) => {
        swiperRef.current = swiper;
      }}
      className="main_swiper"
    >
      {banners.map((banner) => {
        const imgSrc = banner.imageUrl
          ? `${BANNER_IMG_BASE}/${banner.imageUrl}` // 예: /api/files/xxx.jpg
          : undefined;

        return (
          <SwiperSlide key={banner.bannerId}>
            <div className="visual_img_box">
              {imgSrc && (
                <img
                  src={imgSrc}
                  alt={banner.altText || banner.title || "배너 이미지"}
                  style={{ width: "100%", height: "auto" }}
                />
              )}
              {banner.linkUrl && (
                <a href={banner.linkUrl} className="banner_link">
                  {banner.subTitle || banner.title}
                </a>
              )}
            </div>
          </SwiperSlide>
        );
      })}
    </Swiper>
  );

  // 동물 사진용 스와이퍼 ref
  const animalSwiperRef = useRef(null);

  const handlePrev2 = () => {
    if (!animalSwiperRef.current) return;
    animalSwiperRef.current.slidePrev(300);
  };

  const handleNext2 = () => {
    if (!animalSwiperRef.current) return;
    animalSwiperRef.current.slideNext(300);
  };

  // 이미지 게시판 대표 썸네일 목록
  const [animalThumbs, setAnimalThumbs] = useState([]);

  useEffect(() => {
    const fetchAnimalThumbs = async () => {
      try {
        const params = { type: "POTO", page: 0, size: 12 };
        const res = await api.get(BBS_LIST_URL, { params });

        const list = res.data?.bbsList?.content ?? [];
        const repMap = res.data?.representativeImages ?? {};

        const thumbs = list
          .map((post) => {
            const key = String(post.bulletinNum);
            const rep = repMap[key];
            const raw = rep?.thumbnailPath || rep?.imagePath || null;
            return {
              bulletinNum: post.bulletinNum,
              title: post.bbsTitle || post.title || "",
              src: resolveSrc(raw),
            };
          })
          .filter((x) => !!x.src);

        setAnimalThumbs(thumbs);
      } catch (error) {
        console.error("이미지 썸네일 불러오기 실패:", error);
      }
    };

    fetchAnimalThumbs();
  }, []);

  const animalSlidesPerView = Math.min(5, Math.max(1, animalThumbs.length));
  const animalLoop = animalThumbs.length > 1;

  const animalSlideList = (
    <Swiper
      modules={[Navigation, Autoplay, A11y]}
      loop={animalLoop}
      speed={400}
      slidesPerView={animalSlidesPerView}
      spaceBetween={34}
      observer
      observeParents
      onSwiper={(swiper) => {
        animalSwiperRef.current = swiper;
      }}
      className="main_swiper"
      breakpoints={{
        0: {
          slidesPerView: Math.min(2, animalSlidesPerView),
          spaceBetween: 12,
        },
        768: {
          slidesPerView: Math.min(3, animalSlidesPerView),
          spaceBetween: 16,
        },
        1024: {
          slidesPerView: Math.min(4, animalSlidesPerView),
          spaceBetween: 24,
        },
        1280: { slidesPerView: animalSlidesPerView, spaceBetween: 24 },
      }}
    >
      {animalThumbs.map((item) => (
        <SwiperSlide key={item.bulletinNum}>
          <div className="animal_item">
            <Link to={`/bbs/image/${item.bulletinNum}`}>
              <img src={item.src} alt={item.title || "아이들 사진"} />
            </Link>
          </div>
        </SwiperSlide>
      ))}
    </Swiper>
  );

  return (
    <div className="main_page">
      <div className="rowgroup1">
        <section>
          <h2>메인 비주얼 영역</h2>
          <div className="main_visual_wrap waypoint">
            <div className="main_visual_list">
              <div className="main_visual_item">
                <div className="slide_item_wrap">{slideMarkup}</div>
                <div className="item_text_box">
                  <span className="text_item item1">
                    <span className="text">반려견과 함께하는</span>
                  </span>
                  <span className="text_item item2">
                    <em className="text">함개마당 입니다.</em>
                  </span>
                </div>
              </div>
            </div>
            <div className="control_box">
              <button
                type="button"
                className="slide_btn prev"
                onClick={handlePrev}
              >
                이전
              </button>
              <button
                type="button"
                className={`all ${isPlaying ? "pause" : "play"}`}
                onClick={handleTogglePlay}
              >
                {isPlaying ? "정지" : "재생"}
              </button>
              <button
                type="button"
                className="slide_btn next"
                onClick={handleNext}
              >
                다음
              </button>
            </div>
          </div>
        </section>
      </div>

      <div className="rowgroup2 row_index">
        <section>
          <div className="row_wrap">
            <div className="quick_menu_wrap waypoint">
              <div className="quick_menu_title">
                <span>Quick Menu</span>
              </div>
              <div className="quick_menu_list">
                <div className="quick_menu_item quick1">
                  <Link to="/reserve/land/date" className="quick_item_link">
                    <div className="item_box">
                      <i className="menu_icon"></i>
                      <div className="item_text">
                        <span className="title">놀이터 예약</span>
                        <span className="desc">
                          반려견과 함께 <br />
                          놀아볼까요?
                        </span>
                      </div>
                    </div>
                  </Link>
                </div>
                <div className="quick_menu_item quick2">
                  <Link
                    to="/reserve/volunteer/date"
                    className="quick_item_link"
                  >
                    <div className="item_box">
                      <i className="menu_icon"></i>
                      <div className="item_text">
                        <span className="title">봉사 예약</span>
                        <span className="desc">
                          유기견들과 함께하는 <br />
                          봉사활동
                        </span>
                      </div>
                    </div>
                  </Link>
                </div>
                <div className="quick_menu_item quick3">
                  <Link to="/bbs/image" className="quick_item_link">
                    <div className="item_box">
                      <i className="menu_icon"></i>
                      <div className="item_text">
                        <span className="title">센터 아이들</span>
                        <span className="desc">
                          보호중인 귀여운 <br />
                          아이들 보고가세요~
                        </span>
                      </div>
                    </div>
                  </Link>
                </div>
                <div className="quick_menu_item quick4">
                  <Link to="/funds" className="quick_item_link">
                    <div className="item_box">
                      <i className="menu_icon"></i>
                      <div className="item_text">
                        <span className="title">후원하기</span>
                        <span className="desc">
                          당신의 작은 관심이 <br />
                          아이들에게 큰 도움이 됩니다.
                        </span>
                      </div>
                    </div>
                  </Link>
                </div>
              </div>
            </div>
          </div>
        </section>
      </div>

      <div className="rowgroup3 row_index">
        <section>
          <div className="row_wrap">
            <div className="item_area">
              <NormalBbs />
              <div className="map_wrap">
                <div className="bbs_title_box">
                  <span>오시는길</span>
                  <a
                    className="bbs_move_btn"
                    href="/contents/3"
                    data-discover="true"
                  >
                    더보기
                  </a>
                </div>
                <RoughMap />
              </div>
            </div>
          </div>
          <div className="img_bbs_box old_img_wrap">
            <div className="deco_img_box">
              <i className="deco_img img1"></i>
              <i className="deco_img img2"></i>
              <i className="deco_img img3"></i>
            </div>
            <div className="item_area animal_bbs_box">
              <div className="animal_title">아이들 사진</div>
              <div className="animal_inner">
                <div className="animal_list">{animalSlideList}</div>
                <div className="animal_btn_box">
                  <button
                    type="button"
                    className="ani_btn prev"
                    onClick={handlePrev2}
                  >
                    이전
                  </button>
                  <button
                    type="button"
                    className="ani_btn next"
                    onClick={handleNext2}
                  >
                    다음
                  </button>
                </div>
              </div>
            </div>
          </div>
        </section>
      </div>
    </div>
  );
}
