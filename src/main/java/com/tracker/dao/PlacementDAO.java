package com.tracker.dao;

import com.tracker.model.Placement;
import com.tracker.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlacementDAO {

    public boolean saveOrUpdatePlacement(Placement placement) {
        // Check if placement record exists for user
        Placement existing = getPlacementByUserId(placement.getUserId());
        String query;
        if (existing == null) {
            query = "INSERT INTO placement_scores (user_id, aptitude_score, coding_score, interview_score) VALUES (?, ?, ?, ?)";
        } else {
            query = "UPDATE placement_scores SET aptitude_score = ?, coding_score = ?, interview_score = ? WHERE user_id = ?";
        }
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            if (existing == null) {
                stmt.setInt(1, placement.getUserId());
                stmt.setInt(2, placement.getAptitudeScore());
                stmt.setInt(3, placement.getCodingScore());
                stmt.setInt(4, placement.getInterviewScore());
            } else {
                stmt.setInt(1, placement.getAptitudeScore());
                stmt.setInt(2, placement.getCodingScore());
                stmt.setInt(3, placement.getInterviewScore());
                stmt.setInt(4, placement.getUserId());
            }
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Placement getPlacementByUserId(int userId) {
        String query = "SELECT * FROM placement_scores WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Placement(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("aptitude_score"),
                    rs.getInt("coding_score"),
                    rs.getInt("interview_score")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
