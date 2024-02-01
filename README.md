# ![Logo-template](images/logo.jpeg)
# Challenge OST

El objetivo de este documento es proveer informacion relacionada con el challenge:

> Nuestro cliente es un asesor financiero que necesita modernizarse y desea crear un
sitio básico de ecommerce para manejar de forma ma ordenad el flujo de clientes. Actualmente
vende sus servicios de asesoría financiera con consultas individuales, pero no esta seguro si
quiere mantener ese tipo de modelo de negocio.

## 1. Descripción
El frontend se comunicara con el backend para guardar usuario, consultas subscripciones, realizar pagos con las apis de Stripe y finalmente tener acceso a una subscripcion. 

## 2. Frontend

Basada en angular

### 2.1. Instalar y Ejecutar

En la carpeta raiz del proyecto frontend ejecutar lo siguiente

<pre><code>
- npm install
- npm start
</code></pre>

## 3. Backend
Basada en spring boot con Postgress

<pre><code>
- mvn spring-boot:run 
</code></pre>

### 3.1 Docker compose

Para la base de datos Postgress es importante ejecutar el docker compose en la raiz, de la siguiente manera
<pre><code>
- docker-compose up
</code></pre>

### 3.1.1 Accesos a la base de datos
<pre><code>
database = oneseventech
user = jairo
password = jairoprogramador
</code></pre>

### 3.1.1 Usuario por defecto de la App
Este usuario simula tener una subscripcion vigente, pero puede crear mas usuarios si lo desea.
<pre><code>
email = admin@jairogalvez.com
password = 12345
</code></pre>


### 3.2 Endpoints

- Crear Usuario: POST
<pre><code>
curl --location 'http://localhost:8080/user' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=B1135919BEBBBA28C624C7D82F3CD4FA; XSRF-TOKEN=53e78409-f340-4859-b4b4-03a382f225fa' \
--data '{
    "email":"email.com",
    "password":"12345",
    "name":"Jairo2",
    "lastName":"Galvez",
    "newsletters": false
}'
</code></pre>

- Consultar Subscripciones: GET
<pre><code>
curl --location 'http://localhost:8080/subscription' \
--header 'Authorization: Bearer token'
--header 'Cookie: JSESSIONID=B1135919BEBBBA28C624C7D82F3CD4FA; XSRF-TOKEN=53e78409-f340-4859-b4b4-03a382f225fa' \
--data ''
</code></pre>

- Consultar Subscripcion pagada por Usuario: GET
<pre><code>
curl --location 'localhost:8080/payment/user/1' \
--header 'Authorization: Bearer token' \
--header 'Cookie: JSESSIONID=B1135919BEBBBA28C624C7D82F3CD4FA; XSRF-TOKEN=53e78409-f340-4859-b4b4-03a382f225fa'
</code></pre>

- Pagar Subscripcion: POST
<pre><code>
curl --location 'localhost:8080/payment/user/1' \
curl --location 'localhost:8080/payment' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer token' \
--header 'Cookie: JSESSIONID=B1135919BEBBBA28C624C7D82F3CD4FA; XSRF-TOKEN=53e78409-f340-4859-b4b4-03a382f225fa' \
--data '{
    "userId": 1,
    "subscriptionId": 2
}'
</code></pre>

## 3. Probar APP

para probar debe seguir los siguientes pasos, luego de tener las apps y la base de datos funcionando.

- Crear Usuario en el formulario
- Iniciar sesion con el usuario creado
- Comprar uno de las dos subcripciones. Para esto hay que especificar esta tarjeta 4242 4242 4242 4242, el resto de datos los puede inventar.
- Cuando el proceso de compra termine automaticamente regresara a la app y mostrara la compra realizada.
