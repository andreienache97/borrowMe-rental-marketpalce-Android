<?php

    $con = mysqli_connect("localhost", "id5400478_borrow", "4201158833", "id5400478_borrowme");


    $Borrow_ID = $_POST["Borrow_ID"];
    $item_id = $_POST["item_id"];
    $Days = $_POST["Days"];
    $deposit;
    $price;
    $response = array();


       $statement2 = mysqli_prepare($con, "SELECT deposit,price FROM `ITEM_TABLE` WHERE item_id = ?" );
       mysqli_stmt_bind_param($statement2,"s", $item_id);
       mysqli_stmt_execute($statement2);
       mysqli_stmt_store_result($statement2);
       mysqli_stmt_bind_result($statement2, $deposit,$price);

             while(mysqli_stmt_fetch($statement2)){
                 $deposit = $deposit;
                 $price = $price;
             }


     $statement6 = mysqli_prepare($con, "SELECT balance FROM BALANCE_TABLE WHERE email = ?" );
     mysqli_stmt_bind_param($statement6,"s", $BorrowerEmail);
     mysqli_stmt_execute($statement6);
     mysqli_stmt_store_result($statement6);
     mysqli_stmt_bind_result($statement6, $balance);
     while(mysqli_stmt_fetch($statement6)){
      $balance = $balance;
     }

      $Total = $deposit + ($price*$Days);

     if($balance - $Total < 0 ){
        $response["success"] = false;

     }else{

           $statement = mysqli_prepare($con, "UPDATE BORROW_ITEM_TABLE SET Status = 'accepted' WHERE Borrow_ID = ?" );
         	mysqli_stmt_bind_param($statement,"s", $Borrow_ID);
       		mysqli_stmt_execute($statement);

           $statement1 = mysqli_prepare($con, "UPDATE ITEM_TABLE SET Status = '1' WHERE item_id = ?" );
         	mysqli_stmt_bind_param($statement1,"s", $item_id);
       	   mysqli_stmt_execute($statement1);


         $statement3 = mysqli_prepare($con, "SELECT Lender_email,Borrower_email FROM BORROW_ITEM_TABLE WHERE Borrow_ID = ?" );
         mysqli_stmt_bind_param($statement3,"s", $Borrow_ID);
         mysqli_stmt_execute($statement3);
         mysqli_stmt_store_result($statement3);
         mysqli_stmt_bind_result($statement3, $LenderEmail,$BorrowerEmail);

         $response = array();
         while(mysqli_stmt_fetch($statement3)){
           $LenderEmail = $LenderEmail;
           $BorrowerEmail = $BorrowerEmail;
         }
         $statement4 = mysqli_prepare($con, "UPDATE BALANCE_TABLE  SET balance = balance + (?) WHERE email = ?" );
         mysqli_stmt_bind_param($statement4,"is",$Total, $LenderEmail);
          mysqli_stmt_execute($statement4);



       $statement5 = mysqli_prepare($con, "UPDATE BALANCE_TABLE  SET balance = balance - (?) WHERE email = ?" );
          mysqli_stmt_bind_param($statement5,"is",$Total, $BorrowerEmail);
           mysqli_stmt_execute($statement5);

      $response["success"] = true;

     }






    echo json_encode($response);

?>
