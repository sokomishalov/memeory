import React from 'react'
import Fade from "react-reveal/Fade"
import PropTypes from "prop-types"
import _ from "lodash"

class OnScrollUpReveal extends React.Component {

    constructor(props, context) {
        super(props, context);
        this.state = {
            hidden: props.initHidden || false
        }
    }

    componentDidMount() {
        this.prev = window.scrollY;
        window.addEventListener('scroll', this.handleNavigation);
    }

    componentWillUnmount() {
        window.removeEventListener('scroll', this.handleNavigation);
    }


    handleNavigation = (e) => {
        const window = e.currentTarget;
        const {hidden} = this.state

        if (this.prev > window.scrollY && hidden) {
            this.setState({hidden: false})
        } else if (this.prev < window.scrollY && !hidden) {
            this.setState({hidden: true})
        }

        this.prev = window.scrollY;
    };

    fade = (name) => _.eq(this.props.fadeDirection, name)

    render() {
        const {hidden} = this.state
        const {children, mockComponent, useFade} = this.props

        return (
            useFade
                ? (
                    <Fade bottom={this.fade("bottom")}
                          top={this.fade("top")}
                          right={this.fade("right")}
                          left={this.fade("left")}
                          when={!hidden}>
                        {children}
                    </Fade>
                )
                : hidden ? mockComponent : children
        );
    }
}

OnScrollUpReveal.propTypes = {
    initHidden: PropTypes.bool,
    useFade: PropTypes.bool,
    fadeDirection: PropTypes.string,
    mockComponent: PropTypes.any
}

OnScrollUpReveal.defaultProps = {
    initHidden: false,
    useFade: true,
    fadeDirection: null,
    mockComponent: <></>
}

export default OnScrollUpReveal