<?php
    $con = mysqli_connect("localhost", "id5400478_borrow", "4201158833", "id5400478_borrowme");

    $item_id = $_POST["item_id"];

    $statement = mysqli_prepare($con, "SELECT email,name,price,Description,AvailableDate,UnavailableDate,Department,Deposit,Fine FROM ITEM_TABLE WHERE item_id = ?");
    mysqli_stmt_bind_param($statement, "s", $item_id);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $email, $name, $price, $Description, $AvailableDate, $UnavailableDate,$Department,$Deposit,$Fine);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)){
        $response["success"] = true;
        $response["email"] = $email;
        $response["name"] = $name;
        $response["price"] = $price;
        $response["Description"] = $Description;
        $response["AvailableDate"] = $AvailableDate;
        $response["UnavailableDate"] = $UnavailableDate;
        $response["Department"] = $Department;
        $response["Deposit"] = $Deposit;
        $response["Fine"] = $Fine;

    }

    echo json_encode($response);
?>