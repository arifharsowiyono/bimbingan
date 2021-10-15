<?php

require_once '../includes/DbOperations.php';

$response = array();

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(isset($_POST['username']) and isset($_POST['password'])){
		$db = new DbOperations();

		if($db->userLogin($_POST['username'], $_POST['password'])){
			$user = $db->getUserByUsername($_POST['username']);
			$user1 = $db->getUserMahasiswa($_POST['username']);
			$user2 = $db->getUserDosen($_POST['username']);
			$response['error'] = false; 
			$response['message'] = "Login Success";
			$response['id'] = $user['id'];
			$response['username'] = $user['username'];
			$response['email'] = $user['email'];
			$response['password'] = $user['password'];
			$response['nim'] = $user1['nim'];
			$response['nama_depan_mahasiswa'] = $user1['nama_depan'];
			$response['nama_belakang_mahasiswa'] = $user1['nama_belakang'];
			$response['tahun_masuk'] = $user1['tahun_masuk'];
			$response['photo_mahasiswa'] = $user1['photo'];
			$response['nip'] = $user2['nip'];
			$response['nama_depan_dosen'] = $user2['nama_depan'];
			$response['nama_belakang_dosen'] = $user2['nama_belakang'];
			$response['bidang'] = $user2['bidang'];
			$response['photo_dosen'] = $user2['photo'];
		}else{
			$response['error'] = true; 
			$response['message'] = "Invalid username or password";			
		}

	}else{
		$response['error'] = true; 
		$response['message'] = "Required fields are missing";
	}
}else{
    
    $response['error'] = true;
    $response['message'] = "Invalid Request";

}

echo json_encode($response);

?>
