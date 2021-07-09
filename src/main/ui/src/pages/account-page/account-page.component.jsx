import React, { useContext, useEffect, useState, useRef } from "react";
import { useParams } from "react-router-dom";
import AppContext from "../../context/AppContext";
import { makeRequest } from "../../context/UnifiedService";

import CircularProgress from "@material-ui/core/CircularProgress";
import Typography from "@material-ui/core/Typography";

import AccountInfo from "../../components/account-info/account-info.component";

const AccountPage = () => {
  const { id } = useParams();
  const { state } = useContext(AppContext);
  const { userId, auth } = state;
  const [loadingAccounts, setLoadingAccounts] = useState(true);
  const [loadingBalance, setLoadingBalance] = useState(true);
  const [loadingTransactions, setLoadingTransactions] = useState(true);
  const [accounts, setAccounts] = useState();
  const [errorText, setErrorText] = useState("");
  const [accountName, setAccountName] = useState("");
  const [accountCurrency, setAccountCurrency] = useState("");
  const [accountBalance, setAccountBalance] = useState();
  const [accountTransactions, setAccountTransactions] = useState();
  const todayDate = new Date();
  const theLastMonth = todayDate.getMonth() - 1;
  const aMonthAgo = new Date().setMonth(theLastMonth);
  const [startDate, setStartDate] = useState(new Date(aMonthAgo));
  const [endDate, setEndDate] = useState(new Date(todayDate));
  const firstRender = useRef(true);

  const getAccountsSuccess = (accounts) => {
    setLoadingAccounts(false);
    setAccounts(accounts);
  };

  const getAccountsFailure = (error) => {
    setLoadingAccounts(false);
    setErrorText(errorText + error);
  };

  const getAccountBalanceSuccess = (balance) => {
    setLoadingBalance(false);
    const { accountBalance } = balance;
    const { amount } = accountBalance;
    setAccountBalance(amount);
  };

  const getAccountBalanceFailure = (error) => {
    setLoadingBalance(false);
    setErrorText(errorText + error);
  };

  const getAccountTransactionsSuccess = (transactions) => {
    setLoadingTransactions(false);
    const { transactionList } = transactions;
    setAccountTransactions(transactionList);
  };

  const getAccountTransactionsFailure = (error) => {
    setLoadingTransactions(false);
    setErrorText(errorText + error);
  };

  useEffect(() => {
    const request = {
      uri: `/accounts/${userId}`,
      request: {
        method: "get",
        headers: new Headers({
          Authorization: "Bearer " + auth.token,
        }),
      },
    };

    makeRequest(
      request,
      (result) => getAccountsSuccess(result),
      (error) => getAccountsFailure(error)
    );
  }, []);

  useEffect(() => {
    const request = {
      uri: `/accounts/${userId}/${id}`,
      request: {
        method: "get",
        headers: new Headers({
          Authorization: "Bearer " + auth.token,
        }),
      },
    };

    makeRequest(
      request,
      (result) => getAccountBalanceSuccess(result),
      (error) => getAccountBalanceFailure(error)
    );
  }, []);

  useEffect(() => {
    if (firstRender.current) {
      firstRender.current = false; // it's no longer the first render
      return; // skip the code below
    }
    Object.values(accounts).forEach((accountType) => {
      if (Array.isArray(accountType)) {
        accountType.map((account) => {
          const { accountId, accountDescription, initialAmountValue } = account;
          const { accountIdNumber } = accountId;
          const description = accountDescription.accountDescription;
          const { currency } = initialAmountValue;
          if (accountIdNumber === Number(id)) {
            setAccountName(description);
            setAccountCurrency(currency);
          }
        });
      }
    });
  }, [accounts]);

  useEffect(() => {
    const handleDateChange = async () => {
      const startdd = String(startDate.getDate()).padStart(2, "0");
      const startmm = String(startDate.getMonth() + 1).padStart(2, "0"); //January is 0!
      const startyyyy = startDate.getFullYear();
      const enddd = String(endDate.getDate()).padStart(2, "0");
      const endmm = String(endDate.getMonth() + 1).padStart(2, "0"); //January is 0!
      const endyyyy = endDate.getFullYear();
      const newStartDate = startdd + "/" + startmm + "/" + startyyyy;
      const newEndDate = enddd + "/" + endmm + "/" + endyyyy;
      const request = {
        uri: `/transactions/${id}?startDate=${newStartDate}&endDate=${newEndDate}`,
        request: {
          method: "get",
          headers: new Headers({
            Authorization: "Bearer " + auth.token,
          }),
        },
      };
      await makeRequest(
        request,
        (result) => getAccountTransactionsSuccess(result),
        (error) => getAccountTransactionsFailure(error)
      );
    };
    handleDateChange();
  }, [startDate, endDate]);

  const handleStartDateChange = (date) => {
    setStartDate(date);
  };
  const handleEndDateChange = (date) => {
    setEndDate(date);
  };

  if (loadingAccounts || loadingBalance || loadingTransactions) {
    return <CircularProgress />;
  } else {
    if (errorText.length !== 0) {
      return (
        <Typography variant="body2" component="p">
          {errorText}
        </Typography>
      );
    } else {
      return (
        <AccountInfo
          accountName={accountName}
          accountBalance={accountBalance}
          accountCurrency={accountCurrency}
          startDateChange={handleStartDateChange}
          endDateChange={handleEndDateChange}
          startDate={startDate}
          endDate={endDate}
          accountTransactions={accountTransactions}
        />
      );
    }
  }
};

export default AccountPage;
