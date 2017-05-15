<?php

	//$mysqli = new mysqli("localhost","root","root","android_app");
	$con = mysqli_connect("localhost","root","root","android_app");
	
	
	if (mysqli_connect_errno())
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }
  
  $statement = mysqli_prepare($con,"select * from items");
	

	
	mysqli_stmt_execute($statement);
	
	mysqli_stmt_store_result($statement);
	
	mysqli_stmt_bind_result($statement, $item_id, $item_name);
	
	$item = array();
	
	while($row= mysqli_stmt_fetch($statement)){
	
	array_push($item,array(
		'item_id'=>$item_id,
		'item_name'=>$item_name, 
		));
		
	}
	
	echo json_encode($item);
	
	mysqli_stmt_close($statement);
	
	mysqli_close($con);
	/*
	$statement = mysqli_prepare($con,"select * from items");
	
	mysqli_stmt_execute($statement);
	
	mysqli_stmt_store_result($statement);
	
	mysqli_stmt_bind_result($statement, $item_id, $item_name);
	
	
	$item = array();
	
	while($row = mysqli_stmt_fetch($statement)){
    array_push($item,array(
        'item_id'=>$row['item_id'],
        'item_name'=>$row['item_name'],
        
    ));
}
*/
	
	
	
	


?>