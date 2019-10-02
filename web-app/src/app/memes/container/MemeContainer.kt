package app.memes.container

/**
 * @author sokomishalov
 */
import dto.Meme
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div

class MemeContainer(props: Props) : RComponent<MemeContainer.Props, RState>(props) {

    class Props(
            var meme: Meme
    ) : RProps

    override fun RBuilder.render() {
        div("meme-container") {
            div("meme-container-header") {
                div("meme-container-header-logo") {

                }
                div("meme-container-header-channel") {
                    +props.meme.channelName.orEmpty()
                    +props.meme.publishedAt.toString()
                }

            }

            div("meme-container-caption") {
                +props.meme.caption.orEmpty()
            }
        }
    }
}

fun RBuilder.memeContainer(meme: Meme) = child(MemeContainer::class) {
    attrs.meme = meme
}