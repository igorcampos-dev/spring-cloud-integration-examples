#!/usr/bin/env bash
set -e

QUEUE_URL="http://sqs.us-east-1.localhost.localstack.cloud:4566/000000000000/$QUEUE_NAME"
DLQ_ARN="arn:aws:sqs:us-east-1:000000000000:$QUEUE_NAME-dlq"

echo "[SQS] Creating DLQ: $QUEUE_NAME-dlq"
awslocal sqs create-queue --queue-name $QUEUE_NAME-dlq

echo "[SQS] Creating main queue: $QUEUE_NAME"
awslocal sqs create-queue --queue-name $QUEUE_NAME

awslocal sqs set-queue-attributes \
  --queue-url "$QUEUE_URL" \
  --attributes "{
    \"RedrivePolicy\": \"{\\\"deadLetterTargetArn\\\":\\\"$DLQ_ARN\\\",\\\"maxReceiveCount\\\":3}\"
  }"

echo "[SQS] DLQ successfully attached to queue"
