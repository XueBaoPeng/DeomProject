drop table JAVA_RULE if exists;
create table JAVA_RULE(
  id                NUMBER(11) not null,
  target            VARCHAR2(32) not null,
  file_name         VARCHAR2(32) not null,
  full_class_name   VARCHAR2(64) not null,
  simple_class_name VARCHAR2(32) not null,
  src_code          CLOB not null,
  byte_content      BLOB not null,
  create_time       DATE not null,
  create_user_id    NUMBER(11) not null,
  create_user_name  VARCHAR2(128) not null,
  update_time       DATE,
  update_user_id    NUMBER(11),
  update_user_name  VARCHAR2(128),
  is_deleted        NUMBER(1) default 0 not null,
  status            NUMBER(1) default 1 not null,
  group_name        VARCHAR2(32) not null,
  sort              NUMBER(3) default 999,
  name              VARCHAR2(512) not null,
  description       VARCHAR2(2048)
);
;
comment on column JAVA_RULE.id
  is '主键';
comment on column JAVA_RULE.target
  is '目标，一般指哪个系统';
comment on column JAVA_RULE.file_name
  is '文件名';
comment on column JAVA_RULE.full_class_name
  is '全类名';
comment on column JAVA_RULE.simple_class_name
  is '类名';
comment on column JAVA_RULE.src_code
  is '源码';
comment on column JAVA_RULE.byte_content
  is '编译后字节码';
comment on column JAVA_RULE.create_time
  is '创建时间';
comment on column JAVA_RULE.create_user_id
  is '创建用户id';
comment on column JAVA_RULE.create_user_name
  is '创建用户名称';
comment on column JAVA_RULE.update_time
  is '更新时间';
comment on column JAVA_RULE.update_user_id
  is '更新用户id';
comment on column JAVA_RULE.update_user_name
  is '更新用户名称';
comment on column JAVA_RULE.is_deleted
  is '是否已删除，1是 0否';
comment on column JAVA_RULE.status
  is '状态，1有效 0无效';
comment on column JAVA_RULE.group_name
  is '组别名称，一般指哪一系列规则';
comment on column JAVA_RULE.sort
  is '顺序（优先级）';
comment on column JAVA_RULE.name
  is '规则名称';
comment on column JAVA_RULE.description
  is '规则描述';
create unique index IDX_JAVA_RULE_FULL_CLASS_NAME on JAVA_RULE (FULL_CLASS_NAME);
alter table JAVA_RULE
  add constraint PK_JAVA_RULE primary key (ID);