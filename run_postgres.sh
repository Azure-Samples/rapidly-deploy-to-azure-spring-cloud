#!/usr/bin/env bash
docker run -p 5432:5432 -e POSTGRES_USER=asc -e PGUSER=asc -e POSTGRES_PASSWORD=asc postgres:latest
