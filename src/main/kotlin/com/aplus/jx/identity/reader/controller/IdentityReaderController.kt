package com.aplus.jx.identity.reader.controller

import com.aplus.jx.identity.reader.exception.IdentityReadException
import com.aplus.jx.identity.reader.model.IdentityInfo
import com.aplus.jx.identity.reader.response.SimpleResponse
import com.aplus.jx.identity.reader.service.IdentityReadService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/identity/info")
class IdentityReaderController(var identityReadService: IdentityReadService) {

    @GetMapping
    fun reade():SimpleResponse {
        try{
            val info = identityReadService.read()
            return SimpleResponse(data = info)
        }catch (e:IdentityReadException){
            return SimpleResponse(201,"读取身份证信息失败,请联系管理员")
        }
    }
}