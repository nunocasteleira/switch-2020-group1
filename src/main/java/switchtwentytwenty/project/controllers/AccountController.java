package switchtwentytwenty.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import switchtwentytwenty.project.applicationservices.iservices.IFamilyAccountService;
import switchtwentytwenty.project.applicationservices.iservices.ITransactionService;
import switchtwentytwenty.project.controllers.icontrollers.*;
import switchtwentytwenty.project.applicationservices.iservices.IPersonAccountService;
import switchtwentytwenty.project.controllers.icontrollers.ICreateFamilyCashAccountController;
import switchtwentytwenty.project.controllers.icontrollers.ICreatePersonalCashAccountController;
import switchtwentytwenty.project.controllers.icontrollers.IPersonalBankAccountController;
import switchtwentytwenty.project.domain.exceptions.*;
import switchtwentytwenty.project.dto.account.AccountBalanceDTO;
import switchtwentytwenty.project.dto.account.AccountInputDTO;
import switchtwentytwenty.project.dto.account.AccountListDTO;
import switchtwentytwenty.project.dto.account.AccountOutputDTO;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
public class AccountController implements ICreateFamilyCashAccountController, IPersonalBankAccountController, ICreatePersonalCashAccountController, IGetListOfMemberAccounts, IGetAccountBalance {

    private static final String ACCOUNTS = "accounts";
    private static final String ACCOUNT_INFORMATION = "accountInformation";

    @Autowired
    private IFamilyAccountService familyAccountService;

    @Autowired
    private IPersonAccountService personalAccountService;

    @Autowired
    private ITransactionService transactionService;

    /**
     * Method to create a Family Cash Account, giving its information through an input DTO
     *
     * @param inputDTO input information DTO for family cash account
     * @return account output DTO with account information
     */
    @Override
    @PostMapping("/accounts")
    public ResponseEntity<Object> createFamilyCashAccount(@RequestBody AccountInputDTO inputDTO) {
        AccountOutputDTO accountOutputDTO;
        try {
            accountOutputDTO = familyAccountService.createFamilyCashAccount(inputDTO);
            String accountId = Long.toString(accountOutputDTO.getAccountId());
            Link selfLink =
                    linkTo(AccountController.class).slash(ACCOUNTS).slash(accountId).withRel(ACCOUNT_INFORMATION);
            accountOutputDTO.add(selfLink);
            return new ResponseEntity<>(accountOutputDTO, HttpStatus.CREATED);
        } catch (ObjectDoesNotExistException | DuplicateObjectException | InvalidAmountException | InvalidDescriptionException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Method to create a Personal Bank Account, giving its information through an input DTO
     *
     * @param inputDTO input information DTO for personal bank account
     * @return account output DTO with account information
     */
    @Override
    @PostMapping("/accounts/{personId}")
    public ResponseEntity<Object> createPersonalBankAccount(@RequestBody AccountInputDTO inputDTO, @PathVariable("personId") String personId) {
        try {
            AccountOutputDTO accountOutputDTO = personalAccountService.createBankAccount(inputDTO, personId);
            String accountId = Long.toString(accountOutputDTO.getAccountId());
            Link selfLink =
                    linkTo(AccountController.class).slash(ACCOUNTS).slash(accountId).withRel(ACCOUNT_INFORMATION);
            accountOutputDTO.add(selfLink);
            return new ResponseEntity<>(accountOutputDTO, HttpStatus.CREATED);
        } catch (ObjectDoesNotExistException | DuplicateObjectException | InvalidAmountException | InvalidDescriptionException | InvalidProviderException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Method to create a Personal Cash Account, giving its information through an input DTO and the
     * personId.
     *
     * @param accountInputDTO input DTO with the necessary data to create the personal cash
     *                        account.
     * @param personId        the id of the person the personal account will be created.
     * @return account output DTO with the personal cash account information.
     */
    @PostMapping("accounts/cash/{personId}")
    public ResponseEntity<Object> createPersonalCashAccount(@RequestBody AccountInputDTO accountInputDTO, @PathVariable("personId") String personId) {
        try {
            AccountOutputDTO accountOutputDTO = personalAccountService.createPersonalCashAccount(accountInputDTO, personId);

            String accountId = Long.toString(accountOutputDTO.getAccountId());
            Link selfLink =
                    linkTo(AccountController.class).slash(ACCOUNTS).slash(accountId).withRel(ACCOUNT_INFORMATION);
            accountOutputDTO.add(selfLink);
            return new ResponseEntity<>(accountOutputDTO, HttpStatus.CREATED);
        } catch (ObjectDoesNotExistException | DuplicateObjectException | InvalidAmountException | InvalidDescriptionException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * Method to get List of the Family Member's accounts
     *
     * @param personId the email of the Family Member
     * @return account List output DTO with List of accounts
     */

    @Override
    @GetMapping("/accounts/{personId}")
    public ResponseEntity<Object> getAccountList(@NonNull @PathVariable String personId) {
        AccountListDTO accountListDTO;
        try {
            accountListDTO = personalAccountService.getMemberAccountList(personId);

            Link selfLink =
                    linkTo(AccountController.class).slash(ACCOUNTS).slash(personId).withRel("list");
            accountListDTO.add(selfLink);
            return new ResponseEntity<>(accountListDTO, HttpStatus.OK);
        } catch (ObjectDoesNotExistException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @GetMapping("/accounts/{personId}/{accountId}")
    public ResponseEntity<Object> getAccountBalance(@NonNull @PathVariable String personId, @PathVariable long accountId) {
        AccountBalanceDTO accountBalanceDTO;
        try {
            accountBalanceDTO = transactionService.getAccountBalance(accountId);

            Link selfLink =
                    linkTo(AccountController.class).slash(ACCOUNTS).slash(personId).slash(accountId).withRel(ACCOUNT_INFORMATION);
            accountBalanceDTO.add(selfLink);
            return new ResponseEntity<>(accountBalanceDTO, HttpStatus.OK);
        } catch (ObjectDoesNotExistException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}