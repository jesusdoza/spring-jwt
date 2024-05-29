```java
//make key pair
openssl genrsa -out keypair.pem 2048

```
- get public key from file created
    - using keypair to generate public.pem

```java
//terminal make public key 
openssl rsa -in keypair.pem -pubout -out public.pem

```

- create key in correct format for spring
    - creates private.pem key in correct form

```java
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem

```