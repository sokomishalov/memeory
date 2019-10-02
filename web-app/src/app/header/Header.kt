package app.header


/**
 * @author sokomishalov
 */
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div

class Header : RComponent<Header.Props, Header.State>() {

    override fun RBuilder.render() {
        div("header") {
            div("header-text") {
                +"Memeory"
            }
        }
    }

    class Props() : RProps
    class State() : RState
}

fun RBuilder.header() = child(Header::class) {
}