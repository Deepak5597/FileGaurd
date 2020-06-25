package com.gehu.filegaurd.services;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gehu.filegaurd.utilities.DataObject;

/**
 * Servlet implementation class LogoutService
 */
@WebServlet("/LogoutService")
public class LogoutService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutService() {
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
		DataObject dataObject = new DataObject();
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
		try {
			HttpSession session = request.getSession(false);
			session.invalidate();
			response.getWriter().write(dataObject.prepareJsonData("success", "Session terminated successfully.", null));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
