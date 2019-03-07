package com.aplus.jx.identity.reader.service.impl

import com.aplus.jx.identity.reader.consts.EventConst
import com.aplus.jx.identity.reader.enums.Gender
import com.aplus.jx.identity.reader.exception.IdentityReadException
import com.aplus.jx.identity.reader.model.IdCardTxtInfo
import com.aplus.jx.identity.reader.model.IdentityInfo
import com.aplus.jx.identity.reader.native.Sdtapi
import com.aplus.jx.identity.reader.service.IdentityReadService
import com.sun.jna.Memory
import com.sun.jna.Native
import com.sun.jna.Pointer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.core.env.get
import org.springframework.stereotype.Service
import sun.misc.BASE64Encoder
import java.io.File
import java.io.FileInputStream
import java.nio.charset.Charset
import java.nio.file.Paths
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


@Service
class IdentityReadServiceImpl : IdentityReadService {
    val logger = LoggerFactory.getLogger(IdentityReadServiceImpl::class.java)
    @Autowired
    lateinit var environment:Environment


    init {
        val currentRelativePath = Paths.get("")
        runPath = currentRelativePath.toAbsolutePath().toString()
    }
    companion object {
        lateinit var runPath:String
        fun readDll(): Sdtapi {
            return Native.loadLibrary("${runPath}\\jl\\Sdtapi", Sdtapi::class.java)
        }
    }
    override fun read(): IdentityInfo? {

        logger.info("dll path is {}",environment.get("dll_path"))
        logger.info("currentRelativePath is {}",runPath)
        val info = Sdtapi.getCardInstance()
        val lib = readDll()
        val readContent = init(info, lib)
        var idCardInfo:IdentityInfo? = null
        if (Objects.equals(EventConst.Status.SUCCESS, readContent)) {
            idCardInfo = populateIDCard( info)
        }
        val p = Memory(16)
        val flag = lib.ReadIINSNDN(p)
        val cardIdByte = p.getByteArray(0, 16)
        val cardId: String
        val peer = Pointer.nativeValue(p)
        Native.free(peer)//手动释放内存
        Pointer.nativeValue(p, 0)
        return idCardInfo
    }

    @Throws(Exception::class)
    fun populateIDCard(info: IdCardTxtInfo): IdentityInfo {
        val photo = File("${runPath}\\jl\\photo.bmp")
        val name = String(info.name, Charset.forName("gb2312")).trim { it <= ' ' }
        val address = String(info.address, Charset.forName("gb2312")).trim { it <= ' ' }
        val bornDate = String(info.borndate, Charset.forName("gb2312")).trim { it <= ' ' }
        val cardCode = String(info.idno, Charset.forName("gb2312")).trim { it <= ' ' }
        val department = String(info.department, Charset.forName("gb2312")).trim { it <= ' ' }
        val latestAddress = String(info.AppAddress1, Charset.forName("gb2312")).trim { it <= ' ' }
        val sex = String(info.Sex, Charset.forName("gb2312")).trim { it <= ' ' }
        val nativeObject = String(info.nation, Charset.forName("gb2312")).trim { it <= ' ' }
        logger.info("name {} address {} bornDate {} cardCode {} department {} latestAddress {} sex {} nativeObject {}",name,address,bornDate,cardCode,department,latestAddress,sex,nativeObject)
        val inputFile = FileInputStream(photo)
        val buffer = ByteArray(photo.length().toInt())
        inputFile.read(buffer)
        inputFile.close()
        val photoBase64 = BASE64Encoder().encode(buffer)
        val gender = if(sex.equals("男")) Gender.MALE else Gender.FALALE
        return IdentityInfo(name, gender,"GJ001",cardCode, LocalDate.parse(bornDate, DateTimeFormatter.ofPattern("yyyyMMdd")),address,photoBase64)
    }

    fun init(info: IdCardTxtInfo, lib: Sdtapi): Int {
        var readBaseMsg = 0
        val initComm = lib.InitComm(1001)
        if (initComm == EventConst.Status.SUCCESS) {
            val authenticate = lib.Authenticate()
            if (authenticate == EventConst.Status.SUCCESS) {
                readBaseMsg = lib.ReadBaseMsg(info, Pointer.NULL)
                if (readBaseMsg != EventConst.Status.SUCCESS) {
                    throw IdentityReadException("读取身份证失败,信息为 -1")
                }
            } else {
                throw IdentityReadException("二代身份证认证失败，失败信息为 "+authenticate)
            }
        } else {
            throw IdentityReadException("设备端口初始化失败，信息为 "+initComm)
        }


        return readBaseMsg
    }

}