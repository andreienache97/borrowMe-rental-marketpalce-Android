<?php
    $con = mysqli_connect("localhost", "id5400478_borrow", "4201158833", "id5400478_borrowme");

    $email = $_POST["email"];
    $question = $_POST["question"];





    $statement = mysqli_prepare($con, "INSERT INTO FAQ_TABLE (email, questions) VALUES ( ?, ?)");
    mysqli_stmt_bind_param($statement,"ss", $email, $question);
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>
