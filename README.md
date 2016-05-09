# boot-homework

<b>Implementation of a loan Web Service</b><br />
implement a RESTful web service that the client can apply for loan by providing his personal data (e.g. first name and surname), amount and term.
<ul> <li>Loan application risk analysis is performed. Risk is considered too high if:</li>
<ul><li>o the attempt to take loan is made between 00:00 to 6:00 AM with maximum possible amount.</li>
o the client reached the maximum number of applications (e.g. 3) per day from a single IP.</li></ul>
<li> Loan is issued if there are no risks associated with the application. If so, client receives a reference to newly created loan. However, if risk is surrounding the application, client receives "rejection" message.
</li></ul>

<br />
<br />
<b>Runing the application</b><br />
Use the system cmd for windows or Terminal for Linux, got to the project folder.
<br />
Using Maven you can run the application using mvn spring-boot:run. 
Or you can build an executable JAR file with mvn clean package and run the JAR by typing:
<br />
  <pre>java -jar target/boot-homework-0.0.1-SNAPSHOT.jar</pre>
<br />
<b>How to use the application</b><br />
Use the following URL   http://localhost:8080/user/userName/loan
and add he following in the message body
{"firstName":"Test","amount":"10000"}
<br />
<br />
<b>the response looks like :</b>
<br />
<pre>New Loan Created for user userName</pre>
