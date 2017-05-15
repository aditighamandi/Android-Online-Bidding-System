<?php

	$con = mysqli_connect("localhost","root","root","android_app");
	
	if (mysqli_connect_errno())
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }
	$username = $_POST["username"];
	$password = $_POST["password"];
	
	$statement = mysqli_prepare($con,"select * from user where username = ? and password = ?");
	
	mysqli_stmt_bind_param($statement,"ss",$username, $password);
	
	mysqli_stmt_execute($statement);
	
	mysqli_stmt_store_result($statement);
	
	mysqli_stmt_bind_result($statement, $user_id, $name, $username, $password, $category);
	
	$user = array();
	while(mysqli_stmt_fetch($statement)){
		$user[name]=$name;
		$user[username]= $username;
		$user[category]= $category;
		$user[password]= $password;
	}
	
	echo json_encode($user);
	
	mysqli_stmt_close($statement);
	
	mysqli_close($con);

?>