<?php
	//Run PasswordValidation Script
	include "passwordValidation.php";
    $con = mysqli_connect("localhost", "id5400478_borrow", "4201158833", "id5400478_borrowme");


    $email = $_POST["email"];
    $password = $_POST["password"];
    $name = $_POST["name"];
    $phone = $_POST["phone"];
    $address = $_POST["address"];
    $city = $_POST["city"];
    $postcode = $_POST["postcode"];


if(passwordValidation($password))
{
	$statement = mysqli_prepare($con, "UPDATE USER_TABLE SET password = ?, Name = ?, Phone= ?, Address= ?, City= ? , Postcode= ? WHERE Email = ?" );
  	mysqli_stmt_bind_param($statement,"sssssss", $password, $name, $phone, $address, $city, $postcode,$email );
	mysqli_stmt_execute($statement);
	$response["success"] = true;
}
    else
       $response["success"] = false;


    echo json_encode($response);

?>
