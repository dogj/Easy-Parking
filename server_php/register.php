<?php
include_once 'database_conn.inc.php';

// $name=$_GET['name'];
// $age = $_GET['age'];
$username = $_GET['username'];
$password = $_GET['password'];

$sql = "INSERT INTO user VALUES (default, '{$username}','{$password}','','','','0');";

if ($conn->query($sql) === TRUE) {
    echo "New record created successfully";
} else {
    echo "The user name have been used." ;
}

$conn->close();
?>