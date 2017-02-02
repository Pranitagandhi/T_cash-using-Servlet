package com.tieto;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String mob = request.getParameter("mobileNo");
		String pw = request.getParameter("passWord");

		/*************************************************************/

		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con1 = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/t_cash", "root",
					"root");

			System.out.println("Update connection " + con1);
			PreparedStatement ps = (PreparedStatement) con1
					.prepareStatement("select * from registration where mobile_no=" + mob);

			System.out.println("Before result set");
			ResultSet rs = (ResultSet) ps.executeQuery();
			rs.next();

			int id = (rs.getInt(1));
			
			System.out.println("My id="+id);
			
			
			HttpSession session = request.getSession();
			session.setAttribute("MyAttribute", id);

			// response.sendRedirect("index.jsp");
			
			
			if (LoginDao.validate(mob, pw)) {
				RequestDispatcher rd = request.getRequestDispatcher("fundTransfer");
				rd.forward(request, response);
			} else {
				out.print("Sorry username or password error");
				RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
				rd.include(request, response);
			}
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		

		/*************************************************************/

		
	}

}
