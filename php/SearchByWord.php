<?php
	//Create Connection
	$con = mysqli_connect("localhost", "id5400478_borrow", "4201158833", "id5400478_borrowme");
	
	$searchWord = $_POST["searchWord"];
	$searchPattern = "%". $searchWord. "%";
	
	//Create & Execute Statement
	$statement = mysqli_prepare($con,"SELECT item_id, name, price FROM ITEM_TABLE WHERE (LOWER(name) OR LOWER(description) LIKE LOWER(?)) AND CheckedByAdmin = 1 AND Status= 0 ");
	mysqli_stmt_bind_param($statement,"s",$searchPattern);
	mysqli_stmt_execute($statement);
		
	//Bind Results
	mysqli_stmt_bind_result($statement,$item_id,$name,$price);
	
	//Send Data Back
	$searchedProducts = array();
	
	while(mysqli_stmt_fetch($statement))
	{
		$temp = array();
		$temp['item_id'] = $item_id;
		$temp['name'] = $name;
		$temp['price'] = $price;
		array_push($searchedProducts,$temp);
	}
	
	//Send Reported Products
	echo json_encode($searchedProducts);
?>