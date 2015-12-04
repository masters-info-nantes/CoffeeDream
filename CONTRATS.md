# CoffeeDream | Gestion d'authentification de la machine à café
authors : Manon Bobin, Jeremy Bardon, David Perrai  

Stockage : MapDB pour le stockage des (IMEI,TOKEN) et (TOKEN,USERINFOS)
l'insertion des données sera faite directement dans le code (pas d'url rest pour l'insertion).

```
POST http://localhost:8080/auth
IN
	imei (json)
	Imei number of the cell phone used to generate a token	{
		"imei": "0600000001"
	}
	
POSSIBLE OUT :
OUT
	token (json)
	Token is used to get an authenticated user
	{
		"call":"auth"
		"answer": {
			"token": "577e54b1-22b6-4538-b66e-58e8ab67b188"
			"callback": "http://localhost:8080/coffeedream/token"
		}
	}

OUT	
	error(json)
	Error returned if any token is found
	{
		"call":"auth"
		"answer": {
			"error" : "Imei (763524272469643) not found in user database"
		}	
	}
OUT	
	error(json)
	Error returned if any IMEI mal formed ( <15 digits)
	{
		"call":"auth"
		"answer": {
			"error" : "Imei (76352427) malformed. It contains 8 characters instead of 15."
		}	
	}

```	

```
POST http://localhost:8080/coffeedream/token
IN
	token (json)
	Retrieved wih the auth call
	{
		"token": "fe7409af-4b4f-4b66-8eef-441a6a73cce5"
	}
	
POSSIBLE OUT :

OUT
	user (json)
	returne the user
	{
    	"call": "token",
    	"answer": {
        	"user": {
            	"phoneNumber": "0600000002",
            	"firstName": "John",
            	"lastName": "Doe"
        	}
    	}
	}
OUT	
	error (json)
	Error returned if token not found
	{
		"call" :"token"
		"answer": {
			"error": "Token (fe7409af-4b4f-8b66-8eef-441a6a73cce5) not from this server."
		}
	}
OUT	
	error(json)
	Error returned if token malformed  is found
	{
		"call":"auth"
		"answer": {
			"error" : "Token (fe7409af-44b66-8eef-441a6a73cce5) malformed."
		}	
	}
		
```
