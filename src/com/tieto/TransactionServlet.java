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
 * Servlet implementation class TransactionServlet
 */
@WebServlet("/TransactionServlet")
public class TransactionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TransactionServlet() {
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

		String mob = request.getParameter("mobileNo");
		String amount = request.getParameter("amount");
		System.out.println("mobile no " + mob);
		System.out.println("Amt " + amount);

		int amt1 = Integer.parseInt(amount);

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/t_cash", "root",
					"root");

			System.out.println("Transfer Connection " + con);
			PreparedStatement ps = (PreparedStatement) con
					.prepareStatement("select * from registration where mobile_no=" + mob);

			System.out.println("Before result set");
			ResultSet rs = (ResultSet) ps.executeQuery();
			rs.next();
			String mob1 = (rs.getString(3));
			int walletamt = (rs.getInt(5));

			System.out.println("My Mobile no is " + mob1);
			System.out.println("My wallet amt is int" + walletamt);

			/*
			 * ***************************Add Money
			 *******************************/

			Class.forName("com.mysql.jdbc.Driver");
			Connection con1 = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/t_cash", "root",
					"root");

			System.out.println("Update connection " + con1);

			String sql = "UPDATE registration SET wallet = ? " + " WHERE mobile_no = " + mob;

			PreparedStatement pstmt1 = (PreparedStatement) con1.prepareStatement(sql);

			walletamt = walletamt + amt1;

			System.out.println("Total amt=" + walletamt);

			pstmt1.setInt(1, walletamt);

			System.out.println("Updated Successfully! " + pstmt1.executeUpdate());

			/************************************
			 * Session of user maintained
			 ******************************************/

			HttpSession session = request.getSession(false);
			int userid = (int) session.getAttribute("MyAttribute");

			System.out.println("User Id is=" + userid);

			/************************************
			 * Deduction Amount selected
			 ******************************************/

			Class.forName("com.mysql.jdbc.Driver");
			Connection con2 = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/t_cash", "root",
					"root");
			System.out.println("Update connection " + con2);

			String sql1 = "select * from registration where id=" + userid;

			PreparedStatement pstmt2 = (PreparedStatement) con2.prepareStatement(sql1);

			System.out.println("Before result set");
			ResultSet rs2 = (ResultSet) pstmt2.executeQuery();
			rs2.next();

			System.out.println("id =" + rs2.getInt(1));
			System.out.println("Name =" + rs2.getString(2));
			System.out.println("Password =" + rs2.getString(3));
			System.out.println("Mobile no =" + rs2.getString(4));
			System.out.println("Wallet =" + rs2.getInt(5));
			int balance = (rs2.getInt(5));

			System.out.println("User id " + userid);
			/* System.out.println("User Blance "+balance); */

			System.out.println("My Wallet balance =" + balance);

			/************************************
			 * Deduction Amount updated
			 ******************************************/

			Class.forName("com.mysql.jdbc.Driver");
			Connection con3 = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/t_cash", "root",
					"root");

			System.out.println("Update connection " + con3);

			String sql3 = "UPDATE registration SET wallet = ? " + " where id=" + userid;

			PreparedStatement pstmt3 = (PreparedStatement) con3.prepareStatement(sql3);

			balance = (balance - amt1);

			System.out.println("Total deducted amt=" + balance);

			pstmt3.setInt(1, balance);
			System.out.println("Total deducted amt=" + balance);
			System.out.println("Updated Successfully! " + pstmt3.executeUpdate());

			 request.getRequestDispatcher("/successServlet").forward(request, response);


		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
