package com.xinxin.aicare.util;

public class RecordUtils {
    public static boolean IS_SHOW_TIP_DIALOG_0 = false;
    public static boolean IS_SHOW_TIP_DIALOG_1 = false;
    public static boolean IS_SHOW_TIP_DIALOG_2 = false;
    public static boolean IS_SHOW_TIP_DIALOG_3 = false;
    public static boolean IS_SHOW_TIP_DIALOG_4 = false;

    public static boolean checkIsShowTipDialog(int index) {
        switch (index) {
            case 0:
//                if (!IS_SHOW_TIP_DIALOG_0) {
//                    IS_SHOW_TIP_DIALOG_0 = true;
//                    return true;
//                }
                return true;
            case 1:
                if (!IS_SHOW_TIP_DIALOG_1) {
                    IS_SHOW_TIP_DIALOG_1 = true;
                    return true;
                }
                break;
            case 2:
//                if (!IS_SHOW_TIP_DIALOG_2) {
//                    IS_SHOW_TIP_DIALOG_2 = true;
//                    return true;
//                }
                return true;
            case 3:
//                if (!IS_SHOW_TIP_DIALOG_3) {
//                    IS_SHOW_TIP_DIALOG_3 = true;
//                    return true;
//                }
                return true;
            case 4:
//                if (!IS_SHOW_TIP_DIALOG_4) {
//                    IS_SHOW_TIP_DIALOG_4 = true;
//                    return true;
//                }
                return true;
        }

        return false;
    }
}
