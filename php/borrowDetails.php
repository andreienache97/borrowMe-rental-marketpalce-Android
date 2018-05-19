<?php
    $con = mysqli_connect("localhost", "id5400478_borrow", "4201158833", "id5400478_borrowme");

    $Borrow_ID = $_POST["Borrow_ID"];

    $statement = mysqli_prepare($con, "SELECT Lender_email,	Borrower_email,item_id,Start_date,End_date FROM BORROW_ITEM_TABLE WHERE Borrow_ID = ?");
    mysqli_stmt_bind_param($statement, "s", $Borrow_ID);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement,$Lender_email, $Borrower_email, $item_id, $Start_date, $End_date);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)){
        $response["success"] = true;
        $response["Lender_email"] = $Lender_email;
        $response["Borrower_email"] = $Borrower_email;
        $response["item_id"] = $item_id;
        $response["Start_date"] = $Start_date;
        $response["End_date"] = $End_date;
       

    }

    echo json_encode($response);
?>