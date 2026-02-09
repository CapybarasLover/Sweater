FROM ubuntu:latest
LABEL authors="Petra"

ENTRYPOINT ["top", "-b"]