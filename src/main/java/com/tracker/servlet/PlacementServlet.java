package com.tracker.servlet;

import com.google.gson.Gson;
import com.tracker.dao.PlacementDAO;
import com.tracker.model.Placement;

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

@WebServlet("/api/placement/*")
public class PlacementServlet extends HttpServlet {
    private PlacementDAO placementDAO = new PlacementDAO();
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
        Placement placement = placementDAO.getPlacementByUserId(userId);
        
        if (placement == null) {
            placement = new Placement(0, userId, 0, 0, 0); // Default empty
        }
        out.print(gson.toJson(placement));
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
            Placement placement = gson.fromJson(request.getReader(), Placement.class);
            placement.setUserId(userId);
            
            boolean success = placementDAO.saveOrUpdatePlacement(placement);
            res.put("status", success ? "success" : "error");
            out.print(gson.toJson(res));
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
