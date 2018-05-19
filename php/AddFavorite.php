<?php
	//Create Connection
	$con = mysqli_connect("localhost", "id5400478_borrow", "4201158833", "id5400478_borrowme");

	//Get User Information
	$item_id = $_POST["item_id"];
	$email = $_POST["email"];
	
	//Insert into Favourites Table
	$statement = mysqli_prepare($con,"INSERT INTO FAVOURITES_TABLE (item_id,email) VALUES (?,?)");
    mysqli_stmt_bind_param($statement,"is", $item_id, $email);
	mysqli_stmt_execute($statement);
	
	//Send Response back to the app
	$response = array();
    $response["success"] = true;
	
	echo json_encode($response);
	
	
?>