package com.aplus.jx.identity.reader.model

import com.aplus.jx.identity.reader.enums.Gender
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class IdentityInfo(
    //姓名
        var name:String,
    //性别
        @get:JsonFormat(shape = JsonFormat.Shape.OBJECT)
    var gender: Gender,
    //国籍
        var nationality:String,
    //身份证号
        var cardCode:String,
    //出生日期
        var dateOfBirth:LocalDate,
    //地址
        var address:String,
    //照片
        var photo:String
)