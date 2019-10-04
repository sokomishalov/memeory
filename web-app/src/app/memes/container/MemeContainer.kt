package app.memes.container

/**
 * @author sokomishalov
 */
import app.common.flexDiv
import dto.Meme
import kotlinx.css.*
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import styled.css
import styled.styledDiv
import styled.styledImg
import util.http.buildLogoUrl
import util.time.timeAgo

class MemeContainer(props: Props) : RComponent<MemeContainer.Props, RState>(props) {

    class Props(
            var meme: Meme
    ) : RProps

    override fun RBuilder.render() {
        div("meme") {
            div("meme-header") {
                flexDiv {
                    styledDiv {
                        css {
                            width = 30.px
                            height = 30.px
                            borderRadius = 5.px
                            background = "url(${buildLogoUrl(props.meme.channelId.orEmpty())})"
                            backgroundPosition = "center"
                            backgroundSize = "contain"
                            objectFit = ObjectFit.contain
                        }
                    }
                    div("meme-header-channel") {
                        div("meme-header-channel-name") {
                            +props.meme.channelName.orEmpty()
                        }
                        div("meme-header-channel-ago") {
                            +timeAgo(props.meme.publishedAt)
                        }
                    }
                }
            }

            div("meme-caption") {
                +props.meme.caption.orEmpty()
            }

            props.meme.attachments.map {
                div("meme-attachment") {
                    when (it.type) {
                        "IMAGE" -> {
                            styledImg(src = it.url) {
                                css {
                                    objectFit = ObjectFit.contain
                                    width = 100.pct
                                    height = 100.pct
                                    borderBottomLeftRadius = 10.px
                                    borderBottomRightRadius = 10.px
                                }
                            }
                        }
                        "VIDEO" -> div { +"video" }
                        "NONE" -> div { +"none" }
                    }
                }
            }
        }
    }
}

fun RBuilder.memeContainer(meme: Meme) = child(MemeContainer::class) {
    attrs.meme = meme
}