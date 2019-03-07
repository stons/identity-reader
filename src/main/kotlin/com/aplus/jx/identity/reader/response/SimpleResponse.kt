package com.aplus.jx.identity.reader.response

data class SimpleResponse(
    var code:Int = 200,
    var message:String = "成功",
    var data:Any? = null
)