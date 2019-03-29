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
	private static int AGETEST_VALUE = 380; //for runtimetest 
	private static int TOTAL_BYTE = 1;
	
	private static int DETAILMODELOFFSET = 400; //for detailmodel
	private static int DETAILMODELBYTE = 32; //for detailmodel
	public static  void writeFileByNamevec(int[] testData){
		try {
			INvram agent = INvram.getService(); 
			if (agent == null) {				
				LogUtil.logMessage("wzb","writeFileByNamevec agent == null");				
				return;
			}
			String buff = agent.readFileByName(CUSTOM_ADDRESS_FILENAME, AGETEST_VALUE + TOTAL_BYTE);			
			byte[] buffArr = HexDump.hexStringToByteArray(buff.substring(0, buff.length() - 1));			
			LogUtil.logMessage("wzb","writeFileByNamevec read buffArr == "+Arrays.toString(buffArr));						
			ArrayList<Byte> dataArray = new ArrayList<Byte>(buffArr.length);
			LogUtil.logMessage("wzb","writeFileByNamevec dataArray.size == "+dataArray.size());
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
			LogUtil.logMessage("wzb","writeFileByNamevec dataArray == "+dataArray.toString());						
			int flag = agent.writeFileByNamevec(CUSTOM_ADDRESS_FILENAME, AGETEST_VALUE + TOTAL_BYTE, dataArray);			
			LogUtil.logMessage("wzb","ffengfan writeFileByNamevec write flag == "+flag);
		} catch (RemoteException e) {			
			LogUtil.logMessage("wzb","writeFileByNamevec Exception == "+e);			
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
			LogUtil.logMessage("wzb","readFileByNamevec read buffArr == "+Arrays.toString(buffArr));
			int i = AGETEST_VALUE;
			final_result = Integer.parseInt(Byte.toString(buffArr[i]));
		
		} catch (Exception e) {
			LogUtil.logMessage("wzb","readFileByNamevec Exception == "+e);			
			e.printStackTrace();		
		}	
		return final_result;
	}
	
	public static int setDetailModel(){
		int final_result=0;
		try {			
			INvram agent = INvram.getService();			
			if (agent == null) {				
				LogUtil.logMessage("wzb","readFileByNamevec write agent == null");				
				return final_result;			
			}
			String buff = agent.readFileByName(CUSTOM_ADDRESS_FILENAME, DETAILMODELOFFSET + DETAILMODELBYTE);            
			LogUtil.logMessage("wzb","buff="+buff+",buff len:"+buff.length());
			byte[] buffArr = HexDump.hexStringToByteArray(buff.substring(0,buff.length() - 1 ));			
			LogUtil.logMessage("wzb","readFileByNamevec read setDetailModel buffArr == "+Arrays.toString(buffArr));
			String hexdetailModel=buff.substring(buff.length()-64-1,buff.length()-1);
			LogUtil.logMessage("wzb","hexdetailModel="+hexdetailModel);
			String detailModel=asciiToString(hexdetailModel);
			LogUtil.logMessage("wzb","detailModel="+detailModel);
			detailModel=detailModel.trim();
			//set property
			if(detailModel!=null && detailModel.length()>2){
				android.os.SystemProperties.set("persist.sys.model.info",detailModel);
				String ccflag=detailModel.substring(detailModel.length()-2);
				android.os.SystemProperties.set("persist.radio.countrycode",ccflag);
			}else{
				android.os.SystemProperties.set("persist.sys.model.info","");
				android.os.SystemProperties.set("persist.radio.countrycode","");
			}
		} catch (Exception e) {
			LogUtil.logMessage("cjg_test","readFileByNamevec Exception == "+e);			
			e.printStackTrace();	
			return -1;
		}	
		return final_result;
	}
	
	public static String asciiToString(String value) {
		StringBuffer sbu = new StringBuffer();
		String[] chars = new String[value.length() / 2];
		for (int i = 0; i < chars.length; i++) {
			chars[i] = value.substring(i * 2, i * 2 + 2);
			if(chars[i].equals("00"))chars[i]="20";
			sbu.append((char) Integer.parseInt(chars[i], 16));
		}
		return sbu.toString();
	}
	

}
