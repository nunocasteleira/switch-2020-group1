import React, {useContext, useEffect, useState} from "react";
import AppContext from "../../context/AppContext";
import CircularProgress from "@material-ui/core/CircularProgress";
import {getCashAccounts} from "../../context/CashAccountsService";
import GetCashAccountsList from "../../components/cash-accounts/GetCashAccountsList.component";

function GetCashAccounts() {
    const {state} = useContext(AppContext);
    const {userId} = state;

    const [loading, setLoadingState] = useState(true);

    const [displayErrorMessage, setDisplayErrorMessage] = useState(false);
    const [errorMessage, setErrorMessage] = useState("");

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
            .catch(error => {
                if (error.response !== null && error.response !== undefined &&
                    error.response.data !== null && error.response.data !== undefined) {
                    setErrorMessage(error.response.data);
                }
                setDisplayErrorMessage(true);
            })
            .finally(function () {
                setLoadingState(false);
            });
    }, []);

    if (loading === true) {
        return <CircularProgress/>
    } else if (displayErrorMessage) {
        return (
            <div align='center'>
                <h1>Oops! Something went wrong</h1>
                <p>{errorMessage}</p>
            </div>
        )
    } else {
        return <GetCashAccountsList accounts={accounts}/>
    }
}
export default GetCashAccounts;