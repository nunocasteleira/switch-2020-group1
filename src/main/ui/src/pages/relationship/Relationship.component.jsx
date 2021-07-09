import React, {useContext, useEffect, useState} from 'react';

import AppContext from "../../context/AppContext";
import RelationshipsTable from '../../components/relationship-table/RelationshipTable.component'
import {retrieveRelationships} from '../../context/RelationshipService';
import CircularProgress from "@material-ui/core/CircularProgress";

export default function RelationshipsInformation() {

    const { state } = useContext(AppContext);
    const { familyId } = state;
    const { auth } = state;
    const token = auth.token;
    const [loading, setLoadingState] = useState(true);

    const [displayErrorMessage, setDisplayErrorMessage] = useState(false);
    const [errorMessage, setErrorMessage] = useState("");

    const defaultRelationships = {relationshipList: []}
    const [relationships, setRelationships] = useState(defaultRelationships);

    useEffect(() => {
        retrieveRelationships(familyId, token).then(response => {
            console.log(response)
            if(response !== null && response !== undefined &&
                response.data !== null && response.data !== undefined) {
                setRelationships(response.data);
            }
        })
        .catch(error => {
            if (error.response !== null && error.response !== undefined &&
                error.response.data !== null && error.response.data !== undefined) {
                setErrorMessage(error.response.data);
            }
            setDisplayErrorMessage(true);
        })
        .finally(function() {
            setLoadingState(false);
        });
    }, []);

    if (loading === true) {
        return <CircularProgress/>
    } else if(displayErrorMessage) {
        return (
            <div align='center'>
                <h1>Oops! Something went wrong</h1>
                <p>{errorMessage}</p>
            </div>
        )
    }
    else {
        return <RelationshipsTable relationships={relationships}/>
    }
}
