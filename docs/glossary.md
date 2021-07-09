# Glossary

In this file it will be described important terms and its definition.

Remember, it is alphabetically ordered.

| Term                            | PT translation                     |
| ------------------------------- | ---------------------------------- |
| [Account]                       | Conta                              |
| [Account Owner](#account-owner) | Detentor da Conta                  |
| [Bank Account]                  | Conta bancária de débito           |
| [Bank Savings Account]          | Conta bancária de poupança         |
| [Card Token]                    | Dígitos representantes do cartão   |
| [Cash Account]                  | Conta de dinheiro                  |
| [Category]                      | Categoria                          |
| [Category Tree]                 | Árvore de categorias               |
| [Child Category]                | Categoria filho                    |
| [Credit Card Account]           | Conta de Cartão de Crédito         |
| [Credit Limit]                  | Limite de Crédito                  |
| [Family]                        | Família                            |
| [Family Administrator]          | Administrador da família           |
| [Family Category]               | Categoria personalizada da família |
| [Family Member]                 | Membro da família                  |
| [Issuer]                        | Emissor                            |
| [Payment]                       | Pagamento                          |
| [Profile Information]           | Informação do perfil               |
| [Relationship]                  | Relação                            |
| [Relationship Type]             | Tipo de relação                    |
| [Standard Category]             | Categoria por defeito              |
| [Transaction / Movement]        | Transação / Movimento              |
| [Transfer]                      | Transferência                      |


## Family
[family]: #family
_Família_

Families gather the core responsibility of aggregating [family members](#family-member), and it is around family
that the whole app structure will work.
Families are created with a family name, as the family surname.

### Family Member
[family member]: #family-member
_Membro da família_

[Families](#family) are composed by family members.
Each member is a person in the real world.

### Family Administrator

[family administrator]: #family-administrator
_Administrador de família_

[Families](#family) has one (and only one) family administrator. This person is also a member of that family but has more responsibilities (and therefore more options and control) managing the family.
They are responsible to add new [family members](#family-member) and [family categories](#family-category), to name a few.

## Profile Information
[profile information]: #profile-information
_Informação do perfil_

All the information about a [family member] including name, date of birth, phone number, email address, vatNumber number and address.

## Category
[category]: #category
_Categoria_

Categories are used to classify each transaction. They can be [standard](#standard-category) or [family categories](#family-category), being the difference of their source.
Categories can have [child categories](#child-category).

### Child category
[child category]: #child-category
_Categoria filho_

Child category means that some other [category] is its parent. It means that they share some kind of relation between them (of meaning or content).

### Standard Category
[standard category]: #standard-category
_Categoria por defeito_

Standard [categories](#category) are shipped with the application.

### Family Category
[family category]: #family-category
_Categoria personalizada_

Family [categories](#category) are added by each [family administrator], and belongs only to one [family].

### Category Tree
[category tree]: #category-tree
_Árvore de categorias_

Category tree shows [categories](#category) and their [children](#child-category).

## Relationship
[relationship]: #relationship
_Relação_

Family relationship between two [family members](#family-member) using  the level of the main user, as reference. It 
is characterized by the relationship type (designation of the relation), and the two members involved.

### Relationship Type

[relationship type]: #relationship-type

_Tipo de relação_\
Designation of the [relationship](#relationship), which includes the terms presented below.

| Term                   | PT translation           | Term                     | PT translation           |
| ---------------------- | ------------------------ | ------------------------ | ------------------------ |
| Spouse                 | Esposo/Esposa            | Grandparent              | Avô(ó)
| Partner                | Companheiro(a)           | Grandchild               | Neto(a)
| Parent                 | Pai/Mãe                  | Uncle/Aunt               | Tio(a)
| Child                  | Filho(a)                 | Nephew/Niece             | Sobrinho(a)
| Sibling                | Irmão(ã)                 | Cousin                   | Primo(a)


## Account
[account]: #account
_Conta_
Record of financial transactions, activities and balance of the [family members](#family-member). There are different types of accounts.

### Account Owner
[owner]: #account-owner
_Detentor da Conta_

The [Family]/[Family Member] that owns a specific account.

### Bank Account
[Bank Account]: #bank-account
_Conta bancária de débito_

A debit [account] that is managed by a Bank entity and belongs to a [family member](#family-member).

### Bank Savings Account
[Bank Savings Account]: #bank-savings-account
_Conta bancária de poupança_

An interest-bearing deposit [account] held at a Bank or other financial institution and belongs to a [family member](#family-member).

### Cash Account
[cash account]: #cash-account
_Conta de dinheiro_

An [account] that represents the amount of cash a [family member](#family-member) or a [family](#family) has.


### Credit Card Account
[credit card account]: #credit-card-account
_Conta de Cartão de Crédito_

An [account] tied to a Credit Card, owned by a [family member].\
It has an [issuer], a [card token], a [credit limit] and a designation.

Usually there's a revolving account created by a financial institution (issuer) to
enable the owner (family member) to incur in debt, which is charged to the account, and in
which the borrower does not have to pay the outstanding balance on that
account in full every month. The borrower may be required to make a
minimum payment, based on the balance amount, but normally the borrower
may pay any amount between the minimum amount required and the full
balance.

#### Issuer
[issuer]: #issuer
_Emissor_

A financial institution that provides services.

#### Card Token
[card token]: #card-token
_Dígitos representantes_

The last 4 digits (configurable) of a card, to unambiguously represent the card.

#### Credit Limit
[credit limit]: #credit-limit
_Limite de Crédito_

The provided amount borrowed by the [issuer] to the [owner].

### Transaction / Movement
[transaction / movement]: #transaction
_Transação / Movimento_

The transfer of funds into, out of, or from an account. In the context of this project, _transaction_ and _movement_
will be used with the same purpose.

### Payment
[Payment]: #payment
_Pagamento_

Movement of money in exchange for goods or services. A payment can be made in 
the form of cash, credit card or debit card.

### Transfer
[Transfer]: #transfer
_Transferência_

Movement of money direct between accounts that can be performed physically - in 
cash - or electronically - through bank accounts.