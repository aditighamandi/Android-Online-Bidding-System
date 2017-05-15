<?php


	$con = mysqli_connect("localhost","root","root","android_app");
	echo "got it";
	$name = $_POST["name"];
	$username = $_POST["username"];
	$password = $_POST["password"];
	$category = $_POST["category"];
	
	$statement = mysqli_prepare($con, "INSERT INTO user (name, username, password, category) values (?,?,?,?)");
	mysqli_stmt_bind_param($statement,"ssss",$name, $username, $password, $category);
	
	mysqli_stmt_execute($statement);
	
	mysqli_stmt_close($statement);
	mysqli_close($con);

?>