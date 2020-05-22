<?php

 header("Content-type:application/json");

require_once('init.php');

$query = mysqli_query($con, "SELECT * FROM `places` where type = 'wisata'");

$response = array();

while($row = mysqli_fetch_assoc($query)){
    
    array_push($response,
    array(
         'number'       =>$row['number'],
         'name'        =>$row['name'],
		 'address'        =>$row['address'],
		 'openTime'        =>$row['openTime'],
		 'fee'        =>$row['fee'],
		 'imgUrl'        =>$row['imgUrl'],
		 'summary'        =>$row['summary']) 
        );
}

echo json_encode($response);

?>