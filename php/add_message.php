<?php
    $con = mysqli_connect("localhost", "id5400478_borrow", "4201158833", "id5400478_borrowme");
    
    $email_from = $_POST["from"];
    $email_to = $_POST["to"];
    $message = $_POST["message"];
    $item_id = $_POST["item_id"];
    
    $statement = mysqli_prepare($con, "INSERT INTO MESSAGES_TABLE (EmailFrom, EmailTo, Message, ItemID) VALUES (?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement, "sssi", $email_from, $email_to, $message, $item_id);
    mysqli_stmt_execute($statement);
    
    $response = array();
    $response["success"] = true;
    
    echo json_encode($response);
?>
