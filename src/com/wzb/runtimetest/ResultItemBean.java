package com.wzb.runtimetest;
/**
 * @author wzb<wangzhibin_x@qq.com>
 * @date Mar 1, 2019 10:39:35 AM	
 */
public class ResultItemBean {
	
	private String item;
	private String status;
	private String result;
	
	public ResultItemBean(){
		
	}
	
	public ResultItemBean(String item,String status,String result){
		this.item=item;
		this.status=status;
		this.result=result;
	}
	
	public String getItemName(){
		return this.item;
	}
	
	public String getStatus(){
		return this.status;
	}
	public void setStatus(String status){
		this.status=status;
	}
	
	public String getResult(){
		return this.result;
	}
	
	public void setResult(String result){
		this.result=result;
	}

}
