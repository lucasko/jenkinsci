

build:
	docker build -t ruko/jenkinsci .

deploy: build
	docker run --rm -uroot --name jenkinsci -it -w /code -v $$PWD:/code -p 8080:8080 ruko/jenkinsci:2.288-2021.0420 bash
	/code/docker-entrypoint.sh


git/push:
	git add .
	git commit -m "test"
	git push