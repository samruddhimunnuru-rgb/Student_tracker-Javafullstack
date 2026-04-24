package com.tracker.servlet;

import com.google.gson.Gson;
import com.tracker.dao.PlacementDAO;
import com.tracker.dao.TaskDAO;
import com.tracker.model.Placement;
import com.tracker.model.Task;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/api/recommendation")
public class RecommendationServlet extends HttpServlet {
    private TaskDAO taskDAO = new TaskDAO();
    private PlacementDAO placementDAO = new PlacementDAO();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Map<String, String> res = new HashMap<>();

        if (session == null || session.getAttribute("userId") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        int userId = (int) session.getAttribute("userId");
        List<Task> tasks = taskDAO.getTasksByUserId(userId);
        Placement placement = placementDAO.getPlacementByUserId(userId);
        
        String recommendation = "You are all caught up! Consider exploring new subjects.";
        String reason = "No urgent tasks or low placement scores found.";
        
        // Logic 1: Find task with closest deadline that is pending
        long currentTime = new Date().getTime();
        long closestDiff = Long.MAX_VALUE;
        Task urgentTask = null;
        
        for (Task t : tasks) {
            if ("Pending".equals(t.getStatus())) {
                long diff = t.getDeadline().getTime() - currentTime;
                if (diff > 0 && diff < closestDiff) {
                    closestDiff = diff;
                    urgentTask = t;
                }
            }
        }
        
        // Prioritize placement if scores are low (< 50)
        boolean lowCoding = placement != null && placement.getCodingScore() < 50;
        boolean lowAptitude = placement != null && placement.getAptitudeScore() < 50;
        
        if (urgentTask != null && closestDiff < 3 * 24 * 60 * 60 * 1000L) { // Less than 3 days
            recommendation = "Focus on " + urgentTask.getSubject() + " - " + urgentTask.getTopic();
            reason = "Deadline is approaching soon!";
        } else if (lowCoding) {
            recommendation = "Practice Coding Problems (Data Structures & Algorithms)";
            reason = "Your coding score is below average. It's crucial for placements.";
        } else if (lowAptitude) {
            recommendation = "Solve Quantitative Aptitude Questions";
            reason = "Your aptitude score needs improvement.";
        } else if (urgentTask != null) {
            recommendation = "Complete pending task: " + urgentTask.getSubject();
            reason = "You have pending assignments.";
        }

        res.put("recommendation", recommendation);
        res.put("reason", reason);
        
        out.print(gson.toJson(res));
        out.flush();
    }
}
