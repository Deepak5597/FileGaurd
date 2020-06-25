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
 * Servlet implementation class SignupService
 */
@WebServlet("/SignupService")
public class SignupService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignupService() {
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
		PreparedStatement addUserPs=null,verifyUserPs=null;
		ResultSet verifyUserRs = null;
		CloseObjectConfig closeObjectConfig = new CloseObjectConfig();
		DataObject dataObject = new DataObject();
		try {
			connection = DBConnection.getConnection();
			verifyUserPs = connection.prepareStatement(SQLQuery.VERIFY_USER);
			verifyUserPs.setString(1, request.getParameter("email"));
			verifyUserRs = verifyUserPs.executeQuery();
			verifyUserRs.next();
			if(verifyUserRs.getInt(1) > 0) {
				System.out.println("User Already Exist");
				System.out.println(dataObject.prepareJsonData("error", "User Already Exist", null));
				response.getWriter().write(dataObject.prepareJsonData("error", "User Already Exist", null));
			}else {
				final String userPasswordHash = PasswordHashing.hashPassword(request.getParameter("password"));
				addUserPs = connection.prepareStatement(SQLQuery.ADD_NEW_USER);
				addUserPs.setString(1, request.getParameter("email"));
				addUserPs.setString(2, userPasswordHash);
				addUserPs.setString(3, request.getParameter("fName"));
				addUserPs.setString(4, request.getParameter("lName"));
				int i = addUserPs.executeUpdate();
				if(i > 0 ) {
					HttpSession session = request.getSession(false);
					session.setAttribute("fName", request.getParameter("fName"));
					session.setAttribute("lName", request.getParameter("lName"));
					session.setAttribute("email", request.getParameter("email"));
					response.getWriter().write(dataObject.prepareJsonData("error", "User Added Successfully", null));
				}else {
					response.getWriter().write(dataObject.prepareJsonData("error", "Error occurred while adding user.", null));
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			response.getWriter().write("exception");	
		}finally {
			closeObjectConfig.closeResultSet(verifyUserRs);
			closeObjectConfig.closePStatement(verifyUserPs);
			closeObjectConfig.closePStatement(addUserPs);
			closeObjectConfig.closeConnection(connection);
		}
	}

}
