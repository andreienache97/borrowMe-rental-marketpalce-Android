<?php
     $con = mysqli_connect("localhost", "id5400478_borrow", "4201158833", "id5400478_borrowme");
    
    $email_from = $_GET["email1"];
    $email_to = $_GET["email2"];
    $item_id = $_GET["item_id"];
    
    $statement = mysqli_prepare($con, "SELECT * FROM MESSAGES_TABLE WHERE ((EmailFrom = ? AND EmailTo = ?) OR (EmailFrom = ? AND EmailTo = ?)) AND ItemId = ?");
    mysqli_stmt_bind_param($statement, "ssssi", $email_from, $email_to, $email_to, $email_from, $item_id);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $id, $emailFrom, $emailTo, $message, $timestamp, $item_id);
    
    $response = [];
    $response["get_email1"] = $email_from;
    $response["get_email2"] = $email_to;
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
