up:
	mvn clean package && docker compose up -d

down:
	docker compose down && docker image prune -af
