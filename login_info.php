<?php

 header("Content-type:application/json");

require_once('init.php');

$query = mysqli_query($con, "SELECT * FROM `login_info`");

$response = array();

while($row = mysqli_fetch_assoc($query)){
    
    array_push($response,
    array(
        'id'           =>$row['id'],
        'name'       =>$row['name'],
         'user_name'        =>$row['user_name'],
		 'user_password'        =>$row['user_password'],) 
        );
}

echo json_encode($response);

?>