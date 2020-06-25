package com.gehu.filegaurd.utilities;

public class SQLQuery {
	
	public static final String ADD_NEW_USER = "insert into fg_users values(?,?,?,?)";
	public static final String VERIFY_USER = "select count(email) from fg_users where UPPER(email) = UPPER(?)";
	public static final String GET_PASSWORD_HASH = "select password as pwd, email as email, fname as fname, lname as lname from fg_users where UPPER(email) = UPPER(?)";
	public static final String CREATE_FOLDER = "insert into fg_files(filename,filepath,isDirectory,filetype,encryptiontype,createdby,creationdate,systemfilename) values(?,?,?,?,?,?,CURDATE(),?)";
	public static final String CREATE_FILE = "insert into fg_files(filename,filepath,isDirectory,directoryname,filetype,encryptiontype,createdby,creationdate,systemfilename,filesize) values(?,?,?,?,?,?,?,CURDATE(),?,?)";

	public static final String GET_FILE="select * from fg_files where createdby=?";
	
	
	public static final String ADD_FILE="insert into fg_all_files(filename,isDirectory,directoryname,filetype,encryptiontype,createdby,creationdate,filesize,file) values(?,?,?,?,?,?,CURDATE(),?,?)";
}
