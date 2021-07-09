import React from "react";
import {useHistory} from "react-router-dom";
import GetCashAccounts from "../cash-accounts/GetCashAccounts.component";

function AccountsMain() {

    const history = useHistory();

        let handleClick = () => {
            history.push('/accounts/create-personal-cash');
        }
        let handleClick1 = () => {
        history.push('/accounts/create-bank-account');
        }
            return (
                <div align='center'>
                    <h1>Accounts</h1>
                    <br/>
                    <GetCashAccounts/>
                    <br/>
                    <button onClick={handleClick} id="button"> Create Personal Cash Account</button>
                    <button onClick={handleClick1} id="button"> Create Bank Account</button>
                </div>
            )
    }
export default AccountsMain;