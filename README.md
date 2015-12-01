# CoffeeDream

```
POST http://coffeedream.com/auth
IN
	imei (number)
	Imei number of the cell phone used to generate a token	
OUT
	token (json)
	Token is used to get an authenticated user
	{
		"call":"auth"
		"answer": {
			"token": "OJOJOIJLKIOOH"
		}
	}
	
```	

```
POST http://coffeedream.com/token
IN
	token (string)
	Retrieved wih the auth call
OUT
	user (json)	
	{
		"id": 1,
		"firstname": "John",
		"name": "Doe"
	}
```