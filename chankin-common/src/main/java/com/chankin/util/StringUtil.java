package com.chankin.util;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class StringUtil {
    private static Logger log = LoggerFactory.getLogger(StringUtil.class);

    /*
     *  生成加盐密码
     * */
    public static String createPassword(String password, String salt, int hasIteraions) {
        Md5Hash md5Hash = new Md5Hash(password.trim(), salt, hasIteraions);
        return md5Hash.toString();
    }

    public static String exceptionDetail(Throwable throwable) {
        // StringWriter将包含堆栈信息
        Writer writer = new StringWriter();
        //必须将StringWriter封装成PrintWriter对象，
        //以满足printStackTrace的要求
        PrintWriter printWriter = new PrintWriter(writer);
        //获取堆栈信息
        throwable.printStackTrace(printWriter);
        //转换成String，并返回该String
        return "\n" + writer.toString();
    }


    /*
     * 对JSON字符串进行格式化输出
     * */
    public static String formatJson(String jsonStr) {
        if (null == jsonStr || "".equals(jsonStr)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            switch (current) {
                case '{':
                case '[':
                    sb.append(current);
                    sb.append('\n');
                    indent++;
                    addIndentBlank(sb, indent);
                    break;
                case '}':
                case ']':
                    sb.append('\n');
                    indent--;
                    addIndentBlank(sb, indent);
                    sb.append(current);
                    break;
                case ',':
                    sb.append(current);
                    if (last != '\\') {
                        sb.append('\n');
                        addIndentBlank(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
            }
        }

        return sb.toString();
    }

    /**
     * 添加space
     */
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }

    public static String camelToUnderline(String name) {
        StringBuilder result = new StringBuilder();
        if (name != null && name.length() > 0) {
            // 将第一个字符处理成大写
            result.append(name.substring(0, 1).toUpperCase());
            // 循环处理其余字符
            for (int i = 1; i < name.length(); i++) {
                String s = name.substring(i, i + 1);
                // 在大写字母前添加下划线
                if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
                    result.append("_");
                }
                // 其他字符直接转成大写
                result.append(s);
            }
        }
        return result.toString().toLowerCase();
    }

}
