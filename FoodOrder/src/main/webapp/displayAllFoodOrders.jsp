<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*,com.wipro.food.bean.FoodOrderBean"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
String message = (String)request.getAttribute("message");
List<FoodOrderBean> list = (List<FoodOrderBean>)request.getAttribute("list");

if(message != null){
    out.println(message);
}else if(list != null){
    for(FoodOrderBean bean : list){
%>

<hr>
Order ID: <%= bean.getOrderId() %><br>
Customer Name: <%= bean.getCustomerName() %><br>
Food Item: <%= bean.getFoodItem() %><br>
Order Date: <%= bean.getOrderDate() %><br>
Quantity: <%= bean.getQuantity() %><br>
Price: <%= bean.getPrice() %><br>
Remarks: <%= bean.getRemarks() %><br>

<%
    }
}
%>

</body>
</html>