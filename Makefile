.PHONY: build

build: clean
	docker-compose build

test: build
	docker-compose run app ./gradlew test

clean:
	docker-compose down -v --rmi local

api_definition: clean
	docker-compose up swagger

start: build
	docker-compose up