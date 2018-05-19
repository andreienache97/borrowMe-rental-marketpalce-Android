<?php

    $con = mysqli_connect("localhost", "id5400478_borrow", "4201158833", "id5400478_borrowme");


    $Borrow_ID = $_POST["Borrow_ID"];



    $statement = mysqli_prepare($con, "UPDATE BORROW_ITEM_TABLE SET Status = 'denied' WHERE Borrow_ID = ?" );
  	mysqli_stmt_bind_param($statement,"s", $Borrow_ID);
		mysqli_stmt_execute($statement);

    $statement2 = mysqli_prepare($con, "SELECT item_id FROM `BORROW_ITEM_TABLE` WHERE Borrow_ID = ?" );
    mysqli_stmt_bind_param($statement2,"s", $Borrow_ID);
    mysqli_stmt_execute($statement2);
    mysqli_stmt_store_result($statement2);
    mysqli_stmt_bind_result($statement2, $item_id);

    while(mysqli_stmt_fetch($statement2)){
        $item_id = $item_id;
    }

    $statement1 = mysqli_prepare($con, "UPDATE ITEM_TABLE SET Status = '0' WHERE item_id = ?" );
    mysqli_stmt_bind_param($statement1,"s", $item_id);
  mysqli_stmt_execute($statement1);


		$response = array();
		$response["success"] = true;


    echo json_encode($response);

?>
