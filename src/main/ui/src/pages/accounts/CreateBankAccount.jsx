import React, {useCallback, useContext, useEffect, useState} from "react";
import AppContext from "../../context/AppContext";
import {createBankAccount} from "../../context/AccountsService";
import WarningIcon from '@material-ui/icons/Warning';
import CheckIcon from '@material-ui/icons/Check';
import CreateBankAccountForm
    from "../../components/create-bank-account/CreateBankAccountForm.component";
import Button from "@material-ui/core/Button";
import {useHistory} from 'react-router-dom'

function CreateBankAccount() {
    const {state} = useContext(AppContext);
    const {userId} = state;

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

    const history = useHistory();

    const handleSubmit = useCallback((newData) => {
        setNewData(newData);
    }, []);

    useEffect(() => {
        if (newData !== "") {
            createBankAccount(newData, userId)
                .then(response => {
                    console.log(response)
                    setMessage("The account was successfully created.");
                    setDisplayMessage(true);
                    setReturnLink(response.data._links.accountInformation.href.replace("http://localhost:8080", ""))
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
                <CreateBankAccountForm parentCallBack1={handleSubmit}/>
                <WarningIcon style={errorStyle}></WarningIcon>
                <p style={errorStyle}>
                    {error}
                </p>
            </div>
        )
    } else {
        return (
            <div align='center'>
                <CreateBankAccountForm parentCallBack1={handleSubmit}/>
            </div>
        )
    }
}
export default CreateBankAccount;