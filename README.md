# BirdApp API
REST API for the TwitterClone

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


    curl --location --request POST 'http://localhost:5000/api/auth/signup' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "username":"l00pinfinity",
        "email":"collinskipkemboi@gmail.com",
        "password":"qwerty6"
    }'

### Successful Response

    {
        "success": true,
        "message": "User registered successfully"
    }

### Failed Response

1. Email already in use
```yaml
{
  "timestamp": "2023-01-18T09:22:19.190+00:00",
  "status": 400,
  "error": "Bad Request",
  "trace": "com.boitdroid.birdapp.exception.BadRequestException: It appears that the username you entered is already taken\r\n\tat com.boitdroid.birdapp.service.UserServiceImpl.addUser(UserServiceImpl.java:42)\r\n\tat com.boitdroid.birdapp.controller.AuthController.registerUser(AuthController.java:52)\r\n\tat java.base/jdk.internal.reflect",
  "message": "It appears that the email you entered is already in use",
  "path": "/api/auth/signup"
}
```
2. Username is already taken
```yaml
{
  "timestamp": "2023-01-18T09:22:19.190+00:00",
  "status": 400,
  "error": "Bad Request",
  "trace": "com.boitdroid.birdapp.exception.BadRequestException: It appears that the username you entered is already taken\r\n\tat com.boitdroid.birdapp.service.UserServiceImpl.addUser(UserServiceImpl.java:42)\r\n\tat com.boitdroid.birdapp.controller.AuthController.registerUser(AuthController.java:52)\r\n\tat java.base/jdk.internal.reflect",
  "message": "It appears that the username you entered is already taken",
  "path": "/api/auth/signup"
}
```


## Login to account

### Request

`POST /api/signin`

    curl --location --request POST 'http://localhost:5000/api/auth/signin' \
    --header 'Content-Type: application/json' \
    --data-raw '{
    "usernameOrEmail":"l00pinfinity",
    "password":"qwerty6"
    }'
### Response

    {
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjc0MDMzODU0LCJleHAiOjE2NzQwMzc0NTR9.wyeppy7ZP5rANC0FxGDG9WopmfWFhy8xzt2wqiIOoVEJFlhU4nDr7Qabswoi845mNvRhIVFaBxm1y12d1Id0Nw",
    "tokenType": "Bearer"
    }

## Forgot password

### Request


Reset token is sent to the user via provided email if it exists.

### Successful Response


### Failed Response
1. No account with the Email Address

## Reset password

### Request


### Successful Response

### Failed Response
1. Invalid token


## Get User by Username

### Request

`POST /api/users/in/{username}`

    curl --location --request GET 'http://localhost:5000/api/users/l00pinfinity'

### Successful Response

    {
    "id": 1,
    "username": "l00pinfinity",
    "joinedAt": "2023-01-18T09:21:32Z",
    "email": "collinskipkemboi@gmail.com",
    "tweetCount": 0
    }

### Failed Response
1. No user with username provided

```yaml
    {
       "timestamp": "2023-01-18T09:25:59.689+00:00",
       "status": 404,
       "error": "Not Found",
       "trace": "com.boitdroid.birdapp.exception.ResourceNotFoundException: User with username: l00pinfinitys is not found\r\n\tat com.boitdroid.birdapp.repository",
       "message": "User with username: l00pinfinitys is not found",
       "path": "/api/users/l00pinfinitys"
    }
```
