<?php

 $con = mysqli_connect("localhost", "id5400478_borrow", "4201158833", "id5400478_borrowme");
 
 $Borrower_email = $_POST["Borrower_email"];

 
 $statement = mysqli_prepare($con, "SELECT Borrow_ID, Lender_email FROM BORROW_ITEM_TABLE WHERE Borrower_email = ? AND Status = 'accepted'  ");
    mysqli_stmt_bind_param($statement, "s", $Borrower_email);
    mysqli_stmt_execute($statement);
 
 
mysqli_stmt_store_result($statement);
mysqli_stmt_bind_result($statement, $Borrow_ID , $Lender_email);
 
 $products = array(); 
 
 //traversing through all the result 
 while($statement->fetch()){
 $temp = array();
 $temp['Borrow_ID'] = $Borrow_ID; 
 $temp['Lender_email'] = $Lender_email; 
 array_push($products, $temp);
 }
 
 //displaying the result in json format 
 echo json_encode($products);
?>