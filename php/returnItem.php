<?php

    $con = mysqli_connect("localhost", "id5400478_borrow", "4201158833", "id5400478_borrowme");


    $Borrow_ID = $_POST["Borrow_ID"];
    $item_id = $_POST["item_id"];



    $statement = mysqli_prepare($con, "UPDATE BORROW_ITEM_TABLE SET Status = 'borrowed' WHERE Borrow_ID = ?" );
  	mysqli_stmt_bind_param($statement,"s", $Borrow_ID);
		mysqli_stmt_execute($statement);
	//cascade query

    $statement1 = mysqli_prepare($con, "UPDATE ITEM_TABLE SET Status = '0' WHERE item_id = ?" );
  	mysqli_stmt_bind_param($statement1,"s", $item_id);
	mysqli_stmt_execute($statement1);

  $statement2 = mysqli_prepare($con, "SELECT deposit FROM `ITEM_TABLE` WHERE item_id = ?" );
  mysqli_stmt_bind_param($statement2,"s", $item_id);
  mysqli_stmt_execute($statement2);
  mysqli_stmt_store_result($statement2);
  mysqli_stmt_bind_result($statement2, $deposit);

  while(mysqli_stmt_fetch($statement2)){
      $deposit = $deposit;
  }

  $statement3 = mysqli_prepare($con, "SELECT Lender_email,Borrower_email FROM BORROW_ITEM_TABLE WHERE Borrow_ID = ?" );
  mysqli_stmt_bind_param($statement3,"s", $Borrow_ID);
  mysqli_stmt_execute($statement3);
  mysqli_stmt_store_result($statement3);
  mysqli_stmt_bind_result($statement3, $LenderEmail,$BorrowerEmail);

  while(mysqli_stmt_fetch($statement3)){
    $LenderEmail = $LenderEmail;
    $BorrowerEmail = $BorrowerEmail;
  }

  $statement4 = mysqli_prepare($con, "UPDATE BALANCE_TABLE  SET balance = balance + (?) WHERE email = ?" );
  mysqli_stmt_bind_param($statement4,"is",$deposit, $BorrowerEmail);
   mysqli_stmt_execute($statement4);

   $statement5 = mysqli_prepare($con, "UPDATE BALANCE_TABLE  SET balance = balance - (?) WHERE email = ?" );
      mysqli_stmt_bind_param($statement5,"is",$deposit, $LenderEmail);
       mysqli_stmt_execute($statement5);


		$response = array();
		$response["success"] = true;


    echo json_encode($response);

?>
