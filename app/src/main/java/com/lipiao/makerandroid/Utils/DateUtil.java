package com.lipiao.makerandroid.Utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间戳格式化工具类
 * 基于单例模式-使用饿汉式
 * 把13位时间戳转换成"yyyy-MM-dd HH:mm:ss"格式，工具类
 * DateUtil.timeStampDate(String time)
 */
public class DateUtil {

    String TAG="DateUtil";
    private static  DateUtil dateUtil=new DateUtil();
    public static DateUtil getInstance() {
        return dateUtil;
    }
    private DateUtil() {
        LogUtil.d(TAG, "验证是否单例生效,app使用过程中DateUtil只初始化一次即可");
    }

    public static String timeStampDate(String time) {
        Long timeLong = Long.parseLong(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//要转换的时间格式
        Date date;
        try {
            date = sdf.parse(sdf.format(timeLong));
            return sdf.format(date);
        } catch (ParseException e) {
            return null;
        }
    }
}
