if [ $# -lt "2" ]; then
        echo "No parameter passed, Please try again with Web and mongo tag name
                ex: ./filename.sh webContainerTagname mongContainertagName"
        exit 1;
fi
webContainerTag=$1
mongoContainerTag=$2
isContainerRunning=$(docker ps -q -f "name=adpq-web")
if [ "$isContainerRunning" != "" ]
then
        docker stop $isContainerRunning
        docker rm $isContainerRunning
fi
docker build --build-arg tag=$webContainerTag -f ${WORKSPACE}/devops/dockerFiles/web/dockerfile .
docker build --rm --pull=true --build-arg tag=$webContainerTag -t yashittdocker/adpq-web -f ${WORKSPACE}/devops/dockerFiles/web/dockerfile .
docker run -d --name adpq-web -p 8080:8080 -v /home/ubuntu/tomcatDeployFiles:/usr/local/tomcat/webapps/ yashittdocker/adpq-web:$webContainerTag

isContainerRunning=$(docker ps -q -f "name=adpq-mongo")
if [ "$isContainerRunning" != "" ]
then
        docker stop $isContainerRunning
        docker rm $isContainerRunning
fi
docker build --build-arg tag=$mongoContainerTag -f ${WORKSPACE}/devops/dockerFiles/mongo/dockerfile .
docker build --rm --pull=true --build-arg tag=$mongoContainerTag -t yashittdocker/adpq-mongo -f ${WORKSPACE}/devops/dockerFiles/mongo/dockerfile .
docker run -d --name adpq-mongo -v ${WORKSPACE}/devops/mongoconfig/data/db/:/data/db -p 27017:27017 yashittdocker/adpq-mongo:$mongoContainerTag
