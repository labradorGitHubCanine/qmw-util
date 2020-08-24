package com.qmw.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.HashMap;
import java.util.Map;

/**
 * 拼音工具类
 * 2020-08-14
 *
 * @author qmw
 * @since 1.03
 */
public class PinyinUtil {

    // 格式化方式
    private final static HanyuPinyinOutputFormat FORMAT = new HanyuPinyinOutputFormat();

    static {
        FORMAT.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        FORMAT.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        FORMAT.setVCharType(HanyuPinyinVCharType.WITH_V);
    }

    // 生僻字库
    private static final Map<String, String> RARE_WORDS = new HashMap<>();

    static {
        RARE_WORDS.put("x", "匂");
        RARE_WORDS.put("h", "丆");
        RARE_WORDS.put("y", "昳");
    }

    /**
     * 获取字符串中所有中文的拼音首字母，非中文字符直接拼接
     *
     * @param string string
     * @return 拼音首字母
     */
    public static String getPinYinInitials(String string) {
        if (string == null)
            return "";
        StringBuilder builder = new StringBuilder();
        loop:
        for (char c : string.toCharArray()) {
            // 优先从生僻字中找
            for (Map.Entry<String, String> e : RARE_WORDS.entrySet()) {
                if (e.getValue().contains(Character.toString(c))) {
                    builder.append(e.getKey());
                    continue loop; // 找到对应生僻字则直接拼接，无需继续解析
                }
            }
            // 生僻字没找到则解析拼音
            if (Character.toString(c).matches("[\u4e00-\u9fa5]")) {
                try {
                    String[] arr = PinyinHelper.toHanyuPinyinStringArray(c, FORMAT);
                    if (arr.length > 0 && !StringUtil.isEmpty(arr[0]))
                        builder.append(arr[0].charAt(0));
                    else
                        builder.append('?');
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    builder.append('?');
                }
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }

}