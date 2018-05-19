<?php

 $con = mysqli_connect("localhost", "id5400478_borrow", "4201158833", "id5400478_borrowme");
 
 $email = $_POST["email"];

 
 $statement = mysqli_prepare($con, "SELECT item_id, name FROM ITEM_TABLE WHERE CheckedByAdmin = '1' AND email = ?  ");
    mysqli_stmt_bind_param($statement, "s", $email);
    mysqli_stmt_execute($statement);
 
 
mysqli_stmt_store_result($statement);
mysqli_stmt_bind_result($statement, $item_id , $name);
 
 $products = array(); 
 
 //traversing through all the result 
 while($statement->fetch()){
 $temp = array();
 $temp['item_id'] = $item_id; 
 $temp['name'] = $name; 
 array_push($products, $temp);
 }
 
 //displaying the result in json format 
 echo json_encode($products);
?>