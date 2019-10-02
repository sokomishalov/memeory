package util.env

import util.env.EnvType.LOCAL

/**
 * @author sokomishalov
 *
 * FIXME in future
 */
object EnvHolder {
    val envType: EnvType = LOCAL
    const val backendUrl: String = "http://localhost:8080"
}