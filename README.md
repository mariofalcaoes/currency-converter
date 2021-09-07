<h1 align="center">Currency Converter</h1>

<div align="center">
<img src="https://jaya.tech/images/logo-white.png"/>
<br>
<i>Project responsible to convert money.</i>

<a href="https://github.com/abhisheknaiidu/awesome-github-profile-readme/stargazers"><img src="https://img.shields.io/github/stars/abhisheknaiidu/awesome-github-profile-readme" alt="Stars Badge"/></a>
<a href="https://github.com/abhisheknaiidu/awesome-github-profile-readme/network/members"><img src="https://img.shields.io/github/forks/abhisheknaiidu/awesome-github-profile-readme" alt="Forks Badge"/></a>
<a href="https://github.com/abhisheknaiidu/awesome-github-profile-readme/pulls"><img src="https://img.shields.io/github/issues-pr/abhisheknaiidu/awesome-github-profile-readme" alt="Pull Requests Badge"/></a>
<a href="https://github.com/abhisheknaiidu/awesome-github-profile-readme/issues"><img src="https://img.shields.io/github/issues/abhisheknaiidu/awesome-github-profile-readme" alt="Issues Badge"/></a>
<a href="https://github.com/abhisheknaiidu/awesome-github-profile-readme/graphs/contributors"><img alt="GitHub contributors" src="https://img.shields.io/github/contributors/abhisheknaiidu/awesome-github-profile-readme?color=2b9348"></a>
<a href="https://github.com/abhisheknaiidu/awesome-github-profile-readme/blob/master/LICENSE"><img src="https://img.shields.io/github/license/abhisheknaiidu/awesome-github-profile-readme?color=2b9348" alt="License Badge"/></a>

<i>Curious to know about the project? Lets go!</i>

</div>

Application developed with SpringBoot 2.5.4
### Requirements needed to run the application
- JDK 16
- Maven 

### Running the application
- ```bash
  mvn clean install 
  ```
- ```bash
  mvn spring-boot:run -Dspring-boot.run.arguments="--ACCESS_KEY=putHereYourAccessKey"
  ```

### Check application health with actuator
- Run, in the terminal, the command: <br> 
  ```bash
  curl --location --request GET 'http://localhost:8888/currency/converter/actuator/health'
  ```

- If everything is ok, the return should be: <br> 
  ```bash
  {"status":"UP"}
  ```

### Test the conversion of values
- Run, in the terminal, the command: <br>
```bash
curl --location --request POST 'http://localhost:8888/currency/converter/' \
--header 'user_id: 12321' \
--header 'Content-Type: application/json' \
--data-raw '{
"destine_coin": "USD",
"origin_coin": "XOF",
"origin_value": 2000
}'
```
<br> The return will be something like: <br>

```json
{
    "id": 1,
    "date_operation": "2021-09-07T00:57:58.28579966",
    "destine_coin": "USD",
    "destine_value": 3.6295,
    "origin_coin": "XOF",
    "origin_value": 2000,
    "tax": 0.0018
}
```

### Transaction history
- Run, in the terminal, the command: <br>


```bash
curl --location --request GET 'http://localhost:8888/currency/converter/historic/12321'
```

<br> The return will be something like: <br>

```json
{
  "user_id":12321,
  "transactions_history":[
    {
      "id":1,
      "date_operation":"2021-09-07T00:53:29.686591",
      "destine_coin":"XOF",
      "destine_value":1103.44,
      "origin_coin":"usd",
      "origin_value":2.00,
      "tax":551.72
    },
    {
      "id":2,
      "date_operation":"2021-09-07T00:53:35.712737",
      "destine_coin":"XOF",
      "destine_value":1103880.86,
      "origin_coin":"USD",
      "origin_value":2000.00,
      "tax":551.94
    },
    {
      "id":3,
      "date_operation":"2021-09-07T00:53:47.860369",
      "destine_coin":"USD",
      "destine_value":3.63,
      "origin_coin":"XOF",
      "origin_value":2000.00,
      "tax":0.00
    },
    {
      "id":4,
      "date_operation":"2021-09-07T00:57:58.2858",
      "destine_coin":"USD",
      "destine_value":3.63,
      "origin_coin":"XOF",
      "origin_value":2000.00,
      "tax":0.00
    }
  ]
}
```

### Documenting with Swagger
- http://localhost:8888/currency/converter/swagger-ui/
![img.png](img.png)