.PHONY: build

build:
	docker-compose build

test: build
	docker-compose run app ./gradlew test