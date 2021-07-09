import React, {useCallback, useState} from "react";
import "./CreateRelationship.styles.css";
import {makeStyles} from '@material-ui/core/styles';
import RelationshipTypesDropdown from "./RelationshipTypesDropdown";
import FamilyMembersDropdown from "./FamilyMembersDropdown.component";
import {InputLabel} from "@material-ui/core";

const useStyles = makeStyles((theme) => ({
    root: {
        '& .MuiTextField-root': {
            top: 5,
            margin: theme.spacing(1),
            width: '35ch',
            fontSize: 20,
            align: 'center'
        },
    },
}));

function CreateRelationshipForm({parentSubmitHandler}) {
    const [type, setType] = useState("");
    const [mainId, setMainId] = useState("");
    const [otherId, setOtherId] = useState("");

    const classes = useStyles();
    const margin = {
        paddingTop: '50px',
        paddingBottom: '20px',
    }
    console.log(mainId)

    let submitHandler = e => {
        e.preventDefault()
        const data = {
            mainUserId: mainId,
            otherUserId: otherId,
            relationshipType: type,
        }
        console.log(data)
        parentSubmitHandler(data);
    }

    const submittedType = useCallback((type) => {
        setType(type);
    }, []);

    const submittedMainUserId = useCallback((mainId) => {
        setMainId(mainId);
    }, []);

    const submittedOtherUserId = useCallback((otherId) => {
        setOtherId(otherId);
    }, []);

    return (
        <div align='center' style={margin}>
            <h1>Create a new relationship</h1>
            <br/>
            <form onSubmit={submitHandler} className={classes.root}>
                <div>
                    <InputLabel>Main User Email:</InputLabel>
                    <FamilyMembersDropdown submitMemberIdToParent={submittedMainUserId}/>
                </div>
                <br/>
                <br/>
                <div>
                    <InputLabel>Other User Email:</InputLabel>
                    <FamilyMembersDropdown submitMemberIdToParent={submittedOtherUserId}/>
                </div>
                <br/>
                <br/>
                <br/>
                <div>
                    <InputLabel>RelationshipTypes:</InputLabel>
                    <RelationshipTypesDropdown submitTypeToParent={submittedType}/>
                </div>
                <br/>
                <br/>
                <br/>
                <button type="submit" id="button">Create</button>
            </form>
        </div>
    )
}

export default CreateRelationshipForm;
