import React, {useContext, useEffect, useState} from "react";
import {makeStyles} from '@material-ui/core/styles';
import {FormControl, InputLabel, MenuItem, Select} from "@material-ui/core";
import AppContext from "../../context/AppContext";
import {getMembers} from "../../context/CashAccountsService";

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

function MembersDropdown({submitMemberIdToParent}) {
    const classes = useStyles();
    const inputMemberId = React.useRef("");

    const {state} = useContext(AppContext);
    const {userId} = state;
    const {auth} = state;
    const token = auth.token;

    let submitValue = (event) => {
        event.preventDefault();
        const memberId = event.target.value;
        console.log(event.target.value)

        submitMemberIdToParent(memberId);

    }

    const defaultMembers = {familyMembers: []}
    const [members, setMembers] = useState(defaultMembers);

    useEffect(() => {
        getMembers(userId, token).then(response => {
            console.log(response)
            if (response !== null && response !== undefined &&
                response.data !== null && response.data !== undefined) {
                setMembers(response.data);
            }
        })
    }, []);


    if (members.familyMembers.length === 0) {
        console.log(members.familyMembers);
        return (
            <div className={classes.root}>
                <FormControl style={dropdown}>
                    <MenuItem>There are no family members..</MenuItem>
                </FormControl>
            </div>
        )
    }


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
        </div>
    );
}

export default MembersDropdown;