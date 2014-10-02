package userWallet

import java.util.Calendar
import org.hibernate.validator.constraints.{NotEmpty,Email}
import com.fasterxml.jackson.annotation.JsonFormat
import org.codehaus.jackson.annotate.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude._



object User{
    private var userIdCounter : Int = 123455
    private def inc = {userIdCounter += 1; userIdCounter}
}

class User {
		 var  user_id :String  = "";
		 
	    @NotEmpty(message =  "{Invalid Email address}") @Email
		var email : String = "";
	    
		@NotEmpty(message =  "{Invalid password}")
		var password : String = "";
		
		@JsonInclude(value=Include.NON_EMPTY)
		var name : String = "";
		
		@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
		var created_at :java.util.Date = _
		
		@JsonIgnore
		@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
		  var updated_at : java.util.Date= _
		
		
		
		def getUser_id() : String = user_id 
		
		def getEmail() : String = email
		
		def getPassword(): String = password
		
		def getName() : String = name
		
		def getCreated_at(): java.util.Date = created_at
		
		//def getUpdated_at() : java.util.Date = updated_at
		
		
		
		def setEmail(s : String) = { email = s}
		
		def setPassword(s: String) = {password = s}
		
		def setName(s : String) = {name =s}
		
		
		def setUpdated_at()   = {
		  updated_at = java.util.Calendar.getInstance.getTime;
		}
		
		
		
		def setCreated_at()   = {
		  created_at = java.util.Calendar.getInstance.getTime;
		}
		
		
		
		def setUserId()   = {
		  user_id = "u-" + User.inc
		}
		
}
