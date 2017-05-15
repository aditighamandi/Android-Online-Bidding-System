<?php


$conn = mysqli_connect("localhost", "root", "root","android_app");


if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

$item_name = $_POST["item_name"];

$sql = "INSERT INTO items (item_name) VALUES ('$item_name')";


if (mysqli_query($conn, $sql)) {
    echo "New record created successfully";
} else {
    echo "Error: " . $sql . "<br>" . mysqli_error($conn);
}

mysqli_close($conn);

?>