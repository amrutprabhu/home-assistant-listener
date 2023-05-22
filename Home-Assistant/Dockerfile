FROM ghcr.io/graalvm/graalvm-ce:22.3.1 as builder

COPY . /project
RUN cd /project && ./mvnw clean -Pnative package -DskipTests

FROM ubuntu:22.04
RUN mkdir /project
COPY --from=builder /project/target/* /project/
CMD ["/bin/bash"]
ENTRYPOINT ["/project/home-assistant"]