
/*==============================================================*/
/* Table: BAS_AUTH                                              */
/*==============================================================*/
create table BAS_AUTH  (
   ID                   int 		not null auto_increment,
   NAME                 VARCHAR(50) not null,
   DESCRIPTION          VARCHAR(100),
   constraint PK_BAS_AUTH primary key (ID)
);

comment on table BAS_AUTH is
'权限表';

/*==============================================================*/
/* Table: BAS_AUTHMODU                                          */
/*==============================================================*/
create table BAS_AUTHMODU  (
   ID                   int 		not null auto_increment,
   AUTH_ID              VARCHAR(36)                    not null,
   MODU_ID              VARCHAR(36)                    not null,
   constraint PK_BAS_AUTHMODU primary key (ID)
);

comment on table BAS_AUTHMODU is
'权限菜单表';

/*==============================================================*/
/* Table: BAS_MENU                                              */
/*==============================================================*/
create table BAS_MENU  (
   ID                   int 		not null auto_increment,
   NAME                 VARCHAR(100)                   not null,
   LEVELCODE            VARCHAR(200)                   not null,
   SEQ                  NUMBER                         default 0,
   DESCRIPTION          VARCHAR(100),
   MODU_ID              VARCHAR(36),
   constraint PK_BAS_MENU primary key (ID)
);

comment on table BAS_MENU is
'菜单表';

/*==============================================================*/
/* Table: BAS_MODU                                              */
/*==============================================================*/
create table BAS_MODU  (
   ID                   int 		not null auto_increment,
   NAME                 VARCHAR(100),
   URL                  VARCHAR(400),
   DESCRIPTION          VARCHAR(500),
   CONSTRAINT PK_BAS_MODU PRIMARY KEY (ID)
);

Comment on table BAS_MODU is
'模块表';

/*==============================================================*/
/* Table: BAS_ROLE                                              */
/*==============================================================*/
create table BAS_ROLE  (
   ID                  int 		not null auto_increment,
   NAME                 VARCHAR(50)                    not null,
   DESCRIPTION          VARCHAR(100),
   constraint PK_BAS_ROLE primary key (ID)
);

comment on table BAS_ROLE is
'角色表';

/*==============================================================*/
/* Table: BAS_ROLEAUTH                                          */
/*==============================================================*/
create table BAS_ROLEAUTH  (
   ID                   int 		not null auto_increment,
   ROLE_ID              VARCHAR(36)                    not null,
   AUTH_ID              VARCHAR(36)                    not null,
   constraint PK_BAS_ROLEAUTH primary key (ID)
);

comment on table BAS_ROLEAUTH is
'角色权限表';

/*==============================================================*/
/* Table: BAS_ROLEMENU                                          */
/*==============================================================*/
create table BAS_ROLEMENU  (
   ID                   int 		not null auto_increment,
   ROLE_ID              VARCHAR(36)                    not null,
   MENU_ID              VARCHAR(36)                    not null,
   constraint PK_BAS_ROLEMENU primary key (ID)
);

comment on table BAS_ROLEMENU is
'角色菜单表';

/*==============================================================*/
/* Table: BAS_USER                                              */
/*==============================================================*/
create table BAS_USER  (
   ID                   int 		not null auto_increment,
   NAME                 VARCHAR(100)                   not null,
   PASSWORD             VARCHAR(36)                    not null,
   DESCRIPTION          VARCHAR(100),
   DEPARTMENT VARCHAR(50),
   TEL VARCHAR(20),
   MAIL VARCHAR(20),
   constraint PK_BAS_USER primary key (ID)
);

comment on table BAS_USER is
'用户表';

/*==============================================================*/
/* Table: BAS_USERROLE                                          */
/*==============================================================*/
create table BAS_USERROLE  (
   ID                   int 		not null auto_increment,
   USER_ID              VARCHAR(36)                    not null,
   ROLE_ID              VARCHAR(36)                    not null,
   constraint PK_BAS_USERROLE primary key (ID)
);

comment on table BAS_USERROLE is
'用户角色表';

alter table BAS_AUTHMODU
   add constraint FK_BAS_AUTH_FK_AUTHME_BAS_AUTH foreign key (AUTH_ID)
      references BAS_AUTH (ID);

alter table BAS_AUTHMODU
   add constraint FK_BAS_AUTH_FK_AUTHMO_BAS_MODU foreign key (MODU_ID)
      references BAS_MODU (ID);

alter table BAS_MENU
   add constraint FK_BAS_MENU_FK_MENU_M_BAS_MODU foreign key (MODU_ID)
      references BAS_MODU (ID);

alter table BAS_ROLEAUTH
   add constraint FK_BAS_ROLE_FK_ROLEAU_BAS_AUTH foreign key (AUTH_ID)
      references BAS_AUTH (ID);

alter table BAS_ROLEAUTH
   add constraint FK_BAS_ROLE_FK_ROLEAU_BAS_ROLE foreign key (ROLE_ID)
      references BAS_ROLE (ID);

alter table BAS_ROLEMENU
   add constraint FK_BAS_ROLE_FK_ROLEME_BAS_MENU foreign key (MENU_ID)
      references BAS_MENU (ID);

alter table BAS_ROLEMENU
   add constraint FK_BAS_ROLE_FK_ROLEME_BAS_ROLE foreign key (ROLD_ID)
      references BAS_ROLE (ID);

alter table BAS_USERROLE
   add constraint FK_BAS_USER_FK_USERRO_BAS_ROLE foreign key (ROLE_ID)
      references BAS_ROLE (ID);

alter table BAS_USERROLE
   add constraint FK_BAS_USER_FK_USERRO_BAS_USER foreign key (USER_ID)
      references BAS_USER (ID);

INSERT INTO BAS_MODU (ID,NAME, URL) VALUES (0,'空模块', '');
INSERT INTO BAS_MODU (NAME, URL) VALUES ('角色管理', '');
INSERT INTO BAS_MODU (NAME, URL) VALUES ('角色列表', 'manage/roleList.html?action=ROLELIST');
INSERT INTO BAS_MODU (NAME, URL) VALUES ('角色添加', 'manage/roleInfo.html?action=ROLEADD');


INSERT INTO BAS_MENU (NAME,LEVELCODE,SEQ,MODU_ID) VALUES('角色管理','00000000010','3',0)
INSERT INTO BAS_MENU (NAME,LEVELCODE,SEQ,MODU_ID) 
VALUES('角色列表','0000000001000001','3','9DF5DDFF17AA05C0E040007F01006D48');
INSERT INTO BAS_MENU (NAME,LEVELCODE,SEQ,MODU_ID) 
VALUES('角色添加','0000000001000002','3','9DF5DDFF17AB05C0E040007F01006D48');

INSERT INTO BAS_ROLEMENU (ROLE_ID,MENU_ID) VALUES ('2c9083502e937e15012e94111be00000','9DF5DDFF17AC05C0E040007F01006D48');
INSERT INTO BAS_ROLEMENU (ROLE_ID,MENU_ID) VALUES ('2c9083502e937e15012e94111be00000','9DF5DDFF17AD05C0E040007F01006D48');
INSERT INTO BAS_ROLEMENU (ROLE_ID,MENU_ID) VALUES ('2c9083502e937e15012e94111be00000','9DF5DDFF17AE05C0E040007F01006D48');

--缺少用户信息

