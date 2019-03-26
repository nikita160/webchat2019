DROP DATABASE IF EXISTS webchat;
 
CREATE DATABASE webchat DEFAULT CHARACTER SET 'utf8';
 
USE webchat;
 
CREATE TABLE users
(
  user_id int unsigned NOT NULL auto_increment,
  name varchar(20) NOT NULL UNIQUE,
  password varchar(20) NOT NULL,
  PRIMARY KEY (user_id)
) ;
 
CREATE TABLE tokens
(
  token_id int unsigned NOT NULL  auto_increment,
  token varchar(255),
  user_name varchar (255),
  PRIMARY KEY (token_id)
  -- FOREIGN KEY (user_id) REFERENCES users(user_id)
) ;

CREATE TABLE contacts
(
  contact_id int unsigned NOT NULL  auto_increment,
  PRIMARY KEY (contact_id),
  owner  int unsigned,
  contact int unsigned,
  CONSTRAINT FK_user1 FOREIGN KEY (owner) REFERENCES users(user_id),
  CONSTRAINT FK_user2 FOREIGN KEY (contact) REFERENCES users(user_id),
  CHECK (owner<>contact)
);


-- filling:

INSERT INTO users (name, password) VALUES ('admin','admin');
INSERT INTO users (name, password) VALUES ('root','root');
INSERT INTO users (name, password) VALUES ('nikita','qwerty');
INSERT INTO users (name, password) VALUES ('kot','qwerty');


INSERT INTO contacts (owner, contact) VALUES (1,2);
INSERT INTO contacts (owner, contact) VALUES (1,3);
INSERT INTO contacts (owner, contact) VALUES (2,4);

-- test select:

SELECT contacts.contact_id, contacts.owner, u1.name AS name_1, contacts.contact, u2.name AS name_2
FROM contacts
LEFT JOIN users AS u1 ON contacts.owner = u1.user_id
LEFT JOIN users AS u2 ON contacts.contact = u2.user_id
;

SELECT name FROM users WHERE user_id = ANY (SELECT contact FROM contacts WHERE owner=1);

SELECT name FROM users
WHERE user_id =
ANY (SELECT contact FROM contacts
WHERE owner=(
SELECT user_id FROM users WHERE name='admin'));

SELECT * FROM contacts WHERE owner = (SELECT user_id FROM users WHERE name = 'admin')
AND contact = (SELECT user_id FROM users WHERE name = 'root');


SELECT contacts.contact_id, contacts.owner, u1.name AS name_1, contacts.contact, u2.name AS name_2
FROM contacts
LEFT JOIN users AS u1 ON contacts.owner = u1.user_id
LEFT JOIN users AS u2 ON contacts.contact = u2.user_id
;

