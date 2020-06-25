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
import com.gehu.filegaurd.utilities.PasswordHashing;
import com.gehu.filegaurd.utilities.SQLQuery;

/**
 * Servlet implementation class LoginService
 */
@WebServlet("/LoginService")
public class LoginService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginService() {
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
		PreparedStatement verifyCredentialsPs=null;
		ResultSet verifyCredentialsRs = null;
		CloseObjectConfig closeObjectConfig = new CloseObjectConfig();
		DataObject dataObject = new DataObject();
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
		try {
			connection = DBConnection.getConnection();
			verifyCredentialsPs = connection.prepareStatement(SQLQuery.GET_PASSWORD_HASH);
			verifyCredentialsPs.setString(1, request.getParameter("email"));
			verifyCredentialsRs = verifyCredentialsPs.executeQuery();
			
			if(verifyCredentialsRs.next()) {
				final String hashFromTbl = verifyCredentialsRs.getString("pwd");
				String hasFromView = PasswordHashing.hashPassword(request.getParameter("password"));
				if(hashFromTbl.equalsIgnoreCase(hasFromView)) {
					HttpSession session = request.getSession(false);
					session.setAttribute("fName", verifyCredentialsRs.getString("fname"));
					session.setAttribute("lName", verifyCredentialsRs.getString("lname"));
					session.setAttribute("email", verifyCredentialsRs.getString("email"));
					System.out.println("User "+session.getAttribute("fName")+" Successfully Logged In.");
					response.getWriter().write(dataObject.prepareJsonData("success", "User Successfully Logged In.", null));
				}else {
					System.out.println("Wrong Password");
					response.getWriter().write(dataObject.prepareJsonData("error", "Invalid Username or Password", null));
				}
			}else {
				System.out.println("User Not Exist.");
				response.getWriter().write(dataObject.prepareJsonData("error", "Invalid Username or Password", null));
			}
		}catch(Exception e) {
			e.printStackTrace();
			response.getWriter().write("exception");	
		}finally {
			closeObjectConfig.closeResultSet(verifyCredentialsRs);
			closeObjectConfig.closePStatement(verifyCredentialsPs);
			closeObjectConfig.closeConnection(connection);
		}
	}

}
