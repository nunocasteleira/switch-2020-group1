# 1. Implementação

## Geral

- Mapper de primitivos para DTOs (saída)
- Assembler VOs para primitivos ou vice-versa (DTO de Entrada para VOs)

## Controller

### Tipo de Entrada

- Um parâmetro: primitivo.
- Mais que um parâmetro: DTO.

- Path Variable excluída do DTO.

- Usar anotação @NonNull e @NotBlank para verificar DTOs.

### Links

Num método à parte.

### Tipo de Retorno

- POST :
  Objeto dentro DTO

- GET :
  Objeto dentro DTO

- PUT :
  Objeto dentro DTO

## Service

Não ter lógica de negócio no Service. Validações devem estar no repositório.

Quando instanciarmos um objeto que depende de outro objeto, deveremos recorrer às Factories. Para
casos em que iremos guardar o objecto em mais do que um local (que envolva mais do que um agregado),
devemos aplicar Open Closed Transaction.
<https://www.baeldung.com/transaction-configuration-with-jpa-and-spring>

## Assembler

Instanciar os VOs no Assembler e instanciar o objeto no Service. Instanciar dois VO diretamente Mais
do que dois VOs devemos fazer um "DTO" que irá retornar para o service.

## Repository

Devemos instanciar aqui os objetos JPA. Interface do RepositoryJPA deve ser chamada aqui.

Instanciação de objetos JPA e guardar chamando a interface.

No caminho contrário, o Assembler instancia os VOs de domínio e o objecto de domínio é instanciando
no Repository.

## RepositoryJPA

## Domain

## Data Model

Assembler JPA deve converter de VO para primitivo. VO de JPA deve receber um primitivo.

# 2.Documentação

## Sequence Diagram

- Regra dos dois pontos objecto\n: Classe
- Interfaces nos diagramas de sequência (services e repository)
- Fazer um SD geral para o Mapper e DTO
- Cortar instanciação de VOs no DTO e Assembler
- Não detalhar nos métodos getBy

## Class Diagram
