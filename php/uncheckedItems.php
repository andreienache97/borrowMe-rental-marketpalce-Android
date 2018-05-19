<?php

 //database constants
 define('DB_HOST', 'localhost');
 define('DB_USER', 'id5400478_borrow');
 define('DB_PASS', '4201158833');
 define('DB_NAME', 'id5400478_borrowme');
 
 //connecting to database and getting the connection object
 $conn = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);
 
 //Checking if any error occured while connecting
 if (mysqli_connect_errno()) {
 echo "Failed to connect to MySQL: " . mysqli_connect_error();
 die();
 }
 
 //creating a query
 $stmt = $conn->prepare("SELECT item_id, email, name, price, Description, Department FROM ITEM_TABLE WHERE CheckedByAdmin = '0';");
 
 //executing the query 
 $stmt->execute();
 
 //binding results to the query 
 $stmt->bind_result($item_id ,$email, $name, $price, $Description, $Department);
 
 $products = array(); 
 
 //traversing through all the result 
 while($stmt->fetch()){
 $temp = array();
 $temp['item_id'] = $item_id; 
 $temp['email'] = $email; 
 $temp['name'] = $name; 
 $temp['price'] = $price; 
 $temp['Description'] = $Description; 
 $temp['Department'] = $Department; 
 array_push($products, $temp);
 }
 
 //displaying the result in json format 
 echo json_encode($products);
?>