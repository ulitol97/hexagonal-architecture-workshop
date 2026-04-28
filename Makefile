.PHONY: build test

build:
	./mvnw clean compile

test:
	./mvnw test
