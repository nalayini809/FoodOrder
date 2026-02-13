package com.wipro.food.service;

import java.util.Date;
import java.util.List;

import com.wipro.food.bean.FoodOrderBean;
import com.wipro.food.dao.FoodOrderDAO;
import com.wipro.food.util.InvalidInputException;

public class Administrator {

    private FoodOrderDAO dao = new FoodOrderDAO();   // ✅ ADD THIS

    public String addRecord(FoodOrderBean bean) {

        try {

            if (bean == null || bean.getCustomerName() == null || bean.getOrderDate() == null) {
                throw new InvalidInputException();
            }
            if (bean.getCustomerName().length() < 2) {
                return "INVALID CUSTOMER NAME";
            }
      
            if (bean.getQuantity() < 1 || bean.getPrice() <= 0) {
        
            	return "INVALID ORDER DETAILS";
            }

            if (dao.recordExists(bean.getCustomerName(), bean.getOrderDate())) {
                return "ALREADY EXISTS";
            }

            String orderId = dao.generateOrderID(bean.getCustomerName(), bean.getOrderDate());
            bean.setOrderId(orderId);

            return dao.createRecord(bean);

        } catch (InvalidInputException e) {
            return "INVALID INPUT";
        }
    }

    public FoodOrderBean viewRecord(String customerName, Date orderDate) {
        return dao.fetchRecord(customerName, orderDate);
    }

    public List<FoodOrderBean> viewAllRecords() {
        return dao.fetchAllRecords();
    }
}
