import React, {useContext, useEffect, useState} from "react";
import {makeStyles} from '@material-ui/core/styles';
import {FormControl, MenuItem, Select} from "@material-ui/core";
import AppContext from "../../context/AppContext";
import {getFamilyMembers} from "../../context/FamilyService";

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

function FamilyMembersDropdown({submitMemberIdToParent}) {
    const classes = useStyles();
    const inputMemberId = React.useRef("");

    const {state} = useContext(AppContext);
    const {familyId} = state;
    const {auth} = state;
    const token = auth.token;

    const dropdown = {
        top: 20,
        width: '35ch',
    }

    let submitValue = e => {
        e.preventDefault();
        const memberId = e.target.value;
        console.log(e.target.value)
        submitMemberIdToParent(memberId);
    }

    const defaultMembers = {familyMembers: []}
    const [members, setMembers] = useState(defaultMembers);

    useEffect(() => {
        getFamilyMembers(familyId, token).then(response => {
            console.log(response)
            if (response !== null && response !== undefined &&
                response.data !== null && response.data !== undefined) {
                setMembers(response.data);
            }
        })
    }, []);
    console.log(members)

    return (
        <div align='center'>
            <FormControl style={dropdown}>
                <Select onChange={submitValue} defaultValue="" inputRef={inputMemberId} required>
                    {members.familyMembers.map((member) => (
                        <MenuItem value={member.email} key={member.email}>{member.name}</MenuItem>
                    ))}
                </Select>
            </FormControl>
        </div>
    );
}

export default FamilyMembersDropdown;