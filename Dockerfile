FROM mysql:8.0

ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=estore_db
ENV MY_SQL_USER=user
ENV MYSQL_PASSWORD=password

EXPOSE 3306