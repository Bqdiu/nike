package com.example.nike;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nike.Model.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.transform.Result;

public class GetConnectionExample extends AppCompatActivity {
    Connection connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_connection_example);
        Button btnGetData = findViewById(R.id.btnGetData);
        TextView tvResult = findViewById(R.id.tvConnect);

        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBConnection dbConnection = new DBConnection();
                connection = dbConnection.connectionClass();
                if(dbConnection!= null){
                    String query = "Select * from product";
                    try {
                        Statement smt = connection.createStatement();
                        ResultSet set = smt.executeQuery(query);
                        while (set.next()){
                            tvResult.setText(set.getString(2));
                        }
                        connection.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        });
    }
}