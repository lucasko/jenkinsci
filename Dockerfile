#FROM jenkins/jenkins:2.121.3
#FROM jenkins/jenkins:2.176.1
FROM jenkins/jenkins:2.288

USER root
# define env variables
ENV JENKINS_REF="/usr/share/jenkins/ref"

RUN apt-get update

ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false \
              -Duser.timezone=Asia/Taipei

# install jenkins plugins
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt

# copy jenkins shared configuration
#COPY ./ref $JENKINS_REF

COPY --chown=jenkins:jenkins  ./src/init.groovy.d/*  $JENKINS_REF/init.groovy.d/
COPY --chown=jenkins:jenkins  ./src/jobs/seedJobs.groovy  $JENKINS_REF/seeds/jobs/

COPY --chown=jenkins:jenkins  ./src /var/jenkins_home/src

WORKDIR /var/jenkins_home

ENV IMAGE_VERSION=1.0

LABEL image-name=ruko/jenkins
LABEL image-version=2.288-1

USER jenkins
