package com.example.nike.Controller;

import com.example.nike.Model.CategoryProduct;
import com.example.nike.Model.DBConnection;
import com.example.nike.Model.HistorySearch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.xml.transform.Result;

public class HistorySearchHandler {
    private static DBConnection dbConnection = new DBConnection();
    private static Connection conn = dbConnection.connectionClass();

    public static ArrayList<HistorySearch> getHistorySearch(int user_id) {
        ArrayList<HistorySearch> list = new ArrayList<>();
        if (conn != null) {
            try {
                String query = "SELECT * FROM history_search WHERE user_id = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setInt(1, user_id);
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    HistorySearch historySearch = new HistorySearch();
                    historySearch.setId(rs.getInt(1));
                    historySearch.setUser_id(rs.getInt(2));
                    historySearch.setText_search(rs.getString(3));
                    list.add(historySearch);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }

    public static void clearHistorySearch(int user_id)
    {
        if(conn != null)
        {
            String sql = "delete from history_search where user_id = ?";
            try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setInt(1,user_id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void addHistorySearch(int user_id,String text_search)
    {
        if(conn != null)
        {
            if(isTextSearchExists(user_id,text_search))
                return;
            String sql = "insert into history_search (user_id,text_search) values(?,?)";
            try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setInt(1,user_id);
                preparedStatement.setString(2,text_search);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static boolean isTextSearchExists(int user_id, String text_search) {
        String sql = "SELECT COUNT(*) FROM history_search WHERE user_id = ? AND text_search = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, user_id);
            preparedStatement.setString(2, text_search);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
