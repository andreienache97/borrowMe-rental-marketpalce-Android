<?php
    $con = mysqli_connect("localhost", "id5400478_borrow", "4201158833", "id5400478_borrowme");
    
    $user_email = $_GET["email"];
    
    $statement = mysqli_prepare($con, "SELECT * FROM MESSAGES_TABLE WHERE EmailFrom = ? OR EmailTo = ?");
    mysqli_stmt_bind_param($statement, "ss", $user_email, $user_email);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $id, $emailFrom, $emailTo, $message, $timestamp, $item_id);
    
    $response = array();
    $response["success"] = false;
    $response["messages"] = [];
    
    while(mysqli_stmt_fetch($statement)){
        $response["success"] = true;
        array_push($response["messages"], [
            "from" => $emailFrom,
            "to" => $emailTo,
            "message" => $message,
            "timestamp" => $timestamp,
            "item_id" => $item_id
        ]);
    }
    
    echo json_encode($response);
?>
