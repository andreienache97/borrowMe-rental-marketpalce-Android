<?php

 $con = mysqli_connect("localhost", "id5400478_borrow", "4201158833", "id5400478_borrowme");
 
 $Lender_email = $_POST["Lender_email"];

 
 $statement = mysqli_prepare($con, "SELECT Borrow_ID, Borrower_email FROM BORROW_ITEM_TABLE WHERE Lender_email = ? AND Status = 'pending'  ");
    mysqli_stmt_bind_param($statement, "s", $Lender_email);
    mysqli_stmt_execute($statement);
 
 
mysqli_stmt_store_result($statement);
mysqli_stmt_bind_result($statement, $Borrow_ID , $Borrower_email);
 
 $products = array(); 
 
 //traversing through all the result 
 while($statement->fetch()){
 $temp = array();
 $temp['Borrow_ID'] = $Borrow_ID; 
 $temp['Borrower_email'] = $Borrower_email; 
 array_push($products, $temp);
 }
 
 //displaying the result in json format 
 echo json_encode($products);
?>