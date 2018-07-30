package com.patent.tools;

import java.util.Calendar;

public class InviteCode {
	//获取6位随机邀请码(小写字母+数字)
	public static String getRandomCode(){
		String[] randomValues = new String[]{"0","1","2","3","4","5","6","7","8","9",
				"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q",
				"r","s","t","u","v","w","x","y","z"};
//		,"A","B","C","D","E","F","G","H",
//		"I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"
		StringBuffer str = new StringBuffer();
		for(int i = 0;i < 6; i++){
			Double number=Math.random()*(randomValues.length-1);
			str.append(randomValues[number.intValue()]);
		}
		return str.toString();
	}
	//获取6位随机邀请码(数字)
	public static String getRandomNumberCode(){
		String[] randomValues = new String[]{"0","1","2","3","4","5","6","7","8","9"};
		StringBuffer str = new StringBuffer();
		for(int i = 0;i < 6; i++){
			Double number=Math.random()*(randomValues.length-1);
			str.append(randomValues[number.intValue()]);
		}
		return str.toString();
	}
	//获取n位随机码(小写字母+数字)
	public static String getRandomStr(int n){
		String[] randomValues = new String[]{"0","1","2","3","4","5","6","7","8","9",
				"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q",
				"r","s","t","u","v","w","x","y","z"};
		StringBuffer str = new StringBuffer();
		for(int i = 0;i < n; i++){
			Double number=Math.random()*(randomValues.length-1);
			str.append(randomValues[number.intValue()]);
		}
		return str.toString();
	}
	//获取n位随机码(大写字母+数字)
	public static String getRandomAllStr(int n){
		String[] randomValues = new String[]{"0","1","2","3","4","5","6","7","8","9",
				"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q",
				"R","S","T","U","V","W","X","Y","Z"};
		StringBuffer str = new StringBuffer();
		for(int i = 0;i < n; i++){
			Double number=Math.random()*(randomValues.length-1);
			str.append(randomValues[number.intValue()]);
		}
		return str.toString();
	}
	/**
	 * 获取n位随机码(小写字母（去除l和o）+数字)+当前秒数
	 * @description
	 * @author wm
	 * @date 2015-10-21 上午11:18:23
	 * @param n
	 * @return
	 */
	public static String getRandomStr2(int n){
		String[] randomValues = new String[]{"0","1","2","3","4","5","6","7","8","9",
				"a","b","c","d","e","f","g","h","i","j","k","m","n","p","q",
				"r","s","t","u","v","w","x","y","z"};
		StringBuffer str = new StringBuffer();
		for(int i = 0;i < n; i++){
			Double number=Math.random()*(randomValues.length-1);
			str.append(randomValues[number.intValue()]);
		}
		Calendar calendar = Calendar.getInstance();
		Integer currSecond = calendar.get(Calendar.SECOND);
		if(currSecond >= 10){
			str.append(currSecond);	
		}else{
			str.append("0"+currSecond);	
		}
		return str.toString();
	}
	public static void main(String[] args){
		System.out.println(InviteCode.getRandomStr2(4));
	}
}
