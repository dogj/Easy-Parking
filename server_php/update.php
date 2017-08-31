<?php
include_once 'database_conn.inc.php';

$user_id = $_GET['user_id'];
$park_code = $_GET['park_code'];
$start_time = $_GET['start_time'];
$end_time = $_GET['end_time'];
$balance = $_GET['balance'];

$sql = "UPDATE user SET park_code= '$park_code', start_time = '$start_time', end_time = '$end_time', balance = '$balance' WHERE user_id = '$user_id';";

if ($conn->query($sql) === TRUE) {
    echo "Update success";
} else {
    echo "Update failed" ;
}

$conn->close();
?>