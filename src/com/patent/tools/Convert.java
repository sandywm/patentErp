package com.patent.tools;

import java.text.DecimalFormat;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class Convert {
	/**
	 * 获取中文全拼
	 * @description
	 * @author wm
	 * @date 2017-3-7 上午09:46:40
	 * @param inputStr
	 * @return
	 */
	public static String getFullPY(String inputStr){
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
 
        char[] input = inputStr.trim().toCharArray();
        String output = "";
        try {
            for (int i = 0; i < input.length; i++) {
                if (java.lang.Character.toString(input[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i], format);
                    output += temp[0];
                } else
                    output += java.lang.Character.toString(input[i]);
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return output;
	}
	
	/**
	 * 获取中文首字母拼音
	 * @description
	 * @author wm
	 * @date 2017-3-7 上午09:48:39
	 * @param inputStr_chi
	 * @return
	 */
	public static String getFirstSpell(String inputStr_chi){
		StringBuffer pybf = new StringBuffer();   
        char[] arr = inputStr_chi.toCharArray();   
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();   
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);   
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        for (int i = 0; i < arr.length; i++) {   
            if (arr[i] > 128) {   
                    try {   
                            String[] temp = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);   
                            if (temp != null) {   
                                    pybf.append(temp[0].charAt(0));   
                            }   
                    } catch (BadHanyuPinyinOutputFormatCombination e) {   
                            e.printStackTrace();   
                    }   
            } else {   
                    pybf.append(arr[i]);   
            }   
        }   
        return pybf.toString().replaceAll("\\W", "").trim(); 
	}
	
	/**
	 * 保留2位小数的四舍五入
	 * @description
	 * @author wm
	 * @date 2017-5-5 下午03:59:39
	 * @param inputF
	 * @return
	 */
	public static float convertInputNumber(float inputF){
		return (float)(Math.round(inputF * 100)) / 100;
	}
	
	/**
	 * 保留2位小数的四舍五入
	 * @description
	 * @author wm
	 * @date 2017-6-21 下午05:19:40
	 * @param inputD
	 * @return
	 */
	public static String convertInputNumber_1(double inputD){
		DecimalFormat    df   = new DecimalFormat("######0.00");   
		return df.format(inputD);
	}
	
	/**
	 * 保留1位小数的四舍五入
	 * @description
	 * @author wm
	 * @date 2017-6-21 下午05:19:40
	 * @param inputD
	 * @return
	 */
	public static Double convertInputNumber_5(double inputD){
		DecimalFormat    df   = new DecimalFormat("######0.0");   
		return Double.parseDouble(df.format(inputD));
	}
	
	/**
	 * 保留2位小数的四舍五入(格式化金额)
	 * @description
	 * @author wm
	 * @date 2017-11-20 上午09:09:59
	 * @param inputD
	 * @return
	 */
	public static String convertInputNumber_3(double inputD){
		if(inputD > 0){
			DecimalFormat    df   = new DecimalFormat("¥,###.00");   
			return df.format(inputD);
		}
		return "¥0.00";
	}
	
	public static String convertMoney(double inputMoney){
		DecimalFormat    df   = new DecimalFormat("¥,###.##");
		return df.format(inputMoney);
	}
	
	public static String convertMoney_1(double inputMoney){
		DecimalFormat    df   = new DecimalFormat(",###.##");
		return df.format(inputMoney);
	}
	/**
	 * 保留2位小数的四舍五入
	 * @description
	 * @author wm
	 * @date 2017-11-15 上午10:53:26
	 * @param inputD
	 * @return
	 */
	public static Double convertInputNumber_2(double inputD){
		DecimalFormat    df   = new DecimalFormat("######0.00");   
		return Double.parseDouble(df.format(inputD));
	}
	
	/**
	 * 保留1位小数的四舍五入
	 * @description
	 * @author Administrator
	 * @date 2019-1-9 下午03:02:57
	 * @param inputD
	 * @return
	 */
	public static Double convertInputNumber_6(double inputD){
		DecimalFormat    df   = new DecimalFormat("######0.0");   
		return Double.parseDouble(df.format(inputD));
	}
	
	/**
	 * 保留4位小数的四舍五入
	 * @description
	 * @author wm
	 * @date 2017-11-15 上午10:53:26
	 * @param inputD
	 * @return
	 */
	public static Double convertInputNumber_4(double inputD){
		DecimalFormat    df   = new DecimalFormat("######0.0000");   
		return Double.parseDouble(df.format(inputD));
	}
	
	/**
	 * 输入数字金额转换成中文大写，精确到分
	 * @description
	 * @author wm
	 * @date 2018-7-16 上午08:58:18
	 * @param inputMoney
	 * @return
	 */
	public static String MoneyToCNFormat(Double inputMoney){
		final String[] CN_NUMBERS = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		final String[] CN_MONETETARY_UNIT = { "分", "角", "元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾","佰", "仟", "兆", "拾", "佰", "仟" };
	    final String CN_FULL = "整";
	    final String CN_NEGATIVE = "负";
	    final String CN_ZERO = "零角零分";
	    final long MAX_NUMBER = 10000000000000000l;//最大16位整数
	    final String INVALID_AMOUNT = "金额超出最大金额千兆亿(16位整数)";
	    if(Math.abs(inputMoney) >= MAX_NUMBER) return INVALID_AMOUNT;
	    StringBuilder result = new StringBuilder();
        long value = Math.abs(Math.round(inputMoney*100));//四舍五入到小数点后2位
        int i = 0;
        while(true){
        	int temp = (int) (value % 10);
            result.insert(0, CN_MONETETARY_UNIT[i]);
            result.insert(0, CN_NUMBERS[temp]);
            value = value / 10;
            if (value < 1)
                break;
            i++;
        }
        //"零角零分" 转换成 "整"
        int index = result.indexOf(CN_ZERO);
        if(index > -1) {
            result.replace(index, index + 4, CN_FULL);
        }
        //负数
        if(inputMoney < 0) {
            result.insert(0, CN_NEGATIVE);
        }
		return result.toString();
	}
	
	public static void main(String[] args){
		System.out.println(Convert.MoneyToCNFormat(157894.26));
	}
}
