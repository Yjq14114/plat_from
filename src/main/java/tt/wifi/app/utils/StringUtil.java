package tt.wifi.app.utils;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * Created by yangjq on 2016/10/1.
 */
@SuppressWarnings("unchecked")
public class StringUtil {

    public static final String DOT_DOT_DOT = "...";
    public static final char Comma = ',';
    public static final String EMPTY = "";
    public static final String BLANK = " ";
    public static final String SLASH = "/";
    public static final String AT = "@";
    public static final String SPLIT = ":";
    public static final String ISO_8859_1 = "iso-8859-1";
    public static final String GBK = "gbk";
    public static final String GB2312 = "gb2312";
    public static final String UTF8 = "utf8";
    private static final String STAR = "*";

    static String[] normalDays = new String[] {//
            //
            "前天",//
            "昨天",//
            "今天",//
            "明天",//
            "后天"//
    };

    // /**
    // * 字母+数字+中文+下划线
    // */
    // public static final String PATTERN_WORD_NUMERIC_CHINESE =
    // "^[a-zA-Z0-9_\u4e00-\u9fa5]*$";
    /**
     * 只能是字母和数字
     */
    public static final String PATTERN_WORD_NUMERIC = "^[a-zA-Z0-9]*$";
    /**
     * 只能是字母
     */
    public static final String PATTERN_WORD = "^[a-zA-Z]*$";
    // /**
    // * 只能是数字
    // */
    // public static final String PATTERN_NUMERIC = "^[0-9]*$";
    /**
     * 验证email格式
     */
    public static final String PATTERN_EMAIL = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";

    /**
     *
     * {root}/{type}/{server}/{dir}/{filename}.ext<br/>
     * http://{type}.week08.com/{server}/{dir}/{filename}.ext
     *
     * e.g. \/data\/nfs\/photo\/ys000\/pt000\/xxx.jpg <br/>
     * http://photo.week08.com/ys000/pt000/xxx.jpg
     *
     * 如果 path 是 http://开头的地址 则直接返回path
     */
    public static final String convertFileStoragePath(String path, String type) {
        if (path != null && type != null) {
            try {
                if (path.startsWith("http://")) {
                    return path;
                }
                path = path.substring(path.indexOf(type) + type.length())
                        .replace('\\', '/');
                path = "http://" + type + ".week08.com" + path;
            } catch (Exception e) {
                return path;
            }
        }
        return path;
    }

    /**
     * 转换时间成差值的方式<br>
     *
     * 例如时间是：2008-08-08 11:15:00<br>
     * 当前时间是：2008-08-08 11:16:00<br>
     * 得出的结果是：1分钟之前
     *
     * 例如时间是：2008-08-08 11:15:00<br>
     * 当前时间是：2008-08-09 11:16:00<br>
     * 得出的结果是：1天之前
     *
     * 例如时间是：2008-08-08 11:15:00<br>
     * 当前时间是：2008-08-08 21:16:00<br>
     * 得出的结果是：10小时之前
     */
    public static String diffNowTime(Date date) {
        if (date == null)
            return "";
        long now = DateUtil.getDate().getTime();
        long test = date.getTime();
        long span = now - test;
        if (span < 0) {
            return "In the Future";
        }
        for (int i = 0; i < spans.length; i++) {
            if (span >= spans[i]) {
                return (span / spans[i]) + units[i];
            }
        }
        return units[units.length - 1];
    }

    private static long[] spans = { 86400000,// 一天的毫秒
            3600000,// 一小时的毫秒
            60000 // 一分钟的毫秒
    };

    private static String[] units = { "天之前", "小时之前", "分钟之前", "刚刚" };

