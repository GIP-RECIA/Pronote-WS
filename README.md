# Pronote-WS
Application serveur REST produisant les flux XML d'alimentation de pronote


## To run test :
`mvn test -Dspring.profiles.active=test` 
/!\ do not run test directly from IDE, test will fail from missing profile "test", do not add "@ActiveProfiles("test") it will conflic with CI trying to use "test" and "ci" profiles