package com.aplus.jx.identity.reader.consts;

/**
 * 读卡常量
 */
public class EventConst {
	public class Status {
		/**
		 * 成功
		 */
		public static final int SUCCESS = 1;
	}

	public class In {
		/**
		 * 基本信息<br>
		 * 形成文字信息文件WZ.TXT、相片文件XP.WLT和ZP.BMP
		 */
		public static final int BASIC = 1;
		/**
		 *  只读文字信息<br>
		 *  形成文字信息文件WZ.TXT和相片文件XP.WLT
		 */
		public static final int LETTER_ONLY = 2;
		/**
		 * 读最新住址信息<br>
		 * 形成最新住址文件NEWADD.TXT
		 */
		public static final int ADDRESS = 3;
		/**
		 * 读芯片管理号<br>
		 * 形成二进制文件IINSNDN.bin
		 */
		public static final int BIN = 5;
	}
}
