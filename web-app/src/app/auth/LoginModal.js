import React, {useState} from "react";
import "./LoginModal.css"
import {Modal} from 'antd';
import "firebase/auth";
import {withT} from "../../util/locales/i18n";
import Socials from "../settings/socials/socials";

const LoginModal = ({t, trigger}) => {

    const [visible, setVisible] = useState(false);

    const openModal = () => setVisible(true)
    const closeModal = () => setVisible(false)

    return (
        <div>
            <div onClick={openModal}>
                {trigger}
            </div>
            <Modal title={t("auth.modal.caption")}
                   visible={visible}
                   onCancel={closeModal}
                   footer={null}>
                <Socials/>
            </Modal>
        </div>
    );
};

export default withT(LoginModal);