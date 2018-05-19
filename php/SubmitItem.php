<?php
    $con = mysqli_connect("localhost", "id5400478_borrow", "4201158833", "id5400478_borrowme");

    $item_id = $_POST["item_id"];
    $Lemail = $_POST["Lemail"];
    $Bemail = $_POST["Bemail"];
    $ADate = $_POST["ADate"];
    $UDate = $_POST["UDate"];
    $status = 'pending';



    $statement = mysqli_prepare($con, "INSERT INTO BORROW_ITEM_TABLE (Lender_email, Borrower_email, item_id, Start_date,End_date,Status) VALUES (?, ?, ?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement,"ssisss", $Lemail, $Bemail, $item_id, $ADate, $UDate, $status);
    mysqli_stmt_execute($statement);



    $statement_three = mysqli_prepare($con, "UPDATE ITEM_TABLE SET Status = (?) where item_id = (?)");
    mysqli_stmt_bind_param($statement_three,"ii",$s, $item_id);
    mysqli_stmt_execute($statement_three);


    $response = array();
    $response["success"] = true;
    $response["email"] = $Bemail;


    echo json_encode($response);
?>
