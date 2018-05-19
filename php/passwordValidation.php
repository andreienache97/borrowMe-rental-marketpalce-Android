<?php
function passwordValidation	($password)
{
		//Set password as not valid 
		$passwordValid = false;
		
		//Matches if password has 8 or more characters
		if(preg_match('/^[A-Za-z0-9]{8,20}$/',$password))
		{
			//Matches if password contains at least one Uppercase Letter
			if(preg_match('/[A-Z]/',$password))
			{
				//Matches if password contains at least one Lowercase Letter
				if(preg_match('/[a-z]/',$password))
				{
					//Matches if password contains at least one digit
					if(preg_match('/[0-9]/',$password))
					{
						//Password is Validated
						$passwordValid = true;
					}
				}
			}
		}
		
		//Return Boolean for Valid Password
		return $passwordValid;
}		
?>