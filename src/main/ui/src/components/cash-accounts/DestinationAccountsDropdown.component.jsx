import React, {useContext, useEffect, useState} from "react";
import {makeStyles} from '@material-ui/core/styles';
import {useHistory} from "react-router-dom";
import {FormControl, InputLabel, MenuItem, Select} from "@material-ui/core";
import AppContext from "../../context/AppContext";
import {getCashAccounts} from "../../context/CashAccountsService";


const useStyles = makeStyles((theme) => ({
    root: {
        width: '100%',
        maxWidth: 360,
        backgroundColor: theme.palette.background.paper,
    },
}));

const dropdown = {
    top: 5,
    width: '50ch',
}

function DestinationAccountsDropdownComponent({submitDestinationAccountIdToParent}) {
    const classes = useStyles();
    const inputDestinationAccountId = React.useRef("");

    const {state} = useContext(AppContext);
    //não é o userId que queremos, queremos o user da conta de destino, selecionado previamente
    const {userId}={state};

    let submitValue = (event) => {
        event.preventDefault();
        const destinationAccountId = event.target.value;
        console.log(event.target.value)

        submitDestinationAccountIdToParent(destinationAccountId);
    }

    const defaultAccounts = {cashAccounts: []}
    const [accounts, setAccounts] = useState(defaultAccounts);

    useEffect(() => {
        getCashAccounts(userId).then(response => {
            console.log(response)
            if (response !== null && response !== undefined &&
                response.data !== null && response.data !== undefined) {
                setAccounts(response.data);
            }
        })
    }, []);


    if (accounts.cashAccounts.length === 0) {
        console.log(accounts.cashAccounts);
        return (
            <div className={classes.root}>
                <FormControl style={dropdown}>
                    <MenuItem>This family member does not have cash accounts.</MenuItem>
                </FormControl>
            </div>
        )
    }

    return (
        <div className={classes.root}>
            <FormControl style={dropdown}>
                <InputLabel>Destination Cash Account:</InputLabel>
                <Select onClick={submitValue} defaultValue="" inputRef={inputDestinationAccountId} required>
                    {accounts.cashAccounts.map((account) => (
                        <MenuItem value={account.accountId}>{account.accountDescription}</MenuItem>
                    ))}
                </Select>
            </FormControl>
        </div>
    );
}

export default DestinationAccountsDropdownComponent;