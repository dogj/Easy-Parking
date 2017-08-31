<?php
include_once 'database_conn.inc.php';

$username = $_POST['username'];
$password = $_POST['password'];

$sql = "SELECT * FROM user WHERE username ='$username' AND password = '$password'";
$result = $conn->query($sql) or die (mysqli_connect_error());  
       if (mysqli_num_rows($result) > 0) {
            echo "login success";
        }else {
            echo "login failed";
        }

$conn->close();
?>