# Low Level HTTP Requests

###The purpose is to introduce low level HTTP request structure using Java sockets. 

# Quickstart

1) With Make installed, run in terminal:
`make`

2) Without make, run in terminal:
```javac RequestHTTP.java
java RequestHTTP```
________________________________________________________________

The class example grabs the Nasdaq raw html response for Alphabet (Googl), but 
can be used for any website without authorization. Feel free to edit the request
type (e.g. POST, add authorization). For a better way to grab stock ticker prices,
consider using a commerical API, and yahoo has a cool hidden one described in many
blogs, such as the overview here: https://greenido.wordpress.com/2009/12/22/work-like-a-pro-with-yahoo-finance-hidden-api/ 
