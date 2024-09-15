CREATE TABLE usuarios(
	id INT PRIMARY KEY AUTO_INCREMENT,

	cpf VARCHAR(11) UNIQUE NOT NULL,

	senha VARCHAR(50) NOT NULL,

	nome VARCHAR(255) NOT NULL,

	data_nascimento DATE NOT NULL,

	email VARCHAR(255) UNIQUE NOT NULL
);


CREATE TABLE ceps(
	id INT PRIMARY KEY AUTO_INCREMENT,

	numero VARCHAR(8) UNIQUE NOT NULL,

	uf VARCHAR(2) NOT NULL,

	cidade VARCHAR(255) NOT NULL,

	bairro VARCHAR(255) NOT NULL,

	rua VARCHAR(255) NOT NULL
);

CREATE TABLE imoveis(
	id INT PRIMARY KEY AUTO_INCREMENT,

	descricao VARCHAR(500) NOT NULL,

	numero VARCHAR(5) NOT NULL,

	complemento VARCHAR(255),

	valor DECIMAL(14, 2) NOT NULL,
	CHECK (valor >= 0),

	tamanho DECIMAL(8, 3) NOT NULL,
	CHECK (tamanho >= 0),

	usuario_id INT NOT NULL,
	FOREIGN KEY(usuario_id) REFERENCES usuarios(id),

	cep_id INT NOT NULL,
	FOREIGN KEY(cep_id) REFERENCES ceps(id)
);

CREATE TABLE comodos(
	id INT PRIMARY KEY AUTO_INCREMENT,
	descricao VARCHAR(50) NOT NULL
);

CREATE TABLE comodos_imoveis(
	id INT PRIMARY KEY AUTO_INCREMENT,

	quantidade INT NOT NULL,
	CHECK(quantidade >= 0),

	comodo_id INT NOT NULL,
	FOREIGN KEY(comodo_id) REFERENCES comodos(id),

	imovel_id INT NOT NULL,
	FOREIGN KEY(imovel_id) REFERENCES imoveis(id),

	UNIQUE(comodo_id, imovel_id)
);

CREATE TABLE locacoes(
	id INT PRIMARY KEY AUTO_INCREMENT,

	data_inicio DATE NOT NULL,

	data_fim DATE,

	valor DECIMAL(14, 2) NOT NULL,
	CHECK(valor >= 0),

	usuario_id INT NOT NULL,
	FOREIGN KEY(usuario_id) REFERENCES usuarios(id),

	imovel_id INT NOT NULL,
	FOREIGN KEY(imovel_id) REFERENCES imoveis(id)
);