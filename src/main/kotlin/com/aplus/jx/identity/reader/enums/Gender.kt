package com.aplus.jx.identity.reader.enums

import com.fasterxml.jackson.annotation.JsonFormat
import java.io.Serializable

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class Gender(var label:String,var value:String):Serializable {
    MALE("男", "XB001"), FALALE("女", "XB002");
}