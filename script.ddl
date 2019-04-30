--@(#) script.ddl

CREATE TABLE CITY
(
	name varchar (255),
	PRIMARY KEY(name)
);

CREATE TABLE CLIENT
(
	personal_code numeric (20),
	first_name varchar (255),
	last_name varchar (255),
	phone varchar (255),
	email varchar (255),
	birth_date date,
	PRIMARY KEY(personal_code)
);

CREATE TABLE ITEM
(
	id numeric (20),
	name varchar (255),
	description varchar (255),
	width double precision,
	length double precision,
	height double precision,
	weight double precision,
	PRIMARY KEY(id)
);

CREATE TABLE ITEM_CATEGORY
(
	name varchar (255),
	id_ITEM_CATEGORY integer,
	PRIMARY KEY(id_ITEM_CATEGORY)
);

CREATE TABLE authorities
(
	id_authorities integer,
	name char (15) NOT NULL,
	PRIMARY KEY(id_authorities)
);
INSERT INTO authorities(id_authorities, name) VALUES(1, 'regular worker');
INSERT INTO authorities(id_authorities, name) VALUES(2, 'storage manager');

CREATE TABLE contract_statuses
(
	id_contract_statuses integer,
	name char (20) NOT NULL,
	PRIMARY KEY(id_contract_statuses)
);
INSERT INTO contract_statuses(id_contract_statuses, name) VALUES(1, 'waiting for approval');
INSERT INTO contract_statuses(id_contract_statuses, name) VALUES(2, 'signed');
INSERT INTO contract_statuses(id_contract_statuses, name) VALUES(3, 'declined');
INSERT INTO contract_statuses(id_contract_statuses, name) VALUES(4, 'expired');

CREATE TABLE belongs_to
(
	fk_category integer,
	fk_property numeric (20),
	PRIMARY KEY(fk_category, fk_property),
	CONSTRAINT fkc_category FOREIGN KEY(fk_category) REFERENCES ITEM_CATEGORY (id_ITEM_CATEGORY),
	CONSTRAINT fkc_property FOREIGN KEY(fk_property) REFERENCES ITEM (id)
);

CREATE TABLE WAREHOUSE
(
	address varchar (255),
	phone varchar (255),
	email varchar (255),
	id_WAREHOUSE integer,
	fk_location varchar (255) NOT NULL,
	PRIMARY KEY(id_WAREHOUSE),
	CONSTRAINT fkc_location FOREIGN KEY(fk_location) REFERENCES CITY (name)
);

CREATE TABLE EMPLOYEE
(
	time_card_number numeric (20),
	first_name varchar (255),
	last_name varchar (255),
	authority integer,
	fk_workplace integer NOT NULL,
	PRIMARY KEY(time_card_number),
	FOREIGN KEY(authority) REFERENCES authorities (id_authorities),
	CONSTRAINT fkc_workplace FOREIGN KEY(fk_workplace) REFERENCES WAREHOUSE (id_WAREHOUSE)
);

CREATE TABLE RENTABLE_AREA
(
	id numeric (20),
	width double precision,
	length double precision,
	height double precision,
	capacity double precision,
	is_heated boolean,
	outdoors boolean,
	fk_client numeric (20) NOT NULL,
	fk_building integer NOT NULL,
	PRIMARY KEY(id),
	CONSTRAINT fkc_client FOREIGN KEY(fk_client) REFERENCES CLIENT (personal_code),
	CONSTRAINT fkc_building FOREIGN KEY(fk_building) REFERENCES WAREHOUSE (id_WAREHOUSE)
);

CREATE TABLE CONTRACT
(
	id numeric (20),
	date date,
	storing_from date,
	storing_until date,
	rented_area double precision,
	status integer,
	fk_client numeric (20) NOT NULL,
	fk_employee numeric (20) NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY(status) REFERENCES contract_statuses (id_contract_statuses),
	CONSTRAINT fkc_client FOREIGN KEY(fk_client) REFERENCES CLIENT (personal_code),
	CONSTRAINT fkc_employee FOREIGN KEY(fk_employee) REFERENCES EMPLOYEE (time_card_number)
);

CREATE TABLE STORED_ITEM
(
	count numeric (20),
	id_STORED_ITEM integer,
	fk_property numeric (20) NOT NULL,
	fk_storage_area numeric (20) NOT NULL,
	fk_owner numeric (20) NOT NULL,
	PRIMARY KEY(id_STORED_ITEM),
	CONSTRAINT fkc_property FOREIGN KEY(fk_property) REFERENCES ITEM (id),
	CONSTRAINT fkc_storage_area FOREIGN KEY(fk_storage_area) REFERENCES RENTABLE_AREA (id),
	CONSTRAINT fkc_owner FOREIGN KEY(fk_owner) REFERENCES CLIENT (personal_code)
);

CREATE TABLE BILL
(
	name varchar (255),
	description varchar (255),
	ammount double precision,
	id_BILL integer,
	fk_contract numeric (20) NOT NULL,
	PRIMARY KEY(id_BILL),
	CONSTRAINT fkc_contract FOREIGN KEY(fk_contract) REFERENCES CONTRACT (id)
);

CREATE TABLE EXCISE
(
	name varchar (255),
	description varchar (255),
	amount double precision,
	id_EXCISE integer,
	fk_contract numeric (20) NOT NULL,
	PRIMARY KEY(id_EXCISE),
	CONSTRAINT fkc_contract FOREIGN KEY(fk_contract) REFERENCES CONTRACT (id)
);

CREATE TABLE includes
(
	fk_storage_area numeric (20),
	fk_document numeric (20),
	PRIMARY KEY(fk_storage_area, fk_document),
	CONSTRAINT fkc_storage_area FOREIGN KEY(fk_storage_area) REFERENCES RENTABLE_AREA (id),
	CONSTRAINT fkc_document FOREIGN KEY(fk_document) REFERENCES CONTRACT (id)
);

CREATE TABLE PAYMENT
(
	name varchar (255),
	description varchar (255),
	ammount double precision,
	date date,
	id_PAYMENT integer,
	fk_bill integer NOT NULL,
	PRIMARY KEY(id_PAYMENT),
	CONSTRAINT fkc_bill FOREIGN KEY(fk_bill) REFERENCES BILL (id_BILL)
);
