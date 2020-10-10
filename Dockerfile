#
# Docker Best Practices:  https://docs.docker.com/develop/develop-images/dockerfile_best-practices/
# Docker Reduce Image Strageties:  https://www.ardanlabs.com/blog/2020/02/docker-images-part1-reducing-image-size.html
#
FROM arm32v7/openjdk:11-jre-slim AS Build 

# Address issues with headless Docker installs of apt-get componments
RUN echo 'debconf debconf/frontend select Noninteractive' | debconf-set-selections
RUN apt-get update && apt-get install -y \
  git \
  maven \
  && rm -rf /var/lib/apt/lists/* && apt autoremove -y

RUN mkdir -p /app

# Build the project
RUN git clone https://github.com/IngeniiCode/BurnBanComalCounty.git 
WORKDIR /BurnBanComalCounty
RUN mvn install -Dmaven.assembly.skip=false  
# copy the tar to app directory from where the Run stage will copy to it's context
RUN mv /BurnBanComalCounty/target/BurnBanComalCounty*-with-dependencies.jar /app/BurnBanComalCounty.jar 

# Build the project
RUN cd BurnBanComalCounty;mvn compiler:compile jar:jar

#
# RESET and copy just the build jar from our first stage
#
FROM arm32v7/openjdk:11-jre-slim AS Run 
COPY --from=Build /app/BurnBanComalCounty.jar .

EXPOSE 8820 

CMD ["java","-jar","/BurnBanComalCounty.jar"]

