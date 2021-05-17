docker build -f Dockerfile --no-cache -t smartchoice-log:latest .
docker save -o smartchoice-log:latest.tar smartchoice-log:latest