# CSV normalizer application

First, navigate to the application directory then build the Docker image 

```bash
docker build -t normalizer .
```
Run the Docker container using the provided sample.csv file:
```bash
docker run -i normalizer < sample.csv > output.csv
```
If you want to use a different CSV file, provide the full path to the file when running the Docker container:
```bash
docker run -i normalizer < /path/to/your/input.csv > /path/to/your/output.csv
```

