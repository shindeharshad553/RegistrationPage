package com.demoapp;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.io.IOException;
import java.io.PrintWriter;

//Add details to the database 
public class AddDetailsToDatabase extends HttpServlet {

	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

//		on receiving request we have to connect to the database 
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentDetails", "root", "root");
			System.out.println("Successfully connected to database ");

			Statement s = con.createStatement();
			s.execute(
					"CREATE TABLE IF NOT EXISTS student (id INT AUTO_INCREMENT PRIMARY KEY,name VARCHAR(100) NOT NULL,email VARCHAR(50) UNIQUE, dob DATE NOT NULL,gender VARCHAR(10),course VARCHAR(30) NOT NULL,phone VARCHAR(15), address VARCHAR(100))");

//			To get the parameters from the user i.e from the front end fields

			String studName = req.getParameter("name");
			String studEmail = req.getParameter("email");
			String studDOB = req.getParameter("dob");
			String studGender = req.getParameter("gender");
			String studCourse = req.getParameter("course");
			String studPhone = req.getParameter("phone");
			String studAddress = req.getParameter("address");

//			create the prepareStatement and execute the query
			PreparedStatement ps = con.prepareStatement(
					"INSERT INTO student (name ,email,dob,gender,course,phone,address) VALUES (?,?,?,?,?,?,?)");
			ps.setString(1, studName);
			ps.setString(2, studEmail);
			ps.setString(3, studDOB);
			ps.setString(4, studGender);
			ps.setString(5, studCourse);
			ps.setString(6, studPhone);
			ps.setString(7, studAddress);

			int count = ps.executeUpdate();
			if (count > 0) {
				PrintWriter p = res.getWriter();
				p.println("You details are saved successfully....");
			}


//			redirecting the request to the login page 
			res.sendRedirect("successPopUp.html");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
