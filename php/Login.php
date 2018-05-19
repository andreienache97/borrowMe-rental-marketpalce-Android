<?php
    $con = mysqli_connect("localhost", "id5400478_borrow", "4201158833", "id5400478_borrowme");

    $email = $_POST["email"];
    $password = $_POST["password"];

    $statement = mysqli_prepare($con, "SELECT * FROM USER_TABLE WHERE email = ? AND password = ?");
    mysqli_stmt_bind_param($statement, "ss", $email, $password);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $email, $password, $name, $phone,$address, $city, $postcode);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)){
        $response["success"] = true;
        $response["email"] = $email;
        $response["password"] = $password;
        $response["name"] = $name;
        $response["phone"] = $phone;
        $response["address"] = $address;
        $response["city"] = $city;
        $response["postcode"] = $postcode;

    }

    echo json_encode($response);
?>
