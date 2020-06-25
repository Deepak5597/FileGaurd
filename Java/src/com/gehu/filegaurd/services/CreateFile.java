package com.gehu.filegaurd.services;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import com.gehu.filegaurd.dao.DBConnection;
import com.gehu.filegaurd.utilities.CloseObjectConfig;
import com.gehu.filegaurd.utilities.CryptoUtils;
import com.gehu.filegaurd.utilities.DataObject;
import com.gehu.filegaurd.utilities.SQLQuery;

/**
 * Servlet implementation class CreateFile
 */
@WebServlet("/CreateFile")
public class CreateFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateFile() {
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
		System.out.println("in call");
		HttpSession session = request.getSession(false); 
		String encryptionselected = null;
		String folderselected = null;
		CloseObjectConfig closeObjectConfig = new CloseObjectConfig();
		DataObject dataObject = new DataObject();
        Connection connection = null;
		PreparedStatement createFilePs=null;
		String filenameFromUser = null;
		String filenameForSystem = "f_"+new Date().getTime();
        try {
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);            
            FileItem item = null;
            
            for (Object indFile : items) {
            	item = (FileItem) indFile;
                if (item.isFormField()) {
                	if(item.getFieldName().equalsIgnoreCase("encryptiontype")) {
                		encryptionselected = item.getString();
                	}else if(item.getFieldName().equalsIgnoreCase("foldername")) {
                		folderselected = item.getString();
                	}
                	//System.out.println(encryptionselected+ " : "+folderselected);

                } else {
                	filenameFromUser = item.getName();
                	String  systemfilename = filenameForSystem+"."+filenameFromUser.split("\\.")[1];
                	String folderPath = System.getProperty("catalina.home")+"/"+"filegaurd"+"/"+session.getAttribute("email").toString().split("@")[0] +"/"+folderselected+"/"+systemfilename; 
                    //System.out.println(folderPath);
                    filenameFromUser = item.getName();
                    InputStream content = new ByteArrayInputStream(CryptoUtils.encrypt(item.getInputStream()));
                    File targetFile = new File(folderPath);
                    FileUtils.copyInputStreamToFile(content, targetFile);
                    response.setContentType("text/plain");
                    response.setCharacterEncoding("UTF-8");
                    //System.out.println("size : "+item.getSize()+" : "+item.getSize()/1024);
            		connection = DBConnection.getConnection();
    				createFilePs = connection.prepareStatement(SQLQuery.CREATE_FILE);
    				createFilePs.setString(1, filenameFromUser);
    				createFilePs.setString(2, session.getAttribute("email").toString().split("@")[0]+"/"+folderselected+"/"+systemfilename);
    				createFilePs.setString(3, "NO");
    				createFilePs.setString(4, folderselected);
    				createFilePs.setString(5,item.getContentType());
    				createFilePs.setString(6, encryptionselected);
    				createFilePs.setString(7, session.getAttribute("email").toString());
    				createFilePs.setString(8, systemfilename);
    				createFilePs.setString(9, String.valueOf(Math.round(item.getSize()/1024)));
    				createFilePs.executeUpdate();
    				response.getWriter().write(dataObject.prepareJsonData("success", "File Added Successfully.", null));
                }
            }
        } catch (Exception e) {
            throw new ServletException("Parsing file upload failed.", e);

        }finally {
        	closeObjectConfig.closePStatement(createFilePs);
			closeObjectConfig.closeConnection(connection);
        }

	}

}
