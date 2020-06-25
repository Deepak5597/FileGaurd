package com.gehu.filegaurd.utilities;

import java.sql.ResultSet;

import org.json.JSONArray;
import org.json.JSONObject;

public class DataObject {
	
	public String prepareJsonData(String status,String message,Object data)throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("status",status);
		jsonObject.put("message",message);
		jsonObject.put("data",data);
		return jsonObject.toString();
	}
	
	public String prepareStringJsonData(String status,String message,String data)throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("status",status);
		jsonObject.put("message",message);
		jsonObject.put("data",data);
		return jsonObject.toString();
	}
	public String createFolderData(ResultSet rs)throws Exception {
		System.out.println("Setting Data");
		JSONArray jsonArr = new JSONArray();
		while(rs.next()) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("fileid", rs.getString(1));
			jsonObj.put("filename", rs.getString(2));
			jsonObj.put("filepath", rs.getString(3));
			jsonObj.put("isDirectory", rs.getString(4));
			jsonObj.put("directoryname", rs.getString(5));
			jsonObj.put("filetype", rs.getString(6));
			jsonObj.put("encryptiontype", rs.getString(7));
			jsonObj.put("creationdate", rs.getString(8));
			jsonObj.put("createdby", rs.getString(9));
			jsonObj.put("filesize", rs.getString(10));
			jsonArr.put(jsonObj);
		}
		return jsonArr.toString();
	}
}
