#!/bin/bash

#This script setup Docker Jenkins and Web Server containers
if [ $# -lt "1" ]; then
        echo "No parameter passed, Please try again with Jenkins TAGNAME parameter
                ex: ./filename.sh latest_1.3"
        exit 1;
fi

jenkinsContainerTag=$1

#update packages
sudo apt-get update

#checking if docker is already running or not. If not, installing docker-ce
dockerVersion=$(which docker)
emptyVariable=""
if [[ "$dockerVersion" == "$emptyVariable" ]]
then
        curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
        sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
        sudo apt-get update
        apt-cache policy docker-ce
        sudo apt-get install -y docker-ce
else
        echo '**********DOCKER ALREADY INSTALLED************'
fi

#checking if zip is installed or not. If not, installing it
zipVersion=$(which zip)
if [[ "$zipVersion" == "$emptyVariable" ]]
then
        sudo apt-get update
        sudo apt-get install zip -y
else
        echo '**********ZIP ALREADY INSTALLED************'
fi

#deleting jenkins home directory
if [[ -d "/var/jenkins-home" ]]
then
        sudo rm -rf /var/jenkins-home
        echo "******** Jenkins Folder Deleted**********"
fi

#fetching home user directory
userDir=$(eval echo ~$USER)
devops=$userDir"/devops"
packageRepo=$devops"/packageRepo"
tomcatDeployLocation=$devops"/tomcatDeployFiles"

#pulling required packages from Github
if [[ -d $packageRepo ]]
then
        sudo rm -rf $packageRepo
        echo "********** PackageRepo Folder Deleted**********"
fi

sudo mkdir $devops
sudo mkdir $tomcatDeployLocation
sudo mkdir $packageRepo
cd $packageRepo
echo "********** Packagerepo folder created **********"
sudo git clone https://github.com/InTimeTec-LLC/kmt-prototype.git
cd kmt-prototype
cd devops
echo "********** Complete checkout to repo **********"
sudo cp -r jenkins-home/ -d /var/

#stopping existing jenkins docker and pulling the upadted
isContainerRunning=$(sudo docker ps -q -f "name=adpq-jenkins")
if [[ "$isContainerRunning" != "" ]]
then
        sudo docker stop $isContainerRunning
        sudo docker rm $isContainerRunning
fi

#removing the existing jenkins image
isJenkinsImageExist=$(sudo docker images adpq-jenkins -q)
if [[ "$isJenkinsImageExist" != "" ]]
then
        sudo docker stop $isJenkinsImageExist
        sudo docker	 rm $isJenkinsImageExist
fi

HOSTVM_IP=$(wget -qO- http://ipecho.net/plain ; echo)
sudo docker run -d --name adpq-jenkins -p 9010:8080 -p 50000:50000 -v /var/jenkins-home:/var/jenkins_home -v /var/run/docker.sock:/var/run/docker.sock -v $(which docker):/usr/bin/docker -v $tomcatDeployLocation:/var/jenkins_home/deployCodeFiles/ -e HOSTIP=$HOSTVM_IP -e USERDIR=$userDir -u root yashittdocker/adpq-jenkins:$jenkinsContainerTag
