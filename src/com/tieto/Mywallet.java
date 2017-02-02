package com.tieto;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

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
 * Servlet implementation class Mywallet
 */
@WebServlet("/Mywallet")
public class Mywallet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Mywallet() {
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
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
		
		
		HttpSession session = request.getSession(false);
		int userid = (int) session.getAttribute("MyAttribute");

		System.out.println("User Id is=" + userid);

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con2 = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/t_cash", "root",
					"root");
			System.out.println("Update connection " + con2);

			String sql1 = "select * from registration where id="+userid;

			PreparedStatement pstmt2 = (PreparedStatement) con2.prepareStatement(sql1);

			System.out.println("Before result set");
			ResultSet rs2 = (ResultSet) pstmt2.executeQuery();
			rs2.next();

			
			
			out.print("user id =" + rs2.getInt(1));
			out.print("<br></br>");
			out.print("<br></br>");
			out.print("My Name =" + rs2.getString(2));
			out.print("<br></br>");
			out.print("My Password =" + rs2.getString(4));
			out.print("<br></br>");
			out.print("Register Mobile no =" + rs2.getString(3));
			out.print("<br></br>");
			out.print("Wallet Amount=" + rs2.getInt(5));
			out.print("<br></br>");

			System.out.println("id =" + rs2.getInt(1));
			System.out.println("Name =" + rs2.getString(2));
			System.out.println("Password =" + rs2.getString(3));
			System.out.println("Mobile no =" + rs2.getString(4));
			System.out.println("Wallet =" + rs2.getInt(5));

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
