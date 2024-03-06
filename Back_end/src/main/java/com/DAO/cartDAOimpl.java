package com.DAO;

import java.sql.Connection;
import java.util.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

//import com.entity.BookDtls;
import com.entity.Cart;

public class cartDAOimpl implements cartDAO{
	
	private Connection conn;
	
	public cartDAOimpl(Connection conn) {
		this.conn = conn;
	}
	
	
	public boolean addcart(Cart c) {
		boolean f = false;
		
		try {
			String sqlString = "insert into cart(bid,uid,bookName,author,price,total_price) values(?,?,?,?,?,?)";
			PreparedStatement pStatement = conn.prepareStatement(sqlString);
			pStatement.setInt(1, c.getBid());
			pStatement.setInt(2, c.getUserId());
			pStatement.setString(3, c.getBookName());
			pStatement.setString(4, c.getAuthor());
			pStatement.setDouble(5, c.getPrice());
			pStatement.setDouble(6, c.getTotalPrice());
			
			int i = pStatement.executeUpdate();
			if(i == 1) {
				f = true;
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}

		return f;
	}


	public List<Cart> getBookByUser(int userId) {
		
		List<Cart> list = new ArrayList<Cart>();
		Cart c = null;
		double totalPrice = 0;
		try {
			
			String sqlString = "select * from cart where uid=?";
			PreparedStatement pStatement = conn.prepareStatement(sqlString);
			pStatement.setInt(1, userId);
			ResultSet rs = pStatement.executeQuery(); 
			while(rs.next()){
				c = new Cart();
				c.setCid(rs.getInt(1));
				c.setBid(rs.getInt(2));
				c.setUserId(rs.getInt(3));
				c.setBookName(rs.getString(4));
				c.setAuthor(rs.getString(5));
				c.setPrice(rs.getDouble(6));
				
				totalPrice = totalPrice +rs.getDouble(7);
				c.setTotalPrice(totalPrice);
				
				list.add(c);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return list;
	}


	public boolean deleteBook(int cid, int bid, int uid) {
	    boolean success = false;
	    PreparedStatement ps=null;
	    try {
	        String sql = "DELETE FROM cart WHERE bid=? AND uid=? AND cid=?";
	      ps = conn.prepareStatement(sql);
	        ps.setInt(1, bid);
	        ps.setInt(2, uid);
	        ps.setInt(3, cid);

	        int rowsAffected = ps.executeUpdate();

	        if (rowsAffected == 1) {
	            success = true;
	        }
	    } catch (SQLException e) {
	        // Log the exception or handle it appropriately
	        e.printStackTrace();
	        // You might want to throw a custom exception or return false here
	    } finally {
	        // Close resources in a finally block to ensure they are always closed
	        try {
	            if (ps != null) {
	                ps.close();
	            }
	        } catch (SQLException e) {
	            // Log the exception or handle it appropriately
	            e.printStackTrace();
	        }
	    }

	    return success;
	}


	
	
}
