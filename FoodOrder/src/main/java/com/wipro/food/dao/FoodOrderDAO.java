package com.wipro.food.dao;

import java.sql.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.wipro.food.bean.FoodOrderBean;
import com.wipro.food.util.DBUtil;

public class FoodOrderDAO {

    public String createRecord(FoodOrderBean bean) {
        String status = "FAIL";
        try (Connection con = DBUtil.getDBConnection()) {

            String sql = "INSERT INTO FOODORDER_TB VALUES(?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, bean.getOrderId());
            ps.setString(2, bean.getCustomerName());
            ps.setString(3, bean.getFoodItem());
            ps.setDate(4, new java.sql.Date(bean.getOrderDate().getTime()));
            ps.setInt(5, bean.getQuantity());
            ps.setDouble(6, bean.getPrice());
            ps.setString(7, bean.getRemarks());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                status = bean.getOrderId();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public FoodOrderBean fetchRecord(String customerName, Date orderDate)
 {
        FoodOrderBean bean = null;

        try (Connection con = DBUtil.getDBConnection()) {

            String sql = "SELECT * FROM FOODORDER_TB WHERE CUSTOMERNAME=? AND ORDER_DATE=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setDate(2, new java.sql.Date(orderDate.getTime()));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                bean = new FoodOrderBean();
                bean.setOrderId(rs.getString("ORDERID"));
                bean.setCustomerName(rs.getString("CUSTOMERNAME"));
                bean.setFoodItem(rs.getString("FOODITEM"));
                bean.setOrderDate(rs.getDate("ORDER_DATE"));
                bean.setQuantity(rs.getInt("QUANTITY"));
                bean.setPrice(rs.getDouble("PRICE"));
                bean.setRemarks(rs.getString("REMARKS"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    public String generateOrderID(String customerName, Date orderDate)
 {

        String id = "";

        try (Connection con = DBUtil.getDBConnection()) {

            DateFormat f = new SimpleDateFormat("yyyyMMdd");
            String datePart = f.format(orderDate);

            String namePart = customerName.substring(0, 2).toUpperCase();

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT FOODORDER_SEQ.NEXTVAL FROM DUAL");

            if (rs.next()) {
                int seq = rs.getInt(1);
                id = datePart + namePart + seq;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public boolean recordExists(String customerName, java.util.Date date) {
        boolean exists = false;

        try (Connection con = DBUtil.getDBConnection()) {

            String sql = "SELECT ORDERID FROM FOODORDER_TB WHERE CUSTOMERNAME=? AND ORDER_DATE=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setDate(2, new java.sql.Date(date.getTime()));

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                exists = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return exists;
    }

    public List<FoodOrderBean> fetchAllRecords()
 {

        List<FoodOrderBean> list = new ArrayList<>();

        try (Connection con = DBUtil.getDBConnection()) {

            String sql = "SELECT * FROM FOODORDER_TB";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                FoodOrderBean bean = new FoodOrderBean();
                bean.setOrderId(rs.getString("ORDERID"));
                bean.setCustomerName(rs.getString("CUSTOMERNAME"));
                bean.setFoodItem(rs.getString("FOODITEM"));
                bean.setOrderDate(rs.getDate("ORDER_DATE"));
                bean.setQuantity(rs.getInt("QUANTITY"));
                bean.setPrice(rs.getDouble("PRICE"));
                bean.setRemarks(rs.getString("REMARKS"));
                list.add(bean);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
