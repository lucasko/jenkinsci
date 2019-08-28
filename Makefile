

build:
	docker build -t jenkinsci:latest .

deploy: build
	docker run --rm --name jenkinsci -p 8080:8080 jenkinsci:latest

git/push:
	git add .
	git commit -m "test"
	git push