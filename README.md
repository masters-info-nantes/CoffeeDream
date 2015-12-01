# CoffeeDream

```
POST http://coffeedream.com/auth
IN
	imei (number)
	Imei number of the cell phone used to generate a token	
	
POSSIBLE OUT :
OUT
	token (json)
	Token is used to get an authenticated user
	{
		"call":"auth"
		"answer": {
			"token": "OJOJOIJLKIOOH"
			"callback": "http://coffeedream.com/token/"
		}
	}

OUT	
	error(json)
	Error returned if any token is found
	{
		"call":"auth"
		"answer": {
			"error" : "Token not found for this IMEI"
		}	
	}
OUT	
	error(json)
	Error returned if any IMEI mal formed ( <15 digits)
	{
		"call":"auth"
		"answer": {
			"error" : "IMEI MAL FORMED"
		}	
	}

```	

```
POST http://coffeedream.com/token
IN
	token (string)
	Retrieved wih the auth call

POSSIBLE OUT :

OUT
	user (json)
	returne the user
	{
		"call":"token"
		"answer": {
		
			"id": 1,	
			"firstname": "John",			
			"name": "Doe"
		}
	}
OUT	
	error (json)
	Error returned if token not found
	{
		"call" :"token"
		"answer": {
			"error": "token not found"
		}
	}
OUT	
	error(json)
	Error returned if token malformed  is found
	{
		"call":"auth"
		"answer": {
			"error" : "Token not found for this IMEI"
		}	
	}
		
```
