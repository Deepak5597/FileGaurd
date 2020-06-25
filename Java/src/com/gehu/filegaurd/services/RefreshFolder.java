package com.gehu.filegaurd.services;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
 * Servlet implementation class RefreshFolder
 */
@WebServlet("/RefreshFolder")
public class RefreshFolder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RefreshFolder() {
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
		PreparedStatement getFilePs=null;
		ResultSet getFileRs = null;
		CloseObjectConfig closeObjectConfig = new CloseObjectConfig();
		DataObject dataObject = new DataObject();
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
		try {
			System.out.println("Refresh Folder");
			HttpSession session = request.getSession(false); 
				connection = DBConnection.getConnection();
				getFilePs = connection.prepareStatement(SQLQuery.GET_FILE);
				getFilePs.setString(1, session.getAttribute("email").toString());
				getFileRs = getFilePs.executeQuery();
				response.getWriter().write(dataObject.prepareJsonData("success", "File Found", dataObject.createFolderData(getFileRs)));
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			closeObjectConfig.closePStatement(getFilePs);
			closeObjectConfig.closeConnection(connection);
		}
	}

}
