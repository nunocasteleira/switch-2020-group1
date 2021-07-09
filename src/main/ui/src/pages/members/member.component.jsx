import React, {useCallback, useContext, useEffect, useState} from 'react';

import {createFamilyMember} from "../../context/FamilyService";
import Popup from "../../components/Pop-up/pop-up";
import AddMemberFormulary from "../../components/add-member/add-member.component";
import AppContext from "../../context/AppContext";

export default function AddFamilyMember() {

    const { state } = useContext(AppContext);
    const { familyId } = state;
    const {auth} = state;
    const token = auth.token;

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
        if (childData !== null) {
            createFamilyMember(childData, familyId, token).then(response => {
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
                    <b>The Family Member was created successfully!</b>
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
        return (
            <AddMemberFormulary parentCallBack={handleCallBack}/>
        )
    }
}
