package com.tracker.servlet;

import com.google.gson.Gson;
import com.tracker.dao.UserDAO;
import com.tracker.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/auth/*")
public class AuthServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Map<String, Object> res = new HashMap<>();

        try {
            User user = gson.fromJson(request.getReader(), User.class);

            if ("/register".equals(pathInfo)) {
                boolean success = userDAO.registerUser(user);
                if (success) {
                    res.put("status", "success");
                    res.put("message", "Registration successful");
                } else {
                    res.put("status", "error");
                    res.put("message", "Registration failed. Email might already exist.");
                }
            } else if ("/login".equals(pathInfo)) {
                User loggedInUser = userDAO.loginUser(user.getEmail(), user.getPassword());
                if (loggedInUser != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("userId", loggedInUser.getId());
                    session.setAttribute("userName", loggedInUser.getName());
                    
                    res.put("status", "success");
                    res.put("message", "Login successful");
                    res.put("userName", loggedInUser.getName());
                } else {
                    res.put("status", "error");
                    res.put("message", "Invalid email or password");
                }
            } else if ("/logout".equals(pathInfo)) {
                HttpSession session = request.getSession(false);
                if (session != null) {
                    session.invalidate();
                }
                res.put("status", "success");
                res.put("message", "Logged out successfully");
            }
            out.print(gson.toJson(res));
        } catch (Exception e) {
            res.put("status", "error");
            res.put("message", "Server error");
            out.print(gson.toJson(res));
            e.printStackTrace();
        }
        out.flush();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Map<String, Object> res = new HashMap<>();
        
        if ("/session".equals(pathInfo)) {
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("userId") != null) {
                res.put("loggedIn", true);
                res.put("userName", session.getAttribute("userName"));
            } else {
                res.put("loggedIn", false);
            }
        }
        out.print(gson.toJson(res));
        out.flush();
    }
}
