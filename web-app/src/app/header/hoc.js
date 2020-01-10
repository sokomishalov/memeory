import React from "react";
import Header from "./header";

export const withHeader = (WrappedComponent) => {
    return class extends React.Component {
        render() {
            return (
                <>
                    <Header/>
                    <WrappedComponent {...this.props} />
                </>
            );
        }
    };
};

export default withHeader;