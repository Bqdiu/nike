package com.example.nike.Controller;

import com.example.nike.Model.DBConnection;
import com.example.nike.Model.UserAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAccountHandler {
    private static DBConnection db = new DBConnection();

    public static boolean checkEmailExist(String email) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            con = db.connectionClass();
            String sql = "select count(*) from user_account where user_email = ?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, email);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean checkUserExist(String username) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            con = db.connectionClass();
            String sql = "select count(*) from user_account where user_username = ?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public static boolean addUserGoogle(String email, String fullname,String url) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = db.connectionClass();
            String sql = "INSERT INTO user_account (user_email, user_first_name, user_url) VALUES (?, ?, ?)";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, fullname);
            preparedStatement.setString(3, url);
            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error adding user", e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static boolean addUser(String username, String password, String email, String first_name, String last_name) {
        boolean isSuccess = false;
        Connection conn = null;
        try {
            conn = db.connectionClass();

            String query = "INSERT INTO user_account (user_username, user_password, user_email, user_first_name, user_last_name) VALUES (?, ?, ?,?,?)";

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, first_name);
            preparedStatement.setString(5, last_name);
            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                isSuccess = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return isSuccess;
    }

    public static UserAccount checkLogin(String username, String password) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        UserAccount user = null;
        try {
            con = db.connectionClass();
            String sql = "select * from user_account where user_username = ? and user_password = ?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                user = new UserAccount(
                        rs.getInt("user_id"),
                        rs.getString("user_username"),
                        rs.getString("user_password"),
                        rs.getString("user_gender"),
                        rs.getString("user_email"),
                        rs.getString("user_phone_number"),
                        rs.getString("user_address"),
                        rs.getString("user_first_name"),
                        rs.getString("user_last_name"),
                        rs.getInt("user_member_tier"),
                        rs.getInt("user_point"),
                        rs.getString("user_url")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (preparedStatement != null) preparedStatement.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return user;
    }

    public static UserAccount getUserByEmail(String email)
    {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        UserAccount user = null;
        try {
            con = db.connectionClass();
            String sql = "select * from user_account where user_email = ?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, email);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                user = new UserAccount(
                        rs.getInt("user_id"),
                        rs.getString("user_username"),
                        rs.getString("user_password"),
                        rs.getString("user_gender"),
                        rs.getString("user_email"),
                        rs.getString("user_phone_number"),
                        rs.getString("user_address"),
                        rs.getString("user_first_name"),
                        rs.getString("user_last_name"),
                        rs.getInt("user_member_tier"),
                        rs.getInt("user_point"),
                        rs.getString("user_url")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (preparedStatement != null) preparedStatement.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return user;
    }

    public static boolean editUserProfile(String email, String first_name, String last_name, String phone_number, String address)
    {
        boolean isSuccess = false;
        Connection conn = null;
        try {
            conn = db.connectionClass();

            String query = "update user_account set user_first_name = ?, user_last_name = ?, user_phone_number = ?, user_address = ? where user_email = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, first_name);
            preparedStatement.setString(2, last_name);
            preparedStatement.setString(3, phone_number);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, email);
            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                isSuccess = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return isSuccess;
    }

    public static boolean editEmailUser(String email, String newEmail)
    {
        boolean isSuccess = false;
        Connection conn = null;
        try {
            conn = db.connectionClass();

            String query = "update user_account set user_email = ? where user_email = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, newEmail);
            preparedStatement.setString(2, email);
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                isSuccess = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return isSuccess;
    }

    public static boolean resetPasswordByEmail(String email, String newPassword)
    {
        boolean isSuccess = false;
        Connection conn = null;
        try {
            conn = db.connectionClass();

            String query = "update user_account set user_password = ? where user_email = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, email);
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                isSuccess = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return isSuccess;
    }
    public static boolean checkUserPhoneExists(String phone)    {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            con = db.connectionClass();
            String sql = "select count(*) from user_account where user_phone_number = ?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, phone);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


}
