<?php
include_once 'database_conn.inc.php';

$user_id = $_POST['user_id'];
$park_code = $_POST['park_code'];
$start_time = $_POST['start_time'];
$end_time = $_POST['end_time'];
$balance = $_POST['balance'];

// $username = "zilong";
// $park_code = "C101";
// $start_time = "09：10 31-08-2017";
// $end_time = "09：10 31-08-2017";
// $balance = "30";

$sql = "UPDATE user SET park_code= '$park_code', start_time = '$start_time', end_time = '$end_time', balance = '$balance' WHERE username = '$username';";

if ($conn->query($sql) === TRUE) {
    echo "Update success";
} else {
    echo "Update failed" ;
}

$conn->close();
?>