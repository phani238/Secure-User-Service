# Kafka Setup Guide

## Supported Platforms

- Windows
- macOS (Intel/Homebrew)

## macOS Installation

```bash
brew install kafka
brew services start kafka
```

## Verify Installation

```bash
brew list | grep kafka
brew services list | grep kafka
lsof -i :9092
```

Expected:

- Kafka service: started
- Port 9092: listening

## List Topics

```bash
/usr/local/opt/kafka/bin/kafka-topics --bootstrap-server localhost:9092 --list
```

If no topics appear, Kafka is working correctly and no topics have been created yet.

## Stop Kafka

```bash
brew services stop kafka
```

## Start Again

```bash
brew services start kafka
```