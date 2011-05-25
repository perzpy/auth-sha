
/*==============================================================*/
/* Table: BAS_AUTH                                              */
/*==============================================================*/
create table BAS_AUTH  (
   ID                   VARCHAR2(36)                    default sys_guid() not null,
   NAME                 VARCHAR2(50)                    not null,
   DESCRIPTION          VARCHAR2(100),
   constraint PK_BAS_AUTH primary key (ID)
);

comment on table BAS_AUTH is
'权限表';

/*==============================================================*/
/* Table: BAS_AUTHMODU                                          */
/*==============================================================*/
create table BAS_AUTHMODU  (
   ID                   VARCHAR2(36)                    default sys_guid() not null,
   AUTH_ID              VARCHAR2(36)                    not null,
   MODU_ID              VARCHAR2(36)                    not null,
   constraint PK_BAS_AUTHMODU primary key (ID)
);

comment on table BAS_AUTHMODU is
'权限菜单表';

/*==============================================================*/
/* Table: BAS_MENU                                              */
/*==============================================================*/
create table BAS_MENU  (
   ID                   VARCHAR2(36)                    default sys_guid() not null,
   NAME                 VARCHAR2(100)                   not null,
   LEVELCODE            VARCHAR2(200)                   not null,
   SEQ                  NUMBER                         default 0,
   DESCRIPTION          VARCHAR2(100),
   MODU_ID              VARCHAR2(36),
   constraint PK_BAS_MENU primary key (ID)
);

comment on table BAS_MENU is
'菜单表';

/*==============================================================*/
/* Table: BAS_MODU                                              */
/*==============================================================*/
create table BAS_MODU  (
   ID                   VARCHAR2(36)                    default sys_guid() not null,
   NAME                 VARCHAR2(100),
   URL                  VARCHAR2(400),
   DESCRIPTION          VARCHAR2(500),
   CONSTRAINT PK_BAS_MODU PRIMARY KEY (ID)
);

Comment on table BAS_MODU is
'模块表';

/*==============================================================*/
/* Table: BAS_ROLE                                              */
/*==============================================================*/
create table BAS_ROLE  (
   ID                   VARCHAR2(36)                    default sys_guid() not null,
   NAME                 VARCHAR2(50)                    not null,
   DESCRIPTION          VARCHAR2(100),
   constraint PK_BAS_ROLE primary key (ID)
);

comment on table BAS_ROLE is
'角色表';

/*==============================================================*/
/* Table: BAS_ROLEAUTH                                          */
/*==============================================================*/
create table BAS_ROLEAUTH  (
   ID                   VARCHAR2(36)                    default sys_guid() not null,
   ROLE_ID              VARCHAR2(36)                    not null,
   AUTH_ID              VARCHAR2(36)                    not null,
   constraint PK_BAS_ROLEAUTH primary key (ID)
);

comment on table BAS_ROLEAUTH is
'角色权限表';

/*==============================================================*/
/* Table: BAS_ROLEMENU                                          */
/*==============================================================*/
create table BAS_ROLEMENU  (
   ID                   VARCHAR2(36)                    default sys_guid() not null,
   ROLE_ID              VARCHAR2(36)                    not null,
   MENU_ID              VARCHAR2(36)                    not null,
   constraint PK_BAS_ROLEMENU primary key (ID)
);

comment on table BAS_ROLEMENU is
'角色菜单表';

/*==============================================================*/
/* Table: BAS_USER                                              */
/*==============================================================*/
create table BAS_USER  (
   ID                   VARCHAR2(36)                    default sys_guid() not null,
   NAME                 VARCHAR2(100)                   not null,
   PASSWORD             VARCHAR2(36)                    not null,
   DESCRIPTION          VARCHAR2(100),
   DEPARTMENT			VARCHAR2(50),
   TEL					VARCHAR2(20),
   MAIL					VARCHAR2(20),
   constraint PK_BAS_USER primary key (ID)
);

comment on table BAS_USER is
'用户表';

/*==============================================================*/
/* Table: BAS_USERROLE                                          */
/*==============================================================*/
create table BAS_USERROLE  (
   ID                   VARCHAR2(36)                    default sys_guid() not null,
   USER_ID              VARCHAR2(36)                    not null,
   ROLE_ID              VARCHAR2(36)                    not null,
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

