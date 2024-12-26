# Export keycloak realm and user config

Mount keycloak data directory to ./keycloak_data, add this line to compose file:
```
    volumes:
      - ./keycloak_data:/opt/keycloak/data

```

Wait until keycloak started, run bash in container:
```
docker exec -it docker-keycloak-1 bash
```  

Setup demo configuration:

```
cd /opt/keycloak/bin/
./kcadm.sh config credentials --server http://localhost:8080 --realm master --user user --password secret
./kcadm.sh create realms -s realm=DemoBookShop -s enabled=true
./kcadm.sh create roles -r DemoBookShop -s name=employee
./kcadm.sh create roles -r DemoBookShop -s name=customer
./kcadm.sh create users -r DemoBookShop -s username=alice -s firstName=Alice -s lastName=Brown -s enabled=true
./kcadm.sh create users -r DemoBookShop -s username=bob -s firstName=Bob -s lastName=Charles -s enabled=true
./kcadm.sh add-roles -r DemoBookShop --uusername alice --rolename employee --rolename customer
./kcadm.sh add-roles -r DemoBookShop --uusername bob --rolename customer
./kcadm.sh set-password -r DemoBookShop --username alice --new-password secret
./kcadm.sh set-password -r DemoBookShop --username bob --new-password secret
./kcadm.sh create clients -r DemoBookShop -s clientId=edge-service -s enabled=true -s publicClient=false -s secret=secret -s 'redirectUris=["http://localhost:9000","http://localhost:9000/login/oauth2/code/*"]'
```
Stop keycloak and export export config:
```
docker run --rm \
    --name keycloak_exporter \
    -v ./keycloak_data:/opt/keycloak/data \
    quay.io/keycloak/keycloak:26.0 \
    export \
    --realm DemoBookShop \
    --dir /opt/keycloak/data \
    --users realm_file
```

## References
https://github.com/keycloak/keycloak/issues/20442