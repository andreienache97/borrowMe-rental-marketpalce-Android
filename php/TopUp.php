<?php
    $con = mysqli_connect("localhost", "id5400478_borrow", "4201158833", "id5400478_borrowme");

    $email = $_POST["email"];
    $balance = $_POST["balance"];
    $money = $_POST["money"];
    $response = array();
    if($money <= 0){
          $response["success"] = false;
    }else{
        $balance =$balance + $money;
        $statement = mysqli_prepare($con, "UPDATE BALANCE_TABLE SET Balance = (?) where email = (?)");
        mysqli_stmt_bind_param($statement,"is",$balance, $email);
        mysqli_stmt_execute($statement);
        $response["success"] = true;
        $response["email"] = $email;
  }

    echo json_encode($response);
?>
