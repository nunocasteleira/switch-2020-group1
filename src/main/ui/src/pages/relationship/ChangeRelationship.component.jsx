import React, {useCallback, useContext, useEffect, useState} from "react";
import AppContext from "../../context/AppContext";
import {changeRelationship} from "../../context/RelationshipService";
import ChangeRelationshipForm from "../../components/change-relationship/ChangeRelationshipForm.component";
import WarningIcon from '@material-ui/icons/Warning';
import CheckIcon from '@material-ui/icons/Check';
import Button from "@material-ui/core/Button";
import {useHistory} from 'react-router-dom'

function ChangeRelationship() {
    const {state} = useContext(AppContext);
    const {familyId} = state;
    const {auth} = state;
    const token = auth.token;
    const url = window.location.pathname;
    const relationshipId = url.substring(url.lastIndexOf('/') + 1);

    const history = useHistory();
    const errorStyle = {
        color: 'red',
        align: 'center',
        fontSize: '30',
        marginBottom: '-10'
    }
    const successStyle = {
        color: 'darkgreen',
    }

    const [newData, setNewData] = useState("");
    const [message, setMessage] = useState("");
    const [displayMessage, setDisplayMessage] = useState(false)
    const [error, setError] = useState("");
    const [displayError, setDisplayError] = useState(false)

    const [returnLink, setReturnLink] = useState(null)
    const [displayReturnButton, setDisplayReturnButton] = useState(false)

    const handleSubmit = useCallback((newData) => {
        setNewData(newData);
    }, []);


    useEffect(() => {
        if (newData !== "") {
            changeRelationship(newData, familyId, relationshipId, token)
                .then(response => {
                    setMessage("The relationship was successfully edited.");
                    setDisplayMessage(true);
                    setReturnLink(response.data._links.self.href.replace("http://localhost:8080", ""))
                    setDisplayReturnButton(true)
                })
                .catch(error => {
                    if (error.response !== null && error.response !== undefined &&
                        error.response.data !== null && error.response.data !== undefined) {
                        setError(error.response.data);
                    }
                    setDisplayError(true);
                })
        }
    });

    if (displayMessage) {
        return (
            <div align='center'>
                <h1>{message}
                </h1>
                <p style={successStyle}>
                    <CheckIcon></CheckIcon>
                </p>
                {displayReturnButton && (
                    <Button
                        variant="outlined"
                        color="primary"
                        onClick={() => {
                            history.push(returnLink);
                        }}>
                        Return
                    </Button>
                )}
            </div>
        )
    } else if (displayError) {
        return (
            <div align='center'>
                <WarningIcon style={errorStyle}></WarningIcon>
                <p style={errorStyle}>
                    {error}
                </p>
            </div>
        )
    } else {
        return (
            <div align='center'>
                <ChangeRelationshipForm parentSubmitHandler={handleSubmit} relationshipId={relationshipId}/>
            </div>
        )
    }
}

export default ChangeRelationship;