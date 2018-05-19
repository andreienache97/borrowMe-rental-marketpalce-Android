<?php
    $con = mysqli_connect("localhost", "id5400478_borrow", "4201158833", "id5400478_borrowme");
    
    $name = $_POST["name"];
    $password = $_POST["password"];
    
    $statement = mysqli_prepare($con, "SELECT name FROM ADMIN_TABLE WHERE name = ? AND password = ?");
    mysqli_stmt_bind_param($statement, "ss", $name, $password);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $name);
    
    $response = array();
    $response["success"] = false;  
    
    while(mysqli_stmt_fetch($statement)){
        $response["success"] = true;  
        $response["name"] = $name;
       
    }
    
    echo json_encode($response);
?>
