create table meetings (m_id int not null AUTO_INCREMENT,
  mr_id INT,
  holder_name varchar(255),
  begin_time TIMESTAMP NOT NULL,
  end_time TIMESTAMP NOT NULL,
  primary key (m_id),
  foreign key (mr_id)
  references meetingrooms (mr_id) ON DELETE CASCADE ON UPDATE RESTRICT);