    /**
     * 转换时间成生日差值的方式<br>
     *
     * 例如生日时间是：2008-08-08<br>
     * 当前时间是：2008-08-08<br>
     * 得出的结果是：今天
     *
     * 例如时间是：2008-08-08<br>
     * 当前时间是：2008-08-09<br>
     * 得出的结果是：昨天
     *
     * 例如时间是：2008-08-08<br>
     * 当前时间是：2008-08-10<br>
     * 得出的结果是：前天
     *
     * 例如时间是：2008-08-08<br>
     * 当前时间是：2008-08-07<br>
     * 得出的结果是：明天
     *
     * 例如时间是：2008-08-08<br>
     * 当前时间是：2008-08-06<br>
     * 得出的结果是：后天
     *
     * 例如时间是：2008-08-08<br>
     * 当前时间是：2008-08-05<br>
     * 得出的结果是：8月5日
     */
    public static String diffBirthTime(Date date) {
        if (date == null)
            return "";
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        Calendar test = Calendar.getInstance();
        test.setTime(date);
        test.set(Calendar.HOUR, 0);
        test.set(Calendar.MINUTE, 0);
        test.set(Calendar.SECOND, 0);

        long compare = (test.getTimeInMillis() - today.getTimeInMillis()) / 1000;

        int index = (int) compare / 86400;
        index += 2;
        if (index >= 0 && index < normalDays.length) {
            return normalDays[index];
        }

        SimpleDateFormat abnormalDayFmt = new SimpleDateFormat("yyyy-MM-dd");
        return abnormalDayFmt.format(date);
    }

    /**
     * len这个长度是按照一个半角的英文或者数字的长度计算的<br>
     * 如果是中文（日文、韩文、越南）或者全角的英文或者数字<br>
     * 则请在长度的基础上×2
     */
    public static String trimDesc(String desc, int len) {
        if (desc == null) {
            return EMPTY;
        }
        int length = desc.length();
        char[] chars = desc.toCharArray();
        int pixCount = 0;
        int index;
        for (index = 0; index < length; index++) {
            char c = chars[index];
            if ((Character.isDigit(c) && !isHalfChar(c))
                    || (isEChar(c) && !isHalfChar(c)))
                pixCount++;
            else
                pixCount += 2;
            if (pixCount >= len) {
                break;
            }
        }
        if (index < length - 1) {
            desc = desc.substring(0, index + 1);
            return desc + DOT_DOT_DOT;
        }
        return desc;
    }

    /**
     * ascii中的33到126的字符<br>
     * 包括了a-zA-Z,./?[]{}\|":<>~!@#$%^&*()_+=等等
     */
    public static boolean isEChar(char c) {
        return c >= 33 && c <= 126;
    }

    public static boolean isHalfChar(char c) {
        return !(('\uFF61' <= c) && (c <= '\uFF9F'))
                && !(('\u0020' <= c) && (c <= '\u007E'));
    }

    /**
     * 清除字符串两端的空格，如果参数为null，则返回空字符串
     *
     * @param s
     * @return
     */
    public static String trim(String s) {
        if (s == null) {
            return "";
        }
        return s.trim();
    }

    /**
     * 字符串是否为null或空
     *
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {
        if (s == null)
            return true;
        return trim(s).length() == 0;
    }

    /**
     * 判断字符串是否不为空，即!=null, 非空字符串，且length>0
     *
     * @param s
     * @return
     */
    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    /**
     * 截取字符串最左边的len 个字节
     *
     * @param desc
     * @param len
     * @return
     */
    public static String leftB(String desc, int len) {
        if (desc == null) {
            return EMPTY;
        }
        int length = desc.length();
        char[] chars = desc.toCharArray();
        int pixCount = 0;
        int index;
        for (index = 0; index < length; index++) {
            char c = chars[index];
            if ((Character.isDigit(c) && !isHalfChar(c))
                    || (isEChar(c) && !isHalfChar(c)))
                pixCount++;
            else
                pixCount += 2;
            if (pixCount >= len) {
                break;
            }
        }
        if (index < length - 1) {
            desc = desc.substring(0, index + 1);
            return desc + DOT_DOT_DOT;
        }
        return desc;
    }

    /**
     * 字节长度
     *
     * @param str
     * @return
     */
    public static int lenB(String str) {
        str = trim(str);
        return str.getBytes().length;
    }

