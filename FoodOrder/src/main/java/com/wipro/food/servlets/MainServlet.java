package com.wipro.food.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wipro.food.bean.FoodOrderBean;
import com.wipro.food.service.Administrator;
import javax.servlet.annotation.WebServlet;

@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {

	private Administrator admin = new Administrator();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String operation = req.getParameter("operation");

        try {

            if ("newRecord".equals(operation)) {

                String status = addRecord(req);

                if (status.equals("FAIL") || status.equals("INVALID INPUT")
                        || status.equals("INVALID CUSTOMER NAME")
                        || status.equals("INVALID ORDER DETAILS")
                        || status.equals("ALREADY EXISTS")) {
                    resp.sendRedirect("error.html");
                } else {
                    resp.sendRedirect("success.html");
                }

            } else if ("viewRecord".equals(operation)) {

                FoodOrderBean bean = viewRecord(req);

                if (bean == null) {
                    req.setAttribute("message", "No matching records exists! Please try again!");
                } else {
                    req.setAttribute("bean", bean);
                }

                RequestDispatcher rd = req.getRequestDispatcher("displayFoodOrder.jsp");
                rd.forward(req, resp);

            } else if ("viewAllRecords".equals(operation)) {

                List<FoodOrderBean> list = viewAllRecords(req);

                if (list.isEmpty()) {
                    req.setAttribute("message", "No records available!");
                } else {
                    req.setAttribute("list", list);
                }

                RequestDispatcher rd = req.getRequestDispatcher("displayAllFoodOrders.jsp");
                rd.forward(req, resp);
            }

        } catch (Exception e) {
            resp.sendRedirect("error.html");
        }
    }

    public String addRecord(HttpServletRequest request) throws Exception {

        FoodOrderBean bean = new FoodOrderBean();

        bean.setCustomerName(request.getParameter("customerName"));
        bean.setFoodItem(request.getParameter("foodItem"));

        Date date = new SimpleDateFormat("yyyy-MM-dd")
                .parse(request.getParameter("orderDate"));
        bean.setOrderDate(date);

        bean.setQuantity(Integer.parseInt(request.getParameter("quantity")));
        bean.setPrice(Double.parseDouble(request.getParameter("price")));
        bean.setRemarks(request.getParameter("remarks"));

        return admin.addRecord(bean);
    }

    public FoodOrderBean viewRecord(HttpServletRequest request) throws Exception {

        String name = request.getParameter("customerName");
        Date date = new SimpleDateFormat("yyyy-MM-dd")
                .parse(request.getParameter("orderDate"));

        return admin.viewRecord(name, date);
    }

    public List<FoodOrderBean> viewAllRecords(HttpServletRequest request) {
        return admin.viewAllRecords();
    }
}
