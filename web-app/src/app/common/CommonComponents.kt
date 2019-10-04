package app.common

import kotlinx.css.Display
import kotlinx.css.display
import kotlinx.html.DIV
import react.RBuilder
import react.ReactElement
import react.dom.RDOMBuilder
import styled.css
import styled.styledDiv

/**
 * @author sokomishalov
 */

inline fun RBuilder.flexDiv(block: RDOMBuilder<DIV>.() -> Unit): ReactElement {
    return styledDiv {
        css {
            display = Display.flex
        }
        block()
    }
}