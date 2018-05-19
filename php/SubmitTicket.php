<?php
    $con = mysqli_connect("localhost", "id5400478_borrow", "4201158833", "id5400478_borrowme");

    $email = $_POST["email"];
    $type = $_POST["type"];
    $message = $_POST["message"];
    $status = 'received';





    $statement = mysqli_prepare($con, "INSERT INTO TICKET_TABLE (Email, Type, Message, Status) VALUES (?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement,"ssss", $email, $type, $message, $status);
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>
