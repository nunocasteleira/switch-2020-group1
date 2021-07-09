import React, {useContext, useEffect, useState} from "react";
import {makeStyles} from '@material-ui/core/styles';
import {FormControl, InputLabel, MenuItem, Select} from "@material-ui/core";
import {getCashAccounts} from "../../context/CashAccountsService";
import AppContext from "../../context/AppContext";


const useStyles = makeStyles((theme) => ({
    root: {
        width: '100%',
        maxWidth: 360,
    },
}));

function OriginAccountsDropdownComponent({submitOriginAccountIdToParent}) {
    const inputAccountId = React.useRef("");

    const {state} = useContext(AppContext);
    const {userId} = state;

    const classes = useStyles();
    const dropdown = {
        width: '50ch',
    }

    let submitValue = (event) => {
        event.preventDefault();
        const originAccountId = event.target.value;
        console.log(event.target.value)

        submitOriginAccountIdToParent(originAccountId);

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


    return (
        <div className={classes.root}>
            <FormControl style={dropdown}>
                <InputLabel>Origin Cash Account:</InputLabel>
                <Select onClick={submitValue} defaultValue="" inputRef={inputAccountId} required>
                    {accounts.cashAccounts.map((account) => (
                        <MenuItem value={account.accountId}>{account.accountDescription}</MenuItem>
                    ))}
                </Select>
            </FormControl>
        </div>
    );
}

export default OriginAccountsDropdownComponent;