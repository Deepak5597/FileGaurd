package com.gehu.filegaurd.services;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gehu.filegaurd.dao.DBConnection;
import com.gehu.filegaurd.utilities.CloseObjectConfig;
import com.gehu.filegaurd.utilities.DataObject;
import com.gehu.filegaurd.utilities.SQLQuery;

/**
 * Servlet implementation class CreateFolder
 */
@WebServlet("/CreateFolder")
public class CreateFolder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateFolder() {
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
		Connection connection = null;
		PreparedStatement createFilePs=null;
		CloseObjectConfig closeObjectConfig = new CloseObjectConfig();
		DataObject dataObject = new DataObject();
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
		try {
			HttpSession session = request.getSession(false); 
			File file = new File(System.getProperty("catalina.home")+"/"+"filegaurd"+"/"+session.getAttribute("email").toString().split("@")[0]+"/"+request.getParameter("filename"));
			if(file.mkdirs()) {
				connection = DBConnection.getConnection();
				createFilePs = connection.prepareStatement(SQLQuery.CREATE_FOLDER);
				createFilePs.setString(1, request.getParameter("filename"));
				createFilePs.setString(2, session.getAttribute("email").toString().split("@")[0]+"/"+request.getParameter("filename"));
				createFilePs.setString(3, "YES");
				createFilePs.setString(4, "DIRECTORY");
				createFilePs.setString(5, "NONE");
				createFilePs.setString(6, session.getAttribute("email").toString());
				createFilePs.setString(7, "NA");
				createFilePs.executeUpdate();
				response.getWriter().write(dataObject.prepareJsonData("success", "Folder Created Successfully.", null));
			}else {
				response.getWriter().write(dataObject.prepareJsonData("error", "Error While creating Folder", null));
			}

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			closeObjectConfig.closePStatement(createFilePs);
			closeObjectConfig.closeConnection(connection);
		}
	}

}
