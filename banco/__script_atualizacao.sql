CREATE EXTENSION unaccent;


-- DROP TABLE usuario;

CREATE TABLE usuario
(
  id serial NOT NULL,
  email character varying(100) NOT NULL,
  senha character varying(200) NOT NULL,
  CONSTRAINT usuario_id_pk PRIMARY KEY (id),
  CONSTRAINT usuario_email_uk UNIQUE (email)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE usuario
  OWNER TO postgres;

  CREATE TABLE classe
(
  id serial NOT NULL,
  descricao character varying(200) NOT NULL,
  CONSTRAINT classe_id_pk PRIMARY KEY (id),
  CONSTRAINT classe_descricao_uk UNIQUE (descricao)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE classe
  OWNER TO postgres;
  
  CREATE TABLE especialidade
(
  id serial NOT NULL,
  descricao character varying(200) NOT NULL,
  CONSTRAINT especialidade_id_pk PRIMARY KEY (id),
  CONSTRAINT especialidade_descricao_uk UNIQUE (descricao)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE especialidade
  OWNER TO postgres;

  
  
  -- DROP TABLE personagem;

CREATE TABLE personagem
(
  id serial NOT NULL,
  nome character varying(200) NOT NULL,
  vida numeric(10,2) NOT NULL,
  defesa numeric(10,2),
  vel_ataque numeric(10,2),
  vel_movimento numeric(10,2),
  classe_id integer NOT NULL,
  imagem bytea,
  observacao text,
  dano numeric(10,2) NOT NULL,
  CONSTRAINT personagem_id_pk PRIMARY KEY (id),
  CONSTRAINT personagem_classe_id_fk FOREIGN KEY (classe_id)
      REFERENCES classe (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE personagem
  OWNER TO postgres;
  
  -- DROP TABLE personagem_especialidade;

CREATE TABLE personagem_especialidade
(
  id serial NOT NULL,
  personagem_id integer NOT NULL,
  especialidade_id integer NOT NULL,
  CONSTRAINT personagem_especialidade_id_pk PRIMARY KEY (id),
  CONSTRAINT especialidade_personagem_id_fk FOREIGN KEY (personagem_id)
      REFERENCES personagem (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT personagem_especialidade_especialidade_id_fk FOREIGN KEY (especialidade_id)
      REFERENCES especialidade (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE personagem_especialidade
  OWNER TO postgres;
  
  
  CREATE INDEX index_classe ON classe (id);

CREATE INDEX especialidade_index ON especialidade (id);

CREATE INDEX usuario_index ON usuario (id);

CREATE INDEX personagem_classe_index ON personagem (classe_id);

CREATE INDEX personagem_index ON personagem (id);

CREATE INDEX personagemespecialidade_index ON personagem_especialidade (id);

CREATE INDEX personagemespecialidade_esp_index ON personagem_especialidade (especialidade_id);

CREATE INDEX personagemespecialidade_person_index ON personagem_especialidade (personagem_id);


