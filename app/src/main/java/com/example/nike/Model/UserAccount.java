package com.example.nike.Model;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAccount {
    private int userId;
    private String username;
    private String password;
    private String gender;
    private String email;
    private String phoneNumber;
    private String address;
    private String firstName;
    private String lastName;
    private int memberTier;
    private int point;

    public UserAccount(int userId, String username, String password, String gender, String email, String phoneNumber, String address, String firstName, String lastName, int memberTier, int point) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
        this.memberTier = memberTier;
        this.point = point;
    }

    // Getters and setters for all properties

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getMemberTier() {
        return memberTier;
    }

    public void setMemberTier(int memberTier) {
        this.memberTier = memberTier;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        String ip = "192.168.56.1";
        String database = "db_BanQuanAo";
        String username = "sa";
        String password = "123456";
        String port = "1433";

        String ConnectionStr = "jdbc:jtds:sqlserver://" + ip + ":" + port + ";databaseName=" + database + ";user=" + username + ";password=" + password + ";encrypt=true;trustServerCertificate=true;";
        conn = DriverManager.getConnection(ConnectionStr);
        return conn;
    }

    public static boolean addUser(String username, String password, String gender, String email, String phoneNumber, String address, String firstName, String lastName, int memberTier, int point) {
        boolean isSuccess = false;
        Connection conn = null;
        try {
            conn = getConnection();

            String query = "INSERT INTO user_account (user_username, user_password, user_gender, user_email, user_phone_number, user_address, user_first_name, user_last_name, user_member_tier, user_point) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, gender);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, phoneNumber);
            preparedStatement.setString(6, address);
            preparedStatement.setString(7, firstName);
            preparedStatement.setString(8, lastName);
            preparedStatement.setInt(9, memberTier);
            preparedStatement.setInt(10, point);

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

    public static void checkLogin(String email, String password, final LoginCallback callback) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                Connection conn = null;
                boolean isSuccess = false;
                try {
                    conn = getConnection();

                    String query = "SELECT * FROM user_account WHERE user_email = ? AND user_password = ?";

                    PreparedStatement preparedStatement = conn.prepareStatement(query);
                    preparedStatement.setString(1, email);
                    preparedStatement.setString(2, password);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
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

            @Override
            protected void onPostExecute(Boolean result) {
                callback.onLoginResult(result);
            }
        }.execute();
    }

    public interface LoginCallback {
        void onLoginResult(boolean isSuccess);
    }
}
