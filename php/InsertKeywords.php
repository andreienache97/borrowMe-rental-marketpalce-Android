
<?php
    $con = mysqli_connect("localhost", "id5400478_borrow", "4201158833", "id5400478_borrowme");
    $email = $_POST["email"];
    $item_name = $_POST["Name"];
    $item_price = $_POST["Price"];
    $item_Des = $_POST["Des"];
    $K1 = $_POST["Keyword_one"];
    $K2 = $_POST["Keyword_two"];
    $K3 = $_POST["Keyword_three"];
    $K4 = $_POST["Keyword_four"];


    $statement_one = mysqli_prepare($con, "SELECT item_id FROM ITEM_TABLE WHERE email = ? AND name = ? AND price = ? AND Description = ?");
    mysqli_stmt_bind_param($statement_one, "ssss", $email, $item_name,$item_price,$item_Des);
    mysqli_stmt_execute($statement_one);
    mysqli_stmt_store_result($statement_one);
    mysqli_stmt_bind_result($statement_one, $item_id);

         $response = array();
         $response["success"] = false;
         while(mysqli_stmt_fetch($statement_one)){
             $response["success"] = true;
         }
         $statement = mysqli_prepare($con, "INSERT INTO KEYWORD_ITEM (Email, Item_ID, Keyword) VALUES ( ?, ?, ?)");
         mysqli_stmt_bind_param($statement,"sss", $email, $item_id, $K1);
         mysqli_stmt_execute($statement);
         $statement = mysqli_prepare($con, "INSERT INTO KEYWORD_ITEM (Email, Item_ID, Keyword) VALUES ( ?, ?, ?)");
         mysqli_stmt_bind_param($statement,"sss", $email, $item_id, $K2);
         mysqli_stmt_execute($statement);
         $statement = mysqli_prepare($con, "INSERT INTO KEYWORD_ITEM (Email, Item_ID, Keyword) VALUES ( ?, ?, ?)");
         mysqli_stmt_bind_param($statement,"sss", $email, $item_id, $K3);
         mysqli_stmt_execute($statement);
         $statement = mysqli_prepare($con, "INSERT INTO KEYWORD_ITEM (Email, Item_ID, Keyword) VALUES ( ?, ?, ?)");
         mysqli_stmt_bind_param($statement,"sss", $email, $item_id, $K4);
         mysqli_stmt_execute($statement);
         $response["email"] = $email;
    echo json_encode($response);
?>
