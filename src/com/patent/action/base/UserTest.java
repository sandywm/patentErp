package com.patent.action.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserTest implements Comparable<UserTest>{
	private String fwNo;
	private String zlNo;
	
	public UserTest(){}
	
	public UserTest(String fwNo, String zlNo) {
		this.fwNo = fwNo;
		this.zlNo = zlNo;
	}

	public String getFwNo() {
		return fwNo;
	}

	public void setFwNo(String fwNo) {
		this.fwNo = fwNo;
	}

	public String getZlNo() {
		return zlNo;
	}

	public void setZlNo(String zlNo) {
		this.zlNo = zlNo;
	}
	

	@Override
	public int compareTo(UserTest user) {
		// TODO Auto-generated method stub
		int i = Integer.parseInt(this.getZlNo()) - Integer.parseInt(user.getZlNo());//先按照专利号排序
		if(i == 0){
			return Integer.parseInt(this.getFwNo()) - Integer.parseInt(user.getFwNo());//如果专利号相等，再按照发文序号排序
		}
		return i;
	}
	
	public List<UserTest> getList(String fwNo,String zlNo){
		List<UserTest> users = new ArrayList<UserTest>();
		users.add(new UserTest(fwNo, zlNo));
		return users; 
	}
	
	public static void main(String[] args) {  
		
		UserTest ut = new UserTest();
		List<UserTest> u1 = ut.getList("20170508", "112233");
		List<UserTest> u2 = ut.getList("20170507", "112233");
		List<UserTest> u3 = ut.getList("20170507", "112234");
		List<UserTest> users = new ArrayList<UserTest>(); 
		users.addAll(u1);
		users.addAll(u2);
		users.addAll(u3);
        Collections.sort(users);
        for(UserTest user : users){
        	System.out.println("专利号: "+user.getZlNo()+" 发文序号："+user.getFwNo());
        }
	}
}
