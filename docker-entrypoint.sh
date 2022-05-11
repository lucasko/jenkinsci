#!/bin/bash

cd /code

env

mkdir -p $JENKINS_REF/seeds/jobs/

cp -r ./src/init.groovy.d/*  $JENKINS_REF/init.groovy.d/
cp ./src/jobs/seedJobs.groovy  $JENKINS_REF/seeds/jobs/
cp -r ./src /var/jenkins_home/src

#chown -R jenkins:jenkins /var/jenkins_home
#chown -R jenkins:jenkins $JENKINS_REF

#su -c "/usr/local/bin/jenkins.sh" jenkins
/usr/local/bin/jenkins.sh