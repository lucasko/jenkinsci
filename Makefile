

IMAGE_REPO=ruko/jenkinsci
IMAGE_VERSION=2.341-centos7-jdk8

build:
	docker build -t $(IMAGE_REPO):$(IMAGE_VERSION) .

deploy: build
	docker run --rm -uroot --name jenkinsci -it \
	-e JAVA_OPTS="-Djenkins.install.runSetupWizard=false \
	-Dhudson.security.csrf.GlobalCrumbIssuerConfiguration=false \
	-Duser.timezone=Asia/Taipei" \
	-w /code \
	-v $$PWD:/code \
	-p 8080:8080 $(IMAGE_REPO):$(IMAGE_VERSION) bash /code/docker-entrypoint.sh


git/push:
	git add .
	git commit -m "test"
	git push