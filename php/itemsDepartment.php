<?php

 $con = mysqli_connect("localhost", "id5400478_borrow", "4201158833", "id5400478_borrowme");
 
 $dep = $_POST["Department"];

 
 $statement = mysqli_prepare($con, "SELECT item_id, name, price FROM ITEM_TABLE WHERE CheckedByAdmin = '1' AND Status = '0' AND Department = ?  ");
    mysqli_stmt_bind_param($statement, "s", $dep);
    mysqli_stmt_execute($statement);
 
 
mysqli_stmt_store_result($statement);
mysqli_stmt_bind_result($statement, $item_id , $name, $price);
 
 $products = array(); 
 
 //traversing through all the result 
 while($statement->fetch()){
 $temp = array();
 $temp['item_id'] = $item_id; 
 $temp['name'] = $name; 
 $temp['price'] = $price;
 array_push($products, $temp);
 }
 
 //displaying the result in json format 
 echo json_encode($products);
?>