import React, {} from "react";


const TodayTime = () => {
    const today = new Date();
    return(
        <div>
            <div className="today_box">
                <span className="today">{today}</span>
            </div>
        </div>
    );
};

export default TodayTime;