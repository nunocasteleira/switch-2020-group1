import React, {useContext, useEffect, useState} from "react";
import AppContext from "../../context/AppContext";
import CircularProgress from "@material-ui/core/CircularProgress";
import {getCategoriesList, getFamilyCategories} from "../../context/CashAccountsService";
import CategoriesDropdown from "../../components/cash-accounts/CategoriesDropdown.component";

function GetCategories() {
    const {state} = useContext(AppContext);
    const {familyId} = state;

    const [loading, setLoadingState] = useState(true);

    const [displayErrorMessage, setDisplayErrorMessage] = useState(false);
    const [errorMessage, setErrorMessage] = useState("");

    const defaultFamilyCategories = {categoryDTOs: []}
    const [familyCategories, setFamilyCategories] = useState(defaultFamilyCategories);

    useEffect(() => {
        getCategoriesList().then(response => {
            console.log(response)
            if (response !== null && response !== undefined &&
                response.data !== null && response.data !== undefined) {
                setFamilyCategories(response.data);
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
        return <CategoriesDropdown familyCategories={familyCategories}/>
    }
}

export default GetCategories;