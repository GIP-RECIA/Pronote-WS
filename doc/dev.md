# to add NOTICE
'mvn notice:check' Checks that a NOTICE file exists and that its content match what would be generated.
'mvn notice:generate' Generates a new NOTICE file, replacing any existing NOTICE file.

# to add licence headers
'mvn license:check' verify if some files miss license header
'mvn license:format' add the license header when missing. If a header is existing, it is updated to the new one.
'mvn license:remove' remove existing license header

# Actions on RSA public key provided by Index Education
# Le certificat fourni est au format SSH2 et sans doute généré par Putty - une conversion permet de le rendre lisible plus facilement
ssh-keygen -i -f /PATH/ClePubIndexEducation.pub -m RFC4716> /PATH/ClePubIndexEducation.id_rsa.pub

# in production mainly set in system properties the property for log directory else it will log in $catalina_base
-Dlogback.logfileDirectory=/PATH/

# using local profile
'./mvnw clean -Dspring.profiles.active=local -Dspring.config.import="./src/main/resources/application-local.yml" clean package'
