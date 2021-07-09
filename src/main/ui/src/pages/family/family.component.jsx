import React, {useCallback, useContext, useEffect, useState} from 'react';

import AddFamilyFormulary from './../../components/add-family/add-family.component'
import {createFamilyAndAdministrator} from "../../context/FamilyService";
import Popup from "../../components/Pop-up/pop-up";
import AppContext from "../../context/AppContext";

export default function AddFamilyAndAdministrator() {

    const {state} = useContext(AppContext);
    const {auth} = state;
    const token = auth.token;
    const role = auth.role[0];

    const [childData, setChildData] = useState(null);

    const [displayErrorMessage, setDisplayErrorMessage] = useState(false);
    const [errorMessage, setErrorMessage] = useState("");

    const [isOK, setIsOk] = useState(false);

    const [isOpen, setIsOpen] = useState(false);
    const togglePopup = () => {
        setIsOpen(!isOpen);
    }

    const handleCallBack = useCallback((childData) => {
        setChildData(childData);
    }, []);

    useEffect(() => {
        console.log(auth)
        if (childData !== null) {
            createFamilyAndAdministrator(childData, token).then(response => {
                console.log(response)

                if (response.status === 201) {
                    setIsOk(true);
                    {
                        togglePopup()
                    }
                }
            })
                .catch(error => {
                    if (error.response !== null && error.response !== undefined &&
                        error.response.data !== null && error.response.data !== undefined) {
                        setErrorMessage(error.response.data);
                    }
                    setDisplayErrorMessage(true);
                    {
                        togglePopup()
                    }
                });
        }
    }, [childData]);

    if (isOK === true) {
        return (<div align='center'>
            {isOpen && <Popup
                content={<>
                    <b>Family and Administrator Created Successfully!</b>
                    <b> </b>
                    <button onClick={() => {
                        togglePopup();
                        setIsOk(false);
                    }}>Close
                    </button>
                </>}

            />}
        </div>)

    } else if (displayErrorMessage === true) {
        return (<div align='center'>
            {isOpen && <Popup
                content={<>
                    <b>Oops something went wrong!</b>
                    <p>{errorMessage}</p>
                    <button onClick={() => {
                        togglePopup();
                        setDisplayErrorMessage(false);
                    }}>Close
                    </button>
                </>}
            />}
        </div>)
    } else {
        if (role === "ROLE_SYSTEM_MANAGER"){
        return (
            <AddFamilyFormulary parentCallback={handleCallBack}/>
        ) } else {
            return(
            <b>Oops you're not the manager</b>
            )
        }
    }
}
