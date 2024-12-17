podman run -d \
    --name postgres \
    -e POSTGRES_USER=user \
    -e POSTGRES_PASSWORD=secret \
    -e POSTGRES_DB=dbs_catalog \
    -p 5432:5432 postgres:14.4
