import React from 'react';

export const withMemesPage = (WrappedComponent) => {
    return class extends React.Component {
        render() {
            return (
                <div className="flex">
                    {/*<Topics/>*/}
                    <WrappedComponent {...this.props} />
                    {/*<Topics/>*/}
                </div>
            );
        }
    };
};

export default withMemesPage;