# WebProxy
ParseHub WebProxy demo


The only method that needs to be implemented is /proxy/<url>
It should listen for both GET and POST
On a GET request, it should make a get request to <url>
On a POST request, it should make a post request to <url>, passing through any form data
It should set the user-agent header to the same user-agent that it is being called by (note curl/7.35.0 in the examples)

Assuming the web service is running on localhost:8000, here are some examples of how it can be used:

$ curl http://localhost:8000/proxy/http://httpbin.org/get

{
  "args": {}, 
  "headers": {
    "Accept": "*/*", 
    "Accept-Encoding": "gzip, deflate", 
    "Host": "httpbin.org", 
    "User-Agent": "curl/7.35.0"
  }, 
  "origin": "99.250.201.200", 
  "url": "http://httpbin.org/get"
}

$ curl -X POST -d asdf=blah http://localhost:8000/proxy/http://httpbin.org/post

{
  "args": {}, 
  "data": "", 
  "files": {}, 
  "form": {
    "asdf": "blah"
  }, 
  "headers": {
    "Accept": "*/*", 
    "Accept-Encoding": "gzip, deflate", 
    "Content-Length": "9", 
    "Content-Type": "application/x-www-form-urlencoded", 
    "Host": "httpbin.org", 
    "User-Agent": "curl/7.35.0"
  }, 
  "json": null, 
  "origin": "99.250.201.200", 
  "url": "http://httpbin.org/post"
}
