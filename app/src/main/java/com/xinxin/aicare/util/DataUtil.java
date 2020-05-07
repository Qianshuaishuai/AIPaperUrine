package com.xinxin.aicare.util;

public class DataUtil {
    /**
     * 16进制字符串
     *
     * @param b
     * @return
     */
    public static String bytesHexString(byte[] b) {
        String ret = "";
        if (b != null) {
            for (int i = 0; i < b.length; i++) {
                String hex = Integer.toHexString(b[i] & 0xFF);
                if (hex.length() == 1) {
                    hex = "0x0" + hex + ",";
                } else {
                    hex = "0x" + hex + ",";
                }
                ret += hex;
            }
        }
        return ret;
    }

    /**
     * 高低为转换
     *
     * @param lowByte  低位
     * @param highByte 高位
     * @return
     */
    public static int concat(byte lowByte, byte highByte) {
        int low = lowByte & 0xff;
        int high = highByte & 0xff;
//        return (int) ((char) lowByte | (char) highByte << 8);//先将char转换为unsigened char，再进行位移和或操作
        return high << 8 | low;//先将char转换为unsigened char，再进行位移和或操作
    }

    /**
     * 无符号的16进制的byte转int
     *
     * @param b
     * @return
     */
    public static int normalHexByteToInt(byte b) {
        String res = String.format("%02x", new Integer(b & 0xff)).toUpperCase();
        Integer.parseInt(res, 16);
        return Integer.parseInt(res, 16);
    }
}
