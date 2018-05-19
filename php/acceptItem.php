<?php

	//Created Andrei Enache
	//Updated by Sebastian Arocha

	//Create connection
    $con = mysqli_connect("localhost", "id5400478_borrow", "4201158833", "id5400478_borrowme");

	//Get Item ID
    $item_id = $_POST["item_id"];
    
	//Show item as Approved by the admin
    $statement = mysqli_prepare($con, "UPDATE ITEM_TABLE SET CheckedByAdmin = '1' WHERE item_id = ?" );
  	mysqli_stmt_bind_param($statement,"i", $item_id);
	mysqli_stmt_execute($statement);   

	//Remove item from REPORTED_ITEM table
	$statement2 = mysqli_prepare($con, "DELETE FROM REPORTED_ITEM WHERE item_id = ?" );
  	mysqli_stmt_bind_param($statement2,"i", $item_id);
	mysqli_stmt_execute($statement2);
		
	//Send response
	$response = array();
	$response["success"] = true;
	
    echo json_encode($response);

?>