package userWallet
import org.springframework.context.annotation.{Conditional,ConditionContext,Configuration}
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.bind.annotation.{RequestMapping,RequestMethod,RequestParam,RequestBody,RequestHeader}
import org.springframework.web.bind.annotation.{ResponseBody,ResponseStatus,PathVariable}
import org.springframework.web.bind.annotation.RestController
import collection.JavaConverters._
import org.codehaus.jackson.map.ObjectMapper
import org.springframework.http.ResponseEntity
import java.util.Date
import org.springframework.http._
import scala.util.control.Exception._
import java.lang._
import java.util.Iterator
import java.util.ArrayList
import java.util.Arrays
import java.util.List
import javax.validation.constraints.{Min, NotNull, Size}
import javax.validation._
import org.codehaus.jackson.annotate.JsonIgnoreProperties
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.Response.ResponseBuilder
import javax.ws.rs.core.CacheControl
import javax.ws.rs.core.Response
import javax.ws.rs.core.Request
import javax.ws.rs.core.EntityTag
import javax.ws.rs.core.Context
import javax.ws.rs.core.UriInfo
import javax.ws.rs.ext.{RuntimeDelegate,FactoryFinder}

 import org.springframework.context.annotation.Scope;
   import org.springframework.stereotype.Component;

object WalletConfig {
  
  var userMap:Map[String,Any] = Map()
   var idCardMap:Map[String,Any] = Map()
   
   var userId_IdcardMap = new java.util.HashMap[String,String]
  
  
   var loginIdMap:Map[String,Any] = Map()
   
   var userId_LoginIDMap = new java.util.HashMap[String,String]
  
  
  
   var BaIdMap:Map[String,Any] = Map()
   
   var userId_BaIDMap = new java.util.HashMap[String,String]
  

}

