<?php
	$maxCount = $_GET["max"];
	$page = $_GET["page"];
	$size = $_GET["size"];
	/*{
      "link": "https://stackoverflow.com/questions/62203563/how-to-use-a-geolocation-component-that-watches-changes-in-location",
      "owner": {
         "displayName": "Ackman",
         "profileImage": "https://www.gravatar.com/avatar/a7a09b91e8042c41d65972a25321dfcb?s=128&d=identicon&r=PG&f=1",
         "reputation": 1247,
         "userId": 4882612
      },
      "questionId": 62203563,
      "title": "How to use a geolocation component that watches changes in location?"
   }*/
   $stack = array();
   for ($i = 0; $i < $maxCount; $i++){
	   $owner = new Owner("name".$i, "image".$i, $i, $i);
	   $question = new Question("link".$i, $i, "title".$i, $owner);
	   array_push($stack, $question);
   }
   $output = array_slice($stack, $page * $size, $size);
   echo json_encode($output);
   
   class Question{
	   public $link; //"https://stackoverflow.com/questions/62203563/how-to-use-a-geolocation-component-that-watches-changes-in-location"
	   public $question_id;
	   public $title;
	   public $owner;
	   public function __construct($link, $question_id, $title, $owner) {
				$this->link = $link;
				$this->question_id = $question_id;
				$this->title = $title;
				$this->owner = $owner;
		}
	    public function __toString()
        {
          return $this->link." ".$this->question_id." ".$this->title." ".$this->owner;
        }
   }
   class Owner{
	   public $display_name;
	   public $profile_image;
	   public $reputation;
	   public $user_id;
	   public function __construct($display_name, $profile_image, $reputation, $user_id){
			$this->display_name = $display_name;
			$this->profile_image = $profile_image;
			$this->reputation = $reputation;
			$this->user_id = $user_id;
	   }
	   public function __toString(){
          return $this->display_name." ".$this->user_id;
       }
   }
?>