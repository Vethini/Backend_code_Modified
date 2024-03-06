package com.user.servlet;

import java.io.IOException;

import com.DB.DBConnect;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.entity.*;
import com.DAO.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    UserDAOImpl dao =  new UserDAOImpl(DBConnect.getConn());
	    HttpSession session = req.getSession();
	    resp.setContentType("text/plain");
	    resp.setCharacterEncoding("UTF-8");
	    try {
	        String email = req.getParameter("email");
	        String password = req.getParameter("password");

	        if("admin@gmail.com".equals(email) && "admin".equals(password)) {
	            // Admin login
	            session.setAttribute("userType", "admin");
	            session.setAttribute("successMsg", "Admin logged in successfully.");
	            resp.getWriter().write("Admin logged in successfully.");
	        } else {
	            // Regular user login
	            User user = dao.login(email, password);
	            if(user != null) {
	                session.setAttribute("userObj", user);
	                session.setAttribute("userType", "user");
	                session.setAttribute("successMsg", "User logged in successfully.");
	                resp.getWriter().write("User logged in successfully.");
	            } else {
	                // Incorrect username or password
	                session.setAttribute("failedMsg", "Invalid Email Id & Password.");
	                resp.getWriter().write("Invalid Email Id & Password.");
	            }
	        }
	    } catch(Exception e) {
	        e.printStackTrace();
	        // Handle other exceptions
	    }
	}

    }

