<?php

    $con = mysqli_connect("localhost", "id5400478_borrow", "4201158833", "id5400478_borrowme");


    $item_id = $_POST["item_id"];

    $statement2 = mysqli_prepare($con, "SELECT AvailableDate, UnavailableDate FROM `ITEM_TABLE` WHERE item_id = ?" );
    mysqli_stmt_bind_param($statement2,"s", $item_id);
    mysqli_stmt_execute($statement2);
    mysqli_stmt_store_result($statement2);
    mysqli_stmt_bind_result($statement2, $DateA,$DateU);

    $response = array();
		$response["success"] = false;
          while(mysqli_stmt_fetch($statement2)){
              $response["success"] = true;
              $response["ADate"] = $DateA;
              $response["UDate"] = $DateU;
          }


    echo json_encode($response);

?>
