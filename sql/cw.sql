BEGIN TRANSACTION;

CREATE TABLE KEYWORD (
    ID INTEGER PRIMARY KEY NOT NULL, 
    WORD TEXT NOT NULL);

CREATE TABLE MASTER_CHANNEL (
    ID INTEGER PRIMARY KEY NOT NULL, 
    NAME TEXT NOT NULL);
    
CREATE TABLE SUB_CHANNEL (
    ID INTEGER PRIMARY KEY NOT NULL, 
    NAME TEXT NOT NULL, 
    MASTER_CHANNEL_ID NUMERIC NOT NULL,
    FOREIGN KEY(MASTER_CHANNEL_ID) REFERENCES MASTER_CHANNEL(ID));
    
CREATE TABLE SUBCHANNEL_KEYWORD (
    SUB_CHANNEL_ID NUMERIC NOT NULL, 
    KEYWORD_ID NUMERIC NOT NULL,
    PRIMARY KEY (SUB_CHANNEL_ID, KEYWORD_ID),
    FOREIGN KEY(SUB_CHANNEL_ID) REFERENCES SUB_CHANNEL(ID),
    FOREIGN KEY(KEYWORD_ID) REFERENCES KEYWORD(ID));

CREATE TABLE ARTICLE (
    ID INTEGER PRIMARY KEY NOT NULL, 
    TITLE TEXT NOT NULL, 
    AUTHOR TEXT, 
    MIME_TYPE TEXT, 
    LANGUAGE_CODE TEXT, 
    DATE TEXT, 
    URL TEXT, 
    PLAIN_TEXT BLOB, 
    DESCRIPTION TEXT, 
    SUB_CHANNEL_ID NUMERIC NOT NULL,
    FOREIGN KEY(SUB_CHANNEL_ID) REFERENCES SUB_CHANNEL(ID));
    
CREATE TABLE ARTICLE_KEYWORD (
    ARTICLE_ID NUMERIC NOT NULL, 
    KEYWORD_ID NUMERIC NOT NULL,
    PRIMARY KEY (ARTICLE_ID, KEYWORD_ID),
    FOREIGN KEY(ARTICLE_ID) REFERENCES ARTICLE(ID),
    FOREIGN KEY(KEYWORD_ID) REFERENCES KEYWORD(ID));
    
COMMIT;
