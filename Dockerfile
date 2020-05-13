# Stage and thin the application
FROM open-liberty as staging

COPY --chown=1001:0 target/user-service-0.0.1-SNAPSHOT.jar \
                    /staging/fat-user-service-0.0.1-SNAPSHOT.jar

RUN springBootUtility thin \
 --sourceAppPath=/staging/fat-user-service-0.0.1-SNAPSHOT.jar \
 --targetThinAppPath=/staging/thin-user-service-0.0.1-SNAPSHOT.jar \
 --targetLibCachePath=/staging/lib.index.cache

# Build the image
FROM open-liberty

ARG VERSION=1.0
ARG REVISION=SNAPSHOT

LABEL \
  org.opencontainers.image.authors="Your Name" \
  org.opencontainers.image.vendor="Open Liberty" \
  org.opencontainers.image.url="local" \
  org.opencontainers.image.source="https://github.com/OpenLiberty/guide-spring-boot" \
  org.opencontainers.image.version="$VERSION" \
  org.opencontainers.image.revision="$REVISION" \
  vendor="Open Liberty" \
  name="hello app" \
  version="$VERSION-$REVISION" \
  summary="The hello application from the Spring Boot guide" \
  description="This image contains the hello application running with the Open Liberty runtime."

RUN cp /opt/ol/wlp/templates/servers/springBoot2/server.xml /config/server.xml

COPY --chown=1001:0 --from=staging /staging/lib.index.cache /lib.index.cache
COPY --chown=1001:0 --from=staging /staging/thin*.jar \
                    /config/dropins/spring/thin*.jar

RUN configure.sh