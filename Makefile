

IMAGE_REPO=lucasko/jenkinsci
IMAGE_VERSION=2.345-jdk11

build:
	docker build -t $(IMAGE_REPO):$(IMAGE_VERSION) .

deploy/jenkinsci:
	docker-compose up -d jenkinsci
#	docker run --rm -uroot --name jenkinsci -it \
#	-e JAVA_OPTS="-Djenkins.install.runSetupWizard=false \
#	-Dhudson.security.csrf.GlobalCrumbIssuerConfiguration=false \
#	-Duser.timezone=Asia/Taipei" \
#	-w /code \
#	-v $$PWD:/code \
#	-p 8080:8080 $(IMAGE_REPO):$(IMAGE_VERSION) bash /code/docker-entrypoint.sh

push:
	docker push $(IMAGE_REPO):$(IMAGE_VERSION)

deploy/sonar:
	docker-compose up -d sonarqube_db
	docker-compose up -d sonarqube

git/push:
	git add .
	git commit -m "test"
	git push

sonar/generate/token:
	curl -X POST  -u admin:admin1234 http://localhost:9000/api/user_tokens/revoke  -d name=admin  || true
	curl -X POST  -u admin:admin1234 http://localhost:9000/api/user_tokens/generate   -d name=admin | jq -r .'token'