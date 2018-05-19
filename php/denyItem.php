<?php

    $con = mysqli_connect("localhost", "id5400478_borrow", "4201158833", "id5400478_borrowme");


    $item_id = $_POST["item_id"];
    


	
    $statement = mysqli_prepare($con, "UPDATE ITEM_TABLE SET CheckedByAdmin = '2' WHERE item_id = ?" );
  	mysqli_stmt_bind_param($statement,"s", $item_id);
	mysqli_stmt_execute($statement);
	
	//cascade query
	$statement1 = mysqli_prepare($con, "UPDATE BORROW_ITEM_TABLE SET Status = 'denied' WHERE item_id = ?" );
  	mysqli_stmt_bind_param($statement1,"s", $item_id);
	mysqli_stmt_execute($statement1);
	
	/*
	$statement = mysqli_prepare($con, "DELETE FROM ITEM_TABLE WHERE item_id = ?" );
  	mysqli_stmt_bind_param($statement,"i", $item_id);
	mysqli_stmt_execute($statement);*/

		$response = array();
		$response["success"] = true;


    echo json_encode($response);

?>
