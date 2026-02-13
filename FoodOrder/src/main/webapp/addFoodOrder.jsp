<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>Add Food Order</h2>

<form action="MainServlet" method="post">
<input type="hidden" name="operation" value="newRecord">

Customer Name: <input type="text" name="customerName"><br><br>
Food Item: <input type="text" name="foodItem"><br><br>
Order Date: <input type="date" name="orderDate"><br><br>
Quantity: <input type="number" name="quantity"><br><br>
Price: <input type="text" name="price"><br><br>
Remarks: <input type="text" name="remarks"><br><br>

<input type="submit" value="Add">
</form>
</body>
</html>