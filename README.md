# UserBackend API
REST API for authenticating users.

# Initialize Database
```yaml
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES('ROLE_USER');
```

# REST API

Below are the REST APIs for the example app.

## Create account

### Request

`POST /api/signup`
    
    curl --location --request POST 'http://localhost:8080/api/signup' \--header 'Content-Type: application/json' \--data-raw '{"username":"ckb","email":"collinskipkemboi001@gmail.com","password":"tyugbjhn"}'

### Successful Response

    {
        "success": true,
        "message": "User registered successfully"
    }

### Failed Response

1. Email already in use
```yaml
{
    "success": false,
    "message": "Email is already in use"
}
```
2. Username is already taken
```yaml
{
    "success": false,
    "message": "Username is already taken"
}
```


## Login to account

### Request

`POST /api/signin`

    curl --location --request POST 'http://localhost:8080/api/signin' \--header 'Content-Type: application/json' \--data-raw '{"usernameOrEmail":"ckb","password":"qwerty"}'

### Response

    {
        "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJja2IiLCJpYXQiOjE2NzAyNDc1OTksImV4cCI6MTY3MDI0NzY4NX0.CwuhFGhu0S15q5xpXWHfFqj836fXh2W3z1r6RpmcPlEswe50XcfXVg9iLW6F_QDRnnyzxFNJdAoMnWKDmTrq7g",
        "tokenType": "Bearer",
        "expiresIn": 86400
    }

## Forgot password

### Request

`POST /api/forgot-password?email=test@gmail.com`

    curl --location --request POST 'http://localhost:8080/api/forgot-password?email=test@gmail.com' \--data-raw ''

Reset token is sent to the user via provided email if it exists.

### Successful Response

    {
        "success": true,
        "message": "We have sent a password reset token to test@gmail.com"
    }

### Failed Response
1. No account with the Email Address
```yaml
{
    "success": false,
    "message": "There is no account with an email address"
}
```

## Reset password

### Request

`POST /api/reset?token=c52d4ed8-f4a2-41de-90ca-f97bc4a56e37`

    curl --location --request POST 'http://localhost:8080/api/reset?token=c52d4ed8-f4a2-41de-90ca-f97bc4a56e37' \--header 'Content-Type: application/json' \--data-raw '{"password":"qwerty"}'

### Successful Response

    {
        "success": true,
        "message": "Your password has been successfully reset."
    }

### Failed Response
1. Invalid token
```yaml
{
  "success": false,
  "message": "The password reset link is invalid."
}
```

## Get User by Username

### Request

`POST /api/users/in/{username}`

    curl --location --request GET 'http://localhost:8080/api/users/in/{username}'

### Successful Response

    {
        "success": true,
        "message": "User with username available",
        "data": {
            "id": 1,
            "email": "test@gmail.com",
            "username": "ckb",
            "roles": [
                {
                "id": 1,
                "name": "ROLE_USER"
                }
            ]
        }
    }

### Failed Response
1. No user with username provided
```yaml
{
  "success": false,
  "message": "No account with username provided"
}
```
