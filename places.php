<?php

 header("Content-type:application/json");

require_once('init.php');

$query = mysqli_query($con, "SELECT * FROM `places`");

$response = array();

while($row = mysqli_fetch_assoc($query)){
    
    array_push($response,
    array(
        'id'           =>$row['id'],
        'number'       =>$row['number'],
         'name'        =>$row['name'],
		 'latitude'        =>$row['latitude'],
		 'longitude'        =>$row['longitude'],
		 'type'        =>$row['type']) 
        );
}

echo json_encode($response);

?>