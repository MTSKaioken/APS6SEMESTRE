## Proposta do projeto
 Projeto ainda em progresso, para a disciplina de Atividades Praticas Supervisionadas, do curso de Ciência da Computação, da Universidade Paulista.

PS: assim que concluido, serão adicionadas mais informações sobre o projeto.

## Requisitos
1 - JDK Java 8 ou Superior

2 - Maven 3.1.0 ou Superior

## Contribuindo no projeto

1 - a pasta resources tem as imagens que serão utilizadas para testes

1.1 - caso queira testar com outras digitais use o software [SFinGeDemo](https://biolab.csr.unibo.it/ResearchPages/sfinge_demo_download.html), ele possui ótimas parametrizações para gerar digitais ficticias. 

### feito:
- [x] implementado o algoritmo de escala de cinza da imagem
- [x] implementado o algoritmo que troca o preto e o branco da imagem
- [x] implementado o algoritmo de binarização da imagem
- [x] implementado o algoritmo de afinamento/esqueletização de zhangSuen
### pendente:
- [ ] detectação das minucias 
- [ ] Teste que detecte se o filtro no dropdown possui uma classe de implementação

## Rodando o projeto

1 - Clone o repositório
```bash
  git clone git@github.com:MTSKaioken/APS6SEMESTRE.git
```

2 - Entre na pasta raiz do projeto e compile o programa
```bash
  mvn clean install
```

3 - Execute o programa
```bash
  java -jar APS6SEMESTRE-0.0.1-SNAPSHOT.jar
```

Ou abra o projeto a partir do arquivo pom.xml (isso evita erros de indexação do maven), feito isso clique no run

## Referências
* [Valkam-Git - Dactilar](https://github.com/Valkam-Git/Dactilar/tree/c5766fd14668a9b2d94416a827681eb5ea5dfd05)
* [Razzuren - Zhang_Suen_Afinador_de_Bordas](https://github.com/Razzuren/Zhang_Suen_Afinador_de_Bordas)
