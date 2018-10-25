package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 张同心 on 2017/9/13.
 * @function 时间工具
 */

public class TimeUtil {

    /**
     * 格式化时间
     * @param time
     * @return
     */
    public static String filterTime(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(time);
            String newTime =sdf.format(date);
            return newTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 获取系统时间
     * @return 系统时间
     */
    public static String getSystemTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        return time;
    }

}
