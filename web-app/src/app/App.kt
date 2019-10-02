package app

import app.header.header
import app.memes.memes
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div

class App : RComponent<RProps, RState>() {

    override fun RBuilder.render() {
        div("memeory") {
            header()
            memes()
        }
    }
}

fun RBuilder.app() = child(App::class) {}
