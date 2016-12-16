package tt.wifi.app.utils;

/**
 * Created by yangjq on 2016/10/1.
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
public class DateUtil {


    public static Long SERVICE_LOCAL_TIME_DIFFER=0l;

    /**
     * 获取系统时间
     * @return
     */
    public static Date getDate(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MILLISECOND, -SERVICE_LOCAL_TIME_DIFFER.intValue());
        return cal.getTime();
    }

    /**
     * 将字符串日期转换为真实日期对象
     * @param date
     * @param format
     * @return
     */
    public static Date parseDate(String date,String format){
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        if (date==null || date.length()==0) {
            return null;
        }
        try {
            return fmt.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
    /**
     * 将时间转换为字符串
     * @param date
     * @param format
     * @return
     */
    public static String parseDate(Date date,String format){
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        if (date==null) {
            return null;
        }
        try {
            return fmt.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date toBeginDate(String date) {
        if (date==null) {
            return null;
        }
        return toBeginDate(parseDate(date,"yyyy-MM-dd"));
    }

    /**
     * 清除日期中的时分秒,即返回当前日期为00:00:00
     * @param date
     * @return
     */
    public static Date toBeginDate(Date date) {
        if (date==null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 转换为结束日期,主要用于查询时,对时间段的处理
     * @param date
     * @return
     */
    public static Date toEndDate(Date date) {
        if (date==null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY,23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    public static Date toEndDate(String date) {
        if (date==null) {
            return null;
        }
        return toEndDate(parseDate(date,"yyyy-MM-dd"));
    }

    /**
     * 取得本周开始和结束时间,周一和周日
     * @return 返回数组日期本周的起始日期和结束日期
     */
    public static Date[] getWeekDate() {
        Calendar c = Calendar.getInstance();
        // 今天是一周中的第几天
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK )-1;

        if (c.getFirstDayOfWeek() == Calendar.SUNDAY) {
            c.add(Calendar.DAY_OF_MONTH, 1);
        }
        // 计算一周开始的日期
        c.add(Calendar.DAY_OF_MONTH, -dayOfWeek);
        Date startDate=c.getTime();
        c.add(Calendar.DAY_OF_MONTH, 6);
        Date endDate=c.getTime();

        return new Date[]{startDate,endDate};
    }
    public static void main(String[] args) throws ParseException {
        System.out.println(getNowHourMinute());
    }

    /**
     * 重置查询时间段，将起始日期设置为00:00:00，结束时间设置为：23:59:59，参数中的起始时间和结束时间key必须为startTime和endTime
     * @param params
     */
    public static void resetQueryDate(Map<String,Object> params) {
        if (params.containsKey("startTime")){
            Date date = (Date)params.get("startTime");
            date = toBeginDate(date);
            params.put("startTime", date);
        }
        if (params.containsKey("endTime")){
            Date date = (Date)params.get("endTime");
            date = toEndDate(date);
            params.put("endTime", date);
        }
        if (params.containsKey("startDate")){
            Date date = (Date)params.get("startDate");
            date = toBeginDate(date);
            params.put("startDate", date);
        }
        if (params.containsKey("endDate")){
            Date date = (Date)params.get("endDate");
            date = toEndDate(date);
            params.put("endDate", date);
        }
    }

    /**
     * 比较两个时间差，得出时间差的毫秒数
     * @param time1
     * @param time2
     * @return 若值小于0，则表示起始时间小大结束时间
     */
    public static Long dateDiff(Date time1,Date time2) {
        return time1.getTime()-time2.getTime();
    }

    /**
     * 取得指定时间的小时起始时间，即0分0秒
     * @param date
     * @return
     */
    public static Date getBeginTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 取得指定时间的小时结束时间，即59分59秒
     * @param date
     * @return
     */
    public static Date getEndTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }
    /**
     * 目前时刻n天后的时刻
     * @param date
     * @return
     */
    public static Date getAfterMareDay(Date date,int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }
    /**
     * 取得指定几天后时间的小时起始时间，即0分0秒
     * @param date
     * @return
     */
    public static Date getBeginTimeAddDay(Date date,int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 取得指定几天后的时间的小时结束时间，即59分59秒
     * @param date
     * @return
     */
    public static Date getEndTimeAddDay(Date date,int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }
    /**
     * 格式化日期
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(Date date,String format) {
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        return fmt.format(date);
    }
    /**
     * 格式化日期格式为：yyyy-MM-dd，如2015-01-01
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        String format = "yyyy-MM-dd";
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        return fmt.format(date);
    }

    /**
     *
     * @param date
     * @param seconds
     * @return
     */
    public static Date addTime(Date date,int seconds) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, seconds);
        return cal.getTime();
    }

    /**
     * 获取某年某月的第一天日期
     * @param year 年份
     * @param month 月份
     * @return Date
     */
    public static Date getMonthFirst(int year,int month){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取某月的最后一天
     * @param year 年
     * @param month 月
     * @return:String
     */
    public static String getMonthLast(int year,int month){
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime());

        return lastDayOfMonth;
    }

    /**
     * 获取某年第一天日期
     * @param year 年份
     * @return Date
     */
    public static Date getYearFirst(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取某年最后一天日期
     * @param year 年份
     * @return Date
     */
    public static Date getYearLast(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();
        return currYearLast;
    }

    /**
     * 取系统当前的小时，即当前几点钟
     * @return
     */
    public static int getHour() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.getDate());
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
    /**
     * 取系统当前的小时，即当前几点钟
     * @return
     */
    public static int getDayOfMouth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
    /**
     * 将当前的时间的小时，分钟拼接为一个int类型数字 10:00返回1000
     * @return
     */
    public static int getNowHourMinute() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.getDate());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min= calendar.get(Calendar.MINUTE);
        String temp = "";
        if(min<10){
            temp+="0"+min;
        }else{
            temp=""+min;
        }
        return StringUtil.getInt(hour+""+temp);
    }

    /**
     * 根据周期返回开始时间和结束时间
     * @param periodUnit 可选值为：1,2,3,4,5,6分别对应年，月，日，周，时，分
     * @return 返回String数组包含两个值[开始时间，结束时间]，如果周期不存在则开始时间返回null
     */
    public static Date[] getStartAndEndTimeByPeriod(Integer periodUnit){
        return getStartAndEndTimeByPeriod(periodUnit,null);
    }

    /**
     * 根据指定时间来计算时间段,未指定时间按当前时间来计划时间段
     * @param periodUnit
     * @param end
     * @return
     */
    public static Date[] getStartAndEndTimeByPeriod(Integer periodUnit,Date end){
        Calendar cal = Calendar.getInstance();
        if (end==null) {
            end = DateUtil.getDate();
        } else if (end.after(cal.getTime())) { //指定时间不能大于系统当前时间
            end=DateUtil.getDate();
        }
        cal.setTime(end);
        Date start=null;
        switch (periodUnit) {
            case 1://年
                cal.set(Calendar.MONTH,1);
                cal.set(Calendar.DAY_OF_YEAR, 1);
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE,0);
                cal.set(Calendar.SECOND, 0);
                start = cal.getTime();
                break;
            case 2://月
                cal.set(Calendar.DAY_OF_MONTH, 1);
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE,0);
                cal.set(Calendar.SECOND, 0);
                start = cal.getTime();
                break;
            case 3://日
                start=DateUtil.toBeginDate(end);
                break;
            case 4://周
                start = DateUtil.getWeekDate()[0];
                start=DateUtil.toBeginDate(start);
                break;
            case 5://时
                start=DateUtil.getBeginTime(end);
                break;
            case 6://分
                cal.set(Calendar.SECOND, 0);
                start = cal.getTime();
            default:
        }

        return new Date[]{start,end};
    }
    public static Date getTwoDayDate(Date date) throws ParseException{
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, 2);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        String two = sdf.format(cal.getTime());
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date twoDate = sdf1.parse(two);
        return twoDate;
    }
    public static Date getAfterMoreDayDate(Date date,int afterDay) throws ParseException{
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, afterDay);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        String two = sdf.format(cal.getTime());
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date twoDate = sdf1.parse(two);
        return twoDate;
    }

}
