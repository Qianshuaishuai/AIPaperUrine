package com.xinxin.aicare.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串相关方法
 *
 */
public class StringUtil {
	public static boolean isEmail(String string) {
		if (string == null)
			return false;
		String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern p;
		Matcher m;
		p = Pattern.compile(regEx1);
		m = p.matcher(string);
		if (m.matches())
			return true;
		else
			return false;
	}

	public static boolean isPhone(String phone) {
		String regex = "^1\\d{10}$";
		if (phone.length() != 11) {
			return false;
		} else {
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(phone);
			boolean isMatch = m.matches();
			return isMatch;
		}
	}

	/**
	 * ISO-9959-1编码转换为UTF-8
	 *
	 * @param str
	 * @return
	 */
	public static String iso88591ToUtf8(String str) {
		if (str == null) {
			return str;
		}

		try {
			return new String(str.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 判断字符串中是否包含中文
	 * @param str
	 * 待校验字符串
	 * @return 是否为中文
	 * @warn 不能校验是否为中文标点符号
	 */
	public static boolean isContainChinese(String str) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		}
		return false;
	}

	public static String getRandomcode(int size){
		String randomcode2 = "";
		String model = "abcdefghijkLmnopqrstuvwxyz1234567890";
		char[] m = model.toCharArray();
		for (int j=0;j<size ;j++ ){
			char c = m[(int)(Math.random()*36)];
			randomcode2 = randomcode2 + c;
		}
		return randomcode2;
	}

	public static int[] StringToInt(String str){
		if(isEmpty(str)){
			return null;
		}
		String[] arrs = str.split(",");
		int[] ints = new int[arrs.length];
		for(int i=0;i<arrs.length;i++){
			ints[i]=Integer.parseInt(arrs[i].trim());
		}
		return ints;
	}

	/**
	 * 将以逗号分隔的字符串转换成字符串数组
	 * @param valStr
	 * @return String[]
	 */
	public static String[] StrList(String valStr){
		int i = 0;
		String TempStr = valStr;
		String[] returnStr = new String[valStr.length() + 1 - TempStr.replace(",", "").length()];
		valStr = valStr + ",";
		while (valStr.indexOf(',') > 0)
		{
			returnStr[i] = valStr.substring(0, valStr.indexOf(','));
			valStr = valStr.substring(valStr.indexOf(',')+1 , valStr.length());

			i++;
		}
		return returnStr;
	}

	/**获取字符串编码
	 * @param str
	 * @return
	 */
	public static String getEncoding(String str) {
		String encode = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s = encode;
				return s;
			}
		} catch (Exception exception) {
		}
		encode = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s1 = encode;
				return s1;
			}
		} catch (Exception exception1) {
		}
		encode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s2 = encode;
				return s2;
			}
		} catch (Exception exception2) {
		}
		encode = "GBK";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s3 = encode;
				return s3;
			}
		} catch (Exception exception3) {
		}
		return "";
	}

	/**
	 * 判断参数a是否为空，如果为空则返回b (适用与字符串等所有对象)
	 *
	 * @param <K>
	 * @param a
	 * @param b
	 * @return
	 */
	public static <K> K nvl(K a, K b) {
		return a == null ? b : a;
	}

	/**
	 * 去掉字符串前后的空格，如果为空则返回""
	 *
	 * @param a
	 * @return
	 */
	public static String clear(String a) {
		return nvl(a, "").trim();
	}

	/**
	 * 判断字符串为空或者长度是否等于0
	 *
	 * @param a
	 * @return
	 */
	public static boolean isEmpty(String a) {
		return "".equals(clear(a)) || "null".equals(a);
	}

	public static String getRandomStr(int num){
		Random rd = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < num; i++) {
			sb.append(rd.nextInt(10));
		}
		return sb.toString();
	}

	public static boolean isNotEmpty(String a) {
		return !"".equals(clear(a)) && !"null".equals(a);
	}
	/**
	 * 字符串均非空，返回true
	 * @param s
	 * @return
	 */
	public static boolean isNotEmpty(String... s) {
		for (String a : s){
			if(isEmpty(a)){
				return false;
			}
		}
		return true;
	}

	/**
	 * 字符串均为空，返回true
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String... s) {
		for (String a : s){
			if(!isEmpty(a)){
				return false;
			}
		}
		return true;
	}

	/**
	 * 返回首字母
	 *
	 * @param strChinese
	 * @param bUpCase
	 * @return
	 */

	public static String getPYIndexStr(String strChinese, boolean bUpCase) {
		try {
			StringBuffer buffer = new StringBuffer();
			byte b[] = strChinese.getBytes("GBK");//把中文转化成byte数组
			for (int i = 0; i < b.length; i++) {
				if ((b[i] & 255) > 128) {
					int char1 = b[i++] & 255;
					char1 <<= 8;//左移运算符用“<<”表示，是将运算符左边的对象，向左移动运算符右边指定的位数，并且在低位补零。其实，向左移n位，就相当于乘上2的n次方
					int chart = char1 + (b[i] & 255);
					buffer.append(getPYIndexChar((char) chart, bUpCase));
					break;
				}
				char c = (char) b[i];
				if (!Character.isJavaIdentifierPart(c))//确定指定字符是否可以是 Java 标识符中首字符以外的部分。
					c = 'A';
				buffer.append(c);
			}
			return buffer.toString();
		} catch (Exception e) {
			System.out.println((new StringBuilder()).append("\u53D6\u4E2D\u6587\u62FC\u97F3\u6709\u9519").append(e.getMessage()).toString());
		}
		return null;
	}

	/**
	 * 得到首字母
	 *
	 * @param strChinese
	 * @param bUpCase
	 * @return
	 */

	private static char getPYIndexChar(char strChinese, boolean bUpCase) {
		int charGBK = strChinese;
		char result;
		if (charGBK >= 45217 && charGBK <= 45252)
			result = 'A';
		else if (charGBK >= 45253 && charGBK <= 45760)
			result = 'B';
		else if (charGBK >= 45761 && charGBK <= 46317)
			result = 'C';
		else if (charGBK >= 46318 && charGBK <= 46825)
			result = 'D';
		else if (charGBK >= 46826 && charGBK <= 47009)
			result = 'E';
		else if (charGBK >= 47010 && charGBK <= 47296)
			result = 'F';
		else if (charGBK >= 47297 && charGBK <= 47613)
			result = 'G';
		else if (charGBK >= 47614 && charGBK <= 48118)
			result = 'H';
		else if (charGBK >= 48119 && charGBK <= 49061)
			result = 'J';
		else if (charGBK >= 49062 && charGBK <= 49323)
			result = 'K';
		else if (charGBK >= 49324 && charGBK <= 49895)
			result = 'L';
		else if (charGBK >= 49896 && charGBK <= 50370)
			result = 'M';
		else if (charGBK >= 50371 && charGBK <= 50613)
			result = 'N';
		else if (charGBK >= 50614 && charGBK <= 50621)
			result = 'O';
		else if (charGBK >= 50622 && charGBK <= 50905)
			result = 'P';
		else if (charGBK >= 50906 && charGBK <= 51386)
			result = 'Q';
		else if (charGBK >= 51387 && charGBK <= 51445)
			result = 'R';
		else if (charGBK >= 51446 && charGBK <= 52217)
			result = 'S';
		else if (charGBK >= 52218 && charGBK <= 52697)
			result = 'T';
		else if (charGBK >= 52698 && charGBK <= 52979)
			result = 'W';
		else if (charGBK >= 52980 && charGBK <= 53688)
			result = 'X';
		else if (charGBK >= 53689 && charGBK <= 54480)
			result = 'Y';
		else if (charGBK >= 54481 && charGBK <= 55289)
			result = 'Z';
		else
			result = (char) (65 + (new Random()).nextInt(25));
		if (!bUpCase)
			result = Character.toLowerCase(result);
		return result;
	}

	public static String getFirstStr(String str, boolean bUpCase){
		try {
			if(StringUtil.isEmpty(str)){
				return "";
			}
			int length = str.length();
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<length;i++){
				String firstStr = str.substring(i,i+1);
				if(firstStr.compareToIgnoreCase("A")>=0 && firstStr.compareToIgnoreCase("Z")<=0){
					if(bUpCase){
						firstStr = firstStr.toUpperCase();
					}else{
						firstStr = firstStr.toLowerCase();
					}

				}else{
					sb.append(getPYIndexStr(firstStr, bUpCase));
				}
			}
			return sb.toString();
		} catch (Exception e) {
			System.out.println((new StringBuilder()).append("\u53D6\u4E2D\u6587\u62FC\u97F3\u6709\u9519").append(e.getMessage()).toString());
		}
		return null;
	}

	public static String formatDetail(String content){
		StringBuilder newsContent = new StringBuilder();
		if(!StringUtil.isEmpty(content)){
			String[] content_list = content.split("<img");
			newsContent.append(content_list[0]);
			for(int i=1;i<content_list.length;i++) {
				newsContent.append("<img");
				String[] content_list_lists = (content_list[i]+" ").split("/>");
				if (content_list_lists.length != 0) {
					String content_list_lists_0 = content_list_lists[0];
					if (content_list_lists_0.indexOf("style") != -1) {
						content_list_lists_0 = dealStyle(content_list_lists_0);
					}else{
						content_list_lists_0 += " style=\\\"vertical-align:top;\\\"";
					}
					newsContent.append(content_list_lists_0).append("/>");
					if (content_list_lists.length == 2 && "</p><p>".equals(content_list_lists[1].trim())) {
						continue;
					}else if (content_list_lists.length == 2 && "</p><p style=\\\"white-space: normal;\\\">".equals(content_list_lists[1].trim())) {
						continue;
					}else if (content_list_lists.length == 2 && "</p><p style=\"white-space: normal;\">".equals(content_list_lists[1].trim())) {
						continue;
					}else{
						for(int j=1;j<content_list_lists.length;j++){
							newsContent.append(content_list_lists[j]);
							if(j!=content_list_lists.length-1){
								newsContent.append("/>");
							}
						}
					}
				}
			}
		}
		return newsContent.toString();
	}

	//style="float:none;"
	private static String dealStyle(String content){
		StringBuilder newsContent = new StringBuilder();
		String[] content_list0 = content.split("style=\"");
		if (content_list0.length != 0) {
			newsContent.append(content_list0[0]);
			for(int i=1;i<content_list0.length;i++) {
				newsContent.append("style=\"");
				String[] content_list_lists = (content_list0[i]+" ").split("\"");
				if (content_list_lists.length != 0) {
					String content_list_lists_0 = content_list_lists[0];
					if (content_list_lists_0.indexOf("vertical-align:top;") != -1) {
					}else{
						content_list_lists_0 += ";vertical-align:top;";
					}
					newsContent.append(content_list_lists_0).append("\"");
					for(int j=1;j<content_list_lists.length;j++){
						newsContent.append(content_list_lists[j]);
						if(j!=content_list_lists.length-1){
							newsContent.append("\"");
						}
					}
				}
			}
		}else{
			String[] content_list1 = content.split("style='");
			if (content_list1.length != 0) {
				newsContent.append(content_list1[0]);
				for(int i=1;i<content_list1.length;i++) {
					newsContent.append("style='");
					String[] content_list_lists = (content_list1[i]+" ").split("'");
					if (content_list_lists.length != 0) {
						String content_list_lists_0 = content_list_lists[0];
						if (content_list_lists_0.indexOf("vertical-align:top;") != -1) {
						}else{
							content_list_lists_0 += ";vertical-align:top;";
						}
						newsContent.append(content_list_lists_0).append("'");
						for(int j=1;j<content_list_lists.length;j++){
							newsContent.append(content_list_lists[j]);
							if(j!=content_list_lists.length-1){
								newsContent.append("'");
							}
						}
					}
				}
			}
		}
		return newsContent.toString();
	}

}
