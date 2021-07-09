import React, {useCallback, useContext, useEffect, useState} from "react";
import CreateRelationshipForm from '../../components/create-relationship/CreateRelationshipForm.component'
import AppContext from "../../context/AppContext";
import {createRelationship, getRelationshipTypes} from "../../context/RelationshipService";
import WarningIcon from '@material-ui/icons/Warning';
import CheckIcon from '@material-ui/icons/Check';
import Button from "@material-ui/core/Button";
import {useHistory} from 'react-router-dom'

function CreateRelationship() {
    const {state} = useContext(AppContext);
    const {familyId} = state;
    const {auth} = state;
    const token = auth.token;

    const errorStyle = {
        color: 'red',
        align: 'center',
        fontSize: '30',
        marginBottom: '-10'
    }
    const successStyle = {
        color: 'darkgreen',
    }
    const history = useHistory();

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
            createRelationship(newData, familyId, token)
                .then(response => {
                    console.log(response)
                    setMessage("The relationship was successfully created.");
                    setDisplayMessage(true);
                    setReturnLink(response.data._links.self.href.replace("http://localhost:8080", ""))
                    setDisplayReturnButton(true)
                })
                .catch(error => {
                    console.log(error.response.data)
                    if (error.response !== null && error.response !== undefined &&
                        error.response.data !== null && error.response.data !== undefined) {
                        setError(error.response.data);
                    }
                    setDisplayError(true);
                })
        }
    },[newData]);

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
                <CreateRelationshipForm parentSubmitHandler={handleSubmit}/>
                <WarningIcon style={errorStyle}></WarningIcon>
                <p style={errorStyle}>
                    {error}
                </p>
            </div>
        )
    } else {
        return (
            <div align='center'>
                <CreateRelationshipForm parentSubmitHandler={handleSubmit}/>
            </div>
        )
    }
}
export default CreateRelationship;