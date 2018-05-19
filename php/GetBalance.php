<?php
    $con = mysqli_connect("localhost", "id5400478_borrow", "4201158833", "id5400478_borrowme");

    $email = $_POST["email"];




    $statement = mysqli_prepare($con, "Select balance from BALANCE_TABLE where email = ?");
    mysqli_stmt_bind_param($statement,"s", $email);
    mysqli_stmt_execute($statement);
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $balance);

    $response = array();
    $response["success"] = false;
    while(mysqli_stmt_fetch($statement)){
        $response["success"] = true;
        $response["email"] = $email;
        $response["balance"] = $balance;


    }

    echo json_encode($response);
?>
