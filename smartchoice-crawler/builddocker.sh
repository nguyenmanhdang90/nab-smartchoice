docker build -f Dockerfile --no-cache -t smartchoice-crawler:latest .
docker save -o smartchoice-crawler:latest.tar smartchoice-crawler:latest