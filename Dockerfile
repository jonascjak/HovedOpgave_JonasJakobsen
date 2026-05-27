FROM ubuntu:latest
LABEL authors="Jonas Jakobsen"

ENTRYPOINT ["top", "-b"]