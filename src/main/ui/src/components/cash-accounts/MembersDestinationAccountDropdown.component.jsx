import React, {useContext, useEffect, useState} from "react";
import {makeStyles} from '@material-ui/core/styles';
import {FormControl, InputLabel, MenuItem, Select} from "@material-ui/core";
import AppContext from "../../context/AppContext";
import {getMembers, getCashAccounts} from "../../context/CashAccountsService";

const useStyles = makeStyles((theme) => ({
    root: {
            width: '40ch',
            align: 'center'
        },
}));

function MembersDestinationAccountDropdown({submitDestinationAccountIdToParent}) {
    const {state} = useContext(AppContext);
    const {auth, userId} = state;
    const token = auth.token;

    const inputMemberId = React.useRef("");
    const inputDestinationAccountId = React.useRef("");
    const [newData, setNewData] = useState("");

    const classes = useStyles();
    const dropdown = {
        width: '50ch',
    }

    let submitValue = (event) => {
        event.preventDefault();
        const memberId = event.target.value;
        console.log(event.target.value)
        setNewData(memberId);
    }

    let submitValueMemberId = (event) => {
        event.preventDefault();
        const destinationAccountId = event.target.value;
        console.log(event.target.value)

        submitDestinationAccountIdToParent(destinationAccountId);
    }

    const defaultMembers = {familyMembers: []}
    const [members, setMembers] = useState(defaultMembers);

    useEffect(() => {
        getMembers(userId, token).then(response => {
            if (response !== null && response !== undefined &&
                response.data !== null && response.data !== undefined) {
                setMembers(response.data);
                console.log(response.data)
            }
        })
    }, []);

    const defaultAccounts = {cashAccounts: []}
    const [accounts, setAccounts] = useState(defaultAccounts);

    useEffect(() => {
        getCashAccounts(newData).then(response => {
            console.log(response)
            if (response !== null && response !== undefined &&
                response.data !== null && response.data !== undefined) {
                setAccounts(response.data);
                console.log(response.data)
            }
        })
    }, [newData]);

    return (
        <div className={classes.root}>
            <FormControl style={dropdown}>
                <InputLabel>Transfer to: </InputLabel>
                <Select onClick={submitValue} defaultValue="" inputRef={inputMemberId} required>
                    {members.familyMembers.map((member) => (
                        <MenuItem value={member.email}>{member.name}</MenuItem>
                    ))}
                </Select>
            </FormControl>
        <FormControl style={dropdown}>
            <InputLabel>Destination Cash Account:</InputLabel>
            <Select onClick={submitValueMemberId} defaultValue="" inputRef={inputDestinationAccountId} required>
                {accounts.cashAccounts.map((account) => (
                    <MenuItem value={account.accountId}>{account.accountDescription}</MenuItem>
                ))}
            </Select>
        </FormControl>
        </div>
    );
}

export default MembersDestinationAccountDropdown;