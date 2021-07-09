import React, {useContext, useEffect, useState} from "react";
import {FormControl, InputLabel, MenuItem, Select} from "@material-ui/core";
import {getRelationshipTypes} from "../../context/RelationshipService";
import AppContext from "../../context/AppContext";


function RelationshipTypesDropdown({submitTypeToParent}) {
    const {state} = useContext(AppContext);
    const {auth} = state;
    const token = auth.token;

    const inputRelationshipType = React.useRef("");

    const submitValue = e => {
        e.preventDefault()
        const type = e.target.value;
        submitTypeToParent(type);
    }
    const dropdown = {
        top: 20,
        width: '35ch',
    }

    const initialRelationshipTypes = {relationshipTypesList: []}
    const [relationshipTypes, setRelationshipTypes] = useState(initialRelationshipTypes);

    useEffect(() => {
        getRelationshipTypes(token).then(response => {
            console.log(response.data)
            if (response.data !== null && response.data !== undefined) {
                setRelationshipTypes(response.data);
            }
        })
    }, []);
    console.log(relationshipTypes);

    return (
        <div align='center'>
                <FormControl style={dropdown}>
                    <Select onClick={submitValue} defaultValue=""
                            inputRef={inputRelationshipType} required>
                        {relationshipTypes.relationshipTypesList.map((relationshipType) => (
                            <MenuItem
                                value={relationshipType.numericValue} key={relationshipType.relationshipType}>{relationshipType.relationshipType}
                            </MenuItem>
                        ))}
                    </Select>
                </FormControl>
        </div>
    )
}

export default RelationshipTypesDropdown;
