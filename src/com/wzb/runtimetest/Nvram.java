package com.wzb.runtimetest;

import com.wzb.runtimetest.util.LogUtil;

import android.os.RemoteException;
import vendor.mediatek.hardware.nvram.V1_0.INvram;

import java.util.ArrayList;
import java.util.Arrays;

import com.android.internal.util.HexDump;
/**
 * @author wzb<wangzhibin_x@qq.com>
 * @date Mar 11, 2019 5:22:49 PM	
 */


public class Nvram {
	
	private static final String CUSTOM_ADDRESS_FILENAME = "/vendor/nvdata/APCFG/APRDEB/PRODUCT_INFO";
	private static int AGETEST_VALUE = 380;
	private static int TOTAL_BYTE = 1;
	
	public static  void writeFileByNamevec(int[] testData){
		try {
			INvram agent = INvram.getService(); 
			if (agent == null) {				
				LogUtil.logMessage("cjg_test","writeFileByNamevec agent == null");				
				return;
			}
			String buff = agent.readFileByName(CUSTOM_ADDRESS_FILENAME, AGETEST_VALUE + TOTAL_BYTE);			
			byte[] buffArr = HexDump.hexStringToByteArray(buff.substring(0, buff.length() - 1));			
			LogUtil.logMessage("cjg_test","writeFileByNamevec read buffArr == "+Arrays.toString(buffArr));						
			ArrayList<Byte> dataArray = new ArrayList<Byte>(buffArr.length);
			LogUtil.logMessage("cjg_test","writeFileByNamevec dataArray.size == "+dataArray.size());
			for(int i = 0; i < buffArr.length; i++){
				if(i >= AGETEST_VALUE){
					String data = "0";
					int value = testData[i - AGETEST_VALUE];
					if(value == 3){
						data = "3";
					} else if(value == 2){
						data = "2";
					}
					dataArray.add(i, new Byte(data));
				}else{
					dataArray.add(i, buffArr[i]);
				}
			}
			LogUtil.logMessage("cjg_test","writeFileByNamevec dataArray == "+dataArray.toString());						
			int flag = agent.writeFileByNamevec(CUSTOM_ADDRESS_FILENAME, AGETEST_VALUE + TOTAL_BYTE, dataArray);			
			LogUtil.logMessage("cjg_test","ffengfan writeFileByNamevec write flag == "+flag);
		} catch (RemoteException e) {			
			LogUtil.logMessage("cjg_test","writeFileByNamevec Exception == "+e);			
			e.printStackTrace();		
		}
	}
	public static int resultRead(){
		int final_result=0;
		try {			
			INvram agent = INvram.getService();			
			if (agent == null) {				
				LogUtil.logMessage("cjg_test","readFileByNamevec write agent == null");				
				return final_result;			
			}
			String buff = agent.readFileByName(CUSTOM_ADDRESS_FILENAME, AGETEST_VALUE + TOTAL_BYTE);            
			byte[] buffArr = HexDump.hexStringToByteArray(buff.substring(0, buff.length() - 1));			
			LogUtil.logMessage("cjg_test","readFileByNamevec read buffArr == "+Arrays.toString(buffArr));
			int i = AGETEST_VALUE;
			final_result = Integer.parseInt(Byte.toString(buffArr[i]));
		
		} catch (Exception e) {
			LogUtil.logMessage("cjg_test","readFileByNamevec Exception == "+e);			
			e.printStackTrace();		
		}	
		return final_result;
	}

}
