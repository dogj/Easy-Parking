<?php
include_once 'database_conn.inc.php';

$code = $_POST['code'];

$sql = "SELECT * FROM codes WHERE code ='$code'";
$result = $conn->query($sql) or die (mysqli_connect_error());  
       if (mysqli_num_rows($result) > 0) {
            echo "code exist";
        }else {
            echo "code not exist";
        }

$conn->close();
?>