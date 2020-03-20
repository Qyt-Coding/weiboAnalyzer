package com.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

public class DataSourceUtils {

	public static Connection conn;
	public static PreparedStatement ptmt;
	public static String driver = "com.mysql.jdbc.Driver";
	// URL指向要访问的数据库名scutcs
	public static String url = "jdbc:mysql://127.0.0.1:3306/zplweibo?useUnicode=true&characterEncoding=UTF-8";
	// MySQL配置时的用户名
	public static String user = "root";
	// Java连接MySQL配置时的密码
	public static String password = "123456";

	public static Connection getConnection() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	public static PreparedStatement getPreparedStatement(String sql) {
		try {
			ptmt = conn.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ptmt;
	}

	/**
	 * PreparedStatement close
	 */
	public static void ptmtClose() {
		if (ptmt != null) {
			try {
				ptmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void connClose() {
		if(conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
