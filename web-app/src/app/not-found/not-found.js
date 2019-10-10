import React from 'react';
import "./not-found.css"
import {Button} from "antd";
import {ROUTE} from "../../util/router/router";

const NotFound = () => (
    <div className="not-found">
        <div className="image"/>

        <div className="content">
            <div className="fs-50 bold">404</div>
            <div className="fs-20">Страница не найдена</div>
            <Button type="primary"
                    shape="round"
                    size="large"
                    children={<span className="bold">На главную</span>}
                    className="mt-15"
                    href={ROUTE.CORE}/>
        </div>
    </div>
);

export default NotFound