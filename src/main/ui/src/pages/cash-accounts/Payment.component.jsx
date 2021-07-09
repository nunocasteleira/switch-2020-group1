import React, {useCallback, useContext, useEffect, useState} from "react";
import AppContext from "../../context/AppContext";
import WarningIcon from '@material-ui/icons/Warning';
import CheckIcon from '@material-ui/icons/Check';
import PaymentForm from "../../components/cash-accounts/PaymentForm.component";
import {registerPayment} from "../../context/CashAccountsService";

function Payment() {
    const {state} = useContext(AppContext);
    const {originAccountId} = state;

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

    const handleSubmit = useCallback((newData) => {
        setNewData(newData);
    }, []);

    useEffect(() => {
        if (newData !== "") {
            registerPayment(newData, newData.originAccountId)
                .then(response => {
                    console.log(response)
                    setMessage("The payment was successfully registered.");
                    setDisplayMessage(true);
                })
                .catch(error => {
                    if (error.response !== null && error.response !== undefined &&
                        error.response.data !== null && error.response.data !== undefined) {
                        setError(error.response.data);
                    }
                    setDisplayError(true);
                })
        }
    }, [newData]);
    if (displayMessage) {
        return (
            <div align='center'>
                <h1>{message}
                </h1>
                <p style={successStyle}>
                    <CheckIcon></CheckIcon>
                </p>
            </div>
        )
    } else if (displayError) {
        return (
            <div align='center'>
                <PaymentForm parentSubmitHandler={handleSubmit}/>
                <WarningIcon style={errorStyle}></WarningIcon>
                <p style={errorStyle}>
                    {error}
                </p>
            </div>
        )
    } else {
        return (
            <div align='center'>
                <PaymentForm parentSubmitHandler={handleSubmit}/>
            </div>
        )
    }
}

export default Payment;