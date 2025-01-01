.DEFAULT_GOAL := build-run

clean:
	make -C app clean

run-dist:
	make -C app run-dist

build:
	make -C app clean build

install:
	make -C app clean install

run:
	make -C app run

lint:
	make -C app lint

test:
	make -C app test

report:
	make -C app report

build-run: build run

.PHONY: build
