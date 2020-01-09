package com.wj577.selecttime;

import java.util.ArrayList;
import java.util.List;

public class StringHelper {

    /**
     * @param currHour 当前小时
     * @param timeNum  时间差
     * @param currMin  当前分钟
     * @return
     */
    public static List<String> getHourTime(int currHour, int timeNum, int currMin) {
        int hourVolue = 0;
        if (currMin % 10 == 0) {
            hourVolue = (currMin + timeNum) / 60;
        } else {
            hourVolue = (currMin + 10 + timeNum) / 60;
        }
        List<String> list = new ArrayList<>();
        list.add("立即送达");
        for (int i = currHour; i < (24 - hourVolue); i++) {
            list.add((i + hourVolue) + "点");
        }
        return list;
    }

    public static List<String> getHourAll() {
        int hourVolue = 0;
        List<String> list = new ArrayList<>();
        for (int i = 0; i < (24 - hourVolue); i++) {
            list.add((i + hourVolue) + "点");
        }
        return list;
    }

    /**
     * @param start 开始小时
     * @param
     * @return
     */
    public static List<String> getTwoHourTime(int start) {

        List<String> list = new ArrayList<>();
        for (int i = start; i < 23; i++) {
            list.add((i) + "时");
        }
        return list;
    }

    /**
     * @param timeNum 时间差
     * @param currMin 当前分钟
     * @return
     */
    public static List<String> getMin(boolean isAll, int timeNum, int currMin) {
        List<String> list = new ArrayList<>();

        if (isAll) {
            for (int i = 0; i < 6; i++) {
                if (i == 0) {
                    list.add(10 * (i) + "0分");
                } else {
                    list.add(10 * (i) + "分");
                }
            }
        } else {
            int currNum = 0;
            if (currMin % 10 == 0) {
                currNum = (timeNum + currMin) % 60 / 10;
            } else {
                currNum = (timeNum + 10 + currMin) % 60 / 10;
            }
            for (int i = currNum; i < 6; i++) {
                if (i == 0) {
                    list.add(10 * (i) + "0分");
                } else {
                    list.add(10 * (i) + "分");
                }
            }
        }
        return list;
    }

    public static List<String> getDay2() {
        List<String> list = new ArrayList<>();
        list.add("今天");
        list.add("明天");
        return list;
    }



}
