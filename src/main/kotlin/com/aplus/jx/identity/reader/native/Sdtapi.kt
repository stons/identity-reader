package com.aplus.jx.identity.reader.native

import com.aplus.jx.identity.reader.model.IdCardTxtInfo
import com.sun.jna.Library
import com.sun.jna.Pointer


interface Sdtapi : Library {
    companion object {
        fun getCardInstance(): IdCardTxtInfo {
            return IdCardTxtInfo()
        }
    }
    /**
     * 端口初始化
     * @param port 端口
     * @return 1 正确 其他 错误
     */
    abstract fun InitComm(port: Int): Int

    /**
     * 端口关闭
     * @return 1 正确 其他 错误
     */
    abstract fun CloseComm(): Int

    /**
     * 身份证认证
     * @return  1 正确 其他 错误
     */
    abstract fun Authenticate(): Int

    /**
     * 读取信息
     * @return
     */
    abstract fun ReadBaseMsg(msg: IdCardTxtInfo, len: Pointer?): Int

    /**
     * 读取身份证卡ID
     * @param bytes out parameter
     * @return 0 success other error
     */
    abstract fun ReadIINSNDN(p: Pointer?): Int

    /**
     * 读取身份证卡ID
     * @param bytes out parameter
     * @return 0 success other error
     */
    abstract fun Routon_ReadIINSNDN(bytes: ByteArray): Int


}