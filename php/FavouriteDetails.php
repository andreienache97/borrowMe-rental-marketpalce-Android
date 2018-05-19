<?php
	//Create Connection
	$con = mysqli_connect("localhost", "id5400478_borrow", "4201158833", "id5400478_borrowme");

	//Get User Information
	$email = $_POST["email"];
	$id = $_POST["item_id"];
	
	//Insert into Favourites Table
	$statement = mysqli_prepare($con,"SELECT i.email as LenderEmail,i.name,i.price,i.AvailableDate,i.UnavailableDate FROM ITEM_TABLE i JOIN FAVOURITES_TABLE f ON i.item_id = f.item_id WHERE f.email = ? AND i.item_id = ?");
    mysqli_stmt_bind_param($statement,"si", $email,$id);
	mysqli_stmt_execute($statement);
	
	//Bind Results
	mysqli_stmt_bind_result($statement,$lenderEmail,$name,$price,$availabledate,$unavailabledate);
	
	//Send Data Back
	$response = array();
	
	$response["success"] = false;
	
	while(mysqli_stmt_fetch($statement))
	{
		$response["success"] = true;
		$response['email'] = $lenderEmail;
		$response['name'] = $name;
		$response['price'] = $price;
		$response['AvailableDate'] = $availabledate;
		$response['UnavailableDate'] = $unavailabledate;
	}

	//Send Response to the App
	echo json_encode($response);

?>