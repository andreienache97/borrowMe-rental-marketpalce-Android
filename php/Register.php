<?php
	//Run PasswordValidation Script
	include "passwordValidation.php";
    $con = mysqli_connect("localhost", "id5400478_borrow", "4201158833", "id5400478_borrowme");

    $name = $_POST["name"];
    $email = $_POST["email"];
    $password = $_POST["password"];
    $phone = $_POST["phone"];
    $address = $_POST["address"];
    $city = $_POST["city"];
    $postcode = $_POST["postcode"];


   function registerUser() {
        global $con, $email,$password, $name, $phone, $address, $city, $postcode ;
        $statement = mysqli_prepare($con, "INSERT INTO USER_TABLE (email, password, name, phone, address, city, postcode) VALUES (?, ?, ?, ?, ?, ?, ?)");
        mysqli_stmt_bind_param($statement,"sssssss", $email, $password, $name, $phone, $address, $city, $postcode);
        mysqli_stmt_execute($statement);
        mysqli_stmt_close($statement);

				$statement_one = mysqli_prepare($con, "INSERT INTO BALANCE_TABLE (email) VALUES (?)");
				mysqli_stmt_bind_param($statement_one,"s", $email);
				mysqli_stmt_execute($statement_one);
				mysqli_stmt_close($statement_one);
    }

    function usernameAvailable() {
        global $con, $email;
        $statement = mysqli_prepare($con, "SELECT * FROM USER_TABLE WHERE email = ?");
        mysqli_stmt_bind_param($statement, "s", $email);
        mysqli_stmt_execute($statement);
        mysqli_stmt_store_result($statement);
        $count = mysqli_stmt_num_rows($statement);
        mysqli_stmt_close($statement);
        if ($count < 1){
            return true;
        }else {
            return false;
        }
    }


if(passwordValidation($password))
	{
    if (usernameAvailable()){
        {registerUser();
        $response["success"] = true;}
       }
    }else
       $response["success"] = false;

    echo json_encode($response);
?>
