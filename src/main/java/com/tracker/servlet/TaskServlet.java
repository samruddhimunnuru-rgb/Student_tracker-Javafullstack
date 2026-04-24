package com.tracker.servlet;

import com.google.gson.Gson;
import com.tracker.dao.TaskDAO;
import com.tracker.model.Task;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/api/tasks/*")
public class TaskServlet extends HttpServlet {
    private TaskDAO taskDAO = new TaskDAO();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        if (session == null || session.getAttribute("userId") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        
        int userId = (int) session.getAttribute("userId");
        List<Task> tasks = taskDAO.getTasksByUserId(userId);
        out.print(gson.toJson(tasks));
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Map<String, Object> res = new HashMap<>();
        
        if (session == null || session.getAttribute("userId") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            int userId = (int) session.getAttribute("userId");
            Task task = gson.fromJson(request.getReader(), Task.class);
            task.setUserId(userId);
            if (task.getStatus() == null) task.setStatus("Pending");
            
            boolean success = taskDAO.addTask(task);
            if (success) {
                res.put("status", "success");
            } else {
                res.put("status", "error");
            }
            out.print(gson.toJson(res));
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle update status
        HttpSession session = request.getSession(false);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Map<String, Object> res = new HashMap<>();

        if (session == null || session.getAttribute("userId") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            Map<String, Object> data = gson.fromJson(request.getReader(), Map.class);
            int taskId = ((Double) data.get("taskId")).intValue();
            String status = (String) data.get("status");
            
            boolean success = taskDAO.updateTaskStatus(taskId, status);
            res.put("status", success ? "success" : "error");
            out.print(gson.toJson(res));
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Map<String, Object> res = new HashMap<>();

        if (session == null || session.getAttribute("userId") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            int taskId = Integer.parseInt(request.getParameter("id"));
            boolean success = taskDAO.deleteTask(taskId);
            res.put("status", success ? "success" : "error");
            out.print(gson.toJson(res));
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
