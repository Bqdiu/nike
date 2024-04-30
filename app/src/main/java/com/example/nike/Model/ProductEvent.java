package com.example.nike.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ProductEvent {
    private int discountEventId;
    private int categoryProductId;
    private String discountEventName;
    private String discountEventDescription;
    private String discountEventImg;
    private Date discountEventDateCreated;

    // Phương thức để tính thời gian
    private int monthsSinceCreation;

    // Constructors
    public ProductEvent() {
    }

    public ProductEvent(int categoryProductId, String discountEventName, String discountEventDescription, String discountEventImg, Date discountEventDateCreated) {
        this.categoryProductId = categoryProductId;
        this.discountEventName = discountEventName;
        this.discountEventDescription = discountEventDescription;
        this.discountEventImg = discountEventImg;
        this.discountEventDateCreated = discountEventDateCreated;
    }

    // Getters and Setters
    public int getDiscountEventId() {
        return discountEventId;
    }

    public void setDiscountEventId(int discountEventId) {
        this.discountEventId = discountEventId;
    }

    public int getCategoryProductId() {
        return categoryProductId;
    }

    public void setCategoryProductId(int categoryProductId) {
        this.categoryProductId = categoryProductId;
    }

    public String getDiscountEventName() {
        return discountEventName;
    }

    public void setDiscountEventName(String discountEventName) {
        this.discountEventName = discountEventName;
    }

    public String getDiscountEventDescription() {
        return discountEventDescription;
    }

    public void setDiscountEventDescription(String discountEventDescription) {
        this.discountEventDescription = discountEventDescription;
    }

    public String getDiscountEventImg() {
        return discountEventImg;
    }

    public void setDiscountEventImg(String discountEventImg) {
        this.discountEventImg = discountEventImg;
    }

    public Date getDiscountEventDateCreated() {
        return discountEventDateCreated;
    }

    public void setDiscountEventDateCreated(Date discountEventDateCreated) {
        this.discountEventDateCreated = discountEventDateCreated;
    }

    public void setMonthsSinceCreation(int monthsSinceCreation) {
        this.monthsSinceCreation = monthsSinceCreation;
    }

    // Kết nối csdl
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

    // Xử lý show dữ liệu
    public static List<ProductEvent> getAllProductEventsWithMonthsSinceCreation() {
        List<ProductEvent> productEvents = new ArrayList<>();
        try {
            Connection conn = getConnection();
            String query = "SELECT * FROM category_discount_event";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ProductEvent productEvent = new ProductEvent();
                productEvent.setDiscountEventId(resultSet.getInt("discount_event_id"));
                productEvent.setCategoryProductId(resultSet.getInt("category_product_id"));
                productEvent.setDiscountEventName(resultSet.getString("discount_event_name"));
                productEvent.setDiscountEventDescription(resultSet.getString("discount_event_description"));
                productEvent.setDiscountEventImg(resultSet.getString("discount_event_img"));
                productEvent.setDiscountEventDateCreated(resultSet.getDate("discount_event_dateCreated"));

                // Tính số tháng từ ngày tạo đến hiện tại và đặt vào thuộc tính mới
                productEvent.setMonthsSinceCreation(productEvent.monthsSinceCreation());

                productEvents.add(productEvent);
            }
            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productEvents;
    }

    // Thêm phương thức để tính số tháng từ một ngày đến hiện tại
    public int monthsSinceCreation() {
        if (discountEventDateCreated == null) {
            return 0;
        }
        return monthsSince(discountEventDateCreated);
    }

    public static int monthsSince(Date startDate) {
        if (startDate == null) {
            return 0;
        }

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);

        Calendar endCalendar = Calendar.getInstance();

        int diffYears = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        int diffMonths = endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
        int monthsSince = diffYears * 12 + diffMonths;

        return monthsSince;
    }

}
