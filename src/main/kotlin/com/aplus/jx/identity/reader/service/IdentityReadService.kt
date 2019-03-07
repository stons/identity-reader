package com.aplus.jx.identity.reader.service

import com.aplus.jx.identity.reader.exception.IdentityReadException
import com.aplus.jx.identity.reader.model.IdentityInfo

interface IdentityReadService {
    @Throws(IdentityReadException::class)
    fun read():IdentityInfo?
}