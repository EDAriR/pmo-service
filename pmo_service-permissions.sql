insert into permission(id, value) values
  ('POST /friend/:userId'::varchar(255), 'POST /friend/:userId'::varchar(255)),
  ('PUT /friend/agree/:sequence'::varchar(255), 'PUT /friend/agree/:sequence'::varchar(255)),
  ('GET /friend/:userId'::varchar(255), 'GET /friend/:userId'::varchar(255)),
  ('GET /friend'::varchar(255), 'GET /friend'::varchar(255)),
  ('DELETE /friend/:sequence'::varchar(255), 'DELETE /friend/:sequence'::varchar(255)),
  ('DELETE /friend'::varchar(255), 'DELETE /friend'::varchar(255)),
  
  ('POST /follow/:userId'::varchar(255), 'POST /follow/:userId'::varchar(255)),
  ('POST /follow'::varchar(255), 'POST /follow'::varchar(255)),
  ('GET /follow/:userId'::varchar(255), 'GET /follow/:userId'::varchar(255)),
  ('GET /follow/followme'::varchar(255), 'GET /follow/followme'::varchar(255)),
  ('GET /follow'::varchar(255), 'GET /follow'::varchar(255)),
  ('DELETE /follow/:sequence'::varchar(255), 'DELETE /follow/:sequence'::varchar(255)),
  ('DELETE /follow/userId/:userId'::varchar(255), 'DELETE /follow/userId/:userId'::varchar(255)),

  ('POST /blocklist/:userId'::varchar(255), 'POST /blocklist/:userId'::varchar(255)),
  ('GET /blocklist'::varchar(255), 'GET /blocklist'::varchar(255)),
  ('GET /blocklist/:userId'::varchar(255), 'GET /blocklist/:userId'::varchar(255)),
  ('DELETE /blocklist/:sequence'::varchar(255), 'DELETE /blocklist/:sequence'::varchar(255))
  ;
 
