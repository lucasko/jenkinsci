### Jenkins Configuration-as-Code


```sh
docker build -t jenkinsci .

docker run -d  --name jenkinsci -p 8080:8080  jenkinsci
```


curl -s -u admin:1140831a61581415fcfad56186299e5b01  http://localhost:8080/crumbIssuer/api/json

curl -X POST  -H "Jenkins-Crumb:53165e9cac3d3b639bc5e8750358ed1be5a02e315165b43382e710f3bdf8a914" http://admin:1234@localhost:8080/job/test.job/build?token=1140831a61581415fcfad56186299e5b01