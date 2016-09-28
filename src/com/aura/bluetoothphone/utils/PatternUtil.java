package com.aura.bluetoothphone.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则匹配工具类
 * 
 * @Description TODO
 * @author CodeApe
 * @version 1.0
 * @date 2013-11-9
 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd. Inc.
 *             All rights reserved.
 * 
 */
public class PatternUtil {

	/** Emoji表情正则表达式 */
	public static final String PATTERN_FACE_EMOJI = "\\[emoji_[0-9]{3}\\]";
	/** Emoji表情名字长度 */
	public static final int FACE_EMOJI_LENGTH = 11;

	/**
	 * 正则校验手机号码
	 * 
	 * @version 1.0
	 * @createTime 2013-11-9,下午9:34:24
	 * @updateTime 2013-11-9,下午9:34:24
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param mobileNum
	 *            手机号码
	 * @return true 正确手机号码；false 非法手机号码
	 */
	public static boolean isValidMobilePhone(String mobileNum) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobileNum);
		return m.matches();
	}

	/**
	 * 判断是否纯数字
	 * 
	 * @version 1.0
	 * @createTime 2013-12-18,上午10:42:31
	 * @updateTime 2013-12-18,上午10:42:31
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param source
	 *            需要判断的源字符串
	 * @return true 纯数字，false非纯数字
	 */
	public static boolean isNumer(String source) {
		Pattern p = Pattern.compile("\\d*");
		Matcher m = p.matcher(source);
		return m.matches();
	}

	/**
	 * 判断是纯字母串
	 * 
	 * @version 1.0
	 * @createTime 2013-12-18,上午10:45:33
	 * @updateTime 2013-12-18,上午10:45:33
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param source
	 *            判断的源字符串
	 * @return true是纯字母字符串，false非纯字母字符串
	 */
	public static boolean isChar(String source) {
		Pattern p = Pattern.compile("[a-z]*[A-Z]*");
		Matcher m = p.matcher(source);
		return m.matches();
	}

	/**
	 * 是否纯特殊符号串
	 * 
	 * @version 1.0
	 * @createTime 2013-12-18,上午10:47:36
	 * @updateTime 2013-12-18,上午10:47:36
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param source
	 *            纯特殊符号串
	 * @return true纯特殊符号串，false非纯特殊符号串
	 */
	public static boolean isSymbol(String source) {
		Pattern p = Pattern.compile("[{\\[(<~!@#$%^&*()_+=-`|\"?,./;'\\>)\\]}]*");
		Matcher m = p.matcher(source);
		return m.matches();
	}

	/**
	 * 是否合法帐号
	 * 
	 * @version 1.0
	 * @createTime 2013-12-18,下午4:43:04
	 * @updateTime 2013-12-18,下午4:43:04
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param source
	 * @return
	 */
	public static boolean isValidAccount(String source) {
		Pattern p = Pattern.compile("^(?![0-9])[a-zA-Z0-9]+$");
		Matcher m = p.matcher(source);
		return m.matches() && source.length() >= 4 && source.length() <= 50;
	}

	/**
	 * 是否正确密码
	 * 
	 * @version 1.0
	 * @createTime 2013-12-18,上午11:11:35
	 * @updateTime 2013-12-18,上午11:11:35
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param source
	 *            需要判断的密码
	 * @return true 合格的密码，false不合法的密码
	 */
	public static boolean isValidPassword(String source) {
		Pattern p = Pattern.compile("[\\d*[a-z]*[A-Z]*[{\\[(<~!@#$%^&*()_+=-`|\"?,./;'\\>)\\]}]*]*");
		Matcher m = p.matcher(source);
		return m.matches() && source.length() >= 6 && source.length() <= 15;
	}
	
	/**
	 * 是否是正确的邮箱格式
	 *
	 * @version 1.0
	 * @createTime 2014年1月22日,上午11:54:05
	 * @updateTime 2014年1月22日,上午11:54:05
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param source 需要验证的邮箱
	 * @return
	 */
	public static boolean isValidEmail(String source) {
		Pattern p = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
		Matcher m = p.matcher(source);
		return !m.matches();
	}

	/**
	 * 判断是否符合正则的字符串
	 * 
	 * @version 1.0
	 * @createTime 2013-12-18,上午11:18:04
	 * @updateTime 2013-12-18,上午11:18:04
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param source
	 *            需要判断的源字符串
	 * @param pattern
	 *            用于判读的正则表达式
	 * @return true if the source is valid of pattern,else return false
	 */
	public static boolean isValidPattern(String source, String pattern) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(source);
		return m.matches();
	}

	/**
	 * 正则校验身份证号码
	 * 
	 * @version 1.0
	 * @createTime 2013-11-15,下午4:47:17
	 * @updateTime 2013-11-15,下午4:47:17
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param idcard
	 *            身份证号码
	 * @return true 有效的身份证；false 无效的身份证
	 */
	public static boolean isValidIdCardNum(String idcard) {
		Pattern p1 = Pattern.compile("/^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$/");
		Pattern p2 = Pattern.compile("/^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[A-Z])$/");
		Matcher m1 = p1.matcher(idcard);
		Matcher m2 = p2.matcher(idcard);

		return m1.matches() && m2.matches();
	}
	
	/**
	 * 
	 * 验证身份证号码
	 * 
	 * @version 1.0
	 * @createTime 2015年11月20日,上午9:37:07
	 * @updateTime 2015年11月20日,上午9:37:07
	 * @createAuthor	李刚
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param s_aStr
	 * @return
	 */
	public static boolean chechCertificateNum(String s_aStr) {//验正身份证
        String has_x="([0-9]{17}([0-9]|X|x))|([0-9]{15})";
        Pattern p = Pattern.compile(has_x);
        return p.matcher(s_aStr).matches();
    }

	/**
	 * 判断是否是一个表情
	 * 
	 * @version 1.0
	 * @createTime 2013-11-23,下午11:55:53
	 * @updateTime 2013-11-23,下午11:55:53
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param name
	 *            表情名字
	 * @return true 是表情，false不是表情
	 */
	public static boolean isExpression(String name) {
		Pattern p = Pattern.compile(PATTERN_FACE_EMOJI);
		Matcher m = p.matcher(name);
		return m.matches();
	}
	
	
	

}
