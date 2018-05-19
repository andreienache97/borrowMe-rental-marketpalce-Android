<?php
	$con = mysqli_connect("localhost", "id5400478_borrow", "4201158833", "id5400478_borrowme");
	
	$item_id = $_POST["item_id"];
	$reporterEmail = $_POST["reporterEmail"];
	$reason = $_POST["reason"];
	
	$statement = mysqli_prepare($con, "INSERT INTO REPORTED_ITEM (item_id, reporterEmail,reason) VALUES (?, ?,?)");
    mysqli_stmt_bind_param($statement,"iss", $item_id, $reporterEmail,$reason);
    mysqli_stmt_execute($statement);
	
	$statement2 = mysqli_prepare($con, "UPDATE ITEM_TABLE SET CheckedByAdmin='3' WHERE item_id = ?");
    mysqli_stmt_bind_param($statement2,"i", $item_id);
    mysqli_stmt_execute($statement2);
	
	$response = array();

    $response["success"] = true;
    $response["reporterEmail"] = $reporterEmail;


    echo json_encode($response);
?>