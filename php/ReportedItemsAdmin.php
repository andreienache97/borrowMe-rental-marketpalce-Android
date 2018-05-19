<?php

	//Create Connection
	$con = mysqli_connect("localhost", "id5400478_borrow", "4201158833", "id5400478_borrowme");
	
	//Execute the query
	
	//Query to get all the data of all the items that have been reported
	$statement = mysqli_prepare($con,"SELECT i.item_id,i.email,i.name,i.price,i.Description, i.Department,r.reporterEmail,r.reason FROM ITEM_TABLE i JOIN REPORTED_ITEM r 
	WHERE i.CheckedByAdmin = 3 AND i.item_id = r.item_id");
	mysqli_stmt_execute($statement);
	
	
	//Bind Results
	mysqli_stmt_bind_result($statement,$item_id,$email,$name,$price,$description,$department,$reportEmail,$reportReason);
	
	//Send Data Back
	$reportedProducts = array();
	
	while(mysqli_stmt_fetch($statement))
	{
		$temp = array();
		$temp['item_id'] = $item_id;
		$temp['email'] = $email;
		$temp['name'] = $name;
		$temp['price'] = $price;
		$temp['Description'] = $description;
		$temp['Department'] = $department;
		$temp['reporterEmail'] = $reportEmail;
		$temp['reason'] = $reportReason;
		array_push($reportedProducts,$temp);
	}
	
	//Send Reported Products
	echo json_encode($reportedProducts);

?>