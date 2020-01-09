DO
$do$
BEGIN 
   FOR i IN 1..100 LOOP
            insert into products (name, description, price) values (md5(random()::text), md5(random()::text), 123.5);
   END LOOP;
END
$do$;
