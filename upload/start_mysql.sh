docker-compose -f docker-compose-mysql.yml up -d && docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' smartchoice-mysql