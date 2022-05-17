FROM jenkins/jenkins:2.347-centos7-jdk8

USER root

RUN yum -y update
# install jenkins plugins
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
COPY jq-linux64 /usr/local/bin/jq
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
ADD https://repo1.maven.org/maven2/org/sonarsource/scanner/cli/sonar-scanner-cli/4.7.0.2747/sonar-scanner-cli-4.7.0.2747.zip /tmp/my-sonar-scanner.zip

RUN mkdir -p /var/jenkins_home/tools/hudson.plugins.sonar.SonarRunnerInstallation/ & \
	unzip /tmp/my-sonar-scanner.zip -d /var/jenkins_home/tools/hudson.plugins.sonar.SonarRunnerInstallation/ && \
	mv /var/jenkins_home/tools/hudson.plugins.sonar.SonarRunnerInstallation/sonar-scanner-4.7.0.2747 /var/jenkins_home/tools/hudson.plugins.sonar.SonarRunnerInstallation/my-sonar-scanner && \
	chmod +x /var/jenkins_home/tools/hudson.plugins.sonar.SonarRunnerInstallation/my-sonar-scanner/bin/sonar-scanner && \
	rm -rf /tmp/*.zip

RUN chown jenkins:jenkins -R /var/jenkins_home && \
	chown jenkins:jenkins -R /usr/share/jenkins && \
	chown jenkins:jenkins -R /var/jenkins_home && \
	chown jenkins:jenkins -R /usr/local/bin/jq && \
	chmod +x /usr/local/bin/jq

# LABEL image-name=ruko/jenkins
# LABEL image-version=2.341-centos7-jdk8

USER jenkins
