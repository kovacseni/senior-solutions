create table locations (id bigint not null auto_increment, latitude double precision, longitude double precision, location_name varchar(255), primary key (id));

insert into locations (latitude, longitude, location_name) values (47.497912, 19.040235, 'Budapest');

insert into locations (latitude, longitude, location_name) values (-33.88223, 151.33140, 'Sydney');