package app

import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.dom.h2

class App : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        div("App-header") {
            h2 {
                +"Welcome to Memeory"
            }
        }
    }
}

fun RBuilder.app() = child(App::class) {}
