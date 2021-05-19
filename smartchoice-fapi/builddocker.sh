docker build -f Dockerfile --no-cache -t smartchoice-api:latest .
docker save -o smartchoice-api.latest.tar smartchoice-api:latest