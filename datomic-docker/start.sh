#!/bin/bash

if [[ -z "${DATOMIC_LICENSE_KEY}" ]]; then
  echo 'you need to set DATOMIC_LICENSE_KEY env var: \ndocker run -e DATOMIC_LICENSE_KEY=your-license ...'
  exit 1
fi

echo "license-key=$DATOMIC_LICENSE_KEY" >> /datomic-pro/config/dev-transactor-template.properties

/datomic-pro/bin/transactor /datomic-pro/config/dev-transactor-template.properties
