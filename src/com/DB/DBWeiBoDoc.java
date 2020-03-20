package com.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import com.bean.WeiboDoc;

public class DBWeiBoDoc {

	public void insertWeiBoDoc(WeiboDoc weiboDoc) throws SQLException {
		Connection conn;
		PreparedStatement ptmt;
		String driver = "com.mysql.jdbc.Driver";
		// URL指向要访问的数据库名scutcs
		String url = "jdbc:mysql://127.0.0.1:3306/zplweibo?useUnicode=true&characterEncoding=UTF-8";
		// MySQL配置时的用户名
		String user = "root";
		// Java连接MySQL配置时的密码
		String password = "123456";
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 连续数据库
		conn = DriverManager.getConnection(url, user, password);
		String cmdString = "insert into weiboDoc values(?,?,?,?,?,?,?,?,?,?)";
		ptmt = conn.prepareStatement(cmdString);

		ptmt.setString(1, weiboDoc.getCreatedAt());
		ptmt.setString(2, weiboDoc.getId());
		ptmt.setString(3, weiboDoc.getText());
		// weiboDoc.getTextLength()==null?0:weiboDoc.getTextLength();
		if (weiboDoc.getTextLength() != null) {
			ptmt.setInt(4, weiboDoc.getTextLength());
		} else {
			ptmt.setInt(4, 0);
		}

		ptmt.setString(5, weiboDoc.getOriginalPic());

		if (weiboDoc.getRepostsCount() != null) {
			ptmt.setInt(6, weiboDoc.getRepostsCount());
		} else {
			ptmt.setInt(6, 0);
		}

		if (weiboDoc.getCommentsCount() != null) {
			ptmt.setInt(7, weiboDoc.getCommentsCount());
		} else {
			ptmt.setInt(7, 0);
		}

		if (weiboDoc.getAttitudesCount() != null) {
			ptmt.setInt(8, weiboDoc.getAttitudesCount());
		} else {
			ptmt.setInt(8, 0);
		}

		if (weiboDoc.getUser() != null) {
			ptmt.setString(10, weiboDoc.getUser().getId());
		} else {
			ptmt.setString(10, null);
		}
		if (weiboDoc.getRetweetedStatus() != null) {
			ptmt.setString(9, weiboDoc.getRetweetedStatus().getId());
		} else {
			ptmt.setString(9, null);
		}

		// 执行
		ptmt.executeUpdate();
		ptmt.close();
		conn.close();
	}

	public static void select() throws SQLException {
		Connection conn;
		PreparedStatement ptmt;
		String driver = "com.mysql.jdbc.Driver";
		// URL指向要访问的数据库名scutcs
		String url = "jdbc:mysql://127.0.0.1:3306/zplweibo";
		// MySQL配置时的用户名
		String user = "root";
		// Java连接MySQL配置时的密码
		String password = "123456";
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 连续数据库
		conn = DriverManager.getConnection(url, user, password);
		String cmdString = "select * from weiboDoc";
		ptmt = conn.prepareStatement(cmdString);
		ResultSet rs = ptmt.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString("text") + " 年龄：" + rs.getInt("created_at"));
		}
		ptmt.close();
		conn.close();
	}

	public static void main(String args[]) throws SQLException {
		DBWeiBoDoc.select();
	}

	@Test
	public void test1() {
		Connection conn = DataSourceUtils.getConnection();
		String sql = "select * from weiboDoc";
		PreparedStatement ptmt = DataSourceUtils.getPreparedStatement(sql);
		ResultSet rs;
		try {
			rs = ptmt.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString("text") + " 年龄：" + rs.getInt("created_at"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtils.ptmtClose();
			DataSourceUtils.connClose();
		}
	}
}
