import React, {useCallback, useState} from "react";
import "./ChangeRelationship.styles.css";
import {makeStyles} from '@material-ui/core/styles';
import RelationshipTypesDropdown from "../create-relationship/RelationshipTypesDropdown";

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
    const inputRelationshipType = React.useRef("");
    const [type, setType] = useState("");

    const classes = useStyles();
    const margin = {
        paddingTop: '50px',
        paddingBottom: '20px',
    }

    let submitHandler = e => {
        e.preventDefault()
        const data = {
            relationshipType: type
        }
        console.log(data)
        parentSubmitHandler(data);
    }
    const submittedType = useCallback((type) => {
        setType(type);
    }, []);

    return (
        <div align='center' style={margin}>
            <h1>Select the new relationship type</h1>
            <form onSubmit={submitHandler} className={classes.root}>
                <div>
                    <div>
                        <RelationshipTypesDropdown submitTypeToParent={submittedType}/>
                    </div>
                </div>
                <br/>
                <br/>
                <button type="submit" id="button">Submit change</button>
            </form>
        </div>
    )
}

export default CreateRelationshipForm;
