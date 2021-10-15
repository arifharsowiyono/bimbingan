<?php 

	class DbOperations{
		
		private $con; 

		function __construct(){

			require_once dirname(__FILE__).'/DbConnect.php';

			$db = new DbConnect();

			$this->con = $db->connect();

		}

    /*CRUD -> C -> CREATE*/

    public function createUser($email, $username, $pass){

        if($this->isUserExist($username)){
            return 0;
        }else{
            $password = md5($pass);
            $stmt = $this->con->prepare("INSERT INTO pengguna (id, email, username, password) VALUES (NULL, ?, ?, ?)");
            $stmt->bind_param("sss", $email, $username, $password);  
            if($stmt->execute()){
                return 1;        
            }else{
                return 2; 
            }
        }

    }

    public function createMahasiswaDosen($username){

        if(strlen($username)<12){
            if($this->isMahasiswaExist($username)){
                return 0;
            }else{
                $stmt = $this->con->prepare("INSERT INTO mahasiswa (nim) VALUES (?);");
                $stmt->bind_param("s", $username);  
                if($stmt->execute()){
                    return 1;        
                }else{
                    return 2; 
                } 
            }

        }else{
            if($this->isDosenExist($username)){
                return 0;
            }else{
                $stmt = $this->con->prepare("INSERT INTO dosen (nip) VALUES (?);");
                $stmt->bind_param("s", $username);  
                if($stmt->execute()){
                    return 1;        
                }else{
                    return 2; 
                }
            }
        }
        
    }

    public function userLogin($username, $pass){
        $password = md5($pass);
        $stmt = $this->con->prepare("SELECT id FROM pengguna WHERE username = ? AND password = ?");
        $stmt->bind_param("ss",$username,$password);
        $stmt->execute();
        $stmt->store_result();
        return $stmt->num_rows > 0;
    }

    public function getUserByUsername($username){
        $stmt = $this->con->prepare("SELECT * FROM pengguna WHERE username = ?");
        $stmt->bind_param("s",$username);
        $stmt->execute();
        return $stmt->get_result()->fetch_assoc();
    }

    public function getUserMahasiswa($username){
        $stmt = $this->con->prepare("SELECT * FROM mahasiswa WHERE nim = ?");
        $stmt->bind_param("s",$username);
        $stmt->execute();
        return $stmt->get_result()->fetch_assoc();
    }

    public function getUserDosen($username){
        $stmt = $this->con->prepare("SELECT * FROM dosen WHERE nip = ?");
        $stmt->bind_param("s",$username);
        $stmt->execute();
        return $stmt->get_result()->fetch_assoc();
    }


    private function isUserExist($username){
        $stmt = $this->con->prepare("SELECT id FROM pengguna WHERE username = ?");
        $stmt->bind_param("s", $username);
        $stmt->execute(); 
        $stmt->store_result(); 
        return $stmt->num_rows > 0; 
        $stmt->close();
    }

    private function isMahasiswaExist($username){
        $stmt = $this->con->prepare("SELECT nim FROM mahasiswa WHERE nim = ?");
        $stmt->bind_param("s", $username);
        $stmt->execute(); 
        $stmt->store_result(); 
        return $stmt->num_rows > 0; 
    }

    private function isDosenExist($username){
        $stmt = $this->con->prepare("SELECT nip FROM dosen WHERE nip = ?");
        $stmt->bind_param("s", $username);
        $stmt->execute(); 
        $stmt->store_result(); 
        return $stmt->num_rows > 0; 
    }

    public function uploadUserImage($username, $photo){
        $path = "profil_image/$username.jpeg";
        $path1 ="c:\\xampp\\htdocs\\bimbingan\\profil_image\\$username.jpeg";
        $finalpath = "http://your_ip_address/bimbingan/".$path;
        $stmt = $this->con->prepare("UPDATE mahasiswa SET photo = ? WHERE nim = ?");
        $stmt->bind_param("ss", $finalpath, $username); 
        $decoded = base64_decode($photo);
        $stmt->execute();
        $result['success']="1";
        $result['message']="success";
        echo json_encode($result);
        if(file_put_contents($path1, $decoded)){
            
        }
        
    }

}

?>
