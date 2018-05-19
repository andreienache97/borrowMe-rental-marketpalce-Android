<?php

 $con = mysqli_connect("localhost", "id5400478_borrow", "4201158833", "id5400478_borrowme");
 
 $Like_email = $_POST["Like_email"];

 
 $statement = mysqli_prepare($con, "SELECT f.item_ID, i.name FROM FAVOURITES_TABLE f JOIN ITEM_TABLE i ON f.item_id = i.item_id WHERE f.email = ?");
    mysqli_stmt_bind_param($statement, "s", $Like_email);
    mysqli_stmt_execute($statement);
 
 
mysqli_stmt_store_result($statement);
mysqli_stmt_bind_result($statement, $item_ID,$name);
 
 $products = array(); 
 
 //traversing through all the result 
 while($statement->fetch()){
 $temp = array();
 $temp['item_id'] = $item_ID; 
 $temp['name'] = $name; 
 $temp['Like_email'] = $Like_email; 
 array_push($products, $temp);
 }
 
 //displaying the result in json format 
 echo json_encode($products);
?>