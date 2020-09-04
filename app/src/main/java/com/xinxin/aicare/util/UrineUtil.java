package com.xinxin.aicare.util;

public class UrineUtil {
    //提醒模板
    //尿湿，开状态，且未提醒，才弹框
    //尿满，开状态，且未提醒，才弹框
    //过冷，开状态，且（未提醒 或 提醒超过5分钟）
    //过热，开状态，且（未提醒 或 提醒超过5分钟）
    //趴睡，开状态，且（未提醒 或 提醒超过5分钟）
    public static final String MESSAGE_TITLE_NS = "尿湿提醒";
    public static final String MESSAGE_DETAIL_NS = "您的宝宝[NICKNAME]已达到预设的尿湿门槛，请注意更换尿片";
    public static final String MESSAGE_TITLE_NM = "尿满提醒";
    public static final String MESSAGE_DETAIL_NM = "您的宝宝[NICKNAME]已达到预设的尿湿峰值，请尽快更换尿片，避免引起宝宝不适";
    public static final String MESSAGE_TITLE_TB = "过冷提醒";
    public static final String MESSAGE_DETAIL_TB = "您的宝宝[NICKNAME]所处环境温度过低，请检查宝宝是否踢被";
    public static final String MESSAGE_TITLE_FZX = "过热提醒";
    public static final String MESSAGE_DETAIL_FZX = "您的宝宝[NICKNAME]所处环境温度过高，为了宝宝安全，请检查宝宝情况！";
    public static final String MESSAGE_TITLE_PS = "趴睡提醒";
    public static final String MESSAGE_DETAIL_PS = "您的宝宝[NICKNAME]现在处于趴睡状态，请尽快调整睡姿，以免影响宝宝健康！";


    //根据参数计算尿量
    public static int getUrineVolume(String AD, String THERMOMETER, String NUMERICAL_TABLE) {
        int c = Integer.parseInt(AD);

        int[] a = StringUtil.StringToInt(THERMOMETER.replace(" ", ""));//50, 80, 120,160,200,240,280,320,501
        int[] v = StringUtil.StringToInt(NUMERICAL_TABLE.replace(" ", ""));//525,480,413,348,310,275,238,213,100

        int[] K = new int[a.length - 1];//slope
        for (int i = 0; i < K.length; i++) {
            K[i] = 1000 * (a[i + 1] - a[i]) / (v[i] - v[i + 1]);//K is a 1000 times scaled slope
        }
        if (c > v[0]) { //c is the read value from the sensor. Amont is not detectable now
            return 0;
        } else if (c < v[v.length - 1]) { //amount over the max measurable value
            return a[a.length - 1] * -1;
        } else {  //amount measurable
            for (int i = 1; i < K.length; i++) {
                if (c < v[i] && c >= v[i + 1]) { //"="人为加的，否则C等于V中的值时，会被漏掉
                    int tempV = a[i] + K[i] * (v[i] - c) / 1000;
                    return tempV;
                }
            }
        }
        return 0;
    }

//    //根据设置和尿量计算 显示的尿量和尿量比例 和 是否触发提醒。
//    String S = deviceRecommend.getString("NS_S");//阈值，用户设置的尿湿节点
//    String F = deviceRecommend.getString("NS_F");//阈值，用户设置的尿满节点
//
//    String baseVol = NumberUtil.div(NumberUtil.mul(保水值, 报警极限值), 100) + "";//80
//    double doubleA = NumberUtil.div(NumberUtil.mul(F, baseVol), 100); //A=F*V*P,A值精确到10位   S 40  F 50
//    doubleA =NumberUtil.div(doubleA,10,0)*10;
//
//    int URINE_VOLUME_temp = getUrineVolume(AD, 温度表, 数值表);
//    String URINE_VOLUME = (URINE_VOLUME_temp >= 0 ? URINE_VOLUME_temp + "" : ">" + (URINE_VOLUME_temp * -1));//尿量，单位ml  -----页面显示的尿量
//    double URINE_VOLUME_PERCENT_double = (URINE_VOLUME_temp >= 0 ? (NumberUtil.mul(NumberUtil.div(URINE_VOLUME_temp, doubleA), 100)) : (NumberUtil.mul(NumberUtil.div(URINE_VOLUME_temp * -1, doubleA), 100)));
//    String URINE_VOLUME_PERCENT = URINE_VOLUME_PERCENT_double + "";//		  -----页面显示的尿量比例
//    double doubleS = NumberUtil.div(NumberUtil.mul(S, baseVol), 100);
//    boolean is_URINE_VOLUME_S = (URINE_VOLUME_temp >= 0 ? NumberUtil.sub(URINE_VOLUME_temp, doubleS) >= 0 : true);//是否尿湿  ----是否触发尿湿值。
//    boolean is_URINE_VOLUME_F = (URINE_VOLUME_PERCENT_double >= 100 ? true : false);//是否尿满		----是否触发尿满值。

}