    /**
     * 过滤html代码
     *
     * @param s
     * @return
     */
    public static String filterHtml(String s) {
        s = trim(s);
        if (s.length() == 0)
            return "";
        String s1 = s;
        s1 = s1.replaceAll("<[^>]*>", "");
        s1 = s1.replaceAll("&{1}[^(&|;)]{2,5};", "");
        s1 = s1.replaceAll("<", "〈");
        s1 = s1.replaceAll(">", "〉");
        return s1;
    }

    /**
     * 转换html代码，把<和>替换成html表达式
     *
     * @param s
     * @return
     */
    public static String escapeHtml(String s) {
        s = trim(s);
        if (s.length() == 0)
            return "";
        s = s.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        return s;
    }

    // /**
    // * 是否是数字
    // *
    // * @param o
    // * @return
    // */
    // public static boolean isNumeric(Object o) {
    // String s = (String) o;
    // Pattern pattern = Pattern.compile(PATTERN_NUMERIC);
    // Matcher match = pattern.matcher(s);
    // return match.matches();
    // }

    // /**
    // * 是不是格式正确的数据 <BR />
    // * 调用方式，如验证邮箱格式：StringUtil.isRightData("xxxxxx",StringUtil.PATTERN_EMAIL);
    // *
    // * @param o
    // * 要验证的数据
    // * @param pattern
    // * 格式，直接使用内部的常量
    // * @return
    // * @deprecated 请使用ValidatorUtil中的matches方法
    // */
    // public static boolean isRightData(Object data, String pattern) {
    // if (data == null)
    // return false;
    // String s = (String) data;
    // Pattern p = Pattern.compile(pattern);
    // Matcher match = p.matcher(s);
    // return match.matches();
    // }

    /**
     * 解析内容中的图片地址，把img标签的src取出来并以list方式返回
     *
     * @param content
     * @return
     * @throws ParserException
     */
    public static List<String> parseImgUrl(String content)
            throws ParserException {
        Parser p = Parser.createParser(content, "utf-8");
        NodeList nl = p.extractAllNodesThatMatch(new NodeFilter() {
            private static final long serialVersionUID = 1L;

            public boolean accept(Node n) {
                if (n.getText().toLowerCase().startsWith("img")) {
                    return true;
                }
                return false;
            }
        });
        List<String> listSrc = new ArrayList<String>();
        for (Node n : nl.toNodeArray()) {
            ImageTag it = (ImageTag) n;
            String src = it.getAttribute("src");
            listSrc.add(src);
        }
        return listSrc;
    }

    /**
     * 修复具有jsessionid的URL，去除jsessionid
     *
     * @param url
     * @return
     */
    public static String fixUrlJsession(String url) {
        if (url == null)
            return EMPTY;
        int indexOfJsessionid = url.indexOf(";jsessionid");
        if (indexOfJsessionid > 0) {
            url = url.substring(0, indexOfJsessionid);
        }
        return url;
    }

    /**
     * 掩盖一定的文字
     */
    public static String blockString(String value, int start, int end,
                                     String replace) {
        if (value == null || value.length() <= 0 || start >= end
                || value.length() <= start)
            return value;
        replace = replace == null || EMPTY.equals(replace) ? STAR : replace;
        end = value.length() < end ? value.length() : end;
        StringBuilder sb = new StringBuilder();
        if (start != 0) {
            sb.append(value.substring(0, start));
        }
        for (int i = 0; i < end; i++)
            sb.append(replace);
        sb.append(value.substring(end));
        return sb.toString();
    }

    /**
     * 掩盖email。替换@之前的内容start-end的内容
     *
     * @param email
     * @param end
     * @param start
     * @param replace
     * @return
     */
    public static String blockEmail(String email, int start, int end,
                                    String replace) {
        String value = email.substring(0, email.indexOf(AT));
        value = blockString(value, start, end, replace);
        return value += email.substring(email.indexOf(AT));
    }

