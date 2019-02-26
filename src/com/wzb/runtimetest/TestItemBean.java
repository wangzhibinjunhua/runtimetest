package com.wzb.runtimetest;
/**
 * @author wzb<wangzhibin_x@qq.com>
 * @date Feb 26, 2019 6:12:11 PM	
 */
public class TestItemBean {
	private String itemName;
	private Boolean itemSelect;
	
	public TestItemBean(){
		
	}
	
	public TestItemBean(String itemName,Boolean itemSelect){
		this.itemName=itemName;
		this.itemSelect=itemSelect;
	}
	
	public String getItemName() {
		return itemName;
	}
	public Boolean getItemSelect() {
		return itemSelect;
	}
	
	public void setItemSelect(Boolean select) {
		this.itemSelect = select;
	}
	
	public void cbToggle() {
		setItemSelect(!this.itemSelect);
	}
}
