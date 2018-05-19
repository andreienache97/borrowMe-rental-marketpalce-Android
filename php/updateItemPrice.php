<?php

    $con = mysqli_connect("localhost", "id5400478_borrow", "4201158833", "id5400478_borrowme");


    $name = $_POST["name"];
    $Description = $_POST["Description"];
    $item_id = $_POST["item_id"];
 


    $statement = mysqli_prepare($con, "UPDATE ITEM_TABLE SET  name = ?, Description= ? WHERE item_id = ?" );
  	mysqli_stmt_bind_param($statement,"ssi",  $name, $Description, $item_id );
		mysqli_stmt_execute($statement);


		$response = array();
		$response["success"] = true;


    echo json_encode($response);

?>
