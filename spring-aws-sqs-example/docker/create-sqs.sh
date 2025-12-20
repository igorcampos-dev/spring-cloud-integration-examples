#!/usr/bin/env bash
set -e

: "${QUEUE_NAME:?Environment variable QUEUE_NAME is required}"

echo "Creating SQS queue: $QUEUE_NAME"

awslocal sqs create-queue \
  --queue-name "$QUEUE_NAME"

echo "SQS queue '$QUEUE_NAME' created."