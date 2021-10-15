<?php

require_once '../includes/DbOperations.php';

$response = array();

if($_SERVER['REQUEST_METHOD']=='POST'){
    if(isset($_POST['username']) and isset($_POST['photo'])){
        $db = new DbOperations();

        if($db->uploadUserImage($_POST['username'], $_POST['photo'])){
			$response['error'] = false; 
			$response['message'] = "Upload Image Success";
		}else{
			$response['error'] = false; 
			$response['message'] = "Upload Image Success";			
		}

    }else{
        $response['error'] = true;
        $response['message'] = "Required Fields Are Missing";
    }
}else{
    $response['error'] = true;
    $response['message'] = "Invalid Request";
}

echo json_encode($response);

?>
