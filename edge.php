<?php

 header("Content-type:application/json");

require_once('init.php');

$query = mysqli_query($con, "SELECT * FROM `edge`");

$response = array();

while($row = mysqli_fetch_assoc($query)){
    
    array_push($response,
    array(
        'id'           =>$row['id'],
        'source'       =>$row['source'],
         'destination'        =>$row['destination'],
		 'distance'        =>$row['distance'],
		 'roadDensity'        =>$row['roadDensity']) 
        );
}

echo json_encode($response);

?>