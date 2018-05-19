<?php
    $con = mysqli_connect("localhost", "id5400478_borrow", "4201158833", "id5400478_borrowme");

    $email = $_POST["email"];
    $item_name = $_POST["Name"];
    $item_price = $_POST["Price"];
    $item_Des = $_POST["Des"];
    $item_Department = $_POST["Department"];
    $item_ADate = $_POST['ADate'];
    $item_UDate = $_POST['UDate'];
    $deposit = $_POST['Deposit'];
    $fine = $_POST['Fine'];

    $statement = mysqli_prepare($con, "INSERT INTO ITEM_TABLE (email, name, price, description, AvailableDate, UnavailableDate, Department, Deposit, Fine) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement,"sssssssii", $email, $item_name, $item_price, $item_Des, $item_ADate, $item_UDate, $item_Department, $deposit, $fine);
    mysqli_stmt_execute($statement);


         $response = array();

         $response["success"] = true;
         $response["email"] = $email;


    echo json_encode($response);
?>
