package com.gehu.filegaurd.services;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gehu.filegaurd.utilities.CryptoUtils;

/**
 * Servlet implementation class GetFile
 */
@WebServlet("/GetFile")
public class GetFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetFile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        String filepath  = null;
        String encryptiontype  = null;
        String filetype = null;
		byte[] returnContent = null;
	    StringBuffer fileContentStr = new StringBuffer("");
	    BufferedReader reader = null;
	    ServletOutputStream out = response.getOutputStream();
	    
		try {           
			
			filetype= request.getParameter("filetype");
			encryptiontype = request.getParameter("encryptiontype");
			filepath = request.getParameter("filepath");
			response.setContentType(filetype);
	        System.out.println(System.getProperty("catalina.home")+"/"+"filegaurd"+"/"+filepath);
	        System.out.println(encryptiontype);
	   
			reader = new BufferedReader(new FileReader(System.getProperty("catalina.home")+"/"+"filegaurd"+"/"+filepath));
			String line = null;
			while ((line = reader.readLine()) != null) {
		           fileContentStr.append(line).append("\n");
			}
			byte[] fileContent = fileContentStr.toString().trim().getBytes();
			returnContent = CryptoUtils.decrypt(new ByteArrayInputStream(fileContent));
	   } catch (Exception e) {
		   throw new IOException("Unable to convert file to byte array. " + e.getMessage());
	   } finally {
		if (reader != null) {
	           reader.close();
		}
	  }
		   out.write(returnContent);
           out.flush();
           out.close();
	}

}
