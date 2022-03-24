# Account service

### Exposed endpoints
1. /signup
request
```json
{
  "email": "string@domain.com",
  "password": "string",
  "username": "string"
}
```
response
```json
{
  "user": {
    "id": 2
  }
}
```
2. /login
3. /refresh1