@Configuration
@EnableAutoConfiguration
@ComponentScan
@RestController
class WalletConfig {


   
 
  
  @RequestMapping(value = Array("/api/v1/users"), method = Array(RequestMethod.POST), consumes = Array("application/json"))
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
   def UserConfig(@Valid @RequestBody(required = true) userObj : User) : User 
  = {
    
        
        userObj.setUserId()
        userObj.setCreated_at()
        //userObj.setUpdated_at()
        WalletConfig.userMap += (userObj.user_id.toString() -> userObj) 
        return userObj
    
	
    
  }
  
  
  
  
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = Array("/api/v1/users/{user_id}"), method = Array(RequestMethod.GET))
  def getUser(@PathVariable user_id : String, @RequestHeader(value="Etag", required=true , defaultValue="") eTag: String) : Any = {
			 var userId: String = user_id
			var check:Any = null
			try
			 {
		  		  check = WalletConfig.userMap(userId) 
			 }
			 catch
			 {
			   case e : Exception => return new ResponseEntity[String]( "User Id requested not found!", null, HttpStatus.NOT_FOUND )
			   
			 }
				 
		  		 if(check != null)
		  		 {
		  		   
		  		   
		  			 	val tag: String = eTag
		  			 	
		  			 		
						  var userObj:User = check.asInstanceOf[User]
					 
		  			 	
		  			 	  val cacheControl: CacheControl = new CacheControl()
						  cacheControl.setMaxAge(216)
						  var x: String = ""
						    
						  if(userObj.updated_at != null)
						    x = Integer.toString(userObj.updated_at.hashCode())
						  
			  			 	var etag : EntityTag = new EntityTag(x)
						    val httpResponseHeader: HttpHeaders = new HttpHeaders()
						    httpResponseHeader.setCacheControl(cacheControl.toString)
						    httpResponseHeader.add("Etag", etag.getValue)
						    
						 
						    if(etag.getValue.equalsIgnoreCase(tag)){
						      
						      return new ResponseEntity[String]( null, httpResponseHeader, HttpStatus.NOT_MODIFIED )
						    } 
						    else { 
						      return new ResponseEntity[User]( userObj, httpResponseHeader, HttpStatus.OK )
						    }
							  			 	
		  			 	
					      	
					   
		  		 }
		  		 //throw new WebApplicationException(400
		  		  return new ResponseEntity[String]( "User Id requested not found!", null, HttpStatus.NOT_FOUND )
			 
			 
  }
  
  
  
  
  @RequestMapping(value = Array("/api/v1/users/{user_id}"), method = Array(RequestMethod.PUT), consumes = Array("application/json"))
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
   def updateUser(@Valid @RequestBody userObjNew : User,
 @PathVariable user_id : String) : java.util.Map[String,String] = {
		   		 var userId: String = user_id
			 
		  		 var check = WalletConfig.userMap(userId) 
		  		
		  		 if(check != null)
		  		 {
			  		    
					    
							  var userObj:User = check.asInstanceOf[User]
							  
							  
							  userObj.setEmail(userObjNew.email) 
							  userObj.setPassword(userObjNew.password ) 
							  userObj.setName(userObjNew.name ) 
							  userObj.setUpdated_at();
							  
							  WalletConfig.userMap.updated(userId, userObj)
							  
						      var listTest:Map[String,String] = Map()
						      
						      listTest += ("user_Id" -> (userObj.getUser_id().toString()))
						      listTest += ("email" -> userObj.getEmail())
						      listTest += ("password" -> userObj.getPassword())
						      listTest += ("created_at" -> userObj.getCreated_at().toString())
						      
						      return listTest.asJava
					    
		  		 }
		  	 return null
		      
      
  }
  
  
  
  @RequestMapping(value = Array("/api/v1/users/{user_id}/idcards"), method = Array(RequestMethod.POST), consumes = Array("application/json"))
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  def addIdcard(@Valid @RequestBody idCard : IDCard,
 @PathVariable user_id : String) : IDCard = {
      
			 var userId: String = user_id
	
		  		 var check = WalletConfig.userMap(userId) 
		  		
		  		 if(check != null)
		  		 {
		  			 	
						idCard.setCard_id();
						WalletConfig.userId_IdcardMap.put( idCard.getCard_id(), userId)
						WalletConfig.idCardMap += (idCard.card_id -> idCard	) 
			  			return idCard;	  
			      }
		  	 return null
		
  }
  
 
  
  @RequestMapping(value = Array("/api/v1/users/{user_id}/idcards"), method = Array(RequestMethod.GET))
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  def getAllIdcards(@PathVariable user_id : String) : Any = {
      val li	 = new ArrayList[Any]()

		if(user_id.startsWith("u-"))
		{
	
			 var userId: String = user_id
	
		  		 var check = WalletConfig.userMap(userId) 
		  		
		  		 if(check != null)
		  		 {
		  		  
		  		//  WalletConfig.listAllIdCard = Map()
		  		   
		  			 	var idCard : IDCard = null
						
		  			 	var idCardMapJava : java.util.Map[String,Any] = WalletConfig.idCardMap.asJava
		  			 	
		  			 	
		  			 	 
		  			 	
		  			 	var iterator  = idCardMapJava.entrySet().iterator();
				        while (iterator.hasNext()) {
				             var values =  iterator.next();
				             idCard = values.getValue().asInstanceOf[IDCard]
				             
				           
				             
				             if(WalletConfig.userId_IdcardMap.get(idCard.getCard_id()).toString().equals(userId))
				             {
				               
				               
				            	 li.add(idCard)
			               
				             }
				             
				          
				        } 
		  			 	
		  			
						  			 	
		  			 	
		  			 	
		  			 	
		  		   
		  		   
					      return li
		  		 }
		  	 return null
		}
		return null      
      
  }
  
 
 
    @RequestMapping(value = Array("/api/v1/users/{user_id}/idcards/{card_id}"), method = Array(RequestMethod.DELETE))
  @ResponseBody
  @ResponseStatus(HttpStatus.NO_CONTENT)
  def deleteIdCard(@PathVariable user_id : String,@PathVariable card_id : String) : Any = {
     

		if(user_id.startsWith("u-") || card_id.startsWith("c-"))
		{
	
			 var userId: String = user_id
			 var cardId: String = card_id
	
		  		 var check = WalletConfig.userMap(userId) 
		  		
		  		 if(check != null)
		  		 {
		  		  
		  		     var checkIDCard = WalletConfig.idCardMap(cardId) 
		  		
			  		 if(checkIDCard != null)
			  		 {
		  		   
		  		   
		  		   
				  			//WalletConfig.listAllIdCard = Map()
			  		   
			  			 	var idCard : IDCard = checkIDCard.asInstanceOf[IDCard]
			  			 	
			  			 	
			  			 	
 							WalletConfig.idCardMap -= idCard.card_id.toString()
			  			 	WalletConfig.userId_IdcardMap.remove(idCard.card_id)
			  			 	
			  		 }
		  		 }
		  	 return null
		}
		return null      
      
  }
  
 @RequestMapping(value = Array("/api/v1/users/{user_id}/weblogins"), method = Array(RequestMethod.POST), consumes = Array("application/json"))
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  def addLoginId(@Valid @RequestBody loginObj : WebLogin,
 @PathVariable user_id : String) : WebLogin = {
      

			 var userId: String = user_id
	
		  		 var check = WalletConfig.userMap(userId) 
		  		
		  		 if(check != null)
		  		 {
		  			 	
							 
							  loginObj.setLogin_id();
							  
							  WalletConfig.userId_LoginIDMap.put( loginObj.getLogin_id(), userId)
							   
			  			 	  WalletConfig.loginIdMap  += (loginObj.login_id -> loginObj) 
			  			 	  
			  			 	  
			  			 	  return loginObj;
		  		 }
		  	 return null
		      
      
  }
   
  @RequestMapping(value = Array("/api/v1/users/{user_id}/weblogins"), method = Array(RequestMethod.GET))
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  def getAllLoginIds(@PathVariable user_id : String) : Any = {
      val li	 = new ArrayList[Any]()

		 var userId: String = user_id
	
		  		 var check = WalletConfig.userMap(userId) 
		  		
		  		 if(check != null)
		  		 {
		  		  
		  		 // WalletConfig.listAllIdCard = Map()
		  		   
		  			 	var loginObj : WebLogin = null
						
		  			 	var loginIdMapJava : java.util.Map[String,Any] = WalletConfig.loginIdMap.asJava
		  			 	
		  			 	
		  			 	 
		  			 	
		  			 	var iterator  = loginIdMapJava.entrySet().iterator();
				        while (iterator.hasNext()) {
				             var values =  iterator.next();
				             loginObj = values.getValue().asInstanceOf[WebLogin]
				             
				           
				             
				             if(WalletConfig.userId_LoginIDMap.get(loginObj.getLogin_id()).toString().equals(userId))
				             {
				               
				               
				            	 li.add(loginObj)
				               
				             }
				             
				          
				        } 
		  			 	
		  			
						  			 	
		  			 	
		  			 	
		  			 	
		  		   
		  		   
					      return li
		  		 }
		  	 return null
		
		      
      
  }
  
  @RequestMapping(value = Array("/api/v1/users/{user_id}/weblogins/{login_id}"), method = Array(RequestMethod.DELETE))
  @ResponseBody
  @ResponseStatus(HttpStatus.NO_CONTENT)
  def deleteLogin(@PathVariable user_id : String,@PathVariable login_id : String) : Any = {
     

		if(user_id.startsWith("u-") || login_id.startsWith("c-"))
		{
	
			 var userId: String = user_id
			 var loginId: String = login_id
	
		  		 var check = WalletConfig.userMap(userId) 
		  		
		  		 if(check != null)
		  		 {
		  		  
		  		     var checkLoginId = WalletConfig.loginIdMap(loginId) 
		  		
			  		 if(checkLoginId != null)
			  		 {
		  		   
		  		   
		  		   
				  			//WalletConfig.listAllIdCard = Map()
			  		   
			  			 	var loginObj : WebLogin = checkLoginId.asInstanceOf[WebLogin]
			  			 	
			  			 	
			  			 	
 							WalletConfig.loginIdMap -= loginObj.login_id.toString()
			  			 	WalletConfig.userId_LoginIDMap.remove(loginObj.login_id)
			  			 	
			  		 }
		  		 }
		  	 return null
		}
		return null      
      
  }
  
 @RequestMapping(value = Array("/api/v1/users/{user_id}/bankaccounts"), method = Array(RequestMethod.POST), consumes = Array("application/json"))
 @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  def addBaId(@Valid @RequestBody(required = true) bankObj :  BankAccount,
      @PathVariable user_id : String ) : BankAccount = {

		
   
				 
					 var userId: String = user_id
			
				  		 var check = WalletConfig.userMap(userId) 
				  		
				  		 if(check != null)
				  		 {
									  bankObj.setBa_id();
									  
									  
									  
									  WalletConfig.userId_BaIDMap.put( bankObj.getBa_id(), userId)
									   
					  			 	  WalletConfig.BaIdMap  += (bankObj.ba_id -> bankObj) 
					  			 	  
					  			 	  
					  			 	  return bankObj;
							   	  
					  }
				  	 return null
				     
		    
  } 	
  
 
 
 @RequestMapping(value = Array("/api/v1/users/{user_id}/bankaccounts"), method = Array(RequestMethod.GET))
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  def getAllBaIds(@PathVariable user_id : String) : Any = {
      val li	 = new ArrayList[Any]()

		if(user_id.startsWith("u-"))
		{
	
			 var userId: String = user_id
	
		  		 var check = WalletConfig.userMap(userId) 
		  		
		  		 if(check != null)
		  		 {
		  		  
		  		  
		  		   
		  			 	var baObj : BankAccount = null
						
		  			 	var baIdMapJava : java.util.Map[String,Any] = WalletConfig.BaIdMap.asJava
		  			 	
		  			 	
		  			 	 
		  			 	
		  			 	var iterator  = baIdMapJava.entrySet().iterator();
				        while (iterator.hasNext()) {
				             var values =  iterator.next();
				             baObj = values.getValue().asInstanceOf[BankAccount]
				             
				           
				             
				             if(WalletConfig.userId_BaIDMap.get(baObj.getBa_id()).toString().equals(userId))
				             {
				               
				               
				            	 li.add(baObj)
				               
				             }
				             
				          
				        } 
		  			 	
		  			
						  			 	
		  			 	
		  			 	
		  			 	
		  		   
		  		   
					      return li
		  		 }
		  	 return null
		}
		return null      
      
  }
  
  @RequestMapping(value = Array("/api/v1/users/{user_id}/bankaccounts/{ba_id}"), method = Array(RequestMethod.DELETE))
  @ResponseBody
  @ResponseStatus(HttpStatus.NO_CONTENT)
  def deleteBA(@PathVariable user_id : String,@PathVariable ba_id : String) : Any = {
     

		
	
			 var userId: String = user_id
			 var baId: String = ba_id
	
		  		 var check = WalletConfig.userMap(userId) 
		  		
		  		 if(check != null)
		  		 {
		  		  
		  		     var checkBaId = WalletConfig.BaIdMap(baId) 
		  		
			  		 if(checkBaId != null)
			  		 {
		  		   
		  		   
		  		   
				  			
			  		   
			  			 	var baObj : BankAccount = checkBaId.asInstanceOf[BankAccount]
			  			 	
			  			 	
			  			 	
 							WalletConfig.BaIdMap -= baObj.ba_id.toString()
			  			 	WalletConfig.userId_LoginIDMap.remove(baObj.ba_id)
			  			 	
			  		 }
		  		 }
		  	 return null
		
  }
   
}