	/*
	 * public static String repairHtml(String html) { DOMFragmentParser parser =
	 * new DOMFragmentParser(); HTMLDocument document = new HTMLDocumentImpl();
	 *
	 * DocumentFragment fragment = document.createDocumentFragment(); try {
	 * StringReader reader=new StringReader(html); parser.parse(new
	 * InputSource(reader), fragment); reader.close(); StringBuffer sb = new
	 * StringBuffer(); String sr = repairHtml(fragment.getLastChild(), sb);
	 * Pattern p = Pattern.compile("<body([^>]*)>(.*)</body>",
	 * Pattern.CASE_INSENSITIVE); Matcher m = p.matcher(sr); if (m.find()) {
	 * return (m.group(2)); } } catch (Exception e) { return html; } return
	 * html; }
	 *
	 * private static String repairHtml(org.w3c.dom.Node node, StringBuffer sb)
	 * { if (node == null) { return sb.toString(); } int type =
	 * node.getNodeType(); switch (type) { case org.w3c.dom.Node.DOCUMENT_NODE:
	 * repairHtml(((Document) node).getDocumentElement(), sb); break; case
	 * org.w3c.dom.Node.ELEMENT_NODE: sb.append('<');
	 *
	 * sb.append(node.getNodeName()); NamedNodeMap attrs = node.getAttributes();
	 *
	 * for (int i = 0; i < attrs.getLength(); i++) { sb.append(' ');
	 * sb.append(attrs.item(i).getNodeName()); sb.append("=\"");
	 *
	 * sb.append(attrs.item(i).getNodeValue()); sb.append('"'); }
	 * sb.append('>'); // sb.appendln(); // HACK org.w3c.dom.NodeList children =
	 * node.getChildNodes(); if (children != null) { int len =
	 * children.getLength(); for (int i = 0; i < len; i++) {
	 * repairHtml(children.item(i), sb); } } break;
	 *
	 * case org.w3c.dom.Node.TEXT_NODE: sb.append(node.getNodeValue()); break;
	 *
	 * } if (type == org.w3c.dom.Node.ELEMENT_NODE) { sb.append("</");
	 * sb.append(node.getNodeName()); sb.append('>'); } return sb.toString(); }
	 */

    /**
     * 加密字符串
     *
     * @param csinput
     *            被加密的字符串
     * @return 返回通过MD5加密后的字符串, 只截取16个字符
     */
    public static String MD5(String csinput, boolean isshort) {
        byte[] b, b2;
        StringBuffer buf;
        String csreturn = null;

        try {
            b = csinput.getBytes("iso-8859-1");
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(b);
            b2 = md.digest();

            buf = new StringBuffer(b2.length * 2);
            for (int nLoopindex = 0; nLoopindex < b2.length; nLoopindex++) {
                if (((int) b2[nLoopindex] & 0xff) < 0x10) {
                    buf.append("0");
                }
                buf.append(Long.toString((int) b2[nLoopindex] & 0xff, 16));
            }
            csreturn = new String(buf);
            if (isshort) {
                csreturn = csreturn.substring(8, 24);
            }
        } catch (Exception e) {
            // e.printStackTrace() ;
            csreturn = "";
        }

        return csreturn;
    }

    public static String MD5(String csinput) {
        return MD5(csinput, false);
    }

    /**
     * 获取指定url的内容
     *
     * @param url
     * @return
     */
    public static String getUrlHtml(String url) {
        int c;
        if (!url.toLowerCase().startsWith("http://")) {
            url = "http://" + url;
        }
        StringBuffer sb = new StringBuffer();
        try {
            URL Url = new URL(url);
            URLConnection u = Url.openConnection();
            int len = u.getContentLength();
            InputStream in = u.getInputStream();
            int i = len;

            while ((c = in.read()) != -1 && --i > 0) {
                sb.append((char) c);
            }
            in.close();
        } catch (Exception e) {
            return e.getMessage();
        }
        String sBody = sb.toString();
        return sBody.trim();
    }

