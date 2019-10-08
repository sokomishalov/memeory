import React, {useState} from "react";
import {Modal} from 'antd';

export const LoginModal = ({button}) => {

    const [visible, setVisible] = useState(false);

    const openModal = () => setVisible(true);
    const closeModal = () => setVisible(false);

    return (
        <div>
            <div onClick={openModal}>
                {button}
            </div>
            <Modal title="Авторизация через соц. сети"
                   visible={visible}
                   onCancel={closeModal}
                   footer={null}>
                TODO
            </Modal>
        </div>
    );
};