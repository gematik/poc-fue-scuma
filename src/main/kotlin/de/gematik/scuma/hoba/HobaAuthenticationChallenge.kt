package de.gematik.scuma.hoba

import java.util.*

/**
 * Created by rk on 03.11.2022.
 * gematik.de
 */
class HobaAuthenticationChallenge(val maxAge:Int=10, val realm:String?=null, val challenge: ByteArray)  {

    companion object {
        fun fromString(hobaChallenge: String): HobaAuthenticationChallenge {
            val parameterMap = hobaChallenge.toParameterMap()
            require(parameterMap.containsKey("challenge") && parameterMap.containsKey("max-age")) { "malformed challenge - required parameter missing" }
            return HobaAuthenticationChallenge(parameterMap.get("max-age")!!.toInt(), parameterMap.get("realm")?.trim('"'), Base64.getUrlDecoder().decode(parameterMap.get("challenge")!!.trim('"')))
        }
    }

    override fun toString(): String {
        val re = if(realm!=null)""" realm="$realm""" else ""
        return """HOBA challenge="${Base64.getUrlEncoder().encodeToString(challenge)}" max-age=$maxAge${re}"""
    }
}