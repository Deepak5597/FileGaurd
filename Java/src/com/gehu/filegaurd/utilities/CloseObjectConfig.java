package com.gehu.filegaurd.utilities;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.zip.ZipOutputStream;

public class CloseObjectConfig{
	
	
	public void closeResultSet(ResultSet rs) {
		try {
			if(rs != null && !rs.isClosed()) {
				rs.close();
			}
		}catch(Exception e) {
		}
	}
	
	public void closeCallable(CallableStatement cs) {
		try {
		if(cs != null && !cs.isClosed()) {
			cs.close();
		}
		}catch(Exception e) {
		}
	}
	
	public void closeConnection(Connection connection) {
		try {
			if(connection != null && !connection.isClosed()) {
				connection.close();
			}
		}catch(Exception e) {
		}
	}
	
	public void closePStatement(PreparedStatement ps) {
		try {
		if(ps != null && !ps.isClosed()) {
			ps.close();
		}
		}catch(Exception e) {
		}
	}
	
	public void closeFOS(FileOutputStream fos) {
		try {
		if(fos != null) {
			fos.close();
		}
		}catch(Exception e) {
		}
	}
	
	public void closeZOS(ZipOutputStream zos) {
		try {
		if(zos != null) {
			zos.close();
		}
		}catch(Exception e) {
		}
	}
	
	public void closeBIS(BufferedInputStream bis) {
		try {
		if(bis != null) {
			bis.close();
		}
		}catch(Exception e) {
		}
	}
	
	public void closeBlob(Blob blob) {
		try {
		if(blob != null) {
			blob.free();
		}
		}catch(Exception e) {
		}
	}
	
}
