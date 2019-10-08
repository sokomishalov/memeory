import React, {useState} from "react";
import {Modal} from 'antd';
import GoogleLogin from 'react-google-login';
import FacebookLogin from 'react-facebook-login';
import {FACEBOOK_APP_ID} from "../../util/env/env";

export const LoginModal = ({button}) => {

    const [visible, setVisible] = useState(false);

    const openModal = () => setVisible(true);
    const closeModal = () => setVisible(false);

    const responseGoogle = (response) => console.log(response);
    const responseFacebook = (response) => console.log(response);

    return (
        <div>
            <div onClick={openModal}>
                {button}
            </div>
            <Modal title="Авторизация через соц. сети"
                   visible={visible}
                   onCancel={closeModal}
                   footer={null}>

                <div>
                    <GoogleLogin clientId="142446820744-2ev2p083grumiv089k4dk6ai6q5d7h41.apps.googleusercontent.com"
                                 buttonText="Авторизуйтесь через Google"
                                 theme="dark"
                                 scope="https://www.googleapis.com/auth/userinfo.profile"
                                 onSuccess={responseGoogle}
                                 onFailure={responseGoogle}
                    />
                </div>
                <div className="mt-20">
                    <FacebookLogin appId={FACEBOOK_APP_ID}
                                   textButton="Авторизуйтесь через Facebook"
                                   size="small"
                                   fields="name,first_name,last_name,email"
                                   callback={responseFacebook}/>
                </div>
            </Modal>
        </div>
    );
};