# ⛰️ Manaslu ⛰️

Spring Boot × gRPC kotlin Client Mock Application

## Usage

### build

```shell
./gradlew bootjar
```

### run

```shell
./manaslu.sh
```

#### setting

環境変数で次のsettingができます

- HOST  
callするgRPC ServerのHOST

- PORT  
callするgRPC ServerのPORT

- ENABLE_SSL  
gRPC通信でのSSLの有効化

- PARALLELS  
API Callの並列数

- EXECUTION_TIME  
Batchの実行時間

- ENABLE_CLIENTLB  
Client LoadBalancingの有効化

- LOGGING_LEVEL_COM_ARCHETYPE_MANASLU  
Application Log Level

- LOGGING_LEVEL_IO_GRPC  
gPRC Log Level

- LOGGING_LEVEL_IO_NETTY  
netty Log Level
