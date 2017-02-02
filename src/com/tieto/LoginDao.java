package com.tieto;

import java.sql.DriverManager;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

public class LoginDao {
	public static boolean validate(String mobile_no, String password) {
		boolean status = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/t_cash", "root",
					"root");

			PreparedStatement ps = (PreparedStatement) con
					.prepareStatement("select * from registration where mobile_no=? and password=?");
			ps.setString(1, mobile_no);
			ps.setString(2, password);

			ResultSet rs = (ResultSet) ps.executeQuery();
			status = rs.next();

		} catch (Exception e) {
			System.out.println(e);
		}
		return status;
	}
}
