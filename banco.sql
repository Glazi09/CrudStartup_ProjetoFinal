
CREATE DATABASE IF NOT EXISTS linka_startup;
USE linka_startup;


CREATE TABLE IF NOT EXISTS instituicao_parceira (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cnpj VARCHAR(18) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL
);


CREATE TABLE IF NOT EXISTS atividade (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    tipo VARCHAR(30) NOT NULL,
    horas_complementares DECIMAL(5,1) NOT NULL,
    id_instituicao INT NOT NULL,
    FOREIGN KEY (id_instituicao) REFERENCES instituicao_parceira(id)
);

-- Dados de exemplo
INSERT INTO instituicao_parceira (nome, cnpj, email) VALUES
    ('ONG Futuro Jovem', '12.345.678/0001-90', 'contato@futurojovem.org');

INSERT INTO atividade (titulo, tipo, horas_complementares, id_instituicao) VALUES
    ('Curso de Python', 'Curso', 20.0, 1);