    public static String exChs(String s) {
        if (isEmpty(s)) {
            return "";
        }
        try {
            System.out.print(" 编码前:" + s + "\t");
            s = new String(s.getBytes("iso-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
        return s;
    }

    /**
     * 把null转换为空字符串，并清空两边的空格
     *
     * @param s
     * @return
     */
    public static String empty(String s) {
        if (s == null) {
            return "";
        }
        return s.trim();
    }

    /**
     * 把null转换为空字符串，并清空两边的空格
     *
     * @param s
     * @return
     */
    public static String empty(Object s) {
        if (s == null) {
            return "";
        }
        return s.toString().trim();
    }

    /**
     * 转换数据类型为整型 ，转换不成功返回-1
     *
     * @param num
     * @return
     */
    public static int parseInt(String num) {
        if (isEmpty(num)) {
            return -1;
        }
        try {
            return getInt(num);
        } catch (Exception e) {
            return -1;
        }
    }

    public static long parseLong(String num) {
        if (isEmpty(num)) {
            return -1;
        }
        try {
            return getLong(num);
        } catch (Exception e) {
            return -1;
        }
    }

    public static String toSql(String key) {
        if (isEmpty(key)) {
            return "";
        }
        return key.replace("'", "");
    }

    /**
     * 数组转换为字符串，以逗号隔开
     *
     * @param arr
     * @return
     */
    public static String arrayToString(String[] arr) {
        return arrayToString(arr, ",");
    }

    public static String arrayToString(String[] arr, String splitter) {
        if (arr == null || arr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) {
                sb.append(splitter);
            }
        }
        return sb.toString();
    }

    /**
     * 把参数值转换为long型
     *
     * @param val
     * @return
     */
    public static Long getLong(Object val) {
        if (val == null) {
            return null;
        }
        if (val instanceof Long) {
            return (Long) val;
        }
        if (val instanceof Integer) {
            return ((Integer) val).longValue();
        }
        if (val instanceof String) {
            if (val.toString().length() == 0) {
                return null;
            }
            if (!isNumber(val.toString())) {
                return null;
            }
        }
        return Long.valueOf(val.toString());
    }

    /**
     * 把参数值转换为Int型
     *
     * @param val
     * @return
     */
    public static Integer getInt(Object val) {
        if (val == null) {
            return null;
        }
        if (val instanceof Long) {
            return ((Long) val).intValue();
        }
        if (val instanceof Integer) {
            return (Integer) val;
        }
        if (val instanceof String) {
            if (val.toString().length() == 0) {
                return null;
            }
            if (!isNumber(val.toString())) {
                return null;
            }
        }

        return Integer.valueOf(val.toString());
    }

    /**
     * 传字符串是否是数字
     *
     * @param val
     * @return
     */
    public static boolean isNumber(String val) {
        if (isEmpty(val)) {
            return false;
        }
        for (int i = 0; i < val.length(); i++) {
            if (!Character.isDigit(val.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static Integer getInt(Object val, Integer defaultValue) {
        Integer value = getInt(val);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    public static Boolean getBoolean(Object val) {
        if (val == null) {
            return false;
        }
        String value = val.toString().toLowerCase();
        if (value.equals("false") || value.equals("true")) {
            return Boolean.valueOf(val.toString());
        }
        return false;
    }

    public static Float getFloat(Object val) {
        if (val == null) {
            return null;
        }
        if (val instanceof Float) {
            return (Float) val;
        }
        if (val instanceof Float) {
            return (Float) val;
        }
        return Float.valueOf(val.toString());
    }

    public static Double getDouble(Object val) {
        if (val == null) {
            return null;
        }
        if (val instanceof Double) {
            return (Double) val;
        }
        if (val instanceof Float) {
            return ((Float) val).doubleValue();
        }
        return Double.valueOf(val.toString());
    }

    /**
     * 是否是邮箱地址
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        Pattern p = Pattern.compile(PATTERN_EMAIL);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 是否是手机号
     *
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile) {
        Pattern p = Pattern.compile("^((13[0-9])|(14[5,6,7])|(15[^4,\\D])|(17[0,1,3,6,7,8])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    /**
     * 生成验证码（0~9，a~z）
     *
     * @param num
     *            验证码长度
     * @return 验证码
     */
    public static String getVerificationCode(int num) {
        char[] vc = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
                'b', 'c', 'd', 'e', 'f', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
        Random r = new Random();
        if (num < 1) {
            return null;
        }
        char[] vc_new = new char[num];
        for (int i = 0; i < num; i++) {
            vc_new[i] = vc[r.nextInt(vc.length)];
        }
        return new String(vc_new);
    }

    /**
     * 建立一个以时间戳建立的字符串
     *
     * @return
     */
    public static String createInviterCode() {
        StringBuffer flowCode = new StringBuffer();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        String nowdate = sdf.format(DateUtil.getDate());
        flowCode.append(nowdate);
        Random r = new Random();
        for (int i = 0; i < 3; i++) {
            int a = r.nextInt(10);
            flowCode.append(a);
        }
        return flowCode.toString();
    }

    /**
     * 把Object对象转换为字符串
     *
     * @param obj
     * @return
     */
    public static String getString(Object obj) {
        if (obj == null) {
            return "";
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        return obj.toString();
    }

    /**
     * 版本比较
     *
     * @param oldVersion
     *            老版本号
     * @param newVersion
     *            新版本号 return boolean 如果新版本号“大于”老版本号则返回true，否则返回false
     */
    public static boolean compareVresion(String oldVersion, String newVersion) {
        oldVersion = oldVersion.replace(".", "");
        newVersion = newVersion.replace(".", "");
        if (oldVersion.length() < 3) {
            oldVersion += "0";
        }
        if (newVersion.length() < 3) {
            newVersion += "0";
        }
        if (StringUtil.getInt(oldVersion) >= StringUtil.getInt(newVersion)) {
            return false;
        }
        return true;
    }

    /**
     * 根据当前操作系统获取路径分割符
     *
     * @return
     */
    public static String getPathSeparator() {
        return File.separator;
    }

    public static void main(String[] args) {

        System.out.println(createInviterCode());
        System.out.println(getRound(0.099999));

    }

    /**
     * 通过景区路线来获得点坐标数组
     *
     * @param lineString
     * @return
     */
    public static List<Map<String, Double>> getRoadGpsStringArray(
            String lineString) {
        lineString = lineString.substring(12, lineString.length() - 1);
        String[] arr = lineString.split(", ");
        List<Map<String, Double>> list = new ArrayList<Map<String, Double>>();
        for (String string : arr) {
            Map<String, Double> points = new HashMap<String, Double>();
            String[] pointArr = string.split(" ");
            points.put("lon", getDouble(pointArr[0]));
            points.put("lat", getDouble(pointArr[1]));
            list.add(points);
        }
        return list;
    }

    /**
     * 数据库中查询到地点距离转换为实际的距离.
     *
     * @param distance
     * @return
     */
    public static String getDistance(double distance) {
        return getDistenceWithUnit(distance, "km", "m");
    }

    private static String getDistenceWithUnit(double distance, String km,
                                              String meter) {
        String dis = null;
        DecimalFormat df = new DecimalFormat("#####0.0");
        if (distance > 0.01) {
            dis = df.format(distance * 100) + km;
        } else {
            dis = df.format(distance * 100000) + meter;
        }

        return dis;
    }

    /**
     * 数据库中查询到地点距离转换为实际的距离. 以中文为单位
     *
     * @param distance
     * @return
     */
    public static String getDistanceForGbk(double distance) {
        return getDistenceWithUnit(distance, "千米", "米");
    }

    private static final double PI = 3.14159265358979323;// 圆周率
    private final static double R = 6371229; // 地球的半径

    /**
     * 已知两个坐标后计算两个坐标之间的距离
     *
     * @param lng1
     * @param lat1
     * @param lng2
     * @param lat2
     * @return
     */
    public static String getPointToPointDistance(double lng1, double lat1,
                                                 double lng2, double lat2) {

        double x, y, distance;
        x = (lng2 - lng1) * PI * R * Math.cos(((lat1 + lat2) / 2) * PI / 180)
                / 180;
        y = (lat2 - lat1) * PI * R / 180;
        distance = Math.hypot(x, y);
        DecimalFormat df = new DecimalFormat("#####0.0");
        String dis = null;
        if (distance > 1000) {
            dis = df.format(distance / 1000) + "km";
        } else {
            dis = df.format(distance) + "m";
        }
        return dis;
    }

    /**
     * 团队共享时间段时间返回一个int数字类型，方便比对 10：00返回1000
     *
     * @param time
     * @return
     */
    public static int getTimeHourMinute(String time) {
        String[] arr = time.split(":");
        String temp = arr[0] + arr[1];
        return Integer.valueOf(temp);
    }

    /**
     * 判断当前的时间是否在填写邀请码的有效时间范围内
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static boolean isEffectiveForInviterCoce(Date date)
            throws ParseException {
        Long regTime = DateUtil.getTwoDayDate(date).getTime();
        Long nowTime = DateUtil.getDate().getTime();
        if (regTime > nowTime) {
            return true;
        }

        return false;
    }

    /**
     * 电话号码隐藏中间五位
     *
     * @param mobile
     * @return
     */
    public static String getSecrecyMobile(String mobile) {
        return mobile = mobile.substring(0, 3) + "*****"
                + mobile.substring(mobile.length() - 3, mobile.length());
    }

    /**
     * 将BigDecimal 转换为2位小数的string
     *
     * @param decimal
     * @return
     */
    public static String getTwoDecimal(BigDecimal decimal) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");
        return df.format(decimal);
    }

    /**
     * 将BigDecimal 转换为4位小数的string
     *
     * @param decimal
     * @return
     */
    public static String getFourDecimal(BigDecimal decimal) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0000");
        return df.format(decimal);
    }

    public static Double convertFloat2Double(Float valueFloat) {
        return Double.parseDouble(String.valueOf(valueFloat));
    }

    /**
     * 自定义景区门票订单规则 废弃不使用
     * 年+月+日+时+分+秒+景区id+门票类型id+用户id(yyMMddHHmmss+scenicId+ticketTypeId+userId)
     */
    public static String createOrderTicketNo(Long scenicId, Long ticketTypeId,
                                             Long userId) {
        StringBuffer sb = new StringBuffer();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        String currentTime = sdf.format(DateUtil.getDate());

        sb.append(currentTime);
        sb.append(scenicId);
        sb.append(ticketTypeId);
        sb.append(userId);

        return sb.toString();
    }

    // 00001
    /**
     * @author Ljzh 在数字前添加0生成定长字符串
     * @param index
     * @param length
     * @return
     */
    public static String getNumberWithZero(Long index, int length) {
        String in = String.valueOf(index);
        String ret = in;
        for (int i = 0; i < length - in.length(); i++) {
            ret = "0" + ret;
        }
        return ret;
    }

    /**
     * @author Ljzh
     * 组装景区码（5位）
     * 规则：若景区id长度为1位：则补充8686；长度为两位，补充868；长度为3位，补充86；长度4位，补充8；5位则不动，原样返回
     * @param index
     * @return
     */
    public static String getGenerateNumber(Long index) {
        String originalNumber = String.valueOf(index);
        String newNumber;
        int originalLength = originalNumber.length();

        switch (originalLength) {
            case 1:
                newNumber = "8686" + originalNumber;
                break;
            case 2:
                newNumber = "868" + originalNumber;
                break;
            case 3:
                newNumber = "86" + originalNumber;
                break;
            case 4:
                newNumber = "8" + originalNumber;
                break;
            case 5:
                newNumber = originalNumber;
                break;
            default:
                newNumber = originalNumber;
                break;
        }
        return newNumber;
    }

    /**
     * @author Ljzh
     * @param a
     * @return
     */
    public static double getRound(double a) {// 保留两位小数（非四舍五入）
        return (double) ((int) (a * 10000)) / 10000;
    }

    /**
     * 四舍五入
     * 保留两位小数
     * @param d
     * @return
     */
    public static double getDoubleValue(double d) {
        BigDecimal b = new BigDecimal(d);
        double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }

    public static boolean equalDouble(double num1, double num2) {
        if (Math.abs(num1 - num2) < 0.1)
            return true;
        else
            return false;
    }

    /**
     * 获取10000000以内的随机整数（包含0，不包含10000000）
     * @return
     */
    public static Long getThreadLocalRandom() {
        Long rangeLength = (long) 10000000;
        return ThreadLocalRandom.current().nextLong(rangeLength);
    }

    /**返回指定日期对象间相差的天数
     * @author Ljzh
     * @param d1
     * @param d2
     * @param type 0,返回天;1,返回时;2,返回分;3,返回秒;默认返回秒
     * @return
     */
    public static long getTimeDiff(Date d1, Date d2, int type) {
        long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
        Long time = null;
        switch (type) {
            case 0:
                time = diff / 1000 / 60 / 60 / 24;
                break;
            case 1:
                time = diff / 1000 / 60 / 60;
                break;
            case 2:
                time = diff / 1000 / 60;
                break;
            case 3:
                time = diff / 1000;
                break;
            default:
                time = diff / 1000;
                break;
        }
        return Math.abs(time);
    }

    /**
     * 转码 GBK转UTF-8
     * @param gbkStr
     * @return
     */
    public static String getUTF8StringFromGBKString(String gbkStr) {
        try {
            return new String(getUTF8BytesFromGBKString(gbkStr), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new InternalError();
        }
    }

    public static byte[] getUTF8BytesFromGBKString(String gbkStr) {
        int n = gbkStr.length();
        byte[] utfBytes = new byte[3 * n];
        int k = 0;
        for (int i = 0; i < n; i++) {
            int m = gbkStr.charAt(i);
            if (m < 128 && m >= 0) {
                utfBytes[k++] = (byte) m;
                continue;
            }
            utfBytes[k++] = (byte) (0xe0 | (m >> 12));
            utfBytes[k++] = (byte) (0x80 | ((m >> 6) & 0x3f));
            utfBytes[k++] = (byte) (0x80 | (m & 0x3f));
        }
        if (k < utfBytes.length) {
            byte[] tmp = new byte[k];
            System.arraycopy(utfBytes, 0, tmp, 0, k);
            return tmp;
        }
        return utfBytes;
    }

    /**
     * 正则提取数据
     * @param str
     * @return
     */
    public static String getNumFormStr(String str) {
        Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(str);
        return matcher.replaceAll("");
    }

    /**
     * 正则判断非负浮点数
     * @param str
     * @return
     */
    public static boolean isNonnegativeFloat(String str) {
        String rex = "^\\d+(\\.\\d+)?$";
        Pattern pattern = Pattern.compile(rex);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    /***
     * 判断 String 是否int
     *
     * @param input
     * @return
     */
    public static boolean isNum(String input){
        Matcher mer = Pattern.compile("^[0-9]+$").matcher(input);
        return mer.find();
    }

    /**
     *
     * 将一个字符串进行 MD5 加密<BR/>
     * 方法名：MD5<BR/>
     * 创建人：auger <BR/>
     *
     * @param securityStr
     * @return String<BR/>
     * @exception <BR/>
     * @since 1.0.0
     */
    public static String getMd5(String securityStr) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            byte[] btInput = securityStr.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getEmptyString(Object object) {
        if (object == null) {
            return "";
        }
        return object.toString();
    }

    private static double EARTH_RADIUS = 6378.137;
    private static double rad(double d)
    {
        return d * Math.PI / 180.0;
    }

    public static String getDistance(double lng1, double lat1, double lng2, double lat2){
        DecimalFormat df = new DecimalFormat("######0.00");
        double a, b, d, sa2, sb2;
        lat1 = rad(lat1);
        lat2 = rad(lat2);
        a = lat1 - lat2;
        b = rad(lng1 - lng2);

        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2   * EARTH_RADIUS
                * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
                * Math.cos(lat2) * sb2 * sb2));
        return d < 1 ? df.format(d * 1000) + "m" : df.format(d) + "km" ;
    }


}
