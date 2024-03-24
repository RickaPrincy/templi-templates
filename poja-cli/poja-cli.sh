#!/bin/bash
python -m poja \
  --app-name={{APP_NAME}} \
  --package-full-name={{PACKAGE_FULL_NAME}} \
  --region={{AWS_REGION}} \
  --with-own-vpc=true \
  --ssm-sg-id={{SG_ID}} \
  --ssm-subnet1-id={{SUBNET1_ID}}\
  --ssm-subnet2-id={{SUBNET2_ID}}

cd {{APP_NAME}}

git init .
git add --all
git commit -m "chore: init project with poja-cli"
