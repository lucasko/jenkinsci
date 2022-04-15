FROM jenkins/jenkins:2.341-jdk11

USER root

RUN apt-get -y update
# install jenkins plugins
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
COPY jq-linux64 /usr/local/bin/jq
RUN chmod +x /usr/local/bin/jq
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt

# define env variables
ENV JENKINS_REF="/usr/share/jenkins/ref"
ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false \
              -Duser.timezone=Asia/Taipei

# copy jenkins shared configuration
#COPY --chown=jenkins:jenkins  ./src/init.groovy.d/*  $JENKINS_REF/init.groovy.d/
#COPY --chown=jenkins:jenkins  ./src/jobs/seedJobs.groovy  $JENKINS_REF/seeds/jobs/
#COPY --chown=jenkins:jenkins  ./src /var/jenkins_home/src

WORKDIR /var/jenkins_home

# LABEL image-name=ruko/jenkins
# LABEL image-version=2.341-centos7-jdk8

#USER jenkins
