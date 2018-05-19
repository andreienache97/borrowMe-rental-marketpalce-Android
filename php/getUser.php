<?php
    $con = mysqli_connect("localhost", "id5400478_borrow", "4201158833", "id5400478_borrowme");

    $email = $_POST["email"];

    $statement = mysqli_prepare($con, "SELECT email,password,name,phone,address,city,postcode FROM USER_TABLE WHERE email = ?");
    mysqli_stmt_bind_param($statement, "s", $email);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $email, $password, $name, $phone, $address, $city, $postcode);

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