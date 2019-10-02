package app.memes

/**
 * @author sokomishalov
 */
import api.loadMemes
import app.memes.container.memeContainer
import dto.Meme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import react.*
import react.dom.div

class Memes : RComponent<RProps, Memes.State>() {

    class State(
            var page: Int,
            var memes: List<Meme>
    ) : RState

    override fun State.init() {
        page = 0
        memes = emptyList()
    }

    override fun componentDidMount() {
        GlobalScope.launch { loadMore() }
    }

    private suspend fun loadMore() {
        val newMemes = loadMemes(state.page)
        setState {
            page += 1
            memes += newMemes
        }
    }

    override fun RBuilder.render() {
        div("memes") {
            state.memes.map { memeContainer(it) }
        }
    }
}

fun RBuilder.memes() = child(Memes::class) {
}