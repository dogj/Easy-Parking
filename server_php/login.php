<?php
include_once 'database_conn.inc.php';

$username = $_POST['username'];
$password = $_POST['password'];

// $username = "zilong";
// $password = "123";

$sql = "SELECT * FROM user WHERE username ='$username' AND password = '$password'";
$result = $conn->query($sql) or die (mysqli_connect_error());  
       if (mysqli_num_rows($result) > 0) {
            // echo "login success";
            $rows = mysqli_fetch_row($result);
            echo $rows[6];

        }else {
            echo "login failed";
        }

$conn->close();
?>