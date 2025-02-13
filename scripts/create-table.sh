aws dynamodb create-table \
    --table-name Users \
    --attribute-definitions \
        AttributeName=id,AttributeType=S \
        AttributeName=cpf,AttributeType=S \
        AttributeName=email,AttributeType=S \
    --key-schema \
        AttributeName=id,KeyType=HASH \
        AttributeName=cpf,KeyType=RANGE \
    --billing-mode PAY_PER_REQUEST \
    --global-secondary-indexes '[
        {
            "IndexName": "EmailCpfIndex",
            "KeySchema": [
                {"AttributeName": "email", "KeyType": "HASH"},
                {"AttributeName": "cpf", "KeyType": "RANGE"}
            ],
            "Projection": {
                "ProjectionType": "ALL"
            }
        }
    ]'