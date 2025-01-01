.DEFAULT_GOAL := build-run

clean:
	./gradlew clean

run-dist:
	./build/install/app/bin/app

build:
	./gradlew clean build

install:
	./gradlew clean install

run:
	./gradlew run

lint:
	./gradlew checkstyleMain

test:
	./gradlew test

report:
	./gradlew jacocoTestReport

build-run: build run

.PHONY